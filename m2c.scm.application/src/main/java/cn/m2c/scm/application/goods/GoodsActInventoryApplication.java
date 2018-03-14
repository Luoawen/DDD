package cn.m2c.scm.application.goods;

import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.GoodsActInventory;
import cn.m2c.scm.domain.model.goods.GoodsActInventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 活动商品库存
 */
@Service
public class GoodsActInventoryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsActInventoryApplication.class);

    @Autowired
    GoodsActInventoryRepository goodsActInventoryRepository;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void goodsActInventoryFreeze(List<Map> freezeInfos) throws NegativeException {
        for (Map freezeInfoMap : freezeInfos) {
            GoodsActInventory inventory = new GoodsActInventory();
            goodsActInventoryRepository.save(inventory);
        }
    }
}
