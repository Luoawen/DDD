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
	private Integer goodsAmount;
	/**订单总运费*/
	private Integer orderFreight;
	/**平台优惠*/
	private Integer plateformDiscount;
	/**商家优惠*/
	private Integer dealerDiscount;
	/**备注 留言*/
	private String noted;
	/**发票信息*/
	private InvoiceInfo invoice;
	/**结算方式**/
	private String termOfPayment;	
	/**订单明细*/
	private List<DealerOrderDtl> orderDtls;
	
	public DealerOrder(String orderId, String dealerOrderId,
			String dealerId, int goodsAmount, int orderFreight
			,int plateformDiscount, int dealerDiscount, String noted, String termOfPayment
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
	
	public Integer getGoodsAmount() {
		return goodsAmount;
	}

	public Integer getOrderFreight() {
		return orderFreight;
	}

	public Integer getPlateformDiscount() {
		return plateformDiscount;
	}

	public Integer getDealerDiscount() {
		return dealerDiscount;
	}
}
