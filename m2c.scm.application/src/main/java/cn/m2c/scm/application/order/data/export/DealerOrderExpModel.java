package cn.m2c.scm.application.order.data.export;

import cn.m2c.scm.application.dealerorder.data.bean.DealerGoodsBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderQB;
import cn.m2c.scm.application.utils.ExcelField;
import cn.m2c.scm.application.utils.OrderUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 商家订单导出
 */
public class DealerOrderExpModel {
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

    public DealerOrderExpModel(DealerGoodsBean goodsBean, DealerOrderQB dealerOrderQB) {
        this.dealerOrderId = dealerOrderQB.getDealerOrderId();
        //订单状态, 0待付款，1待发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消
        this.orderStatus = OrderUtils.getStatusStr(dealerOrderQB.getOrderStatus());
        this.payNo = null == dealerOrderQB.getPayNo() ? "" : dealerOrderQB.getPayNo();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdDate = null != new Date(dealerOrderQB.getCreatedDate()) ? df.format(new Date(dealerOrderQB.getCreatedDate())) : "";
        this.payDate = null != dealerOrderQB.getPayTime() ? df.format(new Date(dealerOrderQB.getPayTime())) : "";
        this.goodsName = goodsBean.getGoodsName();
        this.skuName = goodsBean.getSkuName();
        DecimalFormat df1 = new DecimalFormat("0.00");
        if (goodsBean.getIsSpecial() == 0) {//不执行特惠价
            this.goodsPrice = df1.format(goodsBean.getDiscountPrice().floatValue() / (double) 100);//*
        } else if (goodsBean.getIsSpecial() == 1) {//执行特惠价
            Long specialPrice = Long.valueOf(goodsBean.getSpecialPrice());
            this.goodsPrice = df1.format(specialPrice.floatValue() / (double) 100);
        }
        this.goodsNum = goodsBean.getSellNum();
        this.postage = df1.format(dealerOrderQB.getOrderFreight().floatValue() / (double) 100);//*
        this.discountAmount = df1.format((dealerOrderQB.getPlateDiscount().floatValue() + dealerOrderQB.getDealerDiscount().floatValue() + dealerOrderQB.getDdCouponDiscount().floatValue()) / (double) 100);
        this.orderMoney = df1.format((dealerOrderQB.getGoodsMoney().floatValue() + dealerOrderQB.getOrderFreight().floatValue() - dealerOrderQB.getDealerDiscount().floatValue() - dealerOrderQB.getPlateDiscount().floatValue() - dealerOrderQB.getDdCouponDiscount().floatValue()) / (double) 100);
        this.revPerson = dealerOrderQB.getRevPerson();
        this.revPhone = dealerOrderQB.getRevPhone();
        this.revAddress = dealerOrderQB.getRevAddress();
        this.saleAfterNo = null == dealerOrderQB.getAfterSellDealerOrderId() ? "" : dealerOrderQB.getAfterSellDealerOrderId();
        this.saleAfterType = OrderUtils.getAfterType(dealerOrderQB.getAfterOrderType());
        this.saleAfterStatus = OrderUtils.getAfterStatusStr(dealerOrderQB.getAfterOrderType(), dealerOrderQB.getAfterStatus());
        this.saleAfterNum = null == dealerOrderQB.getAfterNum() ? "" : String.valueOf(dealerOrderQB.getAfterNum());
        if (this.saleAfterType.equals("1") || this.saleAfterType.equals("2")) {
        	this.saleAfterMoney = df1.format((dealerOrderQB.getAfterMoney().floatValue() - dealerOrderQB.getCouponDiscount().floatValue())/ (double) 100);
		}else {
			this.saleAfterMoney = df1.format((dealerOrderQB.getAfterMoney().floatValue())/ (double) 100);
		}
    }
}
