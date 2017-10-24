package cn.m2c.scm.port.adapter.restful.web.dealerorder;

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
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderBean;
import cn.m2c.scm.application.dealerorder.query.DealerOrderQuery;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;


/**
 * 商家平台订货单
 * @author lqwen
 *
 */
@RestController
@RequestMapping("/dealerorder")
public class DealerOrderAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DealerOrderAgent.class);
	
	@Autowired
	DealerOrderQuery dealerOrderQuery;

	/**
	 * 查询订单列表
	 * @param dealerOrderId 商家订单号
	 * @param orderStatus 订单状态
	 * @param afterSellStatus 售后状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param condition 搜索条件(goodsName,dealerOrderId,payNo,revPhone)
	 * @param payWay 支付方式
	 * @param commentStatus 评论状态
	 * @param mediaInfo 广告位
	 * @param orderClassify 订单类型
	 * @param invoice 开发票
	 * @param pageNum 第几页
	 * @param rows 每页多少行
	 * @return
	 */
	@RequestMapping(value = "/dealerorderlist",method = RequestMethod.GET)
	public ResponseEntity<MPager> getDealerOrderList(
			@RequestParam(value = "dealerId",required = false) String dealerId,
			@RequestParam(value = "dealerOrderId",required = false)String dealerOrderId,
			@RequestParam(value = "orderStatus",required = false)Integer orderStatus,
			@RequestParam(value = "afterSellStatus",required = false)Integer afterSellStatus,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime,
			@RequestParam(value = "condition",required = false)String condition,
			@RequestParam(value = "payWay",required = false)Integer payWay,
			@RequestParam(value = "commentStatus",required = false)Integer commentStatus,
			@RequestParam(value = "mediaInfo",required = false)String mediaInfo,
			@RequestParam(value = "orderClassify",required = false)Integer orderClassify,
			@RequestParam(value = "invoice",required = false)Integer invoice,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
		
		MPager result = new MPager(MCode.V_1);
		
		try {
			Integer total = dealerOrderQuery.dealerOrderTotalQuery(dealerId,dealerOrderId, orderStatus, afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify, mediaInfo, invoice);
			List<DealerOrderBean> dealerOrderList = dealerOrderQuery.dealerOrderQuery(dealerId,dealerOrderId, orderStatus, afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify, mediaInfo, invoice, pageNum, rows);
			if (dealerOrderList != null) {
				result.setContent(dealerOrderList);
				result.setPager(total, pageNum, rows);
				result.setStatus(MCode.V_200);
			}
		} catch (Exception e) {
			LOGGER.error("获取商家订单列表出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result,HttpStatus.OK);
	}
	
	/**
	 * 商家订单详情
	 * @param dealerOrderId
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/dealerorderdetail",method = RequestMethod.GET)
	public ResponseEntity<MResult> getDealerOrderDetail(
			@RequestParam(value = "dealerId",required = false) String dealerId,
			@RequestParam(value = "dealerOrderId",required = false)String dealerOrderId) {
		MResult result = new MResult(MCode.V_1);
		
		try {
			DealerOrderDetailBean orderDetail = dealerOrderQuery.dealerOrderDetailQuery(dealerOrderId, dealerId);
				result.setContent(orderDetail);
				result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取商家订单详情出错" + e.getMessage(), e);
            result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result,HttpStatus.OK);
	}
	
}
