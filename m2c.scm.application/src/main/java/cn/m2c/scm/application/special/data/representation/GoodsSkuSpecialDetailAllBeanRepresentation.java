package cn.m2c.scm.application.special.data.representation;

import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;
import cn.m2c.scm.application.utils.Utils;

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
    private String supplyPrice;
    /**
     * 特惠价
     */
    private String specialPrice;
    /**
     * 原供货价
     */
    private String goodsSupplyPrice;
    /**
     * 原拍获价
     */
    private String goodsSkuPrice;
    
    public GoodsSkuSpecialDetailAllBeanRepresentation(GoodsSkuSpecialBean bean) {
    	this.specialId = bean.getSpecialId();
    	this.skuId = bean.getSkuId();
    	this.skuName = bean.getSkuName();
    	this.supplyPrice =  Utils.moneyFormatCN(bean.getSupplyPrice());
    	this.specialPrice =  Utils.moneyFormatCN(bean.getSpecialPrice());
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

	public String getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(String supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(String specialPrice) {
		this.specialPrice = specialPrice;
	}

	public String getGoodsSupplyPrice() {
		return goodsSupplyPrice;
	}

	public void setGoodsSupplyPrice(String goodsSupplyPrice) {
		this.goodsSupplyPrice = goodsSupplyPrice;
	}

	public String getGoodsSkuPrice() {
		return goodsSkuPrice;
	}

	public void setGoodsSkuPrice(String goodsSkuPrice) {
		this.goodsSkuPrice = goodsSkuPrice;
	}
    
}
