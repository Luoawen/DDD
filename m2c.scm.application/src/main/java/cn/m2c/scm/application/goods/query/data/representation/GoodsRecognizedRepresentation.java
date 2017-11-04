package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;

/**
 * 商品识别图id和url结果
 */
public class GoodsRecognizedRepresentation {
	
	private String goodsId;
	private String recognizedId;
	private String recognizedUrl;
	
	public GoodsRecognizedRepresentation(GoodsBean bean) {
		this.goodsId = bean.getGoodsId();
		this.recognizedId = bean.getRecognizedId();
		this.recognizedUrl = bean.getRecognizedUrl();
	}
	
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
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
}
