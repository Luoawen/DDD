package cn.m2c.scm.application.shop.command;

import java.io.Serializable;

import cn.m2c.common.ArgumentUtils;
import cn.m2c.ddd.common.AssertionConcern;

public class ShopInfoUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 1310691939002417740L;
	
	private String dealerId;
	private String shopId;
	private String shopName;//店铺名
	private String shopIcon;//店铺图标
	private String shopIntroduce;//店铺介绍
	private String shopReceipt;//店铺介绍
	private String customerServiceTel;//客服电话
	
	public ShopInfoUpdateCommand() {
		super();
	}
	
	public ShopInfoUpdateCommand(String dealerId, String shopId, String shopName,
			String shopIcon, String shopIntroduce, String shopReceipt ,String customerServiceTel) {
		super();
		
		assertArgumentNotEmpty(dealerId, "经销商id不能为空");
		
		
		assertArgumentNotEmpty(shopName, "店铺名称不能为空");
		assertArgumentLength(shopName, 50, "店铺名称过长");
		
		assertArgumentNotEmpty(shopIcon, "店铺图标不能为空");
		assertArgumentLength(shopIcon, 100, "店铺图标过长");
		
		assertArgumentNotEmpty(customerServiceTel, "客服电话不能为空");
		
		this.dealerId = dealerId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.shopIcon = shopIcon;
		this.shopIntroduce = shopIntroduce;
		this.shopReceipt = shopReceipt;
		this.customerServiceTel = customerServiceTel;
	}

	public String getDealerId() {
		return dealerId;
	}
	public String getShopName() {
		return shopName;
	}
	public String getShopIcon() {
		return shopIcon;
	}
	public String getShopIntroduce() {
		return shopIntroduce;
	}
	public String getCustomerServiceTel() {
		return customerServiceTel;
	}

	public String getShopId() {
		return shopId;
	}

	public String getShopReceipt() {
		return shopReceipt;
	}
	
	
}
