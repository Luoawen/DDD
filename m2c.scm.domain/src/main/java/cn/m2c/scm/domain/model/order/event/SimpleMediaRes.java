package cn.m2c.scm.domain.model.order.event;
/***
 * 简单媒体资源及专员信息
 * @author fanjc
 * created date 2017年10月24日
 * copyrighted@m2c
 */
public class SimpleMediaRes {
	/**广告位ID*/
	private String mresId;
	/**bd专员及分成*/
	private String bdsRate;
	
	public SimpleMediaRes(String resId, String bdsRate) {
		this.mresId = resId;
		this.bdsRate = bdsRate;
	}

	public String getMresId() {
		return mresId;
	}

	public String getBdsRate() {
		return bdsRate;
	}
	
}
