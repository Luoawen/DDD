package cn.m2c.scm.application.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class SendOrderSMSCommand extends AssertionConcern implements Serializable{
	
	private static final long serialVersionUID = 7100483192909708009L;
	
	private String userId;
	private String shopName;

	public SendOrderSMSCommand(String userId, String shopName) {
		assertArgumentNotNull(userId, "用户id不能为空");
		assertArgumentLength(userId, 36, "用户id过长");
		assertArgumentNotNull(shopName, "店铺名称不能为空");
		this.userId = userId;
		this.shopName = shopName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	
	
}
