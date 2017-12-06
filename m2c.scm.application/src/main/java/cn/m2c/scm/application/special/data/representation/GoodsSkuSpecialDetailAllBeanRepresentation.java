package cn.m2c.scm.application.special.data.representation;

import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialDetailAllBean;

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
    private Long originalSupplyPrice;
    /**
     * 原拍获价
     */
    private Long originalPhotographprice;
    
    public GoodsSkuSpecialDetailAllBeanRepresentation(GoodsSkuSpecialDetailAllBean bean) {
    	this.specialId = bean.getSpecialId();
    	this.skuId = bean.getSkuId();
    	this.skuName = bean.getSkuName();
    	this.supplyPrice = bean.getSupplyPrice();
    	this.specialPrice = bean.getSpecialPrice();
    	this.originalSupplyPrice = bean.getOriginalSupplyPrice();
    	this.originalPhotographprice = bean.getOriginalPhotographprice();
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
