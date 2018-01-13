package cn.m2c.scm.application.shop.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class ShopBackImgCommand extends AssertionConcern implements Serializable {

	private String dealerId;
	
	private String shopId;
	
	private String shopBackImg;

	public ShopBackImgCommand(String dealerId, String shopId, String shopBackImg) throws NegativeException{
		
		if (StringUtils.isEmpty(shopId)) {
			throw new NegativeException(MCode.V_400,"店铺ID为空!");
		}
		if (StringUtils.isEmpty(dealerId)) {
			throw new NegativeException(MCode.V_400,"商家ID为空!");
		}
		if (StringUtils.isEmpty(shopBackImg)) {
			throw new NegativeException(MCode.V_400,"图片地址为空!");
		}
		
		this.dealerId = dealerId;
		this.shopId = shopId;
		this.shopBackImg = shopBackImg;
	}

	public String getDealerId() {
		return dealerId;
	}

	public String getShopId() {
		return shopId;
	}

	public String getShopBackImg() {
		return shopBackImg;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public void setShopBackImg(String shopBackImg) {
		this.shopBackImg = shopBackImg;
	}
	
	
	
	
}
