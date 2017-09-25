package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class RecognizedPicCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 6112831722448583881L;
	
	private String goodsId;
	private String dealerId;
	private String goodsName;
	private String dealerName;
	private String recognizedId;
	private String recognizedUrl;
	
	
	
	public RecognizedPicCommand() {
		super();
	}
	
	
	public RecognizedPicCommand(String goodsId, String dealerId,
			String goodsName, String dealerName, String recognizedId,
			String recognizedUrl) {
		super();
		this.goodsId = goodsId;
		this.dealerId = dealerId;
		this.goodsName = goodsName;
		this.dealerName = dealerName;
		this.recognizedId = recognizedId;
		this.recognizedUrl = recognizedUrl;
	}


	public String getGoodsId() {
		return goodsId;
	}
	public String getDealerId() {
		return dealerId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getDealerName() {
		return dealerName;
	}
	public String getRecognizedId() {
		return recognizedId;
	}
	public String getRecognizedUrl() {
		return recognizedUrl;
	}
	
}	
