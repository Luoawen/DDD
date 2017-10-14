package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.CommonApplication;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.command.GoodsCommand;
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
 * 商品
 *
 * @author ps
 */
@RestController
@RequestMapping("/goods")
public class GoodsAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsAgent.class);

    @Autowired
    GoodsApplication goodsApplication;
    @Autowired
    CommonApplication commonApplication;

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
            List<Map<String, Object>> goodsList = new ArrayList<>();
            Map map = new HashMap<>();
            map.put("goodsId", "SP449EF119C6974667B9F5881C080EE5D2");
            map.put("goodsName", "跑步机");
            map.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
            map.put("goodsPrice", 249000);
            map.put("dealerId", "JXS0F3701D134054EFAB962792CB0866086");
            map.put("dealerName", "飞鸽牌");

            List<Map<String, Object>> ruleList1 = new ArrayList<>();
            Map ruleMap1 = new HashMap<>();
            ruleMap1.put("goodsSkuId", "SPGG449EF119C6974667B9F5881C080EE5D2");
            ruleMap1.put("goodsSkuName", "L,红色");
            ruleMap1.put("goodsSkuInventory", 400);
            ruleMap1.put("goodsSkuPrice", 249000);
            Map ruleMap2 = new HashMap<>();
            ruleMap2.put("goodsSkuId", "SPGG449EF119C6974667B9F5881C080EE5D2");
            ruleMap2.put("goodsSkuName", "L,黄色");
            ruleMap2.put("goodsSkuInventory", 400);
            ruleMap2.put("goodsSkuPrice", 50000);
            ruleList1.add(ruleMap1);
            ruleList1.add(ruleMap2);
            map.put("goodsSkuList", ruleList1);


            Map map1 = new HashMap<>();
            map1.put("goodsId", "SP38C4D0B014E24B64B021EAC4D813A696");
            map1.put("goodsName", "儿童自行车");
            map1.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/bx2L173127.jpg");
            map1.put("goodsPrice", 89900);
            map1.put("dealerId", "JXS0F3701D134054EFAB962792CB0866086");
            map1.put("dealerName", "凤凰牌");

            goodsList.add(map);
            goodsList.add(map1);
            result.setContent(goodsList);

            List<Map<String, Object>> ruleList2 = new ArrayList<>();
            Map ruleMap3 = new HashMap<>();
            ruleMap3.put("goodsSkuId", "SPGG449EF119C6974667B9F5881C080EE5D2");
            ruleMap3.put("goodsSkuName", "L,蓝色");
            ruleMap3.put("goodsSkuInventory", 500);
            ruleMap3.put("goodsSkuPrice", 89900);
            Map ruleMap4 = new HashMap<>();
            ruleMap4.put("goodsSkuId", "SPGG449EF119C6974667B9F5881C080EE5D2");
            ruleMap4.put("goodsSkuName", "L,白色");
            ruleMap4.put("goodsSkuInventory", 500);
            ruleMap4.put("goodsSkuPrice", 99900);
            ruleList2.add(ruleMap3);
            ruleList2.add(ruleMap4);
            map1.put("goodsSkuList", ruleList2);


            result.setPager(2, pageNum, rows);
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
    public ResponseEntity<MPager> goodsDetail(
            @RequestParam(value = "goodsId", required = false) String goodsId) {
        MPager result = new MPager(MCode.V_1);
        try {
            Map map = new HashMap<>();
            map.put("goodsName", "跑步机");
            map.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
            map.put("goodsPrice", 249000);
            map.put("goodsClassifyId", "SPFL449EF119C6974667B9F5881C080EE5D2");
            map.put("dealerId", "SP449EF119C6974667B9F5881C080EE5D2");
            map.put("dealerName", "飞鸽牌");
            result.setContent(map);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods Detail Exception e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
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
            List<Map<String, Object>> goodsList = new ArrayList<>();
            Map map = new HashMap<>();
            map.put("goodsId", "SP449EF119C6974667B9F5881C080EE5D2");
            map.put("goodsName", "跑步机");
            map.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
            Map map1 = new HashMap<>();
            map1.put("goodsId", "SP38C4D0B014E24B64B021EAC4D813A696");
            map1.put("goodsName", "儿童自行车");
            map1.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/bx2L173127.jpg");
            goodsList.add(map);
            goodsList.add(map1);
            result.setContent(goodsList);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goodsDetails Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 增加商品
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
    public ResponseEntity<MResult> modifyGoods(
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
            @RequestParam(value = "goodsSKUs", required = false) String goodsSKUs) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<Map> skuList = JsonUtils.toList(goodsSKUs, Map.class);
            // 生成sku
            List<String> skuCodes = new ArrayList<>();
            if (null != skuList && skuList.size() > 0) {
                for (Map map : skuList) {
                    String skuId = null != map.get("skuId") ? map.get("skuId").toString() : null;
                    if (StringUtils.isEmpty(skuId)) {
                        try {
                            skuId = commonApplication.generateGoodsSku();
                            skuCodes.add(skuId);
                        } catch (Exception e) { //失败重新生成一次
                            skuId = commonApplication.generateGoodsSku();
                            skuCodes.add(skuId);
                        }
                        map.put("skuId",skuId);
                    }
                }
                goodsSKUs = JsonUtils.toStr(skuList);
            } else {
                result = new MResult(MCode.V_1, "商品规格为空");
                return new ResponseEntity<MResult>(result, HttpStatus.OK);
            }
            GoodsCommand command = new GoodsCommand(goodsId, dealerId, goodsName, goodsSubTitle,
                    goodsClassifyId, goodsBrandId, goodsUnitId, goodsMinQuantity,
                    goodsPostageId, goodsBarCode, goodsKeyWord, JsonUtils.toStr(goodsGuarantee),
                    JsonUtils.toStr(goodsMainImages), goodsDesc, goodsSKUs);
            goodsApplication.modifyGoods(command);
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
}
