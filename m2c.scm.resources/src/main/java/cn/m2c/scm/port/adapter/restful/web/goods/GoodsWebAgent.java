package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsSalesListApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSalesListBean;
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

/**
 * 商品销量榜
 */
@RestController
@RequestMapping("/web/goods")
public class GoodsWebAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsWebAgent.class);

    @Autowired
    GoodsSalesListApplication goodsSalesListApplication;
    @Autowired
    GoodsQueryApplication goodsQueryApplication;

    /**
     * 商家商品销量榜前五名
     *
     * @param dealerId 商家id
     * @return
     */
    @RequestMapping(value = "/sales/list/top5", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsSalesListTop5(
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "dealerId", required = false) String dealerId) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsSalesListBean> list = goodsSalesListApplication.queryGoodsSalesListTop5(month, dealerId);
            result.setContent(list);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsSalesListTop5 Exception e:", e);
            result = new MResult(MCode.V_400, "获取商家商品销量榜失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商家商品在售数量
     *
     * @param dealerId 商家id
     * @return
     */
    @RequestMapping(value = "/for/sale/num", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsForSaleNum(
            @RequestParam(value = "dealerId", required = false) String dealerId) {
        MResult result = new MResult(MCode.V_1);
        try {
            Integer num = goodsQueryApplication.goodsForSaleNum(dealerId);
            result.setContent(num);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsForSaleNum Exception e:", e);
            result = new MResult(MCode.V_400, "获取商家商品在售数量失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
