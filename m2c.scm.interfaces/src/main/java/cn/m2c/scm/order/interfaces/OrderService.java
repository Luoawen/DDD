package cn.m2c.scm.order.interfaces;

import java.util.List;
import java.util.Map;

import cn.m2c.scm.order.dto.OrderDto;



/**
 * 
 * @ClassName: OrderService
 * @Description: 订单服务
 * @author moyj
 * @date 2017年4月25日 下午1:42:39
 *
 */
public interface OrderService {
	
	
	/**
	 * 获取要结算的订单
	 * @param orderIdList	//订单编号列表
	 * @return
	 */
	public List<OrderDto> settleListByIdList(List<String> orderIdList);
	
	/**
	 * 结算完成
	 * @param orderIdList //订单编号列表
	 * @return
	 */
	public boolean settled(List<String> orderIdList);
	
	/**
	 * 获取要支付的订单的ID
	 * @param orderId	//订单Id
	 * @return
	 */
	public OrderDto payOrderById(String orderId);
	
	/**
	 * 获取要支付的订单的编号
	 * @param orderNo	//订单No
	 * @return
	 */
	public OrderDto payOrderByNo(String orderNo);
	
	/**
	 * 支付完成
	 * @param orderId		//订单
	 * @param payStartTime	//支付开始时间
	 * @param payEndTime	//支付结算时间
	 * @param payTradeNo    //外部交易号
	 * @param payPrice		//支付金额
	 * @param payWay		//支付方式
	 * @return
	 */
	public boolean payed(String orderId,Long payStartTime,Long payEndTime,String payTradeNo,Long payPrice,Integer payWay,Map<String,Object> appMsgMap);
	
	
	/**
	 * 获取要退款的订单
	 * @param orderId	//订单Id
	 * @return
	 */
	public OrderDto refundOrderById(String orderId);
	
	/**
	 * 退款完成
	 * @param orderId
	 * @param refundAmount
	 * @param refbaredReason
	 * @return
	 */
	public boolean refunded(String orderId,Long refundAmount,String refbaredReason,String handManId,String handManName);
	
	/**
	 * 后台订单统计
	 * @return
	 */
	public Map<String,Object> adminOrderStatistics();
	
	/**
	 * 媒体摘要
	 * @param mediaId
	 * @return
	 */
	public Map<String,Object> mediaOrderStatistics(String mediaId) ;
	
	
	/**
	 * 
	 * @param mresId	//媒体资源ID
	 * @param mresNo	//媒体资源编号
	 * @return
	 */
	public Map<String,Object> mresOrderStatistics(String mresId,String mresNo);
	
	
	/**
	 * 获取要支付的订单的ID
	 * @param orderId	//订单Id
	 * @return
	 */
	public OrderDto orderById(String orderId);
	
	
}
