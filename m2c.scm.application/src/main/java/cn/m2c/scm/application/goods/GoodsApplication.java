package cn.m2c.scm.application.goods;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.goods.command.GoodsCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.Goods;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品
 */
@Service
@Transactional
public class GoodsApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsApplication.class);
    @Autowired
    GoodsRepository goodsRepository;

    /**
     * 商品审核同意,保存商品
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void saveGoods(GoodsCommand command) throws NegativeException {
        LOGGER.info("saveGoods command >>{}", command);
        Goods goods = goodsRepository.queryGoodsById(command.getGoodsId());
        if (null == goods) {//增加商品
            goods = new Goods(command.getGoodsId(), command.getDealerId(), command.getDealerName(), command.getGoodsName(), command.getGoodsSubTitle(),
                    command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(), command.getGoodsGuarantee(),
                    command.getGoodsMainImages(), command.getGoodsDesc(), command.getGoodsShelves(), command.getGoodsSKUs());
        } else {//修改商品审核：修改商品的拍获价，供货价，规格
            goods.modifyApproveGoodsSku(command.getGoodsSKUs());
        }
        goodsRepository.save(goods);
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void modifyGoods(GoodsCommand command) throws NegativeException {
        LOGGER.info("modifyGoods command >>{}", command);
        Goods goods = goodsRepository.queryGoodsById(command.getGoodsId());
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        if (goodsRepository.goodsNameIsRepeat(command.getGoodsId(), command.getDealerId(), command.getGoodsName())) {
            throw new NegativeException(MCode.V_300, "商品名称已存在");
        }
        goods.modifyGoods(command.getGoodsName(), command.getGoodsSubTitle(),
                command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(), command.getGoodsGuarantee(),
                command.getGoodsMainImages(), command.getGoodsDesc(), command.getGoodsSKUs());
    }
}
