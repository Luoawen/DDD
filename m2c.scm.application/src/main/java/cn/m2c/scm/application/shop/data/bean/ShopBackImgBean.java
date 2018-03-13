package cn.m2c.scm.application.shop.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShopBackImgBean {
	
	@ColumnAlias(value = "shop_background_img")
	private String shopBackImg;
	
	@ColumnAlias(value = "last_updated_date")
	private Date lastUpdatedDate;

	public String getShopBackImg() {
		return shopBackImg;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setShopBackImg(String shopBackImg) {
		this.shopBackImg = shopBackImg;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
	

}
