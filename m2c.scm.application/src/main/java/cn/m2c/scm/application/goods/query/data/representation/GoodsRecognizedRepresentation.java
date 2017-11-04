package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;

/**
 * 商品识别图id和url结果
 */
public class GoodsRecognizedRepresentation {
	/**
	 * 商品Id
	 */
	private String goodsId;
	/**
	 * 商品名
	 */
	private String goodsName;
	/**
	 * 商家Id
	 */
	private String dealerId;
	/**
	 * 商家名
	 */
	private String dealerName;
	/**
	 * 识别图Id
	 */
	private String recognizedId;
	/**
	 * 识别图Url
	 */
	private String recognizedUrl;
	
	public GoodsRecognizedRepresentation(GoodsBean bean) {
		this.goodsId = bean.getGoodsId();
		this.goodsName = bean.getGoodsName();
		this.dealerId = bean.getDealerId();
		this.dealerName = bean.getDealerName();
		this.recognizedId = bean.getRecognizedId();
		this.recognizedUrl = bean.getRecognizedUrl();
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
