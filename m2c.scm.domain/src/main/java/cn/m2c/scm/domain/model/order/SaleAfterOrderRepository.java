package cn.m2c.scm.domain.model.order;
/**
 * 售后订单仓储
 * @author fanjc
 *
 */

import java.util.List;

public interface SaleAfterOrderRepository {
	/***
	 * 保存订单
	 * @param order 主订单
	 */
	public void save(SaleAfterOrder order);
	/***
	 * 获取售后单通过订单号
	 * @param saleAfterNo 订单号
	 */
	public SaleAfterOrder getSaleAfterOrderByNo(String saleAfterNo);
	/***
	 * 获取是否已经申请过售后
	 * @param dealerOrderId
	 * @param skuId
	 * @param sortNo
	 * @return
	 */
	public int getSaleAfterOrderBySkuId(String dealerOrderId, String skuId, int sortNo);
	/***
	 * 主要用于更新售后单状态
	 * @param order
	 */
	public void updateSaleAfterOrder(SaleAfterOrder order);
	/***
	 * 获取商家订单
	 * @param dealerOrderId
	 * @return
	 */
	public DealerOrder getDealerOrderByNo(String dealerOrderId);
	/***
	 * 获取订单详情中的某个SKU
	 * @param dealerOrderId
	 * @param skuId
	 * @return
	 */
	public DealerOrderDtl getDealerOrderDtlBySku(String dealerOrderId, String skuId, int sortNo);
	
	/***
	 * 获取售后单通过订单号
	 * @param saleAfterNo 订单号
	 * @param dealerId 商家ID
	 */
	public SaleAfterOrder getSaleAfterOrderByNo(String saleAfterNo, String dealerId);
	
	
	/**
	 * 获取状态为同意售后的满足条件的订单
	 * @param hour
	 */
	public List<SaleAfterOrder> getSaleAfterOrderStatusAgree(int hour);
	/***
	 * 使某个营销不可用， 主要是用于退款时用。
	 * @param orderId
	 * @param marketId
	 */
	public int disabledOrderMarket(String orderId, String marketId);
	/***
	 * 获取申请的满足要求的售后数据
	 * @param hour
	 * @return
	 */
	public List<SaleAfterOrder> getSaleAfterApplyed(int hour);
	/***
	 * 获取退款 且商家同意退款了
	 * @param hour
	 * @return
	 */
	public List<SaleAfterOrder> getAgreeRtMoney(int hour);
	/***
	 * 获取售后用户已发货
	 * @param hour
	 * @return
	 */
	public List<SaleAfterOrder> getUserSend(int hour);
	/***
	 * 获取售后商户已发货
	 * @param hour
	 * @return
	 */
	public List<SaleAfterOrder> getDealerSend(int hour);
	
	/***
	 * 获取售后完成的需要改变状态的订单详情IDs
	 * @return
	 */
	public List<Long> getSpecifiedDtlGoods(int hour);
	/***
	 * 扫描售后完成后的商家详情单也该完成
	 * @param afterNo
	 */
	public void scanDtlGoods(String afterNo);
	
	/***
	 * 使之前的东东失效
	 * @param skuId 
	 * @param dealerOrderId 商家订货单号
	 * @param sortNo
	 */
	public void invalideBefore(String skuId, String dealerOrderId, int sortNo);
}
