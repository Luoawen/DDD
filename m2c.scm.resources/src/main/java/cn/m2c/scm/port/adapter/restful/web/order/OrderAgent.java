package cn.m2c.scm.port.adapter.restful.web.order;

import java.util.ArrayList;
import java.util.List;

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
import cn.m2c.common.MResult;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.data.bean.OrderDealerBean;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.mainOrderBean;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.port.adapter.restful.web.brand.BrandAgent;


@RestController
@RequestMapping("/order")
public class OrderAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BrandAgent.class);
	
	@Autowired
	OrderApplication orderapplication;
	
	@Autowired
	OrderQuery orderQuery;
	
	/**
	 * 查询订单列表
	 * @param orderStatus 订单状态
	 * @param afterSellStatus 售后状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param condition 搜索条件(goodsName,orderId,payNo,revPhone,dealerName,dealerId)
	 * @param payWay 支付方式
	 * @param pageNum 第几页
	 * @param rows 每页多少行
	 * @return
	 */
	@RequestMapping(value = "/manager/orderList",method = RequestMethod.GET)
	public ResponseEntity<MPager> getOrderList(
			@RequestParam(value = "orderStatus",required = false)Integer orderStatus,
			@RequestParam(value = "afterSellStatus",required = false)Integer afterSellStatus,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime,
			@RequestParam(value = "condition",required = false)String condition,
			@RequestParam(value = "payWay",required = false)Integer payWay,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows
			) {
		MPager result = new MPager(MCode.V_1);
		try {
			Integer total = orderQuery.mainOrderQueryTotal(orderStatus, afterSellStatus, startTime, endTime, condition, payWay);
			List<mainOrderBean> mainOrderList = orderQuery.mainOrderListQuery(orderStatus, afterSellStatus, startTime, endTime, condition, payWay, pageNum, rows);
			
			result.setPager(total, pageNum, rows);
			result.setContent(mainOrderList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取订单列表出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result,HttpStatus.OK);
	}

	/**
	 * 订货单详情
	 * @param dealerOrderId
	 * @return
	 */
	@RequestMapping(value = "/manager/orderDetail",method = RequestMethod.GET)
	public ResponseEntity<MResult> getOrderDetail(@RequestParam(value = "dealerOrderId",required = false)String dealerOrderId){
		MResult result = new MResult(MCode.V_1);
		try {
			DealerOrderDetailBean dealerDetail = orderQuery.dealerOrderDetailQuery(dealerOrderId);
			if (null != dealerDetail) {
				result.setContent(dealerDetail);
				result.setStatus(MCode.V_200);
			}
		} catch (Exception e) {
			LOGGER.error("获取商家订单出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result,HttpStatus.OK);
	}
	
	/**
	 * 
	 */
	@RequestMapping(value = "/dmanager/deliverdetail",method = RequestMethod.GET)
	public ResponseEntity<MResult> deliverDetail(@RequestParam(value = "orderDetailId",required = false)String orderDetailId){
		
		return null;
		
	}
	
}
