package cn.m2c.scm.application.order.data.bean;


/***
 * 订单提交时的商品请求数据
 * @author fanjc
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class GoodsReqBean {

	private int purNum;
	
	private int level;
	
	private String marketId;
	/**1 是，0 否*/
	private int isChange = 0;
	
	public GoodsReqBean() {}
	
	public GoodsReqBean(int num, int level, String marketId, int isChange) {
		purNum = num;
		this.level = level;
		this.marketId = marketId;
		this.isChange = isChange;
	}

	public int getPurNum() {
		return purNum;
	}

	public void setPurNum(int purNum) {
		this.purNum = purNum;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public int getIsChange() {
		return isChange;
	}

	public void setIsChange(int isChange) {
		this.isChange = isChange;
	}	
}
