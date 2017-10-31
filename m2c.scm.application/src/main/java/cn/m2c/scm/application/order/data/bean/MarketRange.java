package cn.m2c.scm.application.order.data.bean;
/***
 * 营销适应范围
 * @author fanjc
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class MarketRange {
	/**适用的商品/商家/品类ID*/
	private String id;
	/**适用的商品/商家/品类类型1：商家，2：商品，3：品类*/
	private int type;
	/**作用范围类型0：全场，1：商家，2：商品，3：品类*/
	private int rangeType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRangeType() {
		return rangeType;
	}
	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}
}
