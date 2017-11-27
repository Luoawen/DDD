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
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderAfterSellDetailBean;
import cn.m2c.scm.application.dealerorder.data.bean.SaleFreightBean;
import cn.m2c.scm.application.dealerorder.query.DealerOrderAfterSellQuery;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.domain.NegativeException;

@RestController
@RequestMapping("/dealerorderafter")
public class DealerOrderAfterSellAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DealerOrderAfterSellAgent.class);
	
	@Autowired
	DealerOrderAfterSellQuery dealerOrderAfterSellQuery;

	/**
	 * 商家订单售后列表
	 * @param orderType(期望售后)
	 * @param status(售后状态)
	 * @param startTime(开始时间)
	 * @param endTime(结束时间)
	 * @param condition(搜索条件<goodsName,orderId,afterSellOrderId>)
	 * @param mediaInfo(媒体信息)
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/dealerorderafterselllist",method = RequestMethod.GET)
	public ResponseEntity<MPager> getDealerOrderAfterSellList(
			@RequestParam(value = "dealerId",required = false)String dealerId,
			@RequestParam(value = "dealerOrderId",required = false)String dealerOrderId,
			@RequestParam(value = "orderType",required = false)Integer orderType,
			@RequestParam(value = "status",required = false)Integer status,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime,
			@RequestParam(value = "condition",required = false)String condition,
			@RequestParam(value = "mediaInfo",required = false)String mediaInfo,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
		MPager result = new MPager(MCode.V_1);
		
		try {
			Integer total = dealerOrderAfterSellQuery.totalDealerOrderAfterSellQuery(orderType, dealerId, status, condition, startTime, endTime, mediaInfo);
			List<AfterSellOrderBean> afterSellOrderList = dealerOrderAfterSellQuery.dealerOrderAfterSellQuery(orderType, dealerId,dealerOrderId, status, condition, startTime, endTime, mediaInfo, pageNum, rows);
			result.setContent(afterSellOrderList);
			result.setPager(total, pageNum, rows);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取商家订单售后列表出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了"); 
		}
		return new ResponseEntity<MPager>(result,HttpStatus.OK);
	}
	
	/**
	 * 获取商家订单售后详情(退货/换货)
	 * @param dealerId
	 * @param afterSellOrderId
	 * @return
	 */
	@RequestMapping(value = "/dealerorderafterselldetail",method = RequestMethod.GET)
	public ResponseEntity<MResult> getAfterSellDealerOrderDetail(
			@RequestParam(value = "dealerId",required = false)String dealerId,
			@RequestParam(value = "afterSellOrderId",required = false)String afterSellOrderId) {
			
		MResult result = new MResult(MCode.V_1);
		
		try {
			DealerOrderAfterSellDetailBean afterSellDealerOrderDetail = dealerOrderAfterSellQuery.afterSellDealerOrderDetailQeury(afterSellOrderId, dealerId);
			result.setContent(afterSellDealerOrderDetail);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取商家订单售后详情出错" + e.getMessage(), e);
            result = new MResult(MCode.V_400, "服务器开小差了"); 
		}
		
		return new ResponseEntity<MResult>(result,HttpStatus.OK);
		
	}
	
	/**
	 * 获取售后已经同意退的运费
	 * @param dealerId
	 * @param afterSellOrderId
	 * @return
	 */
	@RequestMapping(value = "/cost/freight",method = RequestMethod.GET)
	public ResponseEntity<MResult> getCostFreight(
			@RequestParam(value="userId", required = false)String userId
			,@RequestParam(value="dealerOrderId", required = false) String dealerOrderId
			,@RequestParam(value="skuId", required= false) String skuId) {
			
		MResult result = new MResult(MCode.V_1);
		
		try {
			SaleFreightBean obj = dealerOrderAfterSellQuery.getHasRtFreight(dealerOrderId, skuId);
			result.setContent(obj);
			result.setStatus(MCode.V_200);
		}
		catch (NegativeException e) {
			result.setStatus(e.getStatus());
			result.setErrorMessage(e.getMessage());
		}
		catch (Exception e) {
			LOGGER.error("获取商家订单售后详情出错" + e.getMessage(), e);
            result = new MResult(MCode.V_400, "服务器开小差了"); 
		}
		
		return new ResponseEntity<MResult>(result,HttpStatus.OK);
		
	}
}
