package cn.m2c.scm.port.adapter.restful.web.dealerorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MResult;


/**
 * 商家平台订货单
 * @author lqwen
 *
 */
@RestController
@RequestMapping("/dealerorder")
public class DealerOrderAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(DealerOrderAgent.class);

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
	public ResponseEntity<MResult> getDealerOrderList(
			@RequestParam(value = "dealerOrderId",required = false)String dealerOrderId,
			@RequestParam(value = "orderStatus",required = false)Integer orderStatus,
			@RequestParam(value = "afterSellStatus",required = false)Integer afterSellStatus,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime,
			@RequestParam(value = "condition",required = false)String condition,
			@RequestParam(value = "payWay",required = false)Integer payWay,
			@RequestParam(value = "commentStatus",required = false)Integer commentStatus,
			@RequestParam(value = "mediaInfo",required = false)Integer mediaInfo,
			@RequestParam(value = "orderClassify",required = false)Integer orderClassify,
			@RequestParam(value = "invoice",required = false)Integer invoice,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {
		return null;
		
	}
	
}
