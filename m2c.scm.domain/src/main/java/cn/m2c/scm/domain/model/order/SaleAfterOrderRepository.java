package cn.m2c.scm.domain.model.order;
/**
 * 售后订单仓储
 * @author fanjc
 *
 */
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
	public DealerOrderDtl getDealerOrderDtlBySku(String dealerOrderId, String skuId);
	
	/***
	 * 获取售后单通过订单号
	 * @param saleAfterNo 订单号
	 * @param dealerId 商家ID
	 */
	public SaleAfterOrder getSaleAfterOrderByNo(String saleAfterNo, String dealerId);
}
