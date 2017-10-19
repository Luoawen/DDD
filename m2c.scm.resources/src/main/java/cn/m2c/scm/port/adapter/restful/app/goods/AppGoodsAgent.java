package cn.m2c.scm.port.adapter.restful.app.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsGuessRepresentation;
import cn.m2c.scm.domain.service.goods.GoodsService;
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
import java.util.Map;

/**
 * 商品
 *
 * @author ps
 */
@RestController
@RequestMapping("/goods/app")
public class AppGoodsAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(AppGoodsAgent.class);

    @Autowired
    GoodsQueryApplication goodsQueryApplication;
    @Autowired
    GoodsService goodsService;

    /**
     * 商品猜你喜欢
     *
     * @param positionType 猜你喜欢位置：1:首页(共32条，分页，每页8条，分4页)，2:购物车页面（共12条，不需分页），3:商品详情页（共12条，不需分页）
     * @param pageNum      第几页
     * @param rows         每页多少行
     * @return
     */
    @RequestMapping(value = "/guess", method = RequestMethod.GET)
    public ResponseEntity<MPager> goodsGuess(
            @RequestParam(value = "positionType", required = false) Integer positionType,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            if (null == positionType) {
                result = new MPager(MCode.V_1, "必选参数为空");
                return new ResponseEntity<MPager>(result, HttpStatus.OK);
            }
            List<GoodsBean> goodsBeanList = goodsQueryApplication.queryGoodsGuessCache(positionType);
            if (null != goodsBeanList && goodsBeanList.size() > 0) {
                if (positionType == 1) { //首页分页
                    List<GoodsBean> goodsBeans = goodsQueryApplication.getPagedList(pageNum, rows, goodsBeanList);
                    List<GoodsGuessRepresentation> resultRepresentation = new ArrayList<>();
                    for (GoodsBean goodsBean : goodsBeans) {
                        List<Map> goodsTags = goodsService.getGoodsTags(goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                        resultRepresentation.add(new GoodsGuessRepresentation(goodsBean, goodsTags));
                    }
                    result.setContent(resultRepresentation);
                    result.setPager(goodsBeanList.size(), pageNum, rows);
                } else {
                    List<GoodsGuessRepresentation> resultRepresentation = new ArrayList<>();
                    for (GoodsBean goodsBean : goodsBeanList) {
                        List<Map> goodsTags = goodsService.getGoodsTags(goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                        resultRepresentation.add(new GoodsGuessRepresentation(goodsBean, goodsTags));
                    }
                    result.setContent(resultRepresentation);
                }
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods guess Exception e:", e);
            result = new MPager(MCode.V_400, "查询猜你喜欢失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }


}
