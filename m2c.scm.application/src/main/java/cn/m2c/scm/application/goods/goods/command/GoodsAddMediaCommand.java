package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class GoodsAddMediaCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 2257738552638535443L;
	private String goodsId;
	private String mediaId;
	private String mediaName;
	private String mresId;
	private String mresName;
	
	public GoodsAddMediaCommand() {
		super();
	}
	
	public GoodsAddMediaCommand(String goodsId, String mediaId,
			String mediaName, String mresId, String mresName) {
		super();
		this.goodsId = goodsId;
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.mresId = mresId;
		this.mresName = mresName;
	}

	public String getGoodsId() {
		return goodsId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public String getMresId() {
		return mresId;
	}
	public String getMresName() {
		return mresName;
	}
	
	
}
