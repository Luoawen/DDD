package cn.m2c.scm.port.adapter.restful.web.special;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.special.GoodsSpecialApplication;
import cn.m2c.scm.application.special.command.GoodsSpecialAddCommand;
import cn.m2c.scm.application.special.command.GoodsSpecialModifyCommand;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.special.data.representation.GoodsSpecialDetailBeanRepresentation;
import cn.m2c.scm.application.special.data.representation.GoodsSpecialListRepresentation;
import cn.m2c.scm.application.special.query.GoodsSpecialQueryApplication;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.special.GoodsSkuSpecial;
import cn.m2c.scm.domain.util.GetMapValueUtils;
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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 商品特惠价
 */
@RestController
@RequestMapping("/goods/special")
public class GoodsSpecialAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecialAgent.class);

    @Autowired
    GoodsSpecialApplication goodsSpecialApplication;
    @Autowired
    GoodsSpecialQueryApplication goodsSpecialQueryApplication;

    @Autowired
    private HttpServletRequest request;
    
    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getGoodsSpecialId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_GOODS_SPECIAL_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getGoodsSpecialId Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }


    /**
     * 增加商品特惠价
     *
     * @param specialId           特惠编号
     * @param goodsId             商品id
     * @param goodsName           商品名称
     * @param skuFlag             是否是多规格：0：单规格，1：多规格
     * @param dealerId            商家id
     * @param dealerName          商家名称
     * @param startTime           开始时间
     * @param endTime             结束时间
     * @param congratulations     祝贺语
     * @param activityDescription 活动描述
     * @param goodsSkuSpecials    格式：[{"skuId":"20171123193901738268","skuName":"辣的,蓝色","specialPrice":200,"supplyPrice":100},{"skuId":"20171123193901663962","skuName":"酸的,蓝色","specialPrice":200,"supplyPrice":100}]
     * @return
     */
    @RequestMapping(value = "/mng", method = RequestMethod.POST)
    @RequirePermissions(value = {"scm:special:add"})
    public ResponseEntity<MResult> addGoodsSpecial(
            @RequestParam(value = "specialId", required = false) String specialId,
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "goodsName", required = false) String goodsName,
            @RequestParam(value = "skuFlag", required = false) Integer skuFlag,
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "dealerName", required = false) String dealerName,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "congratulations", required = false) String congratulations,
            @RequestParam(value = "activityDescription", required = false) String activityDescription,
            @RequestParam(value = "goodsSkuSpecials", required = false) String goodsSkuSpecials) {
        MResult result = new MResult(MCode.V_1);
        List<Map> list = JsonUtils.toList(goodsSkuSpecials, Map.class);
        if (null != list && list.size() > 0) {
            List<GoodsSkuSpecial> goodsSpecials = new ArrayList<>();
            for (Map map : list) {
                if (null != map.get("supplyPrice") && !"".equals(map.get("supplyPrice"))) {
                    Long supplyPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice") * 10000)).longValue();
                    map.put("supplyPrice", supplyPrice);
                }

                Long specialPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "specialPrice") * 10000)).longValue();

                map.put("specialPrice", specialPrice);
            }
            goodsSkuSpecials = JsonUtils.toStr(list);
        }
        GoodsSpecialAddCommand command = new GoodsSpecialAddCommand(specialId, goodsId, goodsName, skuFlag, dealerId,
                dealerName, startTime, endTime, congratulations, activityDescription, goodsSkuSpecials);
        try {
            goodsSpecialApplication.addGoodsSpecial(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (ParseException pe) {
            LOGGER.error("addGoodsSpecial ParseException e:", pe);
            result = new MResult(MCode.V_500, "添加商品特惠活动失败");
        } catch (Exception e) {
            LOGGER.error("addGoodsSpecial Exception e:", e);
            result = new MResult(MCode.V_400, "添加商品特惠活动失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 增加商品特惠价
     *
     * @param specialId           特惠编号
     * @param startTime           开始时间
     * @param endTime             结束时间
     * @param congratulations     祝贺语
     * @param activityDescription 活动描述
     * @param goodsSkuSpecials    格式：[{"skuId":"20171123193901738268","specialPrice":200,"supplyPrice":100},{"skuId":"20171123193901663962","specialPrice":200,"supplyPrice":100}]
     * @return
     */
    @RequestMapping(value = "/mng/{specialId}", method = RequestMethod.PUT)
    @RequirePermissions(value = {"scm:special:update"})
    public ResponseEntity<MResult> modifyGoodsSpecial(
            @PathVariable("specialId") String specialId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "congratulations", required = false) String congratulations,
            @RequestParam(value = "activityDescription", required = false) String activityDescription,
            @RequestParam(value = "goodsSkuSpecials", required = false) String goodsSkuSpecials) {
        MResult result = new MResult(MCode.V_1);
        List<Map> list = JsonUtils.toList(goodsSkuSpecials, Map.class);
        if (null != list && list.size() > 0) {
            List<GoodsSkuSpecial> goodsSpecials = new ArrayList<>();
            for (Map map : list) {
                if (null != map.get("supplyPrice") && !"".equals(map.get("supplyPrice"))) {
                    Long supplyPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "supplyPrice") * 10000)).longValue();
                    map.put("supplyPrice", supplyPrice);
                }

                Long specialPrice = new BigDecimal((GetMapValueUtils.getFloatFromMapKey(map, "specialPrice") * 10000)).longValue();

                map.put("specialPrice", specialPrice);
            }
            goodsSkuSpecials = JsonUtils.toStr(list);
        }
        GoodsSpecialModifyCommand command = new GoodsSpecialModifyCommand(specialId, startTime, endTime, congratulations, activityDescription, goodsSkuSpecials);
        try {
        	String _attach = request.getHeader("attach");
            goodsSpecialApplication.modifyGoodsSpecial(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (ParseException pe) {
            LOGGER.error("modifyGoodsSpecial ParseException e:", pe);
            result = new MResult(MCode.V_500, "修改商品特惠活动失败");
        } catch (Exception e) {
            LOGGER.error("modifyGoodsSpecial Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品特惠活动失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 特惠价首页搜素
     *
     * @param status        状态 0未生效，1已生效，2已失效
     * @param startTime     开始时间
     * @param endTime       结束时间
     * @param searchMessage 搜索条件(商家名/商品名)
     * @param pageNum       第几页
     * @param rows          每页多少行
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getGoodsSpecialAll(
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "searchMessage", required = false) String searchMessage,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows
    ) {
        MPager result = new MPager(MCode.V_1);
        try {
            //查总数
            Integer total = goodsSpecialQueryApplication.queryGoodsSpecialCount(status, startTime, endTime, searchMessage);
            if (total > 0) {
                List<GoodsSpecialBean> goodsSpecialBeanLists = goodsSpecialQueryApplication.queryGoodsSpecialBeanList(status, startTime, endTime, searchMessage, pageNum, rows);
                if (null != goodsSpecialBeanLists && goodsSpecialBeanLists.size() > 0) {
                    List<GoodsSpecialListRepresentation> representations = new ArrayList<GoodsSpecialListRepresentation>();
                    for (GoodsSpecialBean bean : goodsSpecialBeanLists) {
                        representations.add(new GoodsSpecialListRepresentation(bean));
                    }
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
            return new ResponseEntity<MPager>(result, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("getGoodsSpecialAll Exception e:", e);
            result = new MPager(MCode.V_400, "搜索特惠价信息失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 根据specialId查询商品特惠价详情
     *
     * @param specialId
     * @return
     */
    @RequestMapping(value = "/{specialId}", method = RequestMethod.GET)
    public ResponseEntity<MResult> getGoodsSpecialDetailBySpecialId(
            @PathVariable String specialId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsSpecialDetailBeanRepresentation goodsSpecialDetailBeanRepresentation = goodsSpecialQueryApplication.queryGoodsSpecialDetailBeanRepresentationBySpecialId(specialId);
            if (goodsSpecialDetailBeanRepresentation != null) {
                result.setContent(goodsSpecialDetailBeanRepresentation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getGoodsSpecialDetailBySpecialId Exception e:", e);
            result = new MResult(MCode.V_400, "查询商品特惠价详情失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商品特惠价终止
     *
     * @param specialId
     * @return
     */
    @RequestMapping(value = "/mng/end/{specialId}", method = RequestMethod.POST)
    @RequirePermissions(value = {"scm:special:stop"})
    public ResponseEntity<MResult> endGoodsSpecial(
            @PathVariable String specialId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
        	String _attach = request.getHeader("attach");
            goodsSpecialApplication.endGoodsSpecial(specialId, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("endGoodsSpecial NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("endGoodsSpecial Exception e:", e);
            result = new MResult(MCode.V_400, "终止商品特惠活动失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

}
