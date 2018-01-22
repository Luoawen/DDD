package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.CommonApplication;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.goods.GoodsApproveApplication;
import cn.m2c.scm.application.goods.command.GoodsApproveAgreeCommand;
import cn.m2c.scm.application.goods.command.GoodsApproveCommand;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectCommand;
import cn.m2c.scm.application.goods.query.GoodsApproveQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsApproveBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsApproveDetailRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsApproveSearchRepresentation;
import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.application.standstard.bean.StantardBean;
import cn.m2c.scm.application.standstard.query.StantardQuery;
import cn.m2c.scm.application.unit.query.UnitQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.util.GetMapValueUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品审核
 *
 * @author ps
 */
@RestController
public class GoodsApproveAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsApproveAgent.class);

    @Autowired
    GoodsApproveApplication goodsApproveApplication;
    @Autowired
    CommonApplication commonApplication;
    @Autowired
    GoodsApproveQueryApplication goodsApproveQueryApplication;
    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;
    @Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;
    @Autowired
    UnitQuery unitQuery;
    @Autowired
    DealerQuery dealerQuery;
    @Autowired
    PostageModelQueryApplication postageModelQueryApplication;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    StantardQuery stantardQuery;
    @Autowired
    GoodsQueryApplication goodsQueryApplication;

    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/web/goods/approve/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getGoodsApproveId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_GOODS_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getGoodsApproveId Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 增加商品
     *
     * @param goodsId             商品id
     * @param dealerId            商家ID
     * @param dealerName          商家名称
     * @param goodsName           商品名称
     * @param goodsSubTitle       商品副标题
     * @param goodsClassifyId     商品分类id
     * @param goodsBrandId        商品品牌id
     * @param goodsUnitId         商品计量单位id
     * @param goodsMinQuantity    最小起订量
     * @param goodsPostageId      运费模板id
     * @param goodsBarCode        商品条形码
     * @param goodsKeyWord        关键词
     * @param goodsGuarantee      商品保障
     * @param goodsMainImages     商品主图  存储类型是[“url1”,"url2"]
     * @param goodsDesc           商品描述
     * @param goodsShelves        1:手动上架,2:审核通过立即上架
     * @param goodsSpecifications 商品规格,格式：[{"itemName":"尺寸","itemValue":["L","M"]},{"itemName":"颜色","itemValue":["蓝色","白色"]}]
     * @param goodsSkuApproves    商品sku规格列表,格式：[{"availableNum":200,"goodsCode":"111111","marketPrice":6000,"photographPrice":5000,"showStatus":2,"skuName":"L,红","supplyPrice":4000,"weight":20.5}]
     * @return
     */
    @RequestMapping(value = "/web/goods/approve", method = RequestMethod.POST)
    public ResponseEntity<MResult> addGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "dealerName", required = false) String dealerName,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "goodsSubTitle", required = false) String goodsSubTitle,
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "goodsBrandId", required = false) String goodsBrandId,
            @RequestParam(value = "goodsBrandName", required = false) String goodsBrandName,
            @RequestParam(value = "goodsUnitId", required = false) String goodsUnitId,
            @RequestParam(value = "goodsMinQuantity", required = false) Integer goodsMinQuantity,
            @RequestParam(value = "goodsPostageId", required = false) String goodsPostageId,
            @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
            @RequestParam(value = "goodsKeyWord", required = false) List goodsKeyWord,
            @RequestParam(value = "goodsGuarantee", required = false) List goodsGuarantee,
            @RequestParam(value = "goodsMainImages", required = false) List goodsMainImages,
            @RequestParam(value = "goodsMainVideo", required = false) String goodsMainVideo,
            @RequestParam(value = "goodsDesc", required = false) String goodsDesc,
            @RequestParam(value = "goodsShelves", required = false) Integer goodsShelves,
            @RequestParam(value = "goodsSpecifications", required = false) String goodsSpecifications,
            @RequestParam(value = "skuFlag", required = false) Integer skuFlag,//是否是多规格：0：单规格，1：多规格
            @RequestParam(value = "goodsSKUs", required = false) String goodsSkuApproves) {
        MResult result = new MResult(MCode.V_1);

        List<Map> skuList = JsonUtils.toList(goodsSkuApproves, Map.class);
        try {
            if (null != skuList && skuList.size() > 0) {
                for (Map map : skuList) {
                    String skuId = "";
                    try {
                        skuId = commonApplication.generateGoodsSku();
                    } catch (Exception e) { //失败重新生成一次
                        skuId = commonApplication.generateGoodsSku();
                    }
                    if (StringUtils.isEmpty(skuId)) {
                        result = new MResult(MCode.V_1, "商品SKU生成失败");
                        return new ResponseEntity<MResult>(result, HttpStatus.OK);
                    }
                    map.put("skuId", skuId);
                    Long marketPrice = null != map.get("marketPrice") && !"NaN".equals(map.get("marketPrice")) ? new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "marketPrice") * 10000)).longValue() : null;
                    Long photographPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "photographPrice") * 10000)).longValue();
                    if (null != GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice")) {
                        Long supplyPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice") * 10000)).longValue();
                        map.put("supplyPrice", supplyPrice);
                    } else {
                        map.put("supplyPrice", GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice"));
                    }

                    map.put("marketPrice", marketPrice);
                    map.put("photographPrice", photographPrice);
                }
            } else {
                result = new MResult(MCode.V_1, "商品规格为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            goodsSkuApproves = JsonUtils.toStr(skuList);
            GoodsApproveCommand command = new GoodsApproveCommand(goodsId, dealerId, dealerName, goodsName, goodsSubTitle,
                    goodsClassifyId, goodsBrandId, goodsBrandName, goodsUnitId, goodsMinQuantity,
                    goodsPostageId, goodsBarCode, goodsKeyWord, goodsGuarantee,
                    goodsMainImages, goodsMainVideo, goodsDesc, goodsShelves, goodsSpecifications, goodsSkuApproves, skuFlag);
            goodsApproveApplication.addGoodsApprove(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "添加商品失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 同意商品审核
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goods/approve/mng/agree", method = RequestMethod.POST)
    @RequirePermissions(value = {"scm:goodsCheck:agree"})
    public ResponseEntity<MResult> agreeGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "newServiceRate", required = false) String newServiceRate,
            @RequestParam(value = "oldServiceRate", required = false) String oldServiceRate,
            @RequestParam(value = "oldClassifyName", required = false) String oldClassifyName,
            @RequestParam(value = "newClassifyName", required = false) String newClassifyName,
            @RequestParam(value = "settlementMode", required = false) Integer settlementMode

    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            GoodsApproveAgreeCommand command = new GoodsApproveAgreeCommand(goodsId,newServiceRate,oldServiceRate,
                    oldClassifyName,newClassifyName,settlementMode);
            goodsApproveApplication.agreeGoodsApprove(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("agreeGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("agreeGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "同意商品审核失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 拒绝商品审核
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goods/approve/mng/reject", method = RequestMethod.POST)
    @RequirePermissions(value = {"scm:goodsCheck:reject"})
    public ResponseEntity<MResult> rejectGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "rejectReason", required = false) String rejectReason
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsApproveRejectCommand command = new GoodsApproveRejectCommand(goodsId, rejectReason);
            String _attach = request.getHeader("attach");
            goodsApproveApplication.rejectGoodsApprove(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("rejectGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("rejectGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "拒绝商品审核失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改商品审核信息
     *
     * @param goodsId          商品id
     * @param dealerId         商家ID
     * @param goodsName        商品名称
     * @param goodsSubTitle    商品副标题
     * @param goodsClassifyId  商品分类id
     * @param goodsBrandId     商品品牌id
     * @param goodsUnitId      商品计量单位id
     * @param goodsMinQuantity 最小起订量
     * @param goodsPostageId   运费模板id
     * @param goodsBarCode     商品条形码
     * @param goodsKeyWord     关键词
     * @param goodsGuarantee   商品保障
     * @param goodsMainImages  商品主图  存储类型是[“url1”,"url2"]
     * @param goodsDesc        商品描述
     * @param goodsSKUs        商品sku规格列表,格式：[{"availableNum":200,"goodsCode":"111111","marketPrice":6000,"photographPrice":5000,"showStatus":2,"skuId":"SPSHA5BDED943A1D42CC9111B3723B0987BF","skuName":"L,红","supplyPrice":4000,"weight":20.5}]
     * @return
     */
    @RequestMapping(value = "/web/goods/approve", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "goodsSubTitle", required = false) String goodsSubTitle,
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "goodsBrandId", required = false) String goodsBrandId,
            @RequestParam(value = "goodsBrandName", required = false) String goodsBrandName,
            @RequestParam(value = "goodsUnitId", required = false) String goodsUnitId,
            @RequestParam(value = "goodsMinQuantity", required = false) Integer goodsMinQuantity,
            @RequestParam(value = "goodsPostageId", required = false) String goodsPostageId,
            @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
            @RequestParam(value = "goodsKeyWord", required = false) List goodsKeyWord,
            @RequestParam(value = "goodsGuarantee", required = false) List goodsGuarantee,
            @RequestParam(value = "goodsMainImages", required = false) List goodsMainImages,
            @RequestParam(value = "goodsMainVideo", required = false) String goodsMainVideo,
            @RequestParam(value = "goodsDesc", required = false) String goodsDesc,
            @RequestParam(value = "goodsSpecifications", required = false) String goodsSpecifications,
            @RequestParam(value = "goodsSKUs", required = false) String goodsSKUs,
            @RequestParam(value = "oldServiceRate", required = false) String oldServiceRate,
            @RequestParam(value = "newServiceRate", required = false) String newServiceRate,
            @RequestParam(value = "oldClassifyName", required = false) String oldClassifyName,
            @RequestParam(value = "newClassifyName", required = false) String newClassifyName,
            @RequestParam(value = "settlementMode", required = false) String settlementMode) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<Map> skuList = JsonUtils.toList(goodsSKUs, Map.class);
            // 规格增加了，新增的规格需生成新的sku
            if (null != skuList && skuList.size() > 0) {
                for (Map map : skuList) {
                    String skuId = null != map.get("skuId") ? map.get("skuId").toString() : null;
                    if (StringUtils.isEmpty(skuId)) {
                        try {
                            skuId = commonApplication.generateGoodsSku();
                        } catch (Exception e) { //失败重新生成一次
                            skuId = commonApplication.generateGoodsSku();
                        }
                        map.put("skuId", skuId);
                    }
                    Long marketPrice = null != map.get("marketPrice") && !"NaN".equals(map.get("marketPrice")) ? new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "marketPrice") * 10000)).longValue() : null;
                    Long photographPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "photographPrice") * 10000)).longValue();
                    if (null != GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice")) {
                        Long supplyPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice") * 10000)).longValue();
                        map.put("supplyPrice", supplyPrice);
                    } else {
                        map.put("supplyPrice", GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice"));
                    }

                    map.put("marketPrice", marketPrice);
                    map.put("photographPrice", photographPrice);

                }
                goodsSKUs = JsonUtils.toStr(skuList);
            } else {
                result = new MResult(MCode.V_1, "商品规格为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            GoodsApproveCommand command = new GoodsApproveCommand(goodsId, dealerId, goodsName, goodsSubTitle,
                    goodsClassifyId, goodsBrandId, goodsBrandName, goodsUnitId, goodsMinQuantity,
                    goodsPostageId, goodsBarCode, goodsKeyWord, goodsGuarantee,
                    goodsMainImages, goodsMainVideo, goodsDesc, goodsSpecifications, goodsSKUs,
                    oldServiceRate, newServiceRate, oldClassifyName, newClassifyName, settlementMode);
            String _attach = request.getHeader("attach");
            goodsApproveApplication.modifyGoodsApprove(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品审核信息失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/web/goods/approve/{goodsId}", method = RequestMethod.DELETE)
    public ResponseEntity<MResult> delGoodsApprove(
            @PathVariable("goodsId") String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApproveApplication.deleteGoodsApprove(goodsId, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("delGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("delGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "删除商品审核信息失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询商品审核列表
     *
     * @param dealerId        商家ID
     * @param goodsClassifyId 商品分类
     * @param approveStatus   审核状态，1：审核中，2：审核不通过
     * @param condition       搜索条件
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    @RequestMapping(value = {"/web/goods/approve", "/goods/approve"}, method = RequestMethod.GET)
    public ResponseEntity<MPager> searchGoodsApproveByCondition(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "approveStatus", required = false) Integer approveStatus,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = goodsApproveQueryApplication.searchGoodsApproveByConditionTotal(dealerId, goodsClassifyId, approveStatus,
                    condition, startTime, endTime);
            if (total > 0) {
                List<GoodsApproveBean> goodsBeans = goodsApproveQueryApplication.searchGoodsApproveByCondition(dealerId, goodsClassifyId, approveStatus,
                        condition, startTime, endTime, pageNum, rows);
                if (null != goodsBeans && goodsBeans.size() > 0) {
                    List<GoodsApproveSearchRepresentation> representations = new ArrayList<GoodsApproveSearchRepresentation>();
                    for (GoodsApproveBean bean : goodsBeans) {
                        DealerBean dealerBean = dealerQuery.getDealer(bean.getDealerId());
                        String dealerType = "";
                        if (null != dealerBean) {
                            dealerType = null != dealerBean.getDealerClassifyBean() ? dealerBean.getDealerClassifyBean().getDealerSecondClassifyName() : "";
                        }
                        Map goodsClassifyMap = goodsClassifyQueryApplication.getClassifyMap(bean.getGoodsClassifyId());


                        //结算模式 1：按供货价 2：按服务费率
                        Integer settlementMode = null != dealerBean ? dealerBean.getCountMode() : null;
                        Float newServiceRate = null;
                        if (null != settlementMode && settlementMode == 2) {
                            newServiceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(bean.getGoodsClassifyId());
                        }

                        // 查询商品库信息,获取商品变更前的服务费率和分类名称
                        Float oldServiceRate = null;
                        String oldClassifyName = "";
                        GoodsBean goodsBean = goodsQueryApplication.queryGoodsByGoodsId(bean.getGoodsId());
                        if (null != goodsBean) {
                            if (!bean.getGoodsClassifyId().equals(goodsBean.getGoodsClassifyId())) { // 修改了分类
                                Map oldClassifyMap = goodsClassifyQueryApplication.getClassifyMap(goodsBean.getGoodsClassifyId());
                                oldClassifyName = null == oldClassifyMap.get("name") ? "" : (String) oldClassifyMap.get("name");
                                if (settlementMode == 2) {
                                    oldServiceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                                }
                            } else {
                                oldServiceRate = newServiceRate;
                                oldClassifyName = null == goodsClassifyMap.get("name") ? "" : (String) goodsClassifyMap.get("name");
                            }
                        }

                        representations.add(new GoodsApproveSearchRepresentation(bean, goodsClassifyMap, dealerType,
                                settlementMode, newServiceRate, oldServiceRate, oldClassifyName));
                    }
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("searchGoodsApproveByCondition Exception e:", e);
            result = new MPager(MCode.V_400, "查询商品审核列表失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 查询商品审核详情
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = {"/web/goods/approve/{goodsId}", "/goods/approve/{goodsId}"}, method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsApproveDetail(
            @PathVariable("goodsId") String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsApproveBean goodsBean = goodsApproveQueryApplication.queryGoodsApproveByGoodsId(goodsId);
            if (null != goodsBean) {
                Map goodsClassifyMap = goodsClassifyQueryApplication.getClassifyMap(goodsBean.getGoodsClassifyId());
                List<GoodsGuaranteeBean> goodsGuarantee = goodsGuaranteeQueryApplication.queryGoodsGuaranteeByIds(JsonUtils.toList(goodsBean.getGoodsGuarantee(), String.class));
                String goodsUnitName = unitQuery.getUnitNameByUnitId(goodsBean.getGoodsUnitId());
                //结算模式 1：按供货价 2：按服务费率
                Integer settlementMode = dealerQuery.getDealerCountMode(goodsBean.getDealerId());
                Float serviceRate = null;
                if (settlementMode == 2) {
                    serviceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                }
                PostageModelBean postageModelBean = postageModelQueryApplication.queryPostageModelsByModelId(goodsBean.getGoodsPostageId());

                // 查询商品库信息,获取商品变更前的服务费率和分类名称
                Float oldServiceRate = null;
                String oldClassifyName = "";
                GoodsBean bean = goodsQueryApplication.queryGoodsByGoodsId(goodsId);
                if (null != bean) {
                    if (!bean.getGoodsClassifyId().equals(goodsBean.getGoodsClassifyId())) { //
                        Map oldClassifyMap = goodsClassifyQueryApplication.getClassifyMap(bean.getGoodsClassifyId());
                        oldClassifyName = null == oldClassifyMap.get("name") ? "" : (String) oldClassifyMap.get("name");
                        if (settlementMode == 2) {
                            oldServiceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                        }
                    } else {
                        oldServiceRate = serviceRate;
                        oldClassifyName = null == goodsClassifyMap.get("name") ? "" : (String) goodsClassifyMap.get("name");
                    }
                }

                List<Map> goodsSpecifications = JsonUtils.toList(goodsBean.getGoodsSpecifications(), Map.class);
                if (null != goodsSpecifications && goodsSpecifications.size() > 0) {
                    for (Map tempMap : goodsSpecifications) {
                        String standardId = tempMap.get("standardId").toString();
                        StantardBean standard = stantardQuery.getStantardByStantardId(standardId);
                        String standardName = null != standard ? standard.getStantardName() : tempMap.get("itemName").toString();
                        tempMap.put("itemName", standardName);
                    }
                    goodsBean.setGoodsSpecifications(JsonUtils.toStr(goodsSpecifications));
                }

                GoodsApproveDetailRepresentation representation = new GoodsApproveDetailRepresentation(goodsBean,
                        goodsClassifyMap, goodsGuarantee, goodsUnitName, settlementMode, serviceRate, postageModelBean, oldServiceRate, oldClassifyName);
                result.setContent(representation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsDetail Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品审核详情失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

}
