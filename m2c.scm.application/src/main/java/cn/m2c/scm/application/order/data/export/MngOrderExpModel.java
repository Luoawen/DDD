package cn.m2c.scm.application.order.data.export;

import cn.m2c.scm.application.dealerorder.data.bean.OrderDtlBean;
import cn.m2c.scm.application.utils.ExcelField;
import cn.m2c.scm.application.utils.OrderUtils;
import cn.m2c.scm.application.utils.Utils;

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
        //订单状态, 0待付款，1待发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消
        this.orderStatus = OrderUtils.getStatusStr(dtl.getOrderStatus());
        this.payNo = null == dtl.getPayNo() ? "" : dtl.getPayNo();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createdDate = null != dtl.getCreatedDate() ? df.format(dtl.getCreatedDate()) : "";
        this.payDate = null != dtl.getPayTime() ? df.format(dtl.getPayTime()) : "";
        this.goodsName = dtl.getGoodsName();
        this.skuName = dtl.getSkuName();
        if(dtl.getIsSpecial() == 0) {//不执行特惠价
            this.goodsPrice = Utils.moneyFormatCN(dtl.getDiscountPrice());
        }else if(dtl.getIsSpecial() == 1){//执行特惠价
        	this.goodsPrice = Utils.moneyFormatCN(dtl.getSpecialPrice());
        }
        this.goodsNum = dtl.getSellNum();
        this.postage = Utils.moneyFormatCN(dtl.getOrderFreight());//*
        this.discountAmount = Utils.moneyFormatCN(dtl.getPlateDiscount() + dtl.getDealerDiscount());
        this.orderMoney = Utils.moneyFormatCN((dtl.getGoodsMoney() + dtl.getOrderFreight() - dtl.getDealerDiscount() - dtl.getPlateDiscount()));
        this.revPerson = dtl.getRevPerson();
        this.revPhone = dtl.getRevPhone();
        this.revAddress = dtl.getProvince() + dtl.getCity() + dtl.getAreaCounty() + dtl.getRevAddress();
        this.saleAfterNo = null == dtl.getAfterSellDealerOrderId() ? "" : dtl.getAfterSellDealerOrderId();
        this.saleAfterType = OrderUtils.getAfterType(dtl.getAfterOrderType());
        this.saleAfterStatus = OrderUtils.getAfterStatusStr(dtl.getAfterOrderType(),dtl.getAfterStatus());
        this.saleAfterNum = null == dtl.getAfterNum() ? "" : String.valueOf(dtl.getAfterNum());
        
        this.saleAfterMoney = Utils.moneyFormatCN(dtl.getAfterMoney());
        
        skuId = dtl.getSkuId();
        dealerName = dtl.getDealerName();
        brandName = dtl.getBrandName();
        clsName = dtl.getTypeName();
        secondName = dtl.getSecondType();
        thirdName = dtl.getThirdType();
    }
}
