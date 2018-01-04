package cn.m2c.scm.domain.model.special;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 特惠价图片
 */
public class GoodsSpecialImage extends ConcurrencySafeEntity {
	private String imageId;
    private String specialImageUrl;
    private Date createdDate;
    private Date lastUpdatedDate;
	
    public GoodsSpecialImage() {
		super();
	}
	
	public GoodsSpecialImage(String imageId, String specialImageUrl) {
		this.imageId = imageId;
		this.specialImageUrl = specialImageUrl;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}
	
	public void modifySpecialImageUrl(String specialImageUrl) {
		this.specialImageUrl = specialImageUrl;
		this.lastUpdatedDate = new Date();
	}
	
	public String specialImageUrl() {
		return specialImageUrl;
	}
}
