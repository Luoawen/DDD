package cn.m2c.scm.port.adapter.restful.domain.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsActInventoryApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动商品库存
 */
@RestController
@RequestMapping("/domain/goods/activity/inventory")
public class GoodsActInventoryDomainAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsActInventoryDomainAgent.class);

    @Autowired
    GoodsActInventoryApplication goodsActInventoryApplication;

    /**
     * 活动商品冻结库存
     *
     * @param freezeInfo 格式：[{"rule_id":"123456","sku_num":10,"price":100,"sku_id":"123456"},{"rule_id":"123456","sku_num":20,"price":200,"sku_id":"789654"}]
     * @return
     */
    @RequestMapping(value = "/freeze", method = RequestMethod.GET)
    public ResponseEntity<MResult> goodsActInventoryFreeze(
            @RequestParam(value = "freezeInfo", required = false) String freezeInfo) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<Map> freezeInfos = JsonUtils.toList(freezeInfo, Map.class);
            if (null == freezeInfos || freezeInfos.size() <= 0) {
                result.setErrorMessage("冻结参数为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            goodsActInventoryApplication.goodsActInventoryFreeze(freezeInfos);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goodsActInventoryFreeze Exception e:", e);
            result = new MResult(MCode.V_400, "活动商品冻结库存失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }


    public static void main(String[] args) {
        List<Map> list = new ArrayList<>();
        Map map = new HashMap<>();
        map.put("sku_id", "123456");
        map.put("rule_id", "123456");
        map.put("sku_num", 10);
        map.put("price", 100);

        Map map1 = new HashMap<>();
        map1.put("sku_id", "789654");
        map1.put("rule_id", "789654");
        map1.put("sku_num", 20);
        map1.put("price", 200);

        list.add(map);
        list.add(map1);

        System.out.print(JsonUtils.toStr(list));

    }
}
