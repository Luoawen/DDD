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
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.command.GoodsCommand;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsDetailRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsSearchRepresentation;
import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.application.standstard.bean.StantardBean;
import cn.m2c.scm.application.standstard.query.StantardQuery;
import cn.m2c.scm.application.unit.query.UnitQuery;
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
 * 商品
 *
 * @author ps
 */
@RestController
public class GoodsAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsAgent.class);

    @Autowired
    GoodsApplication goodsApplication;
    @Autowired
    CommonApplication commonApplication;
    @Autowired
    GoodsQueryApplication goodsQueryApplication;
    @Autowired
    DealerQuery dealerQuery;
    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;
    @Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;
    @Autowired
    UnitQuery unitQuery;
    @Autowired
    PostageModelQueryApplication postageModelQueryApplication;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    StantardQuery stantardQuery;

    /**
     * 修改商品
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
     * @param goodsMainVideo   商品主图视频
     * @param goodsMainVideoDuration  商品主图视频时长
     * @param goodsMainVideoSize  商品主图视频大小
     * @param goodsDesc        商品描述
     * @param goodsSKUs        商品sku规格列表,格式：[{"availableNum":200,"goodsCode":"111111","marketPrice":6000,"photographPrice":5000,"showStatus":2,"skuId":"SPSHA5BDED943A1D42CC9111B3723B0987BF","skuName":"L,红","supplyPrice":4000,"weight":20.5}]
     * @param changeReason     变更原因
     * @return
     */
    @RequestMapping(value = "/web/goods", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyGoods(
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
            @RequestParam(value = "goodsMainVideoDuration", required = false) Double goodsMainVideoDuration,
            @RequestParam(value = "goodsMainVideoSize", required = false) Integer goodsMainVideoSize,
            @RequestParam(value = "goodsDesc", required = false) String goodsDesc,
            @RequestParam(value = "goodsSpecifications", required = false) String goodsSpecifications,
            @RequestParam(value = "goodsSKUs", required = false) String goodsSKUs,
            @RequestParam(value = "changeReason", required = false) String changeReason) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<Map> skuList = JsonUtils.toList(goodsSKUs, Map.class);
            // 生成sku
            if (null != skuList && skuList.size() > 0) {
                for (Map map : skuList) {
                    String skuId = null != map.get("skuId") ? map.get("skuId").toString() : null;
                    if (StringUtils.isEmpty(skuId)) {
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
                    }
                    Long marketPrice = null != map.get("marketPrice") && !"NaN".equals(map.get("marketPrice")) ? new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "marketPrice") * 10000)).longValue() : null;
                    Long photographPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "photographPrice") * 10000)).longValue();
                    if (!"NaN".equals(map.get("supplyPrice")) && null != GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice")) {
                        Long supplyPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice") * 10000)).longValue();
                        map.put("supplyPrice", supplyPrice);
                    } else {
                        map.put("supplyPrice", null);
                    }
                    map.put("marketPrice", marketPrice);
                    map.put("photographPrice", photographPrice);
                }
                goodsSKUs = JsonUtils.toStr(skuList);
            } else {
                result = new MResult(MCode.V_1, "商品规格为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            GoodsCommand command = new GoodsCommand(goodsId, dealerId, goodsName, goodsSubTitle,
                    goodsClassifyId, goodsBrandId, goodsBrandName, goodsUnitId, goodsMinQuantity,
                    goodsPostageId, goodsBarCode, JsonUtils.toStr(goodsKeyWord), JsonUtils.toStr(goodsGuarantee),
                    JsonUtils.toStr(goodsMainImages), goodsMainVideo, goodsMainVideoDuration, goodsMainVideoSize,
                    goodsDesc, goodsSpecifications, goodsSKUs, changeReason);
            String _attach = request.getHeader("attach");
            goodsApplication.modifyGoods(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyGoods NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyGoods Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品失败");
        }

        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/web/goods/{goodsId}", method = RequestMethod.DELETE)
    public ResponseEntity<MResult> delGoods(
            @PathVariable("goodsId") String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApplication.deleteGoods(goodsId, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("delGoods NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("delGoods Exception e:", e);
            result = new MResult(MCode.V_400, "删除商品失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商品上架
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = {"/web/goods/up/shelf/{goodsId}", "/goods/mng/up/shelf/{goodsId}"}, method = RequestMethod.PUT)
    @RequirePermissions(value = {"scm:goodsStorage:upShelf"})
    public ResponseEntity<MResult> upShelfGoods(
            @PathVariable("goodsId") String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApplication.upShelfGoods(goodsId, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("upShelfGoods NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("upShelfGoods Exception e:", e);
            result = new MResult(MCode.V_400, "商品上架失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商品下架
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = {"/web/goods/off/shelf/{goodsId}", "/goods/mng/off/shelf/{goodsId}"}, method = RequestMethod.PUT)
    @RequirePermissions(value = {"scm:goodsStorage:offShelf"})
    public ResponseEntity<MResult> offShelfGoods(
            @PathVariable("goodsId") String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApplication.offShelfGoods(goodsId, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("offShelfGoods NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("offShelfGoods Exception e:", e);
            result = new MResult(MCode.V_400, "商品下架失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询商品列表
     *
     * @param dealerId        商家ID
     * @param goodsClassifyId 商品分类
     * @param goodsStatus     商品状态，1：仓库中，2：出售中，3：已售罄
     * @param delStatus       是否删除，1:正常，2：已删除
     * @param condition       搜索条件
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    @RequestMapping(value = {"/web/goods", "/goods"}, method = RequestMethod.GET)
    public ResponseEntity<MPager> searchGoodsByCondition(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "goodsStatus", required = false) Integer goodsStatus,
            @RequestParam(value = "delStatus", required = false) Integer delStatus,
            @RequestParam(value = "brandName", required = false) String brandName,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "recognizedStatus", required = false) Integer recognizedStatus, //0:未设置广告图，1已设置广告图
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Long start = System.currentTimeMillis();
            Integer total = goodsQueryApplication.searchGoodsByConditionTotal(dealerId, goodsClassifyId, goodsStatus, delStatus,
                    condition, recognizedStatus, startTime, endTime);
            Long end = System.currentTimeMillis();
            LOGGER.info("查询商品总数耗时：" + (end - start));
            if (total > 0) {
                start = System.currentTimeMillis();
                List<GoodsBean> goodsBeans = goodsQueryApplication.searchGoodsByCondition(dealerId, goodsClassifyId, goodsStatus, delStatus,
                        condition, recognizedStatus, startTime, endTime, pageNum, rows);
                end = System.currentTimeMillis();
                LOGGER.info("查询商品列表耗时：" + (end - start));
                if (null != goodsBeans && goodsBeans.size() > 0) {
                    List<GoodsSearchRepresentation> representations = new ArrayList<GoodsSearchRepresentation>();
                    start = System.currentTimeMillis();
                    for (GoodsBean bean : goodsBeans) {
                        DealerBean dealerBean = dealerQuery.getDealer(bean.getDealerId());
                        String dealerType = "";
                        if (null != dealerBean) {
                            dealerType = null != dealerBean.getDealerClassifyBean() ? dealerBean.getDealerClassifyBean().getDealerSecondClassifyName() : "";
                        }
                        Map goodsClassifyMap = goodsClassifyQueryApplication.getClassifyMap(bean.getGoodsClassifyId());
                        representations.add(new GoodsSearchRepresentation(bean, goodsClassifyMap, dealerType));
                    }
                    end = System.currentTimeMillis();
                    LOGGER.info("查询商品列表处理分类耗时：" + (end - start));
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("searchGoodsByCondition Exception e:", e);
            result = new MPager(MCode.V_400, "查询商品列表失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 查询商品详情
     *
     * @param goodsId
     * @param isDelete 商品是否已删除，可查出已删商品所有的保障(不论保障是否删除)
     * @return
     */
    @RequestMapping(value = {"/web/goods/{goodsId}", "/goods/{goodsId}"}, method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsDetail(
            @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "isDelete", required = false) Integer isDelete
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsBean goodsBean = goodsQueryApplication.queryGoodsByGoodsId(goodsId);
            if (null != goodsBean) {
                Map goodsClassifyMap = goodsClassifyQueryApplication.getClassifyMap(goodsBean.getGoodsClassifyId());
                List<GoodsGuaranteeBean> goodsGuarantee = goodsGuaranteeQueryApplication.queryGoodsGuaranteeByIdsAndIsDelete(JsonUtils.toList(goodsBean.getGoodsGuarantee(), String.class), isDelete);
                String goodsUnitName = unitQuery.getUnitNameByUnitId(goodsBean.getGoodsUnitId());
                //结算模式 1：按供货价 2：按服务费率
                Integer settlementMode = dealerQuery.getDealerCountMode(goodsBean.getDealerId());
                Float serviceRate = null;
                if (settlementMode == 2) {
                    serviceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                }
                PostageModelBean postageModelBean = postageModelQueryApplication.queryPostageModelsByModelId(goodsBean.getGoodsPostageId());

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

                GoodsDetailRepresentation representation = new GoodsDetailRepresentation(goodsBean, goodsClassifyMap,
                        goodsGuarantee, goodsUnitName, settlementMode, serviceRate, postageModelBean);
                result.setContent(representation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsDetail Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品详情失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询根据商品编码商品
     *
     * @param goodsCode
     * @return
     */
    @RequestMapping(value = "/web/goods/code", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsSkuByCode(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "goodsCode", required = false) String goodsCode
    ) {
        MResult result = new MResult(MCode.V_1);
        if (StringUtils.isEmpty(dealerId)) {
            result = new MResult(MCode.V_400, "商家ID不能为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        if (StringUtils.isEmpty(goodsCode)) {
            result = new MResult(MCode.V_400, "商家编码不能为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        try {
            GoodsSkuBean sku = goodsQueryApplication.queryGoodsSkuByCode(dealerId, goodsCode);
            if (null != sku) {
                result.setContent(sku);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsSkuByCode Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品信息失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改商品主图
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/goods/main/image/{goodsId}", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyGoodsMainImages(
            @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "images", required = false) List images
    ) {
        MResult result = new MResult(MCode.V_1);
        if (null == images || images.size() == 0) {
            result = new MResult(MCode.V_400, "商品主图不能为空");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        try {
            String _attach = request.getHeader("attach");
            goodsApplication.modifyGoodsMainImages(goodsId, images, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyGoodsMainImages NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyGoodsMainImages Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品主图失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
