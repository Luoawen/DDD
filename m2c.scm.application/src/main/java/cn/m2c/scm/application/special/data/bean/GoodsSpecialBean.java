package cn.m2c.scm.application.special.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

import java.util.Date;
import java.util.List;

/**
 * 商品特惠价
 */
public class GoodsSpecialBean {
    @ColumnAlias(value = "id")
    private Integer id;
    @ColumnAlias(value = "special_id")
    private String specialId;
    @ColumnAlias(value = "goods_id")
    private String goodsId;
    @ColumnAlias(value = "goods_name")
    private String goodsName;
    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    @ColumnAlias(value = "sku_flag")
    private Integer skuFlag;
    @ColumnAlias(value = "dealer_id")
    private String dealerId;
    @ColumnAlias(value = "dealer_name")
    private String dealerName;
    @ColumnAlias(value = "start_time")
    private Date startTime;
    @ColumnAlias(value = "end_time")
    private Date endTime;
    @ColumnAlias(value = "congratulations")
    private String congratulations;
    @ColumnAlias(value = "activity_description")
    private String activityDescription;
    @ColumnAlias(value = "status")
    private Integer status;
    @ColumnAlias(value = "create_time")
    private Date createTime;
    private List<GoodsSkuSpecialBean> goodsSpecialSkuBeans;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public void setSkuFlag(Integer skuFlag) {
        this.skuFlag = skuFlag;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCongratulations() {
        return congratulations;
    }

    public void setCongratulations(String congratulations) {
        this.congratulations = congratulations;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<GoodsSkuSpecialBean> getGoodsSpecialSkuBeans() {
        return goodsSpecialSkuBeans;
    }

    public void setGoodsSpecialSkuBeans(List<GoodsSkuSpecialBean> goodsSpecialSkuBeans) {
        this.goodsSpecialSkuBeans = goodsSpecialSkuBeans;
    }
}
