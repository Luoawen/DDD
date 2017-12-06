package cn.m2c.scm.port.adapter.restful.web.special;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.special.GoodsSpecialApplication;
import cn.m2c.scm.domain.NegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 定时任务
 */
@RestController
@RequestMapping("/goods/special/job")
public class GoodsSpecialJobAgent {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecialJobAgent.class);

    @Autowired
    GoodsSpecialApplication goodsSpecialApplication;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public ResponseEntity<MResult> startGoodsSpecial() {
        MResult result = new MResult(MCode.V_1);
        try {
            goodsSpecialApplication.startGoodsSpecial();
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("startGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("startGoodsSpecial Exception e:", e);
            result = new MResult(MCode.V_400, "定时更新商品特惠活动状态失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public ResponseEntity<MResult> endGoodsSpecial() {
        MResult result = new MResult(MCode.V_1);
        try {
            goodsSpecialApplication.endGoodsSpecial();
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("endGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("endGoodsSpecial Exception e:", e);
            result = new MResult(MCode.V_400, "定时更新商品特惠活动状态失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
