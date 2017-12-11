package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

/**
 * 商品识别图
 */
public class GoodsRecognized extends IdentifiedValueObject {
    private Goods goods;
    /**
     * 识别图片id
     */
    private String recognizedId;

    /**
     * 识别图片url
     */
    private String recognizedUrl;

    public GoodsRecognized() {
        super();
    }

    public GoodsRecognized(Goods goods, String recognizedId, String recognizedUrl) {
        this.goods = goods;
        this.recognizedId = recognizedId;
        this.recognizedUrl = recognizedUrl;
    }

    public boolean isEqualsId(Integer id) {
        return id.equals(Integer.parseInt(String.valueOf(this.id())));
    }

    public void modifyRecognized(String recognizedId, String recognizedUrl){
        this.recognizedId = recognizedId;
        this.recognizedUrl = recognizedUrl;
    }

    public String recognizedId() {
        return recognizedId;
    }

    public String recognizedUrl() {
        return recognizedUrl;
    }
}
