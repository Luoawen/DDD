package cn.m2c.scm.port.adapter.restful.web.order;

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
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.AftreSellLogisticsBean;
import cn.m2c.scm.application.order.query.AfterSellOrderQuery;

@RestController
@RequestMapping("/aftersellorder")
public class AfterSellOrderAgent {

	private final static Logger LOGGER = LoggerFactory.getLogger(AfterSellOrderAgent.class);

	@Autowired
	AfterSellOrderQuery afterSellOrderQuery;

	/**
	 * 查询售后单列表
	 * 
	 * @param orderType
	 *            售后期望
	 * @param status
	 *            售后状态
	 * @param createDate
	 *            申请时间
	 * @param condition
	 *            搜索条件(商品名称、订单号、售后号)
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param dealerClassify
	 *            商家类型
	 * @param mediaInfo
	 *            媒体信息(判断media_id是否为空)
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/manager/aftersellorderlist", method = RequestMethod.GET)
	public ResponseEntity<MPager> getAfterSellOrderList(
			@RequestParam(value = "orderType", required = false) Integer orderType,
			@RequestParam(value = "status", required = false) Integer status,
			@RequestParam(value = "createDate", required = false) String createDate,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "dealerClassify", required = false) String dealerClassify,
			@RequestParam(value = "mediaInfo", required = false) String mediaInfo,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
		MPager result = new MPager(MCode.V_1);

		try {
			Integer total = afterSellOrderQuery.totalAfterSelleOrderQuery(orderType, status, createDate, condition,
					startTime, endTime, dealerClassify, mediaInfo);
			List<AfterSellOrderBean> afterSelleOrderList = afterSellOrderQuery.afterSelleOrderQuery(orderType, status,
					createDate, condition, startTime, endTime, dealerClassify, mediaInfo, pageNum, rows);
			System.out.println(afterSelleOrderList);
			result.setPager(total, pageNum, rows);
			result.setContent(afterSelleOrderList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取售后订单列表出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}

	/**
	 * 售后物流信息
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	@RequestMapping("/manager/aftreselllogistics")
	public ResponseEntity<MResult> getAftreSellLogistics(
			@RequestParam(value = "afterSellOrderId", required = false) String afterSellOrderId) {
		MResult result = new MResult(MCode.V_1);
		try {
			AftreSellLogisticsBean afterSellOrderLogistics = afterSellOrderQuery
					.afterSellOrderLogisticsQuery(afterSellOrderId);
			result.setContent(afterSellOrderLogistics);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取售后物流信息出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 售后单详情
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	@RequestMapping("/manager/aftersellorderdetail")
	public ResponseEntity<MResult> afterSellOrderDetail(
			@RequestParam(value = "afterSellOrderId", required = false) String afterSellOrderId) {
		MResult result = new MResult(MCode.V_1);
		try {
			AfterSellOrderDetailBean afterSellOrderDetail = afterSellOrderQuery
					.afterSellOrderDetailQeury(afterSellOrderId);
			if (null != afterSellOrderDetail) {
				result.setContent(afterSellOrderDetail);
				result.setStatus(MCode.V_200);
			}
		} catch (Exception e) {
			LOGGER.error("获取售后详情信息出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);

	}

}
