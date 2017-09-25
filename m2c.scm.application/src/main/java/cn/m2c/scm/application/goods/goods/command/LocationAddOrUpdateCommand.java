package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class LocationAddOrUpdateCommand extends AssertionConcern implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2747437239261877360L;
	private String locationId;
	private Integer locSelect;
	private String title;
	private Integer isOnline;
	private String effectiveTime;
	private Integer displayOrder;
	private String imgUrl;
	private Integer locType;
	private String redirectUrl;
	private String goodsId;
	private String goodsName;
	private Long goodsPrice;
	public LocationAddOrUpdateCommand() {
		super();
	}
	
	
	
	
	
	public LocationAddOrUpdateCommand(String locationId, Integer locSelect,
			String title, Integer isOnline, String effectiveTime,
			Integer displayOrder, String imgUrl, Integer locType,
			String redirectUrl, String goodsId, String goodsName,Long goodsPrice) {
		super();
		
		assertArgumentNotNull(locationId, "位置id不能为空");
		assertArgumentLength(locationId, 36, "位置id过长");
		
		assertArgumentRange(locSelect, 0, 2, "选择不合法");
		
		
		this.locationId = locationId;
		this.locSelect = locSelect;
		this.title = title;
		this.isOnline = isOnline;
		this.effectiveTime = effectiveTime;
		this.displayOrder = displayOrder;
		this.imgUrl = imgUrl;
		this.locType = locType;
		this.redirectUrl = redirectUrl;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsPrice=goodsPrice;
	}





	public String getLocationId() {
		return locationId;
	}
	public Integer getLocSelect() {
		return locSelect;
	}
	public String getTitle() {
		return title;
	}
	public Integer getIsOnline() {
		return isOnline;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public Integer getLocType() {
		return locType;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}

	

	public Long getGoodsPrice() {
		return goodsPrice;
	}





	@Override
	public String toString() {
		return "LocationAddOrUpdateCommand [locationId=" + locationId
				+ ", locSelect=" + locSelect + ", title=" + title
				+ ", isOnline=" + isOnline + ", effectiveTime=" + effectiveTime
				+ ", displayOrder=" + displayOrder + ", imgUrl=" + imgUrl
				+ ", locType=" + locType + ", redirectUrl=" + redirectUrl
				+ ", goodsId=" + goodsId + ", goodsName=" + goodsName + "]";
	}

}
