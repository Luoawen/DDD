package cn.m2c.scm.domain.model.shop;

import cn.m2c.common.RedisUtil;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.scm.application.shop.ShopApplication;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Shop extends ConcurrencySafeEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3161891294554265529L;
	
	private static final Logger log = LoggerFactory.getLogger(Shop.class);

	private String shopId;
	private String dealerId;
	private String shopName;//店铺名
	private String shopIcon;//店铺图标
	private String shopIntroduce;//店铺介绍
	private String shopReceipt;//发票信息
	private String customerServiceTel;//客服电话
	private Date createdDate;
	private Date lastUpdatedDate;

	public Shop() {
		super();
	}

	public void updateShopInfo(String shopName, String shopIntroduce,
							   String shopIcon, String shopReceipt, String customerServiceTel) {
		this.shopName = shopName;
		this.shopIntroduce = shopIntroduce;
		this.shopIcon = shopIcon;
		this.shopReceipt = shopReceipt;
		this.customerServiceTel = customerServiceTel;
		this.lastUpdatedDate = new Date();
		removeShopCash(this.dealerId);
	}


	/**
	 * 清除缓存操作
	 * @param dealerId
	 */
	public static void removeShopCash(String dealerId) {
		 String key = ("m2c.scm.shop." + dealerId).trim();
		 try {
			 if(RedisUtil.getString(key)!=null && !"".equals(RedisUtil.getString(key))){
				 RedisUtil.del(key);
			 }
		} catch (Exception e) {
			log.error("清除缓存失败");
		}
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
