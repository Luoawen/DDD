package cn.m2c.scm.application.order.data.export;

import java.util.Date;

import cn.m2c.scm.application.utils.ExcelField;

/**
 * 售后订单导出样式
 */
public class SaleAfterExpModel {
    /*@ExcelField(title = "商家名称")
    private String dealerName;
    @ExcelField(title = "商品名称")
    private String goodsName;
    @ExcelField(title = "平台SKU")
    private String skuId;
    @ExcelField(title = "规格")
    private String skuName;
    @ExcelField(title = "售后总金额/元")
    private Long backMoney;
    
    private Integer status;
    
    private Integer orderType;
    
    @ExcelField(title = "商品标题")
    private String goodsTitle;
    @ExcelField(title = "申请时间")
    private Date createTime;
    
    
    @ExcelField(title = "售后状态")
    private String statusStr;
    @ExcelField(title = "售后期望")
    private String orderTypeStr;
    
	private String saleAfterNo;*/
	
	@ExcelField(title = "售后期望")
    private String orderTypeStr;
	@ExcelField(title = "售后号")
	private String saleAfterNo;
	@ExcelField(title = "订货号")
	private String dealerOrderId;
	@ExcelField(title = "售后状态")
    private String statusStr;
	@ExcelField(title = "申请时间")
    private Date createTime;
	@ExcelField(title = "商品名称")
    private String goodsName;
	@ExcelField(title = "规格")
    private String skuName;
	//售后单价/元
	@ExcelField(title = "售后单价/元")//* 
    private Long saleAfterGoodsPrice;
	//售后数量
	@ExcelField(title = "售后数量")//*
    private Integer sellNum;
	//运费退款/元
	@ExcelField(title = "运费退款/元")//*
    private Long returnFreight;
	@ExcelField(title = "售后总额/元")
    private Long backMoney;
	
    private Integer status;
    
    private Integer orderType;
	
    public String getStatusStr() {
		return statusStr;
	}

	public String getOrderTypeStr() {
		return orderTypeStr;
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
    
    public SaleAfterExpModel() {
    	super();
    }
    
    public SaleAfterExpModel(String saleAfterNo,String dealerOrderId,Integer sellNum, long returnFreight,
    		String goodsName, String skuName, long backMoney, Integer status, Integer orderType, Date createTime, Long saleAfterGoodsPrice) {
        //this.dealerName = dealerName;
    	//this.skuId = skuId;
    	//this.goodsTitle = goodsTitle;
        this.saleAfterNo = saleAfterNo;
        this.dealerOrderId = dealerOrderId;
        this.saleAfterGoodsPrice = saleAfterGoodsPrice;
        this.sellNum = sellNum;
        this.returnFreight = returnFreight;
        
    	this.goodsName = goodsName;
        
        
        this.skuName = skuName;
        
        this.backMoney = backMoney;
        this.status = status;
        
        this.orderType = orderType;
        
        this.createTime = createTime;
        
        this.orderTypeStr = getOrderTypeStr();
        this.statusStr = getStatusStr();
    }

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public void setSaleAfterNo(String saleAfterNo) {
		this.saleAfterNo = saleAfterNo;
	}

	public void setOrderTypeStr(Integer orderType) {
		if (orderType == 0) {
            this.orderTypeStr = "换货";
        } else if (orderType == 1) {
            this.orderTypeStr = "退货";
        } else {
            this.orderTypeStr = "退款";
        }
	}
	public void setStatusStr(Integer orderType,Integer status) {
		switch (orderType) {
			case 0://换货
				switch (status) {
					case -1:
						statusStr="售后已取消";
						break;
					case 3:
						statusStr="商家已拒绝";
						break;
					case 1:
						statusStr="待商家同意";
						break;
					case 4:
						statusStr="待顾客寄回商品";
						break;
					case 5:
					case 6:
						statusStr="待商家发货";
						break;
					case 7:
						statusStr="待顾客收货";
						break;
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
						statusStr="售后已完成";
						break;
				}
				break;
			case 1://退货
				switch (status) {
					case -1:
						statusStr = "售后已取消";
						break;
					case 3:
						statusStr = "商家已拒绝";
						break;
					case 0:
						statusStr = "待商家同意";
						break;
					case 4:
						statusStr = "待顾客寄回商品";
						break;
					case 5:
					case 6:
						statusStr = "待商家确认退款";
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						statusStr = "售后已完成";
						break;
				}
				break;
			case 2://退款
				switch (status) {
	            	case -1:
						statusStr = "售后已取消";
						break;
					case 3:
						statusStr = "商家已拒绝";
						break;
					case 2:
						statusStr = "待商家同意";
						break;
					case 4:
						statusStr = "待商家确认退款";
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						statusStr = "售后已完成";
						break;
				}
			break;
		}
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
