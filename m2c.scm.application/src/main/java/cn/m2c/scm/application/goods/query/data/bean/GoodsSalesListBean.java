package cn.m2c.scm.application.goods.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品销量榜
 */
public class GoodsSalesListBean {
    @ColumnAlias(value = "id")
    private Integer id;
    @ColumnAlias(value = "month")
    private Integer month;
    @ColumnAlias(value = "dealer_id")
    private String dealerId;
    @ColumnAlias(value = "goods_id")
    private String goodsId;
    @ColumnAlias(value = "goods_name")
    private String goodsName;
    @ColumnAlias(value = "goods_sale_num")
    private Integer goodsSaleNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
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

    public Integer getGoodsSaleNum() {
        return goodsSaleNum;
    }

    public void setGoodsSaleNum(Integer goodsSaleNum) {
        this.goodsSaleNum = goodsSaleNum;
    }
}
