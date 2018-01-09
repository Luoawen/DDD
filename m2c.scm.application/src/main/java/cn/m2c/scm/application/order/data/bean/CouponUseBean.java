package cn.m2c.scm.application.order.data.bean;
/***
 * 营销使用, 优惠券使用bean
 * @author yezp
 * created date 2018年1月8日
 * copyrighted@m2c
 */
public class CouponUseBean {
	
	private String goodsId;
	/**满减ID*/
	private String couponId;
	/**商品skuID*/
	private String skuId;
    
	private int skuNum;
	

	
	public CouponUseBean() {
		super();
	}
	

	public CouponUseBean(String goodsId, String couponId, String skuId,
			int skuNum) {
		super();
		this.goodsId = goodsId;
		this.couponId = couponId;
		this.skuId = skuId;
		this.skuNum = skuNum;
	}


	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}


	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public int getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(int skuNum) {
		this.skuNum = skuNum;
	}
}
