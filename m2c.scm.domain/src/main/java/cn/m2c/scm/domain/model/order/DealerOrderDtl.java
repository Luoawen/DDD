package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
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
	private Float sellNum;
	/**是否为换货商品 1是*/
	private int isChange = 0;
	/**换货价*/
	private int changePrice;
	
	private GoodsInfo goodsInfo;
	/**以分为单位，商品金额*/
	private Integer goodsAmount;
	/**平台优惠*/
	private Integer plateformDiscount;
	/**商家优惠*/
	private Integer dealerDiscount;
	/**备注 留言*/
	private String noted;
	/**订单总运费*/
	private Integer freight;
	/**应用的营销ID*/
	private String marketingId;
	/**媒体ID*/
	private String mediaId;
	/**广告位分成比例*/
	private Float resRate = 0f;
	/**BD专员的分成串*/
	private String bdsRate;
	
	public DealerOrderDtl() {
		super();
	}
	
	public DealerOrderDtl(String orderId, String dealerOrderId, ReceiveAddr addr
			,InvoiceInfo invoice, ExpressInfo expressInfo, String mediaResId, String salerUserId
			,String bdsRate, float resRate, String mediaId, int isChange, int changePrice
			, GoodsInfo goodsInfo, int plateformDiscount, int dealerDiscount, String noted) {
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
	public Integer calGoodsMoney() {
		goodsAmount = (int)(goodsInfo.getDiscountPrice() * sellNum);
		return goodsAmount;
	}
	/***
	 * 计算运费
	 * @return
	 */
	public Integer calFreight() {
		
		return freight;
	}
}
