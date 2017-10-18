package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsChoiceRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsDetailMultipleRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsSimpleDetailRepresentation;
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
 * 商品查询(提供出去的)
 */
@RestController
@RequestMapping("/goods")
public class GoodsQueryAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsQueryAgent.class);
    
    @Autowired
    GoodsQueryApplication goodsQueryApplication;

    /**
     * 商品筛选根据商品类别，名称、标题、编号筛选
     *
     * @param goodsClassifyId 商品类别
     * @param condition       名称、标题、编号
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    @RequestMapping(value = "/choice", method = RequestMethod.GET)
    public ResponseEntity<MPager> goodsChoice(
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = goodsQueryApplication.goodsChoiceTotal(goodsClassifyId, condition);
            if (total > 0) {
                List<GoodsBean> goodsBeans = goodsQueryApplication.goodsChoice(goodsClassifyId,
                        condition, pageNum, rows);
                if (null != goodsBeans && goodsBeans.size() > 0) {
                    List<GoodsChoiceRepresentation> representations = new ArrayList<GoodsChoiceRepresentation>();
                    for (GoodsBean bean : goodsBeans) {
                        representations.add(new GoodsChoiceRepresentation(bean));
                    }
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods choice Exception e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 商品详情
     *
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<MResult> goodsDetail(
            @RequestParam(value = "goodsId", required = false) String goodsId) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsBean goodsBean = goodsQueryApplication.queryGoodsByGoodsId(goodsId);
            if (null != goodsBean) {
                GoodsSimpleDetailRepresentation representation = new GoodsSimpleDetailRepresentation(goodsBean);
                result.setContent(representation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods Detail Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 多个商品详情
     *
     * @param goodsIds 多个商品ID逗号分隔
     * @return
     */
    @RequestMapping(value = "/detail/multiple", method = RequestMethod.GET)
    public ResponseEntity<MResult> goodsDetails(
            @RequestParam(value = "goodsIds", required = false) List goodsIds) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsBean> goodsBeanList = goodsQueryApplication.queryGoodsByGoodsIds(goodsIds);
            if (null != goodsBeanList && goodsBeanList.size() > 0) {
                List<GoodsDetailMultipleRepresentation> resultList = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeanList) {
                    resultList.add(new GoodsDetailMultipleRepresentation(goodsBean));
                }
                result.setContent(resultList);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goodsDetails Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
