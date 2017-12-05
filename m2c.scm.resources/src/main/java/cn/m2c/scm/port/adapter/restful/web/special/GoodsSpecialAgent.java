package cn.m2c.scm.port.adapter.restful.web.special;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.special.GoodsSpecialApplication;
import cn.m2c.scm.application.special.command.GoodsSpecialAddCommand;
import cn.m2c.scm.application.special.command.GoodsSpecialModifyCommand;
import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.special.data.representation.GoodsSpecialListRepresentation;
import cn.m2c.scm.application.special.query.GoodsSpecialQueryApplication;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(value = "", method = RequestMethod.POST)
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
    @RequestMapping(value = "/{specialId}", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyGoodsSpecial(
            @PathVariable("specialId") String specialId,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "congratulations", required = false) String congratulations,
            @RequestParam(value = "activityDescription", required = false) String activityDescription,
            @RequestParam(value = "goodsSkuSpecials", required = false) String goodsSkuSpecials) {
        MResult result = new MResult(MCode.V_1);
        GoodsSpecialModifyCommand command = new GoodsSpecialModifyCommand(specialId, startTime, endTime, congratulations, activityDescription, goodsSkuSpecials);
        try {
            goodsSpecialApplication.modifyGoodsSpecial(command);
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
     * @param status 状态 0未生效，1已生效，2已失效
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param searchMessage 搜索条件(商家名/商品名)
     * @param pageNum 第几页
     * @param rows 每页多少行
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getGoodsSpecialAll(
    		@RequestParam(value = "status", required = false) Integer status,
    		@RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "searchMessage",required = false) String searchMessage, 
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows
    		){
    	MPager result = new MPager(MCode.V_1);
    	try {
    		//查总数
        	Integer total = goodsSpecialQueryApplication.queryGoodsSpecialCount(status, startTime, endTime, searchMessage);
        	if(total > 0) {
        		//查特惠价bean集合
        		List<GoodsSpecialBean> goodsSpecialBeanList =  goodsSpecialQueryApplication.queryGoodsSpecialBeanList(status, startTime, endTime, searchMessage, pageNum, rows);
        		if(null != goodsSpecialBeanList && goodsSpecialBeanList.size()>0 ) {
        			//封装表述对象
        			List<GoodsSpecialListRepresentation> representations = new ArrayList<GoodsSpecialListRepresentation>();
        			for(GoodsSpecialBean goodsSpecialBean : goodsSpecialBeanList) {
        				representations.add(new GoodsSpecialListRepresentation(goodsSpecialBean));
        			}
        			result.setContent(representations);
        		}
        	}
        	result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        	return new ResponseEntity<MPager>(result, HttpStatus.OK);
    	}catch (Exception e) {
    		LOGGER.error("getGoodsSpecialAll Exception e:", e);
            result = new MPager(MCode.V_400, "搜索特惠价信息失败");
    	}
    	return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    /**
     * 根据specialId查询商品特惠价详情
     * @param specialId
     * @return
     */
    @RequestMapping(value="/{specialId}",method=RequestMethod.GET)
    public ResponseEntity<MResult> getGoodsSpecialDetailBySpecialId(
    		@PathVariable String specialId
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
			GoodsSpecialBean goodsSpecialBean = goodsSpecialQueryApplication.queryGoodsSkuSpecialBeanBySpecialId(specialId);
			if(goodsSpecialBean != null) {
				result.setContent(goodsSpecialBean);
			}
		} catch (Exception e) {
			LOGGER.error("getGoodsSpecailDetailBySpecialId Exception e:",e);
			result = new MResult(MCode.V_400, "查询商品特惠价详情失败");
		}
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    public static void main(String[] args) {
        GoodsSkuSpecialBean bean = new GoodsSkuSpecialBean();
        bean.setSkuId("20171123193901738268");
        bean.setSkuName("辣的,蓝色");
        bean.setSupplyPrice(100l);
        bean.setSpecialPrice(200l);

        GoodsSkuSpecialBean bean1 = new GoodsSkuSpecialBean();
        bean1.setSkuId("20171123193901663962");
        bean1.setSkuName("酸的,蓝色");
        bean1.setSupplyPrice(100l);
        bean1.setSpecialPrice(200l);

        List<GoodsSkuSpecialBean> list = new ArrayList<>();
        list.add(bean);
        list.add(bean1);
        System.out.print(JsonUtils.toStr(list));
    }
}
