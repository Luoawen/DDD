package cn.m2c.scm.application.order.data.bean;

import java.util.List;

/***
 * 营销适应范围
 * @author fanjc
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public final class MarketRangeSuit extends MarketRange {
	/**作用范围为商品对应规格及数量 为一个jsonArray串*/
	private List<MarketSku> skuList;
	/**0全部参加， 1 个别*/
	private int skuFlag;
	
	public List<MarketSku> getSkuList() {
		return skuList;
	}
	
	public void setSkuList(List<MarketSku> skuList) {
		this.skuList = skuList;
	}

	public int getSkuFlag() {
		return skuFlag;
	}

	public void setSkuFlag(int skuFlag) {
		this.skuFlag = skuFlag;
	}
	
}
