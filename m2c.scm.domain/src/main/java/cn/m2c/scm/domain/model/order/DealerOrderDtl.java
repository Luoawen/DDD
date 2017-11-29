package cn.m2c.scm.domain.model.order;

import java.util.Date;
import java.util.HashMap;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.order.event.OrderSkuReturnEvent;
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
	/**商家订单号*/
	private String dealerOrderId;
	/**订单状态 0待付款，1等发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消*/
	private Integer status = 0;
	/**收货地址*/
	private ReceiveAddr addr;
	/**发票信息**/
	private InvoiceInfo invoice;
	/**快递信息*/
	private ExpressInfo expressInfo;
	
	private GoodsInfo goodsInfo;
	/**以分为单位，商品金额*/
	private Long goodsAmount;
	/**商家优惠*/
	private Long dealerDiscount;
	/**备注 留言*/
	private String noted;
	/**媒体相关信息*/
	private SimpleMediaInfo mediaInfo;
	/**评论状态， 0 待评，1已评*/
	private Integer commentStatus = 0;
	
	private SimpleMarketInfo marketInfo;
	
	/**删除状态*/
	private Integer delFlag = 0;
	
	/**更新时间*/
	private Date updateTime;
	/***
	 * 删除订单(用户主动操作)
	 */
	boolean del() {
		// 检查是否可以取消，只有在未支付的状态下用户可以取消
		if (status > 0 && status < 3) {
			return false;
		}
		delFlag = 1;
		updateTime = new Date();
		return true;
	}
	
	public DealerOrderDtl() {
		super();
	}
	
	public DealerOrderDtl(String orderId, String dealerOrderId, ReceiveAddr addr
			,InvoiceInfo invoice, ExpressInfo expressInfo, SimpleMediaInfo mediaInfo
			, GoodsInfo goodsInfo, long dealerDiscount, String noted
			, SimpleMarketInfo marketInfo
			) {
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.addr = addr;
		this.invoice = invoice;
		this.expressInfo = expressInfo;
		this.mediaInfo = mediaInfo;
		
		this.goodsInfo = goodsInfo;
		this.dealerDiscount = dealerDiscount;
		this.noted = noted;
		this.marketInfo = marketInfo;
	}
	/***
	 * 计算商品金额
	 * @return
	 */
	public Long calGoodsMoney() {
		goodsAmount = (long)(goodsInfo.getDiscountPrice() * goodsInfo.getSellNum());
		return goodsAmount;
	}
	/***
	 * 计算运费
	 * @return
	 
	public Long calFreight() {
		return goodsInfo.getFreight();
	}*/
	
	
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
		updateTime = new Date();
		this.status=2;
	}
	
	void cancel() {
		status = -1;
		updateTime = new Date();
	}
	
	void payed() {
		status = 1;
		updateTime = new Date();
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
		updateTime = new Date();
		return true;
	}
	
	public boolean confirmRev(String userId) {
		
		if (status != 2) {
			return false;
		}
		status = 3;
		updateTime = new Date();
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
	
	String getSkuId() {
		return goodsInfo.getSkuId();
	}
	
	Integer getSaleNum() {
		return goodsInfo.getSellNum();
	}
	
	String getMediaResId() {
		if (mediaInfo != null)
			return mediaInfo.getMediaResId();
		return null;
	}
	
	String getBdsRate() {
		if (mediaInfo != null)
			return mediaInfo.getBdsRate();
		return null;
	}
	/**
	 * 获取优惠后的价钱(纯商品的, 不含运费)
	 * @return
	 */
	long getDiscountMoney() {
		return goodsAmount - goodsInfo.getPlateformDiscount() - dealerDiscount;
	}
	
	public void finished() {
		status = 3;
		updateTime = new Date();
	}
	
	public void dealFinished() {
		status = 4;
		updateTime = new Date();
	}
	
	/***
	 * 设置计算金额
	 * @param skuId
	 * @param discountAmount
	 * @param marketingId
	 */
	public boolean setSkuMoney(String skuId, long discountAmount, String marketingId) {
		if (skuId.equals(this.getSkuId())) {
			this.marketInfo.setMarketId(marketingId);
			goodsInfo.setPlateformDiscount(discountAmount);
			return true;
		}
		return false;
	}
	
	void calOrderMoney() {
		goodsAmount = goodsInfo.calGoodsAmount();
		dealerDiscount = 0l;
	}
	
	long getGoodsAmount() {
		return goodsAmount;
	}
	
	public long getFreight() {
		return goodsInfo.getFreight();
	}
	
	long getPlateformDiscount() {
		return goodsInfo.getPlateformDiscount();
	}
	
	void updateFreight(long f) {
		goodsInfo.setFreight(f);
	}
	
	String getMediaId() {
		if (mediaInfo != null)
			return mediaInfo.getMediaId();
		return null;
	}
	
	String getSellerId() {
		if (mediaInfo != null)
			return mediaInfo.getSalerUserId();
		return null;
	}
	
	public String getMarketId() {
		if (marketInfo != null)
			return marketInfo.getMarketingId();
		
		return null;
	}
	/***
	 * 获取商品价格
	 * @return
	 */
	public long sumGoodsMoney() {
		return goodsAmount;
	}
	
	public boolean isDeliver() {
		if (status != null && status>=2)
			return true;
		
		return false;
	}
	/***
	 * 获取实际金额
	 * @return
	 */
	public long changePrice() {
		if (goodsInfo != null && goodsInfo.isChange()) {
			return goodsInfo.changePrice();
		}
		return goodsAmount;
	}
	
	public Integer sellNum() {
		if (null == goodsInfo || null == goodsInfo.getSellNum())
			return 0;
		return goodsInfo.getSellNum();
	}
	/***
	 * 检测是否应该返还库存， 若是则需要返还库存
	 */
	public void returnInventory(String afterNo, int num) {
		if (status == 1) {
			if (null == goodsInfo || num < 1)
				return;
			HashMap<String, Integer> aa = new HashMap<String, Integer>();
			aa.put(goodsInfo.getSkuId(), num);
			DomainEventPublisher.instance().publish(new OrderSkuReturnEvent(orderId, aa, dealerOrderId, afterNo));
		}
	}
}
