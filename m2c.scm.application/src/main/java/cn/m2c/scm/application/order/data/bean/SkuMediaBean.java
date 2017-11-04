package cn.m2c.scm.application.order.data.bean;

public class SkuMediaBean {

	private String skuId;
	
	private String mresId;
	/**商品顶级分类*/
	private String goodsTypeCode;
	
	public SkuMediaBean() {
		
	}
	
	public SkuMediaBean(String skuId, String mresId) {
		this.skuId = skuId;
		this.mresId = mresId;
	}
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getMresId() {
		return mresId;
	}
	public void setMresId(String mresId) {
		this.mresId = mresId;
	}
	public String getGoodsTypeCode() {
		return goodsTypeCode;
	}
	public void setGoodsTypeCode(String goodsTypeCode) {
		this.goodsTypeCode = goodsTypeCode;
	}
	
}
