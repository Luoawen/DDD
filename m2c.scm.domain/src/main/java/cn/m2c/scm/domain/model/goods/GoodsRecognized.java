package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;
import cn.m2c.scm.domain.IDGenerator;

/**
 * 商品识别图
 */
public class GoodsRecognized extends IdentifiedValueObject {
    private Goods goods;

    /**
     * 识别图编号
     */
    private String recognizedNo;

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
        this.recognizedNo = IDGenerator.get("GR");
        this.goods = goods;
        this.recognizedId = recognizedId;
        this.recognizedUrl = recognizedUrl;
    }

    public boolean isEqualsRecognizedNo(String recognizedNo) {
        return recognizedNo.equals(this.recognizedNo);
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
