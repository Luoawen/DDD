package cn.m2c.scm.port.adapter.restful.web.dealerorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.order.DealerOrderApplication;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.util.GetDisconfDataGetter;

@RestController
@RequestMapping("/scheduled")
public class ScheduledAgent {

	private final static Logger LOGGER = LoggerFactory.getLogger(ScheduledAgent.class);

	@Autowired
	DealerOrderApplication dealerOrderApplication;
	
	@Autowired
	SaleAfterOrderApp saleAfterOrderApplication;
	
	@Autowired
	OrderApplication orderApp;

	/**
	 * 判断是否已满足确认收货条件<待收货状态下七天后自动收货为完成状态>
	 * 
	 * @return
	 */
	@RequestMapping(value = "/statusFinishied", method = RequestMethod.PUT)
	public ResponseEntity<MResult> estimateOrderCinfim() {
		MResult result = new MResult(MCode.V_1);

		try {
			dealerOrderApplication.updateOrderStatus();
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改订单状态发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 完成状态下超过7天变更为交易完成
	 * @return
	 */
	@RequestMapping(value = "/statusDealFinished",method = RequestMethod.PUT)
	public ResponseEntity<MResult> dealFinished() {
		
		MResult result = new MResult(MCode.V_1);

		try {
			dealerOrderApplication.updateDealFinished();
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改订单状态发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 待付款状态下超过24H小时变更为已取消
	 * @return
	 */
	
	@RequestMapping(value = "/statusWaitPay",method = RequestMethod.PUT)
	public ResponseEntity<MResult> waitPay() {
		MResult result = new MResult(MCode.V_1);
		LOGGER.error("--定时请求检查是否有要取消的订单------------------------------------------");
		try {
			//dealerOrderApplication.updateWaitPay();
			orderApp.cancelAllNotPayed();
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("修改订单状态发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 商家同意售后状态下7天变更为交易关闭
	 * @return
	 */
	@RequestMapping(value = "/statusAgreeAfterSell",method = RequestMethod.PUT)
	public ResponseEntity<MResult> statusAgreeAfterSell() {
		MResult result = new MResult(MCode.V_1);

		try {
			saleAfterOrderApplication.updataStatusAgreeAfterSale();
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改订单状态发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	/***
	 * 已经申请售后的但还没有同意的状态，自动取消售后
	 * @return
	 */
	@RequestMapping(value = "/after/sale/applyed",method = RequestMethod.PUT)
	public ResponseEntity<MResult> afterSaleApply() {
		
		MResult result = new MResult(MCode.V_1);
		try {
			String val = GetDisconfDataGetter.getDisconfProperty("scm.job.user");
			saleAfterOrderApplication.cancelApply(val);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改售后订单状态", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/***
	 * 当商家同意售后， 7天没有同意退款，则自动退款
	 * @return
	 */
	@RequestMapping(value = "/after/sale/agreed",method = RequestMethod.PUT)
	public ResponseEntity<MResult> afterSaleAgreed() {
		
		MResult result = new MResult(MCode.V_1);
		try {
			String val = GetDisconfDataGetter.getDisconfProperty("scm.job.user");
			saleAfterOrderApplication.afterAgreed(val);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("自动退款状态", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/***
	 * 当商家同意售后， 退货类型且用户发货， 过七天需要自动收货商家
	 * @return
	 */
	@RequestMapping(value = "/after/sale/user-send",method = RequestMethod.PUT)
	public ResponseEntity<MResult> userSend2DealerAuto() {
		
		MResult result = new MResult(MCode.V_1);
		try {
			String val = GetDisconfDataGetter.getDisconfProperty("scm.job.user");
			saleAfterOrderApplication.dealerAutoRec(val);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("自动退款状态", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/***
	 * 当商家同意售后， 换货类型且商家发货， 过七天需要用户自动收货
	 * @return
	 */
	@RequestMapping(value = "/after/sale/dealer-send",method = RequestMethod.PUT)
	public ResponseEntity<MResult> dealerSend2UserAuto() {
		
		MResult result = new MResult(MCode.V_1);
		try {
			String val = GetDisconfDataGetter.getDisconfProperty("scm.job.user");
			saleAfterOrderApplication.userAutoRec(val);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("自动退款状态", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
}
