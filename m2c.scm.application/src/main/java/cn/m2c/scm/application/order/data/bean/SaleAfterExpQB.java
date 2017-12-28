package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

/**
 * 售后单导出bean
 */
public class SaleAfterExpQB {
	private String saleAfterNo;
	private String dealerOrderId;
    private Date createTime;
    private String goodsName;
    private String skuName;
	//售后单价/元
    private Long saleAfterGoodsPrice;
	//售后数量
    private Integer sellNum;
	//运费退款/元
    private Long returnFreight;
    private Long backMoney;
	
    private Integer status;
    
    private Integer orderType;
    /**
	 * 是否特惠价， 0否， 1是 
	 */
	private Integer isSpecial;
	
	/**
	 * 特惠价
	 */
	private Long specialPrice;
	
	public Integer getIsSpecial() {
		if (null == isSpecial)
			isSpecial = 0;
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Long getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public void setSaleAfterGoodsPrice(Long saleAfterGoodsPrice) {//售后单价,拍获价
		this.saleAfterGoodsPrice = saleAfterGoodsPrice;
	}
	
	public Long getSaleAfterGoodsPrice() {
		return saleAfterGoodsPrice;
	}
	
    public void setReturnFreight(Long returnFreight) {
    	this.returnFreight = returnFreight;
    }
    
    public Long getReturnFreight() {
    	return returnFreight;
    }

    public void setSellNum(Integer sellNum) {
    	this.sellNum = sellNum;
    }
    
    public Integer getSellNum() {
    	return sellNum;
    }
    
    public void setDealerOrderId(String dealerOrderId) {
    	this.dealerOrderId = dealerOrderId;
    }
    
    public String getDealerOrderId() {
    	return dealerOrderId;
    }

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public void setSaleAfterNo(String saleAfterNo) {
		this.saleAfterNo = saleAfterNo;
	}
	
	//public String getDealerName() {
	//	return dealerName;
	//}

	//public void setDealerName(String dealerName) {
	//	this.dealerName = dealerName;
	//}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	//public String getSkuId() {
	//	return skuId;
	//}

	//public void setSkuId(String skuId) {
	//	this.skuId = skuId;
	//}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Long getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(Long backMoney) {
		this.backMoney = backMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	//public String getGoodsTitle() {
	//	return goodsTitle;
	//}

	//public void setGoodsTitle(String goodsTitle) {
	//	this.goodsTitle = goodsTitle;
	//}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
