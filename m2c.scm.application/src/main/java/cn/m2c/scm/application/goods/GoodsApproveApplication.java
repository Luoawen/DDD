package cn.m2c.scm.application.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.goods.command.GoodsApproveCommand;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectBatchCommand;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.GoodsApprove;
import cn.m2c.scm.domain.model.goods.GoodsApproveRepository;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import cn.m2c.scm.domain.model.goods.GoodsSkuRepository;
import cn.m2c.scm.domain.model.shop.Shop;
import cn.m2c.scm.domain.model.shop.ShopRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品审核
 */
@Service
@Transactional
public class GoodsApproveApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsApproveApplication.class);

    @Autowired
    GoodsApproveRepository goodsApproveRepository;
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    GoodsSkuRepository goodsSkuRepository;

    /**
     * 商家添加商品需审核
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsApprove(GoodsApproveCommand command) throws NegativeException {
        LOGGER.info("addGoodsApprove command >>{}", command);
        Shop shop = shopRepository.getShop(command.getDealerId());
        if (null == shop) {
            throw new NegativeException(MCode.V_300, "店铺信息不存在，不能增加商品");
        }
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getGoodsId());
        if (null == goodsApprove) {
            if (goodsRepository.goodsNameIsRepeat(null, command.getDealerId(), command.getGoodsName()) ||
                    goodsApproveRepository.goodsNameIsRepeat(null, command.getDealerId(), command.getGoodsName())) {
                throw new NegativeException(MCode.V_300, "商品名称已存在");
            }
            if (null != command.getGoodsCodes() && command.getGoodsCodes().size() > 0) {
                if (goodsSkuRepository.goodsCodeIsRepeat(command.getDealerId(), command.getGoodsCodes())) {
                    throw new NegativeException(MCode.V_300, "商品编码已存在");
                }
            }
            goodsApprove = new GoodsApprove(command.getGoodsId(), command.getDealerId(), command.getDealerName(),
                    command.getGoodsName(), command.getGoodsSubTitle(), command.getGoodsClassifyId(),
                    command.getGoodsBrandId(), command.getGoodsBrandName(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), JsonUtils.toStr(command.getGoodsKeyWord()),
                    JsonUtils.toStr(command.getGoodsGuarantee()), JsonUtils.toStr(command.getGoodsMainImages()),
                    command.getGoodsDesc(), command.getGoodsShelves(), command.getGoodsSpecifications(), command.getGoodsSkuApproves(), command.getSkuFlag());
            goodsApproveRepository.save(goodsApprove);
        }
    }

    /**
     * 修改商品需审核信息，添加一条商品审核记录
     * 如果存在审核记录则覆盖
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsApproveForModifyGoods(GoodsApproveCommand command) throws NegativeException {
        LOGGER.info("addGoodsApproveForModifyGoods command >>{}", command);
        if (goodsRepository.goodsNameIsRepeat(command.getGoodsId(), command.getDealerId(), command.getGoodsName())
                || goodsApproveRepository.goodsNameIsRepeat(command.getGoodsId(), command.getDealerId(), command.getGoodsName())) {
            throw new NegativeException(MCode.V_300, "商品名称已存在");
        }
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getGoodsId());
        if (null == goodsApprove) {
            goodsApprove = new GoodsApprove(command.getGoodsId(), command.getDealerId(), command.getDealerName(),
                    command.getGoodsName(), command.getGoodsSubTitle(), command.getGoodsClassifyId(),
                    command.getGoodsBrandId(), command.getGoodsBrandName(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), JsonUtils.toStr(command.getGoodsKeyWord()),
                    JsonUtils.toStr(command.getGoodsGuarantee()), JsonUtils.toStr(command.getGoodsMainImages()),
                    command.getGoodsDesc(), null, command.getGoodsSpecifications(), command.getGoodsSkuApproves(),command.getSkuFlag());
        } else {
            goodsApprove.modifyGoodsApprove(command.getGoodsName(), command.getGoodsSubTitle(),
                    command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsBrandName(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), JsonUtils.toStr(command.getGoodsKeyWord()), JsonUtils.toStr(command.getGoodsGuarantee()),
                    JsonUtils.toStr(command.getGoodsMainImages()), command.getGoodsDesc(), command.getGoodsSpecifications(), command.getGoodsSkuApproves());
        }
        goodsApproveRepository.save(goodsApprove);
    }

    /**
     * 同意商品审核
     *
     * @param goodsId
     */
    @EventListener(isListening = true)
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void agreeGoodsApprove(String goodsId) throws NegativeException {
        LOGGER.info("agreeGoodsApprove goodsId >>{}", goodsId);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(goodsId);
        if (null == goodsApprove) {
            throw new NegativeException(MCode.V_300, "商品审核信息不存在");
        }
        goodsApprove.agree();
        goodsApproveRepository.remove(goodsApprove);
    }

    /**
     * 拒绝商品审核
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void rejectGoodsApprove(GoodsApproveRejectCommand command) throws NegativeException {
        LOGGER.info("rejectGoodsApprove command >>{}", command);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getGoodsId());
        if (null == goodsApprove) {
            throw new NegativeException(MCode.V_300, "商品审核信息不存在");
        }
        goodsApprove.reject(command.getRejectReason());
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsApprove(GoodsApproveCommand command) throws NegativeException {
        LOGGER.info("modifyGoodsApprove command >>{}", command);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getGoodsId());
        if (null == goodsApprove) {
            throw new NegativeException(MCode.V_300, "商品审核信息不存在");
        }
        goodsApprove.modifyGoodsApprove(command.getGoodsName(), command.getGoodsSubTitle(),
                command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsBrandName(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                command.getGoodsPostageId(), command.getGoodsBarCode(), JsonUtils.toStr(command.getGoodsKeyWord()), JsonUtils.toStr(command.getGoodsGuarantee()),
                JsonUtils.toStr(command.getGoodsMainImages()), command.getGoodsDesc(), command.getGoodsSpecifications(), command.getGoodsSkuApproves());
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void deleteGoodsApprove(String goodsId) throws NegativeException {
        LOGGER.info("deleteGoodsApprove goodsId >>{}", goodsId);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(goodsId);
        if (null == goodsApprove) {
            throw new NegativeException(MCode.V_300, "商品审核信息不存在");
        }
        goodsApprove.remove();
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void removeGoodsApprove(String goodsId) throws NegativeException {
        LOGGER.info("deleteGoodsApprove goodsId >>{}", goodsId);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(goodsId);
        if (null != goodsApprove) {
            goodsApprove.remove();
        }
    }

    /**
     * 修改商品品牌名称
     *
     * @param brandId
     * @param brandName
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsApproveBrandName(String brandId, String brandName) throws NegativeException {
        LOGGER.info("modifyGoodsApproveBrandName brandId >>{}", brandId);
        List<GoodsApprove> goodsList = goodsApproveRepository.queryGoodsApproveByBrandId(brandId);
        if (null != goodsList) {
            for (GoodsApprove goods : goodsList) {
                goods.modifyBrandName(brandName);
            }
        }
    }

    /**
     * 修改商品供应商名称
     *
     * @param dealerId
     * @param dealerName
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsApproveDealerName(String dealerId, String dealerName) throws NegativeException {
        LOGGER.info("modifyGoodsApproveDealerName dealerId >>{}", dealerId);
        List<GoodsApprove> goodsList = goodsApproveRepository.queryGoodsApproveByDealerId(dealerId);
        if (null != goodsList) {
            for (GoodsApprove goods : goodsList) {
                goods.modifyDealerName(dealerName);
            }
        }
    }

    /**
     * 批量同意商品审核
     * @param goodsIds
     * @throws NegativeException 
     */
    @EventListener(isListening = true)
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void agreeGoodsApproveBatch(List goodsIds) throws NegativeException {
    	LOGGER.info("agreeGoodsApproveBatch goodsIds >>{}", goodsIds);
    	List<GoodsApprove> goodsApproveList = goodsApproveRepository.queryGoodsApproveByIdList(goodsIds);
    	if(null != goodsApproveList && goodsApproveList.size()>0) {
    		for(GoodsApprove goodsApprove : goodsApproveList) {
    			goodsApprove.agree();
    	        goodsApproveRepository.remove(goodsApprove);
    		}
    	}else {
    		throw new NegativeException(MCode.V_300, "所选商品的审核信息不存在");
    	}
	}
    
    /**
     * 批量拒绝商品审核
     * @param command
     * @throws NegativeException 
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void rejectGoodsApproveBatch(GoodsApproveRejectBatchCommand command) throws NegativeException {
    	LOGGER.info("rejectGoodsApproveBatch command >>{}",command);
    	List<GoodsApprove> goodsApproveList = goodsApproveRepository.queryGoodsApproveByIdList(command.getGoodsIds());
    	if(goodsApproveList != null && goodsApproveList.size() > 0) {
    		for(GoodsApprove goodsApprove : goodsApproveList) {
    			goodsApprove.reject(command.getRejectReason());
    		}
    	}else {
    		throw new NegativeException(MCode.V_300, "所选商品的审核信息不存在");
    	}
	}

    /**
     * 修改审核中商品的商品保障(保障删除后需删除审核中商品的对应保障)
     * @param dealerId
     * @param guaranteeId
     */
	public void modifyGoodsApproveGuarantee(String dealerId, String guaranteeId) {
		LOGGER.info("modifyGoodsApproveGuarantee dealerId >>{}", dealerId);
		LOGGER.info("modifyGoodsApproveGuarantee dealerId >>{}", guaranteeId);
		List<GoodsApprove> goodsApproveList = goodsApproveRepository.queryGoodsByDealerIdAndGuaranteeId(dealerId, guaranteeId);
		if(null != goodsApproveList && goodsApproveList.size() > 0) {
			for(GoodsApprove goodsApprove : goodsApproveList) {
				goodsApprove.delGoodsApproveGuarantee(guaranteeId);
			}
		}
	}
}
