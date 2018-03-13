package cn.m2c.scm.port.adapter.restful.domain.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsForNormalRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.domain.GoodsDetailRepresentation;
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
 * 商品接口
 */
@RestController
@RequestMapping("/domain/goods")
public class GoodsDomainAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDomainAgent.class);

    @Autowired
    GoodsQueryApplication goodsQueryApplication;

    /**
     * 给营销工具提供接口查询商品信息
     *
     * @param goodsIds
     * @return
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryNormalGoodsByGoodsIds(
            @RequestParam(value = "goodsIds", required = false) List goodsIds) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsBean> goodsBeans = goodsQueryApplication.queryGoodsByGoodsIds(goodsIds);
            if (null != goodsBeans && goodsBeans.size() > 0) {
                List<GoodsForNormalRepresentation> resultRepresentation = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeans) {
                    resultRepresentation.add(new GoodsForNormalRepresentation(goodsBean));
                }
                result.setContent(resultRepresentation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryNormalGoodsByGoodsIds Exception e:", e);
            result = new MPager(MCode.V_400, "查询在售商品列表失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 给营销工具提供接口查询商品信息
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsByGoodsId(
            @RequestParam(value = "goodsId", required = false) String goodsId) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsBean goodsBean = goodsQueryApplication.queryGoodsByGoodsId(goodsId);
            if (null != goodsBean) {
                result.setContent(new GoodsDetailRepresentation(goodsBean));
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsByGoodsId Exception e:", e);
            result = new MPager(MCode.V_400, "查询商品信息失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
