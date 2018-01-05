package cn.m2c.scm.application.special.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class GoodsSpecialImageCommand  extends AssertionConcern implements Serializable {
	private String imageId;
	//特惠价图片url
	private String specialImageUrl;

	public GoodsSpecialImageCommand(String imageId, String specialImageUrl) throws NegativeException {
		if(StringUtils.isEmpty(imageId)) {
			throw new NegativeException(MCode.V_1, "请刷新页面重新获取特惠价图片id");
		}
		if(StringUtils.isEmpty(specialImageUrl) && StringUtils.isEmpty(specialImageUrl.trim())) {
			throw new NegativeException(MCode.V_1, "特惠价图片url不能为空");
		}
		this.imageId = imageId;
		this.specialImageUrl = specialImageUrl;
	}
	
	public String getImageId() {
		return imageId;
	}

	public String getSpecialImageUrl() {
		return specialImageUrl;
	}

	@Override
	public String toString() {
		return "GoodsSpecialImageCommand [specialImageUrl=" + specialImageUrl + "]";
	}
}
