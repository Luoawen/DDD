package cn.m2c.scm.application.order.data.bean;

import java.util.List;

/***
 * 订单计算时用的market 活动
 * @author 89776
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class MarketBean {

	private String fullCutId;
	
	private String fullCutNo;
	/**满减状态，1：未生效，2：已生效，3已失效*/
	private int status;
	
	private String fullCutName;
	
	private int remainNum;
	/**满减形式，1：减钱，2：打折，3：换购*/
	private int fullCutType;
	/**门槛类型， 1：金额，2：件数*/
	private int thresholdType;
	
	private List<MarketLevelBean> itemList;
	/**作用范围，0：全场，1：商家，2：商品，3：品类*/
	private int rangeType;
	
	private List<MarketRange> suitableRangeList;
	
	private List<MarketRange> removeRangeList;

	public String getFullCutId() {
		return fullCutId;
	}

	public void setFullCutId(String fullCutId) {
		this.fullCutId = fullCutId;
	}

	public String getFullCutNo() {
		return fullCutNo;
	}

	public void setFullCutNo(String fullCutNo) {
		this.fullCutNo = fullCutNo;
	}
	/**满减状态，1：未生效，2：已生效，3已失效*/
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getFullCutName() {
		return fullCutName;
	}

	public void setFullCutName(String fullCutName) {
		this.fullCutName = fullCutName;
	}

	public int getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(int remainNum) {
		this.remainNum = remainNum;
	}
	/**满减形式，1：减钱，2：打折，3：换购*/
	public int getFullCutType() {
		return fullCutType;
	}

	public void setFullCutType(int fullCutType) {
		this.fullCutType = fullCutType;
	}

	/**门槛类型， 1：金额，2：件数*/
	public int getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}

	public List<MarketLevelBean> getItemList() {
		return itemList;
	}

	public void setItemList(List<MarketLevelBean> itemList) {
		this.itemList = itemList;
	}

	public int getRangeType() {
		return rangeType;
	}

	public void setRangeType(int rangeType) {
		this.rangeType = rangeType;
	}

	public List<MarketRange> getSuitableRangeList() {
		return suitableRangeList;
	}

	public void setSuitableRangeList(List<MarketRange> suitableRangeList) {
		this.suitableRangeList = suitableRangeList;
	}

	public List<MarketRange> getRemoveRangeList() {
		return removeRangeList;
	}

	public void setRemoveRangeList(List<MarketRange> removeRangeList) {
		this.removeRangeList = removeRangeList;
	}	
	
	private int threshold;
	/**
	 * 获取层级对应的 计算金额
	 * @param level
	 * @return
	 */
	public int getLevelNum(int level) {
		for (MarketLevelBean l : itemList) {
			if (level == l.getLevel()) {
				switch (fullCutType) {
				case 1:
					threshold = l.getThreshold();
					return l.getMoney();
				case 2:
					threshold = l.getThreshold();
					return (int)l.getDiscount() * 100;
				case 3:
					threshold = l.getThreshold();
					return l.getBuyingPrice();
				}
			}
		}
		return -1;
	}
	/***
	 * 获取门槛
	 * <br>先获取 getLevelNum()
	 * @return
	 */
	public int getThreshold() {
		return threshold;
	}
}
