package cn.m2c.scm.application.order.data.export;

import java.text.DecimalFormat;
import java.util.Date;

import cn.m2c.scm.application.order.data.bean.SaleAfterExpQB;
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
    private String saleAfterGoodsPrice;
	//售后数量
	@ExcelField(title = "售后数量")//*
    private Integer sellNum;
	//运费退款/元
	@ExcelField(title = "运费退款/元")//*
    private String returnFreight;
	@ExcelField(title = "售后总额/元")
    private String backMoney;
	
    private Integer status;
    
    private Integer orderType;
	
    public String getStatusStr() {
		return statusStr;
	}

	public String getOrderTypeStr() {
		return orderTypeStr;
	}
    
	public void setSaleAfterGoodsPrice(String saleAfterGoodsPrice) {//售后单价,拍获价
		this.saleAfterGoodsPrice = saleAfterGoodsPrice;
	}
	
	public String getSaleAfterGoodsPrice() {
		return saleAfterGoodsPrice;
	}
	
    public void setReturnFreight(String returnFreight) {
    	this.returnFreight = returnFreight;
    }
    
    public String getReturnFreight() {
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
    
    public SaleAfterExpModel(SaleAfterExpQB saleAfterExpQB) {
        //this.dealerName = dealerName;
    	//this.skuId = skuId;
    	//this.goodsTitle = goodsTitle;
    	//售后总价    售后单价   运费   ordertypestr   statusstr
        this.saleAfterNo = saleAfterExpQB.getSaleAfterNo();
        DecimalFormat df1 = new DecimalFormat("0.00");
        this.backMoney = df1.format((saleAfterExpQB.getBackMoney().floatValue()+saleAfterExpQB.getReturnFreight().floatValue())/100);
        this.dealerOrderId = saleAfterExpQB.getDealerOrderId();
        this.saleAfterGoodsPrice = df1.format(saleAfterExpQB.getSaleAfterGoodsPrice().floatValue()/100);
        this.sellNum = saleAfterExpQB.getSellNum();
        this.returnFreight = df1.format(saleAfterExpQB.getReturnFreight().floatValue()/100);
        
    	this.goodsName = saleAfterExpQB.getGoodsName();
        
        
        this.skuName = saleAfterExpQB.getSkuName();
        
        this.status = saleAfterExpQB.getStatus();
        
        this.orderType = saleAfterExpQB.getOrderType();
        
        this.createTime = saleAfterExpQB.getCreateTime();
        
        this.setOrderTypeStr(saleAfterExpQB.getOrderType());
        this.setStatusStr(saleAfterExpQB.getOrderType(), saleAfterExpQB.getStatus());
        
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

	public String getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(String backMoney) {
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
