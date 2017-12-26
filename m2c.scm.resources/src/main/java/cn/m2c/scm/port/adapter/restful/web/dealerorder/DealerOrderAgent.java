package cn.m2c.scm.port.adapter.restful.web.dealerorder;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import cn.m2c.common.MResult;
import cn.m2c.common.StringUtil;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderQB;
import cn.m2c.scm.application.dealerorder.query.DealerOrderQuery;
import cn.m2c.scm.application.order.DealerOrderApplication;
import cn.m2c.scm.application.order.command.UpdateAddrCommand;
import cn.m2c.scm.application.order.command.UpdateAddrFreightCmd;
import cn.m2c.scm.application.order.command.UpdateOrderFreightCmd;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.domain.NegativeException;

/**
 * 商家平台订货单
 * 
 * @author lqwen
 *
 */
@RestController
@RequestMapping("/dealerorder")
public class DealerOrderAgent {

	private final static Logger LOGGER = LoggerFactory.getLogger(DealerOrderAgent.class);

	@Autowired
	DealerOrderQuery dealerOrderQuery;

	@Autowired
	DealerOrderApplication dealerOrderApplication;

	@Autowired
	private  HttpServletRequest request;
	/**
	 * 查询订单列表
	 * 
	 * @param dealerId
	 *            商家ID
	 * @param orderStatus
	 *            订单状态
	 * @param afterSellStatus
	 *            售后状态
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param condition
	 *            搜索条件(goodsName,dealerOrderId,payNo,revPhone)
	 * @param payWay
	 *            支付方式
	 * @param commentStatus
	 *            评论状态
	 * @param mediaInfo
	 *            广告位
	 * @param orderClassify
	 *            订单类型
	 * @param invoice
	 *            开发票
	 * @param pageNum
	 *            第几页
	 * @param rows
	 *            每页多少行
	 * @return
	 */
	@RequestMapping(value = {"/dealerorderlist", "/web/dealerorderlist"}, method = RequestMethod.GET)
	public ResponseEntity<MPager> getDealerOrderList(
			@RequestParam(value = "dealerId", required = false) String dealerId,
			@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId,
			@RequestParam(value = "orderStatus", required = false) Integer orderStatus,
			@RequestParam(value = "afterSellStatus", required = false) Integer afterSellStatus,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "payWay", required = false) Integer payWay,
			@RequestParam(value = "commentStatus", required = false) Integer commentStatus,
			@RequestParam(value = "hasMedia", required = false) Integer hasMedia,
			@RequestParam(value = "orderClassify", required = false) Integer orderClassify,
			@RequestParam(value = "invoice", required = false) Integer invoice,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows) {

		MPager result = new MPager(MCode.V_1);

		try {
			/*Integer total = dealerOrderQuery.dealerOrderTotalQuery(dealerId, dealerOrderId, orderStatus,
					afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify, mediaInfo,
					invoice);
			List<DealerOrderBean> dealerOrderList = dealerOrderQuery.dealerOrderQuery(dealerId, dealerOrderId,
					orderStatus, afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify,
					mediaInfo, invoice, pageNum, rows);
			if (dealerOrderList != null) {
				result.setContent(dealerOrderList);
				result.setPager(total, pageNum, rows);
				result.setStatus(MCode.V_200);
			}*/
			
			if (StringUtils.isEmpty(dealerId)) {
				result = new MPager(MCode.V_1, "dealerId参数为空");
				return new ResponseEntity<MPager>(result, HttpStatus.OK);
			}
			
			Integer total = dealerOrderQuery.dealerOrderTotalQuery1(dealerId, orderStatus,
					afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify, hasMedia,
					invoice);
			List<DealerOrderQB> dealerOrderList = dealerOrderQuery.dealerOrderQuery1(dealerId,
					orderStatus, afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify,
					hasMedia, invoice, pageNum, rows);
			if (dealerOrderList != null) {
				result.setContent(dealerOrderList);
				result.setPager(total, pageNum, rows);
				result.setStatus(MCode.V_200);
			}
			
		} catch (Exception e) {
			LOGGER.error("获取商家订单列表出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}

	/**
	 * 商家订单详情
	 * 
	 * @param dealerOrderId
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/dealerorderdetail", method = RequestMethod.GET)
	public ResponseEntity<MResult> getDealerOrderDetail(
			@RequestParam(value = "dealerId", required = false) String dealerId,
			@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId) {
		MResult result = new MResult(MCode.V_1);
		try {
			if (StringUtil.isEmpty(dealerId) || StringUtil.isEmpty(dealerOrderId)) {
				throw new NegativeException(MCode.V_1,"请传入商家ID和商家订单ID");
			}
			DealerOrderDetailBean orderDetail = dealerOrderQuery.dealerOrderDetailQuery(dealerOrderId, dealerId);
			result.setContent(orderDetail);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取商家订单详情出错" + e.getMessage(), e);
			result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 修改收货地址
	 * 
	 * @param dealerOrderId
	 * @param province
	 * @param provCode
	 * @param city
	 * @param cityCode
	 * @param area
	 * @param areaCode
	 * @param street
	 * @param revPerson
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/addr", method = RequestMethod.PUT)
	public ResponseEntity<MResult> updateAddress(
			@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "provCode", required = false) String provCode,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "cityCode", required = false) String cityCode,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "areaCode", required = false) String areaCode,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "revPerson", required = false) String revPerson,
			@RequestParam(value = "phone", required = false) String phone
			,@RequestParam(value = "userId", required = false) String userId
			) {

		MResult result = new MResult(MCode.V_1);

		try {
			if (StringUtil.isEmpty(dealerOrderId))
				throw new NegativeException(MCode.V_1,"请传入商家订单ID");
			UpdateAddrCommand command = new UpdateAddrCommand(dealerOrderId, province, provCode, city, cityCode, area,
					areaCode, street, revPerson, phone, userId);
			
			String _attach= request.getHeader("attach");
			
			dealerOrderApplication.updateAddress(command, _attach);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改收货地址发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 修改订单运费
	 * 
	 * @param dealerOrderId
	 * @param freight
	 * @return
	 */
	@RequestMapping(value = "/freight", method = RequestMethod.PUT)
	public ResponseEntity<MResult> updateFreight(
			@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId,
			@RequestParam(value = "freight", required = false) long freight
			,@RequestParam(value = "userId", required = false) String userId
			) {

		MResult result = new MResult(MCode.V_1);

		try {
			if (StringUtil.isEmpty(dealerOrderId))
				throw new NegativeException(MCode.V_1,"请传入商家订单ID");
			String _attach= request.getHeader("attach");
			
			UpdateOrderFreightCmd command = new UpdateOrderFreightCmd(dealerOrderId, freight, userId);
			dealerOrderApplication.updateOrderFreight(command, _attach);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改订单运费发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	@RequestMapping(value = {"/addrfreight", "/web/addrfreight"}, method = RequestMethod.PUT)
	public ResponseEntity<MResult> updateAddrFreight(
			@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId,
			@RequestParam(value = "province", required = false) String province,
			@RequestParam(value = "provCode", required = false) String provCode,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "cityCode", required = false) String cityCode,
			@RequestParam(value = "area", required = false) String area,
			@RequestParam(value = "areaCode", required = false) String areaCode,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "revPerson", required = false) String revPerson,
			@RequestParam(value = "phone", required = false) String phone
			,@RequestParam(value = "freights", required = false) String freights
			,@RequestParam(value = "userId", required = false) String userId
			) {

		MResult result = new MResult(MCode.V_1);

		try {
			if (StringUtil.isEmpty(dealerOrderId))
				throw new NegativeException(MCode.V_1, "商家订单号为空");
			UpdateAddrFreightCmd cmd = new UpdateAddrFreightCmd(dealerOrderId, province, provCode, city, cityCode, area,
					areaCode, street, revPerson, phone, freights, userId);
			
			String _attach= request.getHeader("attach");
			dealerOrderApplication.updateAddrFreight(cmd, _attach);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("修改收货地址发生错误", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
}
