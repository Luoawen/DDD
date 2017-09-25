package cn.m2c.goods.domain.location;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class Location  extends ConcurrencySafeEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9218591648240220595L;
	private String locationId;
	private Integer locSelect;//0:未选择 1：首页幻灯  2：搜索无结果推荐
	private String title;
	private Integer isOnline;//0:未选择 1：上线 2：下线
	private String effectiveTime;
	private Integer displayOrder;
	private String imgUrl;
	private Integer locType;// 1：链接 2：商品
	private String redirectUrl;
	private String goodsId;
	private String goodsName;
	private Long goodsPrice;
	private Integer locStatus=1;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	private Date upDate;
	public Location() {
		super();
	}
	public Location(String locationId, Integer locSelect, String title,
			Integer isOnline, String effectiveTime, Integer displayOrder,
			String imgUrl, Integer locType, String redirectUrl, String goodsId,
			String goodsName) {
		super();
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
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public Integer getLocStatus() {
		return locStatus;
	}
	public Date getUpDate() {
		return upDate;
	}
	public Long getGoodsPrice() {
		return goodsPrice;
	}
	public void add(String locationId, Integer locSelect, String title,
			Integer isOnline, String effectiveTime, Integer displayOrder,
			String imgUrl, Integer locType, String redirectUrl,
			String goodsId, String goodsName,Long goodsPrice) {
		this.locationId = locationId;
		this.locSelect = locSelect;
		this.title = title;
		this.isOnline = isOnline;
		if(isOnline==1){
			this.upDate = new Date();
		}
		this.effectiveTime = effectiveTime;
		this.displayOrder = displayOrder;
		this.imgUrl = imgUrl;
		this.locType = locType;
		this.redirectUrl = redirectUrl;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsPrice = goodsPrice;
	}
	
	
	public void update(String locationId, Integer locSelect, String title,
			Integer isOnline, String effectiveTime, Integer displayOrder,
			String imgUrl, Integer locType, String redirectUrl,
			String goodsId, String goodsName ,Long goodsPrice) {
		this.locationId = locationId;
		this.locSelect = locSelect;
		this.title = title;
		this.isOnline = isOnline;
		if(isOnline==1){
			this.upDate = new Date();
		}
		this.effectiveTime = effectiveTime;
		this.displayOrder = displayOrder;
		this.imgUrl = imgUrl;
		this.locType = locType;
		this.redirectUrl = redirectUrl;
		this.goodsId = goodsId;
		this.goodsName = goodsName;	
		this.goodsPrice = goodsPrice;
	}
	
	
	
	public void del() {
		this.locStatus=2;
	}

}
