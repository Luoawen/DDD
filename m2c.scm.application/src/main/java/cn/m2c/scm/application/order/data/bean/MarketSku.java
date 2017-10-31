package cn.m2c.scm.application.order.data.bean;
/***
 * 营销中的sku结构 bean
 * @author fanjc
 * created date 2017年10月31日
 * copyrighted@m2c
 */
public class MarketSku {
	
	private String skuId;
	
	private Integer skuNum;
	
	private Integer skuRemianNum;

	public MarketSku() {
		
	}
	
	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public Integer getSkuNum() {
		return skuNum;
	}

	public void setSkuNum(Integer skuNum) {
		this.skuNum = skuNum;
	}

	public Integer getSkuRemianNum() {
		return skuRemianNum;
	}

	public void setSkuRemianNum(Integer skuRemianNum) {
		this.skuRemianNum = skuRemianNum;
	}
}
