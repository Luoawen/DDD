package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsGuaranteeRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品保障
 */
@RestController
@RequestMapping("/goods/guarantee")
public class GoodsGuaranteeAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGuaranteeAgent.class);

    @Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;

    /**
     * 查询商品保障
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsGuarantee() {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsGuaranteeBean> list = goodsGuaranteeQueryApplication.queryGoodsGuarantee();
            if (null != list && list.size() > 0) {
                List<GoodsGuaranteeRepresentation> resultList = new ArrayList<>();
                for (GoodsGuaranteeBean bean : list) {
                    resultList.add(new GoodsGuaranteeRepresentation(bean));
                }
                result.setContent(resultList);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsGuarantee Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品保障失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
