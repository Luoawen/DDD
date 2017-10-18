package cn.m2c.scm.port.adapter.restful.web.classify;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;
import cn.m2c.scm.application.classify.data.representation.GoodsClassifyRandomRepresentation;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
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
import java.util.List;

/**
 * 商品分类
 */
@RestController
@RequestMapping("/goods/classify")
public class GoodsClassifyQueryAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsClassifyQueryAgent.class);

    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;

    /**
     * 随机取商品分类
     *
     * @param number 随机取商品分类的数量
     * @return
     */
    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ResponseEntity<MResult> goodsClassifyRandom(
            @RequestParam(value = "number", required = false, defaultValue = "10") Integer number) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsClassifyBean> goodsClassifyBeans = goodsClassifyQueryApplication.queryGoodsClassifyRandom(number);
            if (null != goodsClassifyBeans && goodsClassifyBeans.size() > 0) {
                List<GoodsClassifyRandomRepresentation> resultList = new ArrayList<>();
                for (GoodsClassifyBean bean : goodsClassifyBeans) {
                    resultList.add(new GoodsClassifyRandomRepresentation(bean));
                }
                result.setContent(resultList);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goodsClassifyRandom Exception e:", e);
            result = new MResult(MCode.V_400, "随机查询商品分类失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
