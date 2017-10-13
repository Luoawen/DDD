package cn.m2c.scm.application.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.scm.application.goods.command.GoodsApproveCommand;
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
     * 添加商品审核
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsApprove(GoodsApproveCommand command) throws NegativeException {
        LOGGER.info("addGoodsApprove command >>{}", command);
        GoodsApprove goodsApprove = goodsApproveRepository.queryGoodsApproveById(command.getApproveId());
        if (null == goodsApprove) {
            if (goodsRepository.goodsNameIsRepeat(null, command.getGoodsName())) {
                throw new NegativeException(MCode.V_300, "商品名称已存在");
            }
            goodsApprove = new GoodsApprove(command.getApproveId(), command.getDealerId(), command.getDealerName(),
                    command.getGoodsName(), command.getGoodsSubTitle(), command.getGoodsClassifyId(),
                    command.getGoodsBrandId(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(),
                    JsonUtils.toStr(command.getGoodsGuarantee()), JsonUtils.toStr(command.getGoodsMainImages()),
                    command.getGoodsDesc(), command.getGoodsShelves(), command.getGoodsSkuApproves(), command.getSkuCodes());
            goodsApproveRepository.save(goodsApprove);
        }
    }
}
