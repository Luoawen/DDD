package cn.m2c.scm.port.adapter.restful.admin.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

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

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.order.data.bean.MediaResOrderDetailBean;
import cn.m2c.scm.application.order.data.export.OrderMediaExpModel;
import cn.m2c.scm.application.order.data.export.SaleAfterExpModel;
import cn.m2c.scm.application.order.data.representation.MediaResOrderDetailBeanRepresentation;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.application.utils.ExcelUtil;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.port.adapter.service.order.OrderServiceImpl;

@RestController
@RequestMapping("/order-out")
public class AdminOrderOutAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminOrderOutAgent.class);
	
	@Autowired
	OrderQuery orderQuery;
	@Autowired
	OrderServiceImpl orderServiceImpl;
	/**
     * 给媒体提供的广告位订单明细
     * @return
     */
    @RequestMapping(value = "/orderdetail", method = RequestMethod.GET)
    public ResponseEntity<MPager> getMediaResOrderDetail(
    		@RequestParam(value = "userMessage", required = false) String userMessage,                 //下单用户名/账号
    		@RequestParam(value = "orderId", required = false) String orderId,                         //订单号
    		@RequestParam(value = "payStatus", required = false) Integer payStatus,                    //支付状态(-1已取消,0待付款,1已付款)
    		@RequestParam(value = "payWay", required = false) Integer payWay,                          //支付方式(1支付宝,2微信)
    		@RequestParam(value = "afterSellOrderType", required = false) Integer afterSellOrderType,  //售后方式(0换货,1退货退款,2仅退款)
    		//@RequestParam(value = "mediaIds", required = false) List mediaIds,                         //媒体编号
    		//@RequestParam(value = "mediaResIds", required = false) List mediaResIds,                   //广告位条码
    		@RequestParam(value = "mediaCate", required = false) String mediaCate,                     //媒体分类
    		@RequestParam(value = "mediaNo", required = false) Integer mediaNo,                        //媒体编号
    		@RequestParam(value = "mediaName", required = false) String mediaName,                     //媒体名
    		@RequestParam(value = "mresCate", required = false) Integer mresCate,                      //广告位位置
    		@RequestParam(value = "formId", required = false) Integer formId,                          //广告位形式
    		@RequestParam(value = "mresNo", required = false) Long mresNo,                             //广告位条码
    		@RequestParam(value = "goodsMessage", required = false) String goodsMessage,               //商品名/平台SKU
    		@RequestParam(value = "dealerName", required = false) String dealerName,                   //商家名
    		@RequestParam(value = "orderTime", required = false) String orderTime,                     //起始时间（下单）
    		@RequestParam(value = "orderEndTime", required = false) String orderEndTime, 			   //截至时间（下单）
    		@RequestParam(value = "pageOrNot", required = false, defaultValue = "1") Integer pageOrNot,//是否分页(0不分页，1分页)
    		@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
    		@RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows
    		){
    	MPager result = new MPager(MCode.V_1);
    	try {
			//根据用户名/账号查下单用户id,用户名/手机号(Map)
			Map<String,String> userMap = orderServiceImpl.getUserMobileOrUserName(userMessage);
			List<String> userIds = new ArrayList<String>();
			if(null != userMap && userMap.size() > 0) {//查到用户信息，封装userId用于订单查询
				Iterator<Entry<String,String>> iter = userMap.entrySet().iterator(); 
		        while(iter.hasNext()){ 
		            Entry<String,String> entry = iter.next(); 
		            String key = entry.getKey(); 
		            userIds.add(key);
		        }
			}else {//没有查到用户信息
				result.setContent("");
				result.setPager(userIds.size(), pageNum, rows);
		        result.setStatus(MCode.V_200);
		        return new ResponseEntity<MPager>(result,HttpStatus.OK);
			}
    	
    		if(StringUtils.isEmpty(userMessage)) {//无下单用户/账号信息,不限制userIds条件
    			userIds = null;
    		}
    		//查总数
    		Integer total = orderQuery.getMediaResOrderDetailTotal(userIds, orderId, payStatus, payWay, afterSellOrderType, mediaCate, mediaNo, mediaName, mresCate, formId, mresNo, goodsMessage, dealerName, orderTime, orderEndTime);
    		//Integer total = orderQuery.getMediaResOrderDetailTotal(userIds, orderId, payStatus, payWay, afterSellOrderType, mediaResIds, goodsMessage, dealerName, orderTime, orderEndTime);
    		if(total > 0){
    			List<MediaResOrderDetailBean> mediaResOrderDetailBeans = orderQuery.getMediaResOrderDetail(userIds, orderId, payStatus, payWay, afterSellOrderType, mediaCate, mediaNo, mediaName, mresCate, formId, mresNo,  goodsMessage, dealerName, orderTime, orderEndTime, pageOrNot, pageNum, rows);
    			//List<MediaResOrderDetailBean> mediaResOrderDetailBeans = orderQuery.getMediaResOrderDetail(userIds, orderId, payStatus, payWay, afterSellOrderType, mediaResIds, goodsMessage, dealerName, orderTime, orderEndTime, pageOrNot, pageNum, rows);
    			if(null != mediaResOrderDetailBeans && mediaResOrderDetailBeans.size() > 0){
    				List<MediaResOrderDetailBeanRepresentation> representations = new ArrayList<>();
    				for(MediaResOrderDetailBean adOrderDetailBean : mediaResOrderDetailBeans){
    					MediaResOrderDetailBeanRepresentation representation = new MediaResOrderDetailBeanRepresentation(adOrderDetailBean);
    					representation.setUserMessage(userMap.get(adOrderDetailBean.getUserId()));
    					//调用媒体接口,根据媒体编号,广告位形式id 获取 媒体分类,广告位位置,广告位形式
    		    		Map map = orderServiceImpl.getMediaCateAndFormMessage(adOrderDetailBean.getMediaCate(),adOrderDetailBean.getFormId());
    		    		//广告位位置map
    		    		Map advCateMap =  (Map) map.get("advCate");
    		    		Map advCateInfoMap = null;
    		    		if(adOrderDetailBean.getMresCate() != null) {
    		    			advCateInfoMap = (Map) advCateMap.get(adOrderDetailBean.getMresCate().toString());
    		    		}
    		    		representation.setMresCate(advCateInfoMap == null ? "" :(String)advCateInfoMap.get("dicName"));
    		    		//媒体分类
    		    		Map mediaCateMap = (Map)map.get("mediaCate");
    		    		representation.setMediaCate(mediaCateMap == null ? "" : (String)mediaCateMap.get("parCateName")+">"+(String)mediaCateMap.get("chdCateName"));
    		    		//广告位形式
    		    		representation.setFormId(map.get("formId") == null ? "" : (String)map.get("formId"));
    		    		//封装媒体相关信息
    					representations.add(representation);
    				}
    				result.setContent(representations);
    			}
    		}
    		result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
    	}catch (NegativeException ne) {
    		LOGGER.error("获取广告位订单明细失败,ne:" + ne.getMessage());
    		result = new MPager(ne.getStatus(), ne.getMessage());
    	} catch (Exception e) {
    		LOGGER.error("获取广告位订单明细失败,e:" + e.getMessage());
    		result = new MPager(MCode.V_400, "获取广告位订单明细失败");
    	}
    	return new ResponseEntity<MPager>(result,HttpStatus.OK);
    }
    
    /**
     * 给媒体的广告位订单明细导出
     * 
     * @param response
     * @param userMessage
     * @param orderId
     * @param payStatus
     * @param payWay
     * @param afterSellOrderType
     * @param mediaCate
     * @param mediaNo
     * @param mediaName
     * @param mresCate
     * @param formId
     * @param mresNo
     * @param goodsMessage
     * @param dealerName
     * @param orderTime
     * @param orderEndTime
     * @param pageOrNot
     * @param pageNum
     * @param rows
     */
    @RequestMapping(value = "/orderdetail/export", method = RequestMethod.GET)
    @RequirePermissions(value = {"scm:ordermedia:export"})
    public void exportExcel(HttpServletResponse response,
    		@RequestParam(value = "userMessage", required = false) String userMessage,                 //下单用户名/账号
    		@RequestParam(value = "orderId", required = false) String orderId,                         //订单号
    		@RequestParam(value = "payStatus", required = false) Integer payStatus,                    //支付状态(-1已取消,0待付款,1已付款)
    		@RequestParam(value = "payWay", required = false) Integer payWay,                          //支付方式(1支付宝,2微信)
    		@RequestParam(value = "afterSellOrderType", required = false) Integer afterSellOrderType,  //售后方式(0换货,1退货退款,2仅退款)
    		@RequestParam(value = "mediaCate", required = false) String mediaCate,                     //媒体分类
    		@RequestParam(value = "mediaNo", required = false) Integer mediaNo,                        //媒体编号
    		@RequestParam(value = "mediaName", required = false) String mediaName,                     //媒体名
    		@RequestParam(value = "mresCate", required = false) Integer mresCate,                      //广告位位置
    		@RequestParam(value = "formId", required = false) Integer formId,                          //广告位形式
    		@RequestParam(value = "mresNo", required = false) Long mresNo,                             //广告位条码
    		@RequestParam(value = "goodsMessage", required = false) String goodsMessage,               //商品名/平台SKU
    		@RequestParam(value = "dealerName", required = false) String dealerName,                   //商家名
    		@RequestParam(value = "orderTime", required = false) String orderTime,                     //起始时间（下单）
    		@RequestParam(value = "orderEndTime", required = false) String orderEndTime 			   //截至时间（下单）
    		) {
    	try {
    		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
    		String fileName = "广告位订单明细"+df.format(new Date())+".xls";
			//根据用户名/账号查下单用户id,用户名/手机号(Map)
			Map<String,String> userMap = orderServiceImpl.getUserMobileOrUserName(userMessage);
			List<String> userIds = new ArrayList<String>();
			if(null != userMap && userMap.size() > 0) {//查到用户信息，封装userId用于订单查询
				Iterator<Entry<String,String>> iter = userMap.entrySet().iterator(); 
		        while(iter.hasNext()){ 
		            Entry<String,String> entry = iter.next(); 
		            String key = entry.getKey(); 
		            userIds.add(key);
		        }
		        
		        if(StringUtils.isEmpty(userMessage)) {//无下单用户/账号信息,不限制userIds条件
	    			userIds = null;
	    		}
		        //做了限制,查5000条
    			List<MediaResOrderDetailBean> mediaResOrderDetailBeans = orderQuery.getMediaResOrderDetail(userIds, orderId, payStatus, payWay, afterSellOrderType, mediaCate, mediaNo, mediaName, mresCate, formId, mresNo,  goodsMessage, dealerName, orderTime, orderEndTime, 1, 1, 5000);

    			if(null != mediaResOrderDetailBeans && mediaResOrderDetailBeans.size() > 0){
    				List<OrderMediaExpModel> orderMediaList = new ArrayList<>();
    				for(MediaResOrderDetailBean adOrderDetailBean : mediaResOrderDetailBeans){
    					OrderMediaExpModel orderMediaExpModel = new OrderMediaExpModel(adOrderDetailBean);
    					orderMediaExpModel.setUserMessage(userMap.get(adOrderDetailBean.getUserId()));
    					//调用媒体接口,根据媒体编号,广告位形式id 获取 媒体分类,广告位位置,广告位形式
    		    		Map map = orderServiceImpl.getMediaCateAndFormMessage(adOrderDetailBean.getMediaCate(),adOrderDetailBean.getFormId());
    		    		//广告位位置map
    		    		Map advCateMap =  (Map) map.get("advCate");
    		    		Map advCateInfoMap = null;
    		    		if(adOrderDetailBean.getMresCate() != null) {
    		    			advCateInfoMap = (Map) advCateMap.get(adOrderDetailBean.getMresCate().toString());
    		    		}
    		    		orderMediaExpModel.setMresCate(advCateInfoMap == null ? "" :(String)advCateInfoMap.get("dicName"));
    		    		//媒体分类
    		    		Map mediaCateMap = (Map)map.get("mediaCate");
    		    		orderMediaExpModel.setMediaCate(mediaCateMap == null ? "" : (String)mediaCateMap.get("parCateName")+">"+(String)mediaCateMap.get("chdCateName"));
    		    		//广告位形式
    		    		orderMediaExpModel.setFormId(map.get("formId") == null ? "" : (String)map.get("formId"));
    		    		//封装媒体相关信息
    		    		orderMediaList.add(orderMediaExpModel);
    				}
    				if(orderMediaList.size() > 0) {
    					ExcelUtil.writeExcel(response, fileName, orderMediaList, OrderMediaExpModel.class);
    				}else {
    					ExcelUtil.writeExcel(response, fileName, null, OrderMediaExpModel.class);
    				}
    			}else {
    				ExcelUtil.writeExcel(response, fileName, null, OrderMediaExpModel.class);
    			}
			}else {//没有查到用户信息
				ExcelUtil.writeExcel(response, fileName, null, OrderMediaExpModel.class);
			}
    	} catch (NegativeException ne) {
    		LOGGER.error("广告位订单明细导出失败,ne:" + ne.getMessage());
    	} catch (Exception e) {
    		LOGGER.error("广告位订单明细导出失败,e:" + e.getMessage());
    	}
    }
    
	
}
