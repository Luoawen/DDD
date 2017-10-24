package cn.m2c.scm.port.adapter.restful.app.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.comment.query.GoodsCommentQueryApplication;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.representation.app.AppGoodsDetailRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.app.AppGoodsGuessRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.app.AppGoodsSearchRepresentation;
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

import javax.annotation.Resource;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
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
    @Resource(name = "goodsRestService")
    GoodsService goodsRestService;
    @Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;
    @Autowired
    UnitQuery unitQuery;
    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;
    @Resource(name = "goodsDubboService")
    GoodsService goodsDubboService;
    @Autowired
    GoodsCommentQueryApplication goodsCommentQueryApplication;

    /**
     * 商品猜你喜欢
     *
     * @param positionType 猜你喜欢位置：1:首页(共32条，分页，每页8条，分4页) 2:购物车页面（共12条，不需分页）3:商品详情页（共16条，不需分页）4:搜索结果页(共32条，分页，每页8条，分4页)
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
                if (positionType == 1 || positionType == 4) { //首页分页
                    List<GoodsBean> goodsBeans = goodsQueryApplication.getPagedList(pageNum, rows, goodsBeanList);
                    List<AppGoodsGuessRepresentation> resultRepresentation = new ArrayList<>();
                    for (GoodsBean goodsBean : goodsBeans) {
                        List<Map> goodsTags = goodsRestService.getGoodsTags(goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                        resultRepresentation.add(new AppGoodsGuessRepresentation(goodsBean, goodsTags));
                    }
                    result.setContent(resultRepresentation);
                    result.setPager(goodsBeanList.size(), pageNum, rows);
                } else {
                    List<AppGoodsGuessRepresentation> resultRepresentation = new ArrayList<>();
                    for (GoodsBean goodsBean : goodsBeanList) {
                        List<Map> goodsTags = goodsRestService.getGoodsTags(goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                        resultRepresentation.add(new AppGoodsGuessRepresentation(goodsBean, goodsTags));
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

                Integer commentTotal = goodsCommentQueryApplication.queryGoodsCommentTotal(goodsId);
                GoodsCommentBean goodsCommentBean = null;
                if (commentTotal > 0) {
                    goodsCommentBean = goodsCommentQueryApplication.queryGoodsDetailComment(goodsId);
                }
                AppGoodsDetailRepresentation representation = new AppGoodsDetailRepresentation(goodsBean,
                        goodsGuarantee, goodsUnitName, null, commentTotal, goodsCommentBean);
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
     * @param sn              机器码
     * @param userId          用户ID
     * @param searchFrom      搜索来源HOT_KEYWORD,INPUT
     * @param goodsClassifyId 商品分类
     * @param condition       条件
     * @param sortType        排序类型：1：综合，2：价格
     * @param sort            1：降序，2：升序
     * @param pageNum         第几页
     * @param rows            每页多少条
     * @return
     */
    @RequestMapping(value = "search", method = RequestMethod.GET)
    public ResponseEntity<MPager> appSearchGoods(
            @RequestParam(value = "sn", required = false) String sn,
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "searchFrom", required = false) String searchFrom,//HOT_KEYWORD,INPUT
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "sortType", required = false) Integer sortType,
            @RequestParam(value = "sort", required = false) Integer sort,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = goodsQueryApplication.appSearchGoodsTotal(goodsClassifyId, condition);
            if (total > 0) {
                Map resultMap = new HashMap<>();
                List<GoodsBean> goodsBeans = goodsQueryApplication.appSearchGoods(goodsClassifyId, condition, sortType,
                        sort, pageNum, rows);
                if (null != goodsBeans && goodsBeans.size() > 0) {
                    List<AppGoodsSearchRepresentation> representations = new ArrayList<AppGoodsSearchRepresentation>();
                    List<String> goodsClassifyIds = new ArrayList<>();
                    for (GoodsBean goodsBean : goodsBeans) {
                        // 获取商品分类的一级大类
                        goodsClassifyIds.add(goodsBean.getGoodsClassifyId());
                        List<Map> goodsTags = goodsRestService.getGoodsTags(goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                        representations.add(new AppGoodsSearchRepresentation(goodsBean, goodsTags));
                    }
                    resultMap.put("goods", representations);

                    // 获取商品分类的一级大类
                    List<GoodsClassifyBean> goodsClassifyBeanList = goodsClassifyQueryApplication.getFirstClassifyByClassifyIds(goodsClassifyIds);
                    List<Map> classifyMap = new ArrayList<>();
                    for (GoodsClassifyBean bean : goodsClassifyBeanList) {
                        Map map = new HashMap<>();
                        map.put("classifyId", bean.getClassifyId());
                        map.put("classifyName", bean.getClassifyName());
                        classifyMap.add(map);
                    }
                    resultMap.put("goodsClassify", classifyMap);

                    result.setContent(resultMap);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("searchGoodsByCondition Exception e:", e);
            result = new MPager(MCode.V_400, "搜索商品列表失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * APP拍照获取商品
     *
     * @param token
     * @param recognizedInfo
     * @param barNo
     * @param location
     * @param sn
     * @param os
     * @param appVersion
     * @param osVersion
     * @param triggerTime
     * @param userId
     * @param userName
     * @return
     */
    @RequestMapping(value = "/app/recognized", method = RequestMethod.GET)
    public ResponseEntity<MResult> recognizedPic(
            @RequestParam(value = "token", required = true) String token,
            @RequestParam(value = "recognizedInfo", required = false) String recognizedInfo,
            @RequestParam(value = "barNo", required = false) String barNo,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "sn", required = true) String sn,
            @RequestParam(value = "os", required = true) String os,
            @RequestParam(value = "appVersion", required = true) String appVersion,
            @RequestParam(value = "osVersion", required = true) String osVersion,
            @RequestParam(value = "triggerTime", required = true) long triggerTime,
            @RequestParam(value = "userId", required = false, defaultValue = "") String userId,
            @RequestParam(value = "userName", required = false, defaultValue = "") String userName
    ) {
        MResult result = new MResult(MCode.V_1);
        Map mediaMap = goodsDubboService.getMediaResourceInfo(barNo);
        String mresId = null == mediaMap ? "" : (String) mediaMap.get("mresId");
        try {
            List<GoodsBean> goodsBeans = goodsQueryApplication.recognizedGoods(recognizedInfo, location);
            if (null != goodsBeans && goodsBeans.size() > 0) {
                List<AppGoodsDetailRepresentation> representations = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeans) {
                    List<GoodsGuaranteeBean> goodsGuarantee = goodsGuaranteeQueryApplication.queryGoodsGuaranteeByIds(JsonUtils.toList(goodsBean.getGoodsGuarantee(), String.class));
                    String goodsUnitName = unitQuery.getUnitNameByUnitId(goodsBean.getGoodsUnitId());

                    Integer commentTotal = goodsCommentQueryApplication.queryGoodsCommentTotal(goodsBean.getGoodsId());
                    GoodsCommentBean goodsCommentBean = null;
                    if (commentTotal > 0) {
                        goodsCommentBean = goodsCommentQueryApplication.queryGoodsDetailComment(goodsBean.getGoodsId());
                    }
                    AppGoodsDetailRepresentation representation = new AppGoodsDetailRepresentation(goodsBean,
                            goodsGuarantee, goodsUnitName, mresId, commentTotal, goodsCommentBean);
                    representations.add(representation);
                }
                result.setContent(representations);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("recognizedPic Exception e:", e);
            result = new MResult(MCode.V_400, "拍照获取商品失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
