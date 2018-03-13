package cn.m2c.scm.application.shop.data.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShopBackImgBean {
	
	@ColumnAlias(value = "shop_background_img")
	private String shopBackImg;
	
	@ColumnAlias(value = "last_updated_date")
	private Date lastUpdatedTime;
	
	private String lastUpdateDate;
	


	public String getLastUpdateDate() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(lastUpdatedTime);
	}


	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}


	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}


	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}


	public String getShopBackImg() {
		return shopBackImg;
	}


	public void setShopBackImg(String shopBackImg) {
		this.shopBackImg = shopBackImg;
	}

	
	

}
