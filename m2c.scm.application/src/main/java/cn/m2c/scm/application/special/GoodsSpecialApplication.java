package cn.m2c.scm.application.special;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.special.command.GoodsSpecialAddCommand;
import cn.m2c.scm.application.special.command.GoodsSpecialModifyCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.special.GoodsSpecial;
import cn.m2c.scm.domain.model.special.GoodsSpecialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

/**
 * 商品特惠活动
 */
@Service
@Transactional
public class GoodsSpecialApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecialApplication.class);

    @Autowired
    GoodsSpecialRepository goodsSpecialRepository;

    /**
     * 添加特惠活动
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class, ParseException.class})
    public void addGoodsSpecial(GoodsSpecialAddCommand command) throws NegativeException, ParseException {
        LOGGER.info("addGoodsSpecial command >>{}", command);
        GoodsSpecial goodsSpecial = goodsSpecialRepository.queryGoodsSpecialBySpecialId(command.getSpecialId());
        if (null == goodsSpecial) {
            goodsSpecial = goodsSpecialRepository.queryGoodsSpecialByGoodsId(command.getGoodsId());
            if (null != goodsSpecial) {
                throw new NegativeException(MCode.V_300, "商品优惠活动已存在");
            }
            goodsSpecial = new GoodsSpecial(command.getSpecialId(), command.getGoodsId(), command.getGoodsName(), command.getSkuFlag(),
                    command.getDealerId(), command.getDealerName(), command.getStartTime(), command.getEndTime(),
                    command.getCongratulations(), command.getActivityDescription(), command.getGoodsSkuSpecials());
            goodsSpecialRepository.save(goodsSpecial);
        }
    }

    /**
     * 修改特惠活动
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class, ParseException.class})
    public void modifyGoodsSpecial(GoodsSpecialModifyCommand command) throws NegativeException, ParseException {
        LOGGER.info("modifyGoodsSpecial command >>{}", command);
        GoodsSpecial goodsSpecial = goodsSpecialRepository.queryGoodsSpecialBySpecialId(command.getSpecialId());
        if (null == goodsSpecial) {
            throw new NegativeException(MCode.V_300, "商品优惠活动不存在");
        }
        goodsSpecial.modifyGoodsSpecial(command.getStartTime(), command.getEndTime(),
                command.getCongratulations(), command.getActivityDescription(), command.getGoodsSkuSpecials());
    }
}
