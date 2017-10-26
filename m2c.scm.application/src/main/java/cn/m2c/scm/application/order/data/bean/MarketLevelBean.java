package cn.m2c.scm.application.order.data.bean;
/**
 * 营销层级
 * @author fanjc
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class MarketLevelBean {
	
	private int threshold;
	
	private float discount;
	
	private int money;
	
	private int level;
	/**换购价*/
	private int buyingPrice;
	
	private String goodsIds;

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(int buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public String getGoodsIds() {
		return goodsIds;
	}

	public void setGoodsIds(String goodsIds) {
		this.goodsIds = goodsIds;
	}
}
