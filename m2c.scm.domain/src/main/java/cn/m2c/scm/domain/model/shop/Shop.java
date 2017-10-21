package cn.m2c.scm.domain.model.shop;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class Shop extends ConcurrencySafeEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3161891294554265529L;

	private String shopId;
	private String dealerId;
	private String shopName;//店铺名
	private String shopIcon;//店铺图标
	private String shopIntroduce;//店铺介绍
	private String shopReceipt;//发票信息
	private String customerServiceTel;//客服电话
	private Date createdDate;
	private Date lastUpdatedDate;
	
	
	public void updateShopInfo(String shopName, String shopIntroduce,
			String shopIcon,String shopReceipt, String customerServiceTel) {
		this.shopName = shopName;
		this.shopIntroduce = shopIntroduce;
		this.shopIcon = shopIcon;
		this.shopReceipt = shopReceipt;
		this.customerServiceTel = customerServiceTel;
	}




	public Shop(String shopId, String dealerId, String shopName,
			String shopIcon, String shopIntroduce, String shopReceipt,
			String customerServiceTel) {
		super();
		this.shopId = shopId;
		this.dealerId = dealerId;
		this.shopName = shopName;
		this.shopIcon = shopIcon;
		this.shopIntroduce = shopIntroduce;
		this.shopReceipt = shopReceipt;
		this.customerServiceTel = customerServiceTel;
	}
	
}
