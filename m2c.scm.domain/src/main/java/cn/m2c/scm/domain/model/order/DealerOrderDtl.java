package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.order.log.event.OrderOptLogEvent;
/**
 * 商家订单明细
 * @author fanjc
 *
 */
public class DealerOrderDtl extends ConcurrencySafeEntity {

	private static final long serialVersionUID = 1L;

	private String orderId;
	
	//private DealerOrder dealerOrder;
	
	private String dealerOrderId;
	/**订单状态 0待付款，1等发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消*/
	private Integer status = 0;
	/**收货地址*/
	private ReceiveAddr addr;
	/**发票信息**/
	private InvoiceInfo invoice;
	/**快递信息*/
	private ExpressInfo expressInfo;
	/**媒体资源ID*/
	private String mediaResId;
	/**促销员ID*/
	private String salerUserId;
	/**购买数量*/
	private Integer sellNum;
	/**是否为换货商品 1是*/
	private int isChange = 0;
	/**换货价*/
	private long changePrice;
	
	private GoodsInfo goodsInfo;
	/**以分为单位，商品金额*/
	private Long goodsAmount;
	/**平台优惠*/
	private Long plateformDiscount;
	/**商家优惠*/
	private Long dealerDiscount;
	/**备注 留言*/
	private String noted;
	/**订单总运费*/
	private Long freight;
	/**应用的营销ID*/
	private String marketingId;
	/**媒体ID*/
	private String mediaId;
	/**广告位分成比例*/
	private Float resRate = 0f;
	/**BD专员的分成串*/
	private String bdsRate;
	/**评论状态， 0 待评，1已评*/
	private Integer commentStatus = 0;
	
	public DealerOrderDtl() {
		super();
	}
	
	public DealerOrderDtl(String orderId, String dealerOrderId, ReceiveAddr addr
			,InvoiceInfo invoice, ExpressInfo expressInfo, String mediaResId, String salerUserId
			,String bdsRate, float resRate, String mediaId, int isChange, int changePrice
			, GoodsInfo goodsInfo, long plateformDiscount, long dealerDiscount, String noted) {
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.addr = addr;
		this.invoice = invoice;
		this.expressInfo = expressInfo;
		this.mediaResId = mediaResId;
		this.salerUserId = salerUserId;
		this.bdsRate = bdsRate;
		this.resRate = resRate;
		this.mediaId = mediaId;
		this.isChange = isChange;
		this.changePrice = changePrice;
		
		this.goodsInfo = goodsInfo;
		this.plateformDiscount = plateformDiscount;
		this.dealerDiscount = dealerDiscount;
		this.noted = noted;
	}
	/***
	 * 计算商品金额
	 * @return
	 */
	public Long calGoodsMoney() {
		goodsAmount = (long)(goodsInfo.getDiscountPrice() * sellNum);
		return goodsAmount;
	}
	/***
	 * 计算运费
	 * @return
	 */
	public Long calFreight() {
		
		return freight;
	}
	
	
	/**
	 * 更新订单详情的物流信息
	 * @param expressName
	 * @param expressNo
	 * @param expressNote
	 * @param expressPerson
	 * @param expressPhone
	 * @param expressWay
	 */
	public void updateOrderDetailExpress(String expressName, String expressNo,
			String expressNote, String expressPerson, String expressPhone,
			Integer expressWay, String expressCode) {
		this.expressInfo.updateExpress(expressName,expressNo,expressNote,expressPerson,expressPhone,expressWay
				, expressCode);
		this.status=2;
	}
	
	void cancel() {
		status = -1;
	}
	/***
	 * 确认收货
	 * @return
	 */
	boolean confirmRev() {
		if (status != 2) {
			return false;
		}
		status = 3;
		return true;
	}
	
	public boolean confirmRev(String userId) {
		
		if (status != 2) {
			return false;
		}
		status = 3;
		DomainEventPublisher.instance().publish(new OrderOptLogEvent(orderId, dealerOrderId, "用户确认收货成功", userId));
		return true;
	}
	/***
	 * 是否确认收货
	 * @return
	 */
	public boolean isRecBB() {
		return status >= 3;
	}
	
	boolean isEqualSku(String skuId) {
		return this.goodsInfo.getSkuId().equals(skuId);
	}
	
	boolean isSameExpressNo(String no) {
		if (no != null && no.equals(getExpressNo()))
			return true;
		return false;
	}
	
	String getExpressNo() {
		return expressInfo.getExpressNo();
	}
	/***
	 * 是否可以申请售后
	 * @return
	 */
	public boolean canApplySaleAfter() {
		return status >= 1 && status < 5;
	}
	
	public void hasCommented() {
		commentStatus = 1;
	}
}
