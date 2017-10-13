package cn.m2c.scm.application.goods.command;

import cn.m2c.common.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格审核
 */
public class GoodsSkuApproveCommand{
    /**
     * 规格审核id
     */
    private String skuApproveId;

    /**
     * 规格名称
     */
    private String skuName;

    /**
     * 可用库存
     */
    private Integer availableNum;

    /**
     * 重量
     */
    private Float weight;

    /**
     * 拍获价
     */
    private Long photographPrice;

    /**
     * 市场价
     */
    private Long marketPrice;

    /**
     * 供货价
     */
    private Long supplyPrice;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 是否对外展示，1：不展示，2：展示
     */
    private Integer showStatus;

    public GoodsSkuApproveCommand(String skuApproveId, String skuName, Integer availableNum, Float weight,
                                  Long photographPrice, Long marketPrice, Long supplyPrice, String goodsCode,
                                  Integer showStatus) {
        this.skuApproveId = skuApproveId;
        this.skuName = skuName;
        this.availableNum = availableNum;
        this.weight = weight;
        this.photographPrice = photographPrice;
        this.marketPrice = marketPrice;
        this.supplyPrice = supplyPrice;
        this.goodsCode = goodsCode;
        this.showStatus = showStatus;
    }

    public String getSkuApproveId() {
        return skuApproveId;
    }

    public void setSkuApproveId(String skuApproveId) {
        this.skuApproveId = skuApproveId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Long getPhotographPrice() {
        return photographPrice;
    }

    public void setPhotographPrice(Long photographPrice) {
        this.photographPrice = photographPrice;
    }

    public Long getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Long marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Long getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(Long supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    public static void main(String [] args){
        GoodsSkuApproveCommand command = new GoodsSkuApproveCommand("SPSHA5BDED943A1D42CC9111B3723B0987BF", "L,红",
                200, 20.5f,5000l, 6000l, 4000l,"111111",
                2);
       /* GoodsSkuApproveCommand command1 = new GoodsSkuApproveCommand("SPSHA5BDED943A1D42CC9111B3723B0987BF", "XL,白",
                200, 20.5f,5000l, 6000l, 4000l,"111111",
                2);*/
        List<GoodsSkuApproveCommand>  commands = new ArrayList<>();
        commands.add(command);
        /*commands.add(command1);*/
        System.out.print(JsonUtils.toStr(commands));
    }
}