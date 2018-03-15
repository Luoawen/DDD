package cn.m2c.scm.application.goods;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.goods.command.GoodsActInventoryFreezeCommand;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.Goods;
import cn.m2c.scm.domain.model.goods.GoodsActInventory;
import cn.m2c.scm.domain.model.goods.GoodsActInventoryRepository;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import cn.m2c.scm.domain.model.goods.GoodsSku;
import cn.m2c.scm.domain.model.goods.GoodsSkuRepository;
import cn.m2c.scm.domain.model.goods.event.GoodsOutInventoryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
    @Autowired
    GoodsSkuRepository goodsSkuRepository;
    @Autowired
    GoodsRepository goodsRepository;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public List<Map> goodsActInventoryFreeze(List<GoodsActInventoryFreezeCommand> freezeInfos) throws NegativeException {
        boolean isFreeze = false;
        if (null == freezeInfos || freezeInfos.size() <= 0) {
            throw new NegativeException(MCode.V_1, "冻结参数为空");
        }

        String goodsId = freezeInfos.get(0).getGoodsId();
        Goods goods = goodsRepository.queryGoodsByGoodsId(goodsId);
        if (!goods.isSelling()) {
            throw new NegativeException(MCode.V_203, "商品状态不在出售中");
        }
        List<Map> resultList = new ArrayList<>();
        for (GoodsActInventoryFreezeCommand command : freezeInfos) {
            // 查询商品规格信息
            GoodsSku goodsSku = goodsSkuRepository.queryGoodsSkuById(command.getSkuId());
            if (!goodsSku.isShow()) {
                throw new NegativeException(MCode.V_203, "商品规格不对外展示");
            }

            Map map = new HashMap<>();
            map.put("skuId", command.getSkuId());
            Integer availableNum = goodsSku.availableNum();
            if (availableNum > 0) {
                isFreeze = true;
                Integer realFreezeNum = command.getSkuNum();
                if (availableNum < command.getSkuNum()) { // 如果可用库存小于活动预设库存，直接冻结剩余所有库存
                    realFreezeNum = availableNum;
                }

                // 扣减商品库存
                int result = goodsSkuRepository.freezeActInventory(command.getSkuId(), realFreezeNum, goodsSku.concurrencyVersion());
                if (result <= 0) {
                    throw new NegativeException(MCode.V_201, "扣减商品库存失败");//201:扣减商品库存失败
                }

                // 增加活动冻结库存
                GoodsActInventory inventory = new GoodsActInventory(IDGenerator.get(), command.getRuleId(), command.getGoodsId(), command.getSkuId(),
                        command.getSkuNum(), realFreezeNum, command.getPrice());
                goodsActInventoryRepository.save(inventory);
                map.put("freezeNum", realFreezeNum);
            } else {
                // 实际冻结0
                GoodsActInventory inventory = new GoodsActInventory(IDGenerator.get(), command.getRuleId(), command.getGoodsId(), command.getSkuId(),
                        command.getSkuNum(), 0, command.getPrice());
                goodsActInventoryRepository.save(inventory);
                map.put("freezeNum", 0);
            }
            resultList.add(map);
        }
        if (!isFreeze) {
            throw new NegativeException(MCode.V_203, "商品库存不足");
        }

        // 更新商品状态
        List<Integer> goodsIds = new ArrayList<>();
        goodsIds.add(goods.getId());
        DomainEventPublisher
                .instance()
                .publish(new GoodsOutInventoryEvent(goodsIds));

        return resultList;
    }
}
