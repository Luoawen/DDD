package cn.m2c.scm.application.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.goods.command.GoodsApproveCommand;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.GoodsApprove;
import cn.m2c.scm.domain.model.goods.GoodsApproveRepository;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 商家添加商品需审核
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsApprove(GoodsApproveCommand command) throws NegativeException {
        LOGGER.info("addGoodsApprove command >>{}", command);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getGoodsId());
        if (null == goodsApprove) {
            if (goodsRepository.goodsNameIsRepeat(null, command.getDealerId(), command.getGoodsName())) {
                throw new NegativeException(MCode.V_300, "商品名称已存在");
            }
            goodsApprove = new GoodsApprove(command.getGoodsId(), command.getDealerId(), command.getDealerName(),
                    command.getGoodsName(), command.getGoodsSubTitle(), command.getGoodsClassifyId(),
                    command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(),
                    JsonUtils.toStr(command.getGoodsGuarantee()), JsonUtils.toStr(command.getGoodsMainImages()),
                    command.getGoodsDesc(), command.getGoodsShelves(), command.getGoodsSpecifications(), command.getGoodsSkuApproves());
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
        if (goodsRepository.goodsNameIsRepeat(command.getGoodsId(), command.getDealerId(), command.getGoodsName())) {
            throw new NegativeException(MCode.V_300, "商品名称已存在");
        }
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getGoodsId());
        if (null == goodsApprove) {
            goodsApprove = new GoodsApprove(command.getGoodsId(), command.getDealerId(), command.getDealerName(),
                    command.getGoodsName(), command.getGoodsSubTitle(), command.getGoodsClassifyId(),
                    command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(),
                    JsonUtils.toStr(command.getGoodsGuarantee()), JsonUtils.toStr(command.getGoodsMainImages()),
                    command.getGoodsDesc(), null, command.getGoodsSpecifications(), command.getGoodsSkuApproves());
        } else {
            goodsApprove.modifyGoodsApprove(command.getGoodsName(), command.getGoodsSubTitle(),
                    command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(), JsonUtils.toStr(command.getGoodsGuarantee()),
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
                command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(), JsonUtils.toStr(command.getGoodsGuarantee()),
                JsonUtils.toStr(command.getGoodsMainImages()), command.getGoodsDesc(), command.getGoodsSpecifications(), command.getGoodsSkuApproves());
    }
}
