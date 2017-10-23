package cn.m2c.scm.application.goods.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品保障
 */
public class GoodsGuaranteeBean {
    @ColumnAlias(value = "guarantee_id")
    private String guaranteeId;

    @ColumnAlias(value = "guarantee_name")
    private String guaranteeName;

    @ColumnAlias(value = "guarantee_desc")
    private String guaranteeDesc;

    public String getGuaranteeName() {
        return guaranteeName;
    }

    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }

    public String getGuaranteeDesc() {
        return guaranteeDesc;
    }

    public void setGuaranteeDesc(String guaranteeDesc) {
        this.guaranteeDesc = guaranteeDesc;
    }

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
    }
}
