package cn.m2c.scm.application.order.data.export;

import cn.m2c.scm.application.dealerorder.data.bean.DealerGoodsBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderQB;
import cn.m2c.scm.application.utils.ExcelField;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 商家订单导出
 */
public class DealerOrderExpModel {
    @ExcelField(title = "订货单")
    private String dealerOrderId;
    @ExcelField(title = "订单状态")
    private String orderStatus;
    @ExcelField(title = "支付单号")
    private String payNo;
    @ExcelField(title = "下单时间")
    private String createdDate;
    @ExcelField(title = "支付时间")
    private String payDate;
    @ExcelField(title = "商品名称")
    private String goodsName;
    @ExcelField(title = "规格")
    private String skuName;
    @ExcelField(title = "单价/元")
    private String goodsPrice;
    @ExcelField(title = "数量")
    private Integer goodsNum;
    @ExcelField(title = "运费/元")
    private String postage;
    @ExcelField(title = "订单总额/元")
    private String orderMoney;
    @ExcelField(title = "收货人姓名")
    private String revPerson;
    @ExcelField(title = "收货人号码")
    private String revPhone;
    @ExcelField(title = "收货地址")
    private String revAddress;
    @ExcelField(title = "售后号")
    private String saleAfterNo;
    @ExcelField(title = "售后期望")
    private String saleAfterType;
    @ExcelField(title = "售后状态")
    private String saleAfterStatus;
    @ExcelField(title = "售后数量")
    private String saleAfterNum;
    @ExcelField(title = "售后金额")
    private String saleAfterMoney;

    public DealerOrderExpModel(DealerGoodsBean goodsBean, DealerOrderQB dealerOrderQB) {
        this.dealerOrderId = dealerOrderQB.getDealerOrderId();
        //订单状态, 0待付款，1等发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消
        this.orderStatus = getStatusStr(dealerOrderQB.getOrderStatus());
        this.payNo = null == dealerOrderQB.getPayNo() ? "" : dealerOrderQB.getPayNo();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdDate = null != new Date(dealerOrderQB.getCreatedDate()) ? df.format(new Date(dealerOrderQB.getCreatedDate())) : "";
        this.payDate = null != dealerOrderQB.getPayTime() ? df.format(new Date(dealerOrderQB.getPayTime())) : "";
        this.goodsName = goodsBean.getGoodsName();
        this.skuName = goodsBean.getSkuName();
        this.goodsPrice = String.valueOf(goodsBean.getDiscountPrice() / 100);
        this.goodsNum = goodsBean.getSellNum();
        this.postage = String.valueOf(goodsBean.getFreight() / 100);
        this.orderMoney = String.valueOf((dealerOrderQB.getGoodsMoney() + dealerOrderQB.getOrderFreight() - dealerOrderQB.getDealerDiscount() - dealerOrderQB.getPlateDiscount()) / 100);
        this.revPerson = dealerOrderQB.getRevPerson();
        this.revPhone = dealerOrderQB.getRevPhone();
        this.revAddress = dealerOrderQB.getRevAddress();
        this.saleAfterNo = null == dealerOrderQB.getAfterSellDealerOrderId() ? "" : dealerOrderQB.getAfterSellDealerOrderId();
        this.saleAfterType = getAfterType(dealerOrderQB.getAfterOrderType());
        this.saleAfterStatus = getAfterStatusStr(dealerOrderQB.getAfterOrderType(),dealerOrderQB.getAfterStatus());
        this.saleAfterNum = null == dealerOrderQB.getAfterNum() ? "" : String.valueOf(dealerOrderQB.getAfterNum());
        this.saleAfterMoney = String.valueOf(dealerOrderQB.getAfterMoney() / 100);
    }

    private String getStatusStr(Integer status) {
        String statusStr = "";
        if (null != status) {
            switch (status) {
                case 0:
                    statusStr = "待付款";
                    break;
                case 1:
                    statusStr = "等发货";
                    break;
                case 2:
                    statusStr = "待收货";
                    break;
                case 3:
                    statusStr = "完成";
                    break;
                case 4:
                    statusStr = "交易完成";
                    break;
                case 5:
                    statusStr = "交易关闭";
                    break;
                case -1:
                    statusStr = "已取消";
                    break;
            }
        }
        return statusStr;
    }

    // 订单类型，0换货， 1退货，2仅退款
    private String getAfterType(Integer status) {
        String statusStr = "";
        if (null != status) {
            switch (status) {
                case 0:
                    statusStr = "换货";
                    break;
                case 1:
                    statusStr = "退货";
                    break;
                case 2:
                    statusStr = "仅退款";
                    break;
            }
        }
        return statusStr;
    }

    // 状态：待商家同意，待顾客寄回商品，待商家确认退款，待商家发货，待顾客收货，售后已完成，售后已取消，商家已拒绝
    private String getAfterStatusStr(Integer afterType,Integer status) {
    	String statusStr = "";
    	if(null != afterType && null != status) {
    		switch (afterType) {
				case 0://换货
					switch (status) {
						case -1:
							statusStr = "售后已取消";
							break;
						case 3:
							statusStr = "商家已拒绝";
							break;
						case 1:
							statusStr = "待商家同意";
							break;
						case 4:
							statusStr = "待顾客寄回商品";
							break;
						case 5:
						case 6:
							statusStr = "待商家发货";
							break;
						case 7:
							statusStr = "待顾客收货";
							break;
						case 8:
						case 9:
						case 10:
						case 11:
						case 12:
							statusStr = "售后已完成";
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
	            case 2://仅退款
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
    	return statusStr;
    }
    
    // 状态，0申请退货,1申请换货,2申请退款,3拒绝,4同意(退换货),5客户寄出,6商家收到,7商家寄出,8客户收到,9同意退款, 10确认退款,11交易完成，12交易关闭
    /*private String getAfterStatusStr(Integer status) {
        String statusStr = "";
        if (null != status) {
            switch (status) {
                case 0:
                    statusStr = "申请退货";
                    break;
                case 1:
                    statusStr = "申请换货";
                    break;
                case 2:
                    statusStr = "申请退款";
                    break;
                case 3:
                    statusStr = "拒绝";
                    break;
                case 4:
                    statusStr = "同意(退换货)";
                    break;
                case 5:
                    statusStr = "客户寄出";
                    break;
                case 6:
                    statusStr = "商家收到";
                    break;
                case 7:
                    statusStr = "商家寄出";
                    break;
                case 8:
                    statusStr = "客户收到";
                    break;
                case 9:
                    statusStr = "同意退款";
                    break;
                case 10:
                    statusStr = "确认退款";
                    break;
                case 11:
                    statusStr = "交易完成";
                    break;
                case 12:
                    statusStr = "交易关闭";
                    break;
            }
        }
        return statusStr;
    }*/
}
