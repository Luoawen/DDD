package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
/***
 * 订单中的商品SKU及数量对应
 * @author fanjc
 * created date 2017年10月19日
 * copyrighted@m2c
 */
public class SkuNumBean {
	
	@ColumnAlias(value = "sku_id")
	private String skuId; //skuId
	
	@ColumnAlias(value = "sell_num")
	private Integer num; // 数量
	
	@ColumnAlias(value = "is_change")
	private Integer isChange; // 是否为换货商品，0 否， 1是
	
	@ColumnAlias(value = "goods_amount")
	private long goodsAmount; // 商品金额
	
	@ColumnAlias(value = "marketing_id")
	private String marketId; // 营销活动
	
	public String getSkuId() {
		return skuId;
	}
	
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public Integer getNum() {
		return num;
	}
	
	public void setNum(Integer num) {
		this.num = num;
	} 
}
