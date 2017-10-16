package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.CommonApplication;
import cn.m2c.scm.application.goods.GoodsApproveApplication;
import cn.m2c.scm.application.goods.command.GoodsApproveCommand;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectCommand;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品审核
 *
 * @author ps
 */
@RestController
@RequestMapping("/goods/approve")
public class GoodsApproveAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsApproveAgent.class);

    @Autowired
    GoodsApproveApplication goodsApproveApplication;
    @Autowired
    CommonApplication commonApplication;

    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "dealerName", required = false) String dealerName,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "goodsSubTitle", required = false) String goodsSubTitle,
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "goodsBrandId", required = false) String goodsBrandId,
            @RequestParam(value = "goodsUnitId", required = false) String goodsUnitId,
            @RequestParam(value = "goodsMinQuantity", required = false) Integer goodsMinQuantity,
            @RequestParam(value = "goodsPostageId", required = false) String goodsPostageId,
            @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
            @RequestParam(value = "goodsKeyWord", required = false) String goodsKeyWord,
            @RequestParam(value = "goodsGuarantee", required = false) List goodsGuarantee,
            @RequestParam(value = "goodsMainImages", required = false) List goodsMainImages,
            @RequestParam(value = "goodsDesc", required = false) String goodsDesc,
            @RequestParam(value = "goodsShelves", required = false) Integer goodsShelves,
            @RequestParam(value = "goodsSpecifications", required = false) String goodsSpecifications,
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
                    map.put("skuId", skuId);
                }
            } else {
                result = new MResult(MCode.V_1, "商品规格为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            goodsSkuApproves = JsonUtils.toStr(skuList);
            GoodsApproveCommand command = new GoodsApproveCommand(goodsId, dealerId, dealerName, goodsName, goodsSubTitle,
                    goodsClassifyId, goodsBrandId, goodsUnitId, goodsMinQuantity,
                    goodsPostageId, goodsBarCode, goodsKeyWord, goodsGuarantee,
                    goodsMainImages, goodsDesc, goodsShelves, goodsSpecifications, goodsSkuApproves);
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
    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    public ResponseEntity<MResult> agreeGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            goodsApproveApplication.agreeGoodsApprove(goodsId);
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
     * @param approveId
     * @return
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public ResponseEntity<MResult> rejectGoodsApprove(
            @RequestParam(value = "approveId", required = false) String approveId,
            @RequestParam(value = "rejectReason", required = false) String rejectReason
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsApproveRejectCommand command = new GoodsApproveRejectCommand(approveId, rejectReason);
            goodsApproveApplication.rejectGoodsApprove(command);
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
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyGoodsApprove(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "goodsSubTitle", required = false) String goodsSubTitle,
            @RequestParam(value = "goodsClassifyId", required = false) String goodsClassifyId,
            @RequestParam(value = "goodsBrandId", required = false) String goodsBrandId,
            @RequestParam(value = "goodsUnitId", required = false) String goodsUnitId,
            @RequestParam(value = "goodsMinQuantity", required = false) Integer goodsMinQuantity,
            @RequestParam(value = "goodsPostageId", required = false) String goodsPostageId,
            @RequestParam(value = "goodsBarCode", required = false) String goodsBarCode,
            @RequestParam(value = "goodsKeyWord", required = false) String goodsKeyWord,
            @RequestParam(value = "goodsGuarantee", required = false) List goodsGuarantee,
            @RequestParam(value = "goodsMainImages", required = false) List goodsMainImages,
            @RequestParam(value = "goodsDesc", required = false) String goodsDesc,
            @RequestParam(value = "goodsSpecifications", required = false) String goodsSpecifications,
            @RequestParam(value = "goodsSKUs", required = false) String goodsSKUs) {
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
                }
                goodsSKUs = JsonUtils.toStr(skuList);
            } else {
                result = new MResult(MCode.V_1, "商品规格为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            GoodsApproveCommand command = new GoodsApproveCommand(goodsId, dealerId, goodsName, goodsSubTitle,
                    goodsClassifyId, goodsBrandId, goodsUnitId, goodsMinQuantity,
                    goodsPostageId, goodsBarCode, goodsKeyWord, goodsGuarantee,
                    goodsMainImages, goodsDesc, goodsSpecifications, goodsSKUs);
            goodsApproveApplication.modifyGoodsApprove(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品审核失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    public static void main(String[] args) {
        //specifications
        Map map = new HashMap<>();
        map.put("itemName", "尺寸");
        List<String> values = new ArrayList<>();
        values.add("L");
        values.add("M");
        map.put("itemValue", values);

        Map map1 = new HashMap<>();
        map1.put("itemName", "颜色");
        List<String> values1 = new ArrayList<>();
        values1.add("蓝色");
        values1.add("白色");
        map1.put("itemValue", values1);

        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map1);

        System.out.print(JsonUtils.toStr(list));

    }
}
