package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.scm.application.goods.query.data.bean.GoodsSpecValueBean;

/**
 * 商品规格值
 */
public class GoodsSpecValueRepresentation {

    private String value;

    public GoodsSpecValueRepresentation(GoodsSpecValueBean bean) {
        this.value = bean.getSpecValue();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
