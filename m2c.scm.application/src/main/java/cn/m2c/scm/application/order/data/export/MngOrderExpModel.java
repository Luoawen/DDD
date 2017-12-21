package cn.m2c.scm.application.order.data.export;

import cn.m2c.scm.application.dealerorder.data.bean.OrderDtlBean;
import cn.m2c.scm.application.utils.ExcelField;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * 平台订单详情导出
 */
public class MngOrderExpModel {
    @ExcelField(title = "订货号")
    private String dealerOrderId;
    @ExcelField(title = "订单状态")
    private String orderStatus;
    @ExcelField(title = "支付单号")
    private String payNo;
    @ExcelField(title = "下单时间")
    private String createdDate;
    @ExcelField(title = "支付时间")
    private String payDate;
    @ExcelField(title = "商家名称")
    private String dealerName;
    @ExcelField(title = "品牌")
    private String brandName;
    @ExcelField(title = "一级分类")
    private String clsName;
    @ExcelField(title = "二级分类")
    private String secondName;
    @ExcelField(title = "三级分类")
    private String thirdName;
    @ExcelField(title = "平台sku")
    private String skuId;
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
    @ExcelField(title = "优惠金额/元")
    private String discountAmount;
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
    @ExcelField(title = "售后金额/元")
    private String saleAfterMoney;
    
    public MngOrderExpModel(OrderDtlBean dtl) {
        this.dealerOrderId = dtl.getDealerOrderId();
        //订单状态, 0待付款，1等发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消
        this.orderStatus = getStatusStr(dtl.getOrderStatus());
        this.payNo = null == dtl.getPayNo() ? "" : dtl.getPayNo();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdDate = null != dtl.getCreatedDate() ? df.format(dtl.getCreatedDate()) : "";
        this.payDate = null != dtl.getPayTime() ? df.format(dtl.getPayTime()) : "";
        this.goodsName = dtl.getGoodsName();
        this.skuName = dtl.getSkuName();
        DecimalFormat df1 = new DecimalFormat("0.00");
        if(dtl.getIsSpecial() == 0) {//不执行特惠价
            this.goodsPrice = df1.format(dtl.getDiscountPrice().floatValue() / (double)100);//*
        }else if(dtl.getIsSpecial() == 1){//执行特惠价
        	Long specialPrice = Long.valueOf(dtl.getSpecialPrice());
        	this.goodsPrice = df1.format(specialPrice.floatValue() / (double)100);
        }
        this.goodsNum = dtl.getSellNum();
        this.postage = df1.format(dtl.getOrderFreight().floatValue() / (double)100);//*
        this.discountAmount = df1.format((dtl.getPlateDiscount().floatValue()+dtl.getDealerDiscount().floatValue())/(double)100);
        this.orderMoney = df1.format((dtl.getGoodsMoney().floatValue() + dtl.getOrderFreight().floatValue() - dtl.getDealerDiscount().floatValue() - dtl.getPlateDiscount().floatValue()) / (double)100);
        this.revPerson = dtl.getRevPerson();
        this.revPhone = dtl.getRevPhone();
        this.revAddress = dtl.getProvince() + dtl.getCity() + dtl.getAreaCounty() + dtl.getRevAddress();
        this.saleAfterNo = null == dtl.getAfterSellDealerOrderId() ? "" : dtl.getAfterSellDealerOrderId();
        this.saleAfterType = getAfterType(dtl.getAfterOrderType());
        this.saleAfterStatus = getAfterStatusStr(dtl.getAfterOrderType(),dtl.getAfterStatus());
        this.saleAfterNum = null == dtl.getAfterNum() ? "" : String.valueOf(dtl.getAfterNum());
        
        this.saleAfterMoney = df1.format(dtl.getAfterMoney().floatValue() / (double)100);
        
        skuId = dtl.getSkuId();
        dealerName = dtl.getDealerName();
        brandName = dtl.getBrandName();
        clsName = dtl.getTypeName();
        secondName = dtl.getSecondType();
        thirdName = dtl.getThirdType();
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
    
}
