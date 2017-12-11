package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 修改商品识别图
 */
public class GoodsRecognizedDelCommand extends AssertionConcern implements Serializable {
    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 识别图主键id
     */
    private Integer id;

    /**
     * 识别图片id
     */
    private String recognizedId;

    /**
     * 识别图片url
     */
    private String recognizedUrl;

    public GoodsRecognizedDelCommand(String goodsId, Integer id, String recognizedId, String recognizedUrl) {
        this.goodsId = goodsId;
        this.id = id;
        this.recognizedId = recognizedId;
        this.recognizedUrl = recognizedUrl;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public Integer getId() {
        return id;
    }

    public String getRecognizedId() {
        return recognizedId;
    }

    public String getRecognizedUrl() {
        return recognizedUrl;
    }
}
