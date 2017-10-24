package cn.m2c.scm.domain.model.order.event;
/**
 * 简单销量实体
 * @author fanjc
 * created date 2017年10月23日
 * copyrighted@m2c
 */
public class SimpleSale {
	
	private String skuId;
	
	private int num = 0;

	public String getSkuId() {
		return skuId;
	}

	public int getNum() {
		return num;
	}
	
	public SimpleSale(String sku, int num) {
		this.skuId = sku;
		this.num = num;
	}
}
