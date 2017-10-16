package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.scm.application.goods.query.data.bean.GoodsSpecValueBean;

/**
 * 商品规格值
 */
public class GoodsSpecValueRepresentation {

    private String specValue;

    public GoodsSpecValueRepresentation(GoodsSpecValueBean bean) {
        this.specValue = bean.getSpecValue();
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }
}
