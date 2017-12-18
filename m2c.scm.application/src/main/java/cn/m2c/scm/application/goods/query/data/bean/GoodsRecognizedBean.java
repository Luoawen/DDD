package cn.m2c.scm.application.goods.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 识别图
 */
public class GoodsRecognizedBean {
    /**
     * 识别图编号
     */
    @ColumnAlias(value = "recognized_no")
    private String recognizedNo;

    /**
     * 识别图id
     */
    @ColumnAlias(value = "recognized_id")
    private String recognizedId;
    /**
     * 识别图url
     */
    @ColumnAlias(value = "recognized_url")
    private String recognizedUrl;

    public String getRecognizedId() {
        return recognizedId;
    }

    public void setRecognizedId(String recognizedId) {
        this.recognizedId = recognizedId;
    }

    public String getRecognizedUrl() {
        return recognizedUrl;
    }

    public void setRecognizedUrl(String recognizedUrl) {
        this.recognizedUrl = recognizedUrl;
    }

    public String getRecognizedNo() {
        return recognizedNo;
    }

    public void setRecognizedNo(String recognizedNo) {
        this.recognizedNo = recognizedNo;
    }
}
