package cn.m2c.scm.application.order.data.export;

import cn.m2c.scm.application.order.data.bean.SaleAfterExpQB;
import cn.m2c.scm.application.utils.ExcelField;
import cn.m2c.scm.application.utils.OrderUtils;
import cn.m2c.scm.application.utils.Utils;

import java.util.Date;

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
        //this.backMoney = df1.format((saleAfterExpQB.getBackMoney().floatValue()+saleAfterExpQB.getReturnFreight().floatValue())/(double)100);
        this.backMoney = Utils.moneyFormatCN(saleAfterExpQB.getBackMoney() + saleAfterExpQB.getReturnFreight() - saleAfterExpQB.getCouponDiscount());
        this.dealerOrderId = saleAfterExpQB.getDealerOrderId();
        if (saleAfterExpQB.getIsSpecial() == 0) {//不是特惠价,售后单价展示“拍获价”
            this.saleAfterGoodsPrice = Utils.moneyFormatCN(saleAfterExpQB.getSaleAfterGoodsPrice());
        } else if (saleAfterExpQB.getIsSpecial() == 1) {//特惠价，售后单价展示“特惠价”
            this.saleAfterGoodsPrice = Utils.moneyFormatCN(saleAfterExpQB.getSpecialPrice());
        }
        this.sellNum = saleAfterExpQB.getSellNum();
        this.returnFreight = Utils.moneyFormatCN(saleAfterExpQB.getReturnFreight());

        this.goodsName = saleAfterExpQB.getGoodsName();


        this.skuName = saleAfterExpQB.getSkuName();

        this.status = saleAfterExpQB.getStatus();

        this.orderType = saleAfterExpQB.getOrderType();

        this.createTime = saleAfterExpQB.getCreateTime();

        this.orderTypeStr = OrderUtils.getAfterType(saleAfterExpQB.getOrderType());
        this.statusStr = OrderUtils.getAfterStatusStr(saleAfterExpQB.getOrderType(), saleAfterExpQB.getStatus());

        this.orderTypeStr = getOrderTypeStr();
    }

    public String getSaleAfterNo() {
        return saleAfterNo;
    }

    public void setSaleAfterNo(String saleAfterNo) {
        this.saleAfterNo = saleAfterNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
