package cn.m2c.goods.domain.goods;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

public class GoodsStatusEvent  extends AssertionConcern implements DomainEvent{
	
	private Date loginDate;
	private String goodsId;
	private String goodsName;
	private String mediaId;
	private String mediaName;
	private String mresId;
	private String mresName;
	private Integer goodsStatus;
	private Integer oldGoodsStatus;
	private String goodsClassifyId;
	private String goodsClassifyName;
	
	
	
	

	public GoodsStatusEvent() {
		super();
	}
	
	
	/**
	 * 
	 * @param goodsId
	 * @param goodsName
	 * @param mediaId
	 * @param mediaName
	 * @param mresId
	 * @param mresName
	 * @param goodStatus
	 */
	public GoodsStatusEvent(String goodsId, String goodsName,
			String mediaId, String mediaName, String mresId,
			String mresName, Integer oldGoodsStatus, Integer goodStatus,String goodsClassifyId,String goodsClassifyName) {
		this.setGoodsId(goodsId);
		this.setGoodsName(goodsName);
		this.setMediaId(mediaId);
		this.setMediaName(mediaName);
		this.setMresId(mresId);
		this.setMresName(mresName);
		this.setLoginDate(new Date());
		this.setOldGoodsStatus(oldGoodsStatus);
		this.setGoodsStatus(goodStatus);
		this.setGoodsClassifyId(goodsClassifyId);
		this.setGoodsClassifyName(goodsClassifyName);
	}




	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}


	public void setGoodsClassifyId(String goodsClassifyId) {
		this.goodsClassifyId = goodsClassifyId;
	}


	public String getGoodsClassifyName() {
		return goodsClassifyName;
	}


	public void setGoodsClassifyName(String goodsClassifyName) {
		this.goodsClassifyName = goodsClassifyName;
	}


	public Integer getGoodsStatus() {
		return goodsStatus;
	}


	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}


	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public void setMresId(String mresId) {
		this.mresId = mresId;
	}

	public void setMresName(String mresName) {
		this.mresName = mresName;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public String getGoodsName() {
		return goodsName;
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

	
	public Integer getOldGoodsStatus() {
		return oldGoodsStatus;
	}


	public void setOldGoodsStatus(Integer oldGoodsStatus) {
		this.oldGoodsStatus = oldGoodsStatus;
	}


	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return this.getLoginDate();
	}

}
