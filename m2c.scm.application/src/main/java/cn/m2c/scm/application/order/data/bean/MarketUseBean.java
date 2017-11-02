package cn.m2c.scm.application.order.data.bean;
/***
 * 营销使用, 用于调用营销接口 实体
 * @author fanjc
 * created date 2017年11月2日
 * copyrighted@m2c
 */
public class MarketUseBean {
	
	private String goodsId;
	/**满减ID*/
	private String fullCutId;
	/**商品skuID*/
	private String skuId;
    
	private int skuNum;
	
	public MarketUseBean(String goodsId, String marketId, String skuId, int purNum) {
		this.goodsId = goodsId;
		fullCutId = marketId;
		this.skuId = skuId;
		skuNum= purNum;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getFullCutId() {
		return fullCutId;
	}

	public void setFullCutId(String fullCutId) {
		this.fullCutId = fullCutId;
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
