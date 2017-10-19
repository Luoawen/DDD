package cn.m2c.scm.domain.model.order;

import java.util.List;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
/**
 * 商家订单
 * @author fanjc
 *
 */
public class DealerOrder extends ConcurrencySafeEntity {

	private static final long serialVersionUID = 1L;

	//private MainOrder order;
	
	private String orderId;
	
	private String dealerOrderId;
	
	private String dealerId;
	/**订单状态 0待付款，1等发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消*/
	private Integer status = 0;
	
	private ReceiveAddr addr;
	
	/**以分为单位，商品金额*/
	private Long goodsAmount;
	/**订单总运费*/
	private Long orderFreight;
	/**平台优惠*/
	private Long plateformDiscount;
	/**商家优惠*/
	private Long dealerDiscount;
	/**备注 留言*/
	private String noted;
	/**发票信息*/
	private InvoiceInfo invoice;
	/**结算方式 1 按供货价， 2按服务费率**/
	private Integer termOfPayment;	
	/**订单明细*/
	private List<DealerOrderDtl> orderDtls;
	
	public DealerOrder() {
		super();
	}
	public DealerOrder(String orderId, String dealerOrderId,
			String dealerId, long goodsAmount, long orderFreight
			,long plateformDiscount, long dealerDiscount, String noted, Integer termOfPayment
			, ReceiveAddr addr, InvoiceInfo invoice, List<DealerOrderDtl> orderDtl) {
		this.orderId = orderId;
		this.dealerId = dealerId;
		this.dealerOrderId = dealerOrderId;
		this.orderFreight = orderFreight;
		this.goodsAmount = goodsAmount;
		this.noted = noted;
		this.invoice = invoice;
		this.addr = addr;
		this.orderDtls = orderDtl;
		
		this.plateformDiscount = plateformDiscount;
		this.dealerDiscount = dealerDiscount;
		this.termOfPayment = termOfPayment;
	}
	
	public Long getGoodsAmount() {
		return goodsAmount;
	}

	public Long getOrderFreight() {
		return orderFreight;
	}

	public Long getPlateformDiscount() {
		return plateformDiscount;
	}

	public Long getDealerDiscount() {
		return dealerDiscount;
	}
}
