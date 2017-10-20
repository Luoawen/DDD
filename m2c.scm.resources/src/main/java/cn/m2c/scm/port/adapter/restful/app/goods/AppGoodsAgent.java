package cn.m2c.scm.port.adapter.restful.app.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsGuessRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.app.AppGoodsDetailRepresentation;
import cn.m2c.scm.application.unit.query.UnitQuery;
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

import java.io.Writer;
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
    @Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;
    @Autowired
    UnitQuery unitQuery;

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

    /**
     * 查询商品详情
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsDetailApp(
            @RequestParam(value = "goodsId", required = false) String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsBean goodsBean = goodsQueryApplication.appGoodsDetailByGoodsId(goodsId);
            if (null != goodsBean) {
                List<GoodsGuaranteeBean> goodsGuarantee = goodsGuaranteeQueryApplication.queryGoodsGuaranteeByIds(JsonUtils.toList(goodsBean.getGoodsGuarantee(), String.class));
                String goodsUnitName = unitQuery.getUnitNameByUnitId(goodsBean.getGoodsUnitId());
                AppGoodsDetailRepresentation representation = new AppGoodsDetailRepresentation(goodsBean,
                        goodsGuarantee, goodsUnitName);
                result.setContent(representation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsDetailApp Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品详情失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询商品图文详情
     *
     * @param writer
     * @param goodsId
     */
    @RequestMapping(value = "/desc", method = RequestMethod.GET)
    public void appGoodsDesc(Writer writer,
                             @RequestParam(value = "goodsId", required = true) String goodsId) {
        try {
            GoodsBean goodsBean = goodsQueryApplication.appGoodsDetailByGoodsId(goodsId);
            StringBuffer sb = new StringBuffer();
            sb.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" /><style>img{max-width:100%;border:0; margin : 0;padding :0 ;vertical-align:top;}</style>");
            sb.append(goodsBean.getGoodsDesc());
            writer.write(sb.toString());
            writer.close();
        } catch (IllegalArgumentException e) {
            LOGGER.error("appGoodsDesc Exception e:", e);
        } catch (Exception e) {
            LOGGER.error("appGoodsDesc Exception e:", e);
        }
    }

    /**
     * 商品搜索
     *
     * @param goodsClassifyId 商品分类
     * @param condition       条件
     * @param sortType        排序类型：1：综合，2：价格
     * @param sort            1：降序，2：升序
     * @param pageNum         第几页
     * @param rows            每页多少条
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MPager> searchGoodsByCondition(
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "sortType", required = false) Integer sortType,
            @RequestParam(value = "sort", required = false) Integer sort,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }


}
