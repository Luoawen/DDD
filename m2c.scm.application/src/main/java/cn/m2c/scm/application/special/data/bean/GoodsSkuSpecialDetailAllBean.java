package cn.m2c.scm.application.special.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品特惠价详情(含原拍获价与供货价)
 */
public class GoodsSkuSpecialDetailAllBean {
    private Integer specialId;
    private String skuId;
    private String skuName;
    /**
     * 供货价
     */
    private Long supplyPrice;
    /**
     * 特惠价
     */
    private Long specialPrice;
    /**
     * 原供货价
     */
    private Long goodsSupplyPrice;
    /**
     * 原拍获价
     */
    private Long goodsSkuPrice;
    
	public GoodsSkuSpecialDetailAllBean(Integer specialId, String skuId, String skuName, Long supplyPrice,
			Long specialPrice, Long goodsSupplyPrice, Long goodsSkuPrice) {
		super();
		this.specialId = specialId;
		this.skuId = skuId;
		this.skuName = skuName;
		this.supplyPrice = supplyPrice;
		this.specialPrice = specialPrice;
		this.goodsSupplyPrice = goodsSupplyPrice;
		this.goodsSkuPrice = goodsSkuPrice;
	}
	public GoodsSkuSpecialDetailAllBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public Long getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(Long supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public Long getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(Long specialPrice) {
		this.specialPrice = specialPrice;
	}
	public Long getGoodsSupplyPrice() {
		return goodsSupplyPrice;
	}
	public void setGoodsSupplyPrice(Long goodsSupplyPrice) {
		this.goodsSupplyPrice = goodsSupplyPrice;
	}
	public Long getGoodsSkuPrice() {
		return goodsSkuPrice;
	}
	public void setGoodsSkuPrice(Long goodsSkuPrice) {
		this.goodsSkuPrice = goodsSkuPrice;
	}
    
    
}
