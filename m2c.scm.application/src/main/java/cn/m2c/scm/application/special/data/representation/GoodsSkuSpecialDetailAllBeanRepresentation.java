package cn.m2c.scm.application.special.data.representation;

import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;

/**
 * 商品特惠价详情表述对象(包含原供货价和拍获价)
 */
public class GoodsSkuSpecialDetailAllBeanRepresentation {
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
    
    public GoodsSkuSpecialDetailAllBeanRepresentation(GoodsSkuSpecialBean bean) {
    	this.specialId = bean.getSpecialId();
    	this.skuId = bean.getSkuId();
    	this.skuName = bean.getSkuName();
    	this.supplyPrice = bean.getSupplyPrice();
    	this.specialPrice = bean.getSpecialPrice();
    	//this.goodsSupplyPrice = bean.getGoodsSupplyPrice();
    	//this.goodsSkuPrice = bean.getGoodsSkuPrice();
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
