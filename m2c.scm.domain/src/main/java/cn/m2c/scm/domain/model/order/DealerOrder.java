package cn.m2c.scm.domain.model.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.scm.domain.model.order.event.SimpleMediaRes;
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
	/** 下单时间 **/
	private Date createdDate;
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
	
	public ReceiveAddr getAddr() {
		return addr;
	}
	
	
	void cancel() {
		status = -1;
		for (DealerOrderDtl d : orderDtls) {
			d.cancel();
		}
	}
	
	void payed() {
		status = 1;
		for (DealerOrderDtl d : orderDtls) {
			d.payed();
		}
	}

	/**
	 * 同步订单的物流信息
	 * @param expressName
	 * @param expressNo
	 * @param expressNote
	 * @param expressPerson
	 * @param expressPhone
	 * @param expressWay
	 */
	public boolean updateExpress(String expressName, String expressNo,
			String expressNote, String expressPerson, String expressPhone,
			Integer expressWay, String expressCode) {
		if (status >= 2 || status < 1) {
			return false;
		}
		status = 2;
		for (DealerOrderDtl dealerOrderDtl : orderDtls) {
			dealerOrderDtl.updateOrderDetailExpress(expressName,expressNo,expressNote,expressPerson
					,expressPhone,expressWay, expressCode);
		}
		return true;
	}
	/***
	 * 检查是否全部确认收货，除指定的sku外
	 * @param sku
	 * @param dll
	 * @return
	 */
	public boolean checkAllRev(String sku, DealerOrderDtl dll) {
		for (DealerOrderDtl dtl : orderDtls) {
			if (dtl.isEqualSku(sku) || dtl.isSameExpressNo(dll.getExpressNo())) {
				dtl.confirmRev();
			}
			if (!dtl.isRecBB())
				return false;
		}
		return true;
	}
	
	public void confirmRev() {
		
		if (status != 2)
			return;
		status = 3;
	}
	/**
	 * 获取销量
	 * @return
	 */
	Map<String, Integer> getSaleNums() {
		Map<String, Integer> arr = new HashMap<String, Integer>();
		for (DealerOrderDtl dtl : orderDtls) {
			arr.put(dtl.getSkuId(), (dtl.getSaleNum() == null ? 0: dtl.getSaleNum()));
		}
		return arr;
	}
	
	/**
	 * 获取销量
	 * @return
	 */
	List<SimpleMediaRes> getAllMediaRes() {
		List<SimpleMediaRes> arr = new ArrayList<SimpleMediaRes>();
		for (DealerOrderDtl dtl : orderDtls) {
			String mres = dtl.getMediaResId();
			if (!StringUtils.isEmpty(mres))
				arr.add(new SimpleMediaRes(mres, dtl.getBdsRate(), dtl.getSkuId(), dtl.getDiscountMoney()));
		}
		return arr;
	}
	
	/***
	 * 设置计算金额
	 * @param skuId
	 * @param discountAmount
	 * @param marketingId
	 */
	public boolean setSkuMoney(String skuId, long discountAmount, String marketingId) {
		for (DealerOrderDtl dtl : orderDtls) {
			if (dtl.setSkuMoney(skuId, discountAmount, marketingId))
				return true;
		}
		return false;
	}
	/***
	 * 计算订单金额
	 */
	void calOrderMoney() {
		goodsAmount = 0l;
		orderFreight = 0l;
		plateformDiscount = 0l;
		dealerDiscount = 0l;
		for (DealerOrderDtl dtl : orderDtls) {
			dtl.calOrderMoney();
			goodsAmount += dtl.getGoodsAmount();
			orderFreight += dtl.getFreight();
			plateformDiscount += dtl.getPlateformDiscount();
		}
	}
	/**
	 * 修改收货地址
	 * @param addr
	 */
	public void updateAddr(ReceiveAddr addr) {
		this.addr = addr;
	}
	
	/**
	 * 更新订单运费
	 * @param orderFreight
	 */
	public void updateOrderFreight(long orderFreight) {
		this.orderFreight = orderFreight;
	}
	
	/**
	 * 更新订单状态<将待收货改为已完成>
	 */
	public void updateOrderStatus() {
		this.status = 3;
		
	}
	
	/**
	 * 更新状态信息<完成状态更新为订单完成>
	 */
	public void updateOrderStatusFinished() {
		this.status = 4;
		
	}
	
	/**
	 * 更新状态信息<待付款状态24H未付款更新为已取消>
	 */
	public void updateOrderStatusWaitPay() {
		this.status = -1;
	}
	
	
	public long dateToLong() {
		return this.createdDate.getTime();
	}
	/**
	 * 更新运费 主要用于商家修改
	 * @param freights
	 */
	public boolean updateOrderFreight(Map<String, Integer> freights) {
		if (freights == null)
			return true;
		long frg = 0;
		for (DealerOrderDtl d : orderDtls) {
			Integer f = freights.get(d.getSkuId());
			if (f != null) {
				d.updateFreight(f * 100);
				frg += f * 100;
			}
			else
				frg += d.getFreight();
		}
		orderFreight = frg;
		return true;
	}
	/***
	 * 能更新地址及运费
	 * @return
	 */
	public boolean canUpdateFreight() {
		if (status > 1) {
			return false;
		}
		return true;
	}
}
