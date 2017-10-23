package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
/***
 * 订单中的商品SKU及数量对应
 * @author 89776
 * created date 2017年10月19日
 * copyrighted@m2c
 */
public class SkuNumBean {
	
	@ColumnAlias(value = "sku_id")
	private String skuId; //skuId
	
	@ColumnAlias(value = "sell_num")
	private Float num; // 数量
	
	public String getSkuId() {
		return skuId;
	}
	
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public Float getNum() {
		return num;
	}
	public void setNum(Float num) {
		this.num = num;
	} 
}
