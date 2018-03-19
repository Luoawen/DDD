package cn.m2c.scm.port.adapter.restful.domain.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsActInventoryApplication;
import cn.m2c.scm.application.goods.command.GoodsActInventoryFreezeCommand;
import cn.m2c.scm.domain.NegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping(value = "/freeze", method = RequestMethod.POST)
    public ResponseEntity<MResult> goodsActInventoryFreeze(
            @RequestParam(value = "freezeInfo", required = false) String freezeInfo) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsActInventoryFreezeCommand> freezeInfos = JsonUtils.toList(freezeInfo, GoodsActInventoryFreezeCommand.class);
            if (null == freezeInfos || freezeInfos.size() <= 0) {
                result.setErrorMessage("冻结参数为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            List<Map> resultList = goodsActInventoryApplication.goodsActInventoryFreeze(freezeInfos);
            result.setStatus(MCode.V_200);
            result.setContent(resultList);
        } catch (NegativeException ne) {
            LOGGER.error("goodsActInventoryFreeze NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("goodsActInventoryFreeze Exception e:", e);
            result = new MResult(MCode.V_400, "活动商品冻结库存失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 活动（限时购）创建失败，冻结的商品库存返还
     *
     * @return
     */
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    public ResponseEntity<MResult> goodsActInventoryReturn() {
        MResult result = new MResult(MCode.V_1);
        try {
            goodsActInventoryApplication.goodsActInventoriesReturn();
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("goodsActInventoryReturn NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("goodsActInventoryReturn Exception e:", e);
            result = new MResult(MCode.V_400, "活动商品冻结库存返还失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
