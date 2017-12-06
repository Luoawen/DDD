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
    private Long originalSupplyPrice;
    /**
     * 原拍获价
     */
    private Long originalPhotographprice;
    
	public GoodsSkuSpecialDetailAllBean(Integer specialId, String skuId, String skuName, Long supplyPrice,
			Long specialPrice, Long originalSupplyPrice, Long originalPhotographprice) {
		super();
		this.specialId = specialId;
		this.skuId = skuId;
		this.skuName = skuName;
		this.supplyPrice = supplyPrice;
		this.specialPrice = specialPrice;
		this.originalSupplyPrice = originalSupplyPrice;
		this.originalPhotographprice = originalPhotographprice;
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
	public Long getOriginalSupplyPrice() {
		return originalSupplyPrice;
	}
	public void setOriginalSupplyPrice(Long originalSupplyPrice) {
		this.originalSupplyPrice = originalSupplyPrice;
	}
	public Long getOriginalPhotographprice() {
		return originalPhotographprice;
	}
	public void setOriginalPhotographprice(Long originalPhotographprice) {
		this.originalPhotographprice = originalPhotographprice;
	}
    
    
}
