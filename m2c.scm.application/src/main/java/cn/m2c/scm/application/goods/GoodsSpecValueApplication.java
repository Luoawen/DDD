package cn.m2c.scm.application.goods;

import cn.m2c.scm.application.goods.command.GoodsSpecValueCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.GoodsSpecValue;
import cn.m2c.scm.domain.model.goods.GoodsSpecValueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品规格值
 */
@Service
@Transactional
public class GoodsSpecValueApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecValueApplication.class);

    @Autowired
    GoodsSpecValueRepository goodsSpecValueRepository;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addGoodsSpecValue(GoodsSpecValueCommand command) throws NegativeException {
        LOGGER.info("addGoodsSpecValue command >>{}", command);
        GoodsSpecValue goodsSpecValue = goodsSpecValueRepository.queryGoodsSpecValueById(command.getSpecId());
        if (null == goodsSpecValue) {
            goodsSpecValue = new GoodsSpecValue(command.getSpecId(), command.getDealerId(),command.getStandardId(), command.getSpecValue());
            goodsSpecValueRepository.save(goodsSpecValue);
        }
    }
}
