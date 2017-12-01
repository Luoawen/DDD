package cn.m2c.scm.application.order.data.export;

import java.util.Date;

import cn.m2c.scm.application.utils.ExcelField;

/**
 * 售后订单导出样式
 */
public class SaleAfterExpModel {
    @ExcelField(title = "商家名称")
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
    
    public String getStatusStr() {
    	if (null == statusStr) {
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
    		
    		/*switch(status) {
            case 0:
            	statusStr="申请退货";
            	break;
            case 1:
            	statusStr="申请换货";
            	break;
            case 2:
            	statusStr="申请退款";
            	break;
            case 3:
            	statusStr="商家拒绝申请";
            	break;
            case 4:
            	statusStr="商家同意申请";
            	break;
            case 5:
            	statusStr="客户寄出";
            	break;
            case 6:
            	statusStr="商家收到";
            	break;
            case 7:
            	statusStr="商家寄出";
            	break;
            case 8:
            	statusStr="客户收到";
            	break;
            case 9:
            	statusStr="同意退款";
            	break;
            case 10:
            	statusStr="确认退款";
            	break;
            case 11:
            	statusStr="交易关闭";
            	break;
            }*/
    	}
		return statusStr;
	}

	public String getOrderTypeStr() {
		if (null == orderTypeStr) {
			if (orderType == 0) {
	            this.orderTypeStr = "换货";
	        } else if (orderType == 1) {
	            this.orderTypeStr = "退货";
	        } else {
	            this.orderTypeStr = "退款";
	        }
		}
		return orderTypeStr;
	}

	private String saleAfterNo;
    
    public SaleAfterExpModel() {
    	super();
    }

    public SaleAfterExpModel(String dealerName, String goodsName, String skuId, String skuName,
    		long backMoney, Integer status, Integer orderType, String goodsTitle, Date createTime) {
        this.dealerName = dealerName;
        this.goodsName = goodsName;
        
        this.skuId = skuId;
        this.skuName = skuName;
        
        this.backMoney = backMoney;
        this.status = status;
        
        this.orderType = orderType;
        this.goodsTitle = goodsTitle;
        this.createTime = createTime;
        
        if (orderType == 0) {
            this.orderTypeStr = "换货";
        } else if (orderType == 1) {
            this.orderTypeStr = "退货";
        } else {
            this.orderTypeStr = "退款";
        }
        
        switch (orderType) {
			case 0://换货
				switch (status) {
					case -1:
						this.statusStr="售后已取消";
						break;
					case 3:
						this.statusStr="商家已拒绝";
						break;
					case 1:
						this.statusStr="待商家同意";
						break;
					case 4:
						this.statusStr="待顾客寄回商品";
						break;
					case 5:
					case 6:
						this.statusStr="待商家发货";
						break;
					case 7:
						this.statusStr="待顾客收货";
						break;
					case 8:
					case 9:
					case 10:
					case 11:
					case 12:
						this.statusStr="售后已完成";
						break;
				}
				break;
			case 1://退货
				switch (status) {
					case -1:
						this.statusStr = "售后已取消";
						break;
					case 3:
						this.statusStr = "商家已拒绝";
						break;
					case 0:
						this.statusStr = "待商家同意";
						break;
					case 4:
						this.statusStr = "待顾客寄回商品";
						break;
					case 5:
					case 6:
						this.statusStr = "待商家确认退款";
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						this.statusStr = "售后已完成";
						break;
				}
				break;
			case 2://退款
				switch (status) {
	            	case -1:
	            		this.statusStr = "售后已取消";
						break;
					case 3:
						this.statusStr = "商家已拒绝";
						break;
					case 2:
						this.statusStr = "待商家同意";
						break;
					case 4:
						this.statusStr = "待商家确认退款";
						break;
					case 9:
					case 10:
					case 11:
					case 12:
						this.statusStr = "售后已完成";
						break;
				}
				break;
        }
        /*switch(status) {
        case 0:
        	statusStr="申请退货";
        	break;
        case 1:
        	statusStr="申请换货";
        	break;
        case 2:
        	statusStr="申请退款";
        	break;
        case 3:
        	statusStr="商家拒绝申请";
        	break;
        case 4:
        	statusStr="商家同意申请";
        	break;
        case 5:
        	statusStr="客户寄出";
        	break;
        case 6:
        	statusStr="商家收到";
        	break;
        case 7:
        	statusStr="商家寄出";
        	break;
        case 8:
        	statusStr="客户收到";
        	break;
        case 9:
        	statusStr="同意退款";
        	break;
        case 10:
        	statusStr="确认退款";
        	break;
        case 11:
        	statusStr="交易关闭";
        	break;
        }*/
    }

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public void setSaleAfterNo(String saleAfterNo) {
		this.saleAfterNo = saleAfterNo;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public Long getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(Long backMoney) {
		this.backMoney = backMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer orderType,Integer status) {
		this.orderType = orderType;
		this.status = status;
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
		/*switch(status) {
        case 0:
        	statusStr="申请退货";
        	break;
        case 1:
        	statusStr="申请换货";
        	break;
        case 2:
        	statusStr="申请退款";
        	break;
        case 3:
        	statusStr="商家拒绝申请";
        	break;
        case 4:
        	statusStr="商家同意申请";
        	break;
        case 5:
        	statusStr="客户寄出";
        	break;
        case 6:
        	statusStr="商家收到";
        	break;
        case 7:
        	statusStr="商家寄出";
        	break;
        case 8:
        	statusStr="客户收到";
        	break;
        case 9:
        	statusStr="同意退款";
        	break;
        case 10:
        	statusStr="确认退款";
        	break;
        case 11:
        	statusStr="交易关闭";
        	break;
        }*/
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
		if (orderType == 0) {
            this.orderTypeStr = "换货";
        } else if (orderType == 1) {
            this.orderTypeStr = "退货";
        } else {
            this.orderTypeStr = "退款";
        }
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
