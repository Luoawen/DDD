package cn.m2c.scm.application.order.data.bean;
/***
 * 用户订单状态统计
 * @author fanjc
 * created date 2017年11月17日
 * copyrighted@m2c
 */
public class UserOrderStatic {
	/**未付款订单数*/
	private int waitPays;
	/**待收货数*/
	private int waitRecs;
	/**待评论数*/
	private int waitComments;

	public int getWaitPays() {
		return waitPays;
	}

	public void setWaitPays(int waitPays) {
		this.waitPays = waitPays;
	}

	public int getWaitRecs() {
		return waitRecs;
	}

	public void setWaitRecs(int waitRecs) {
		this.waitRecs = waitRecs;
	}

	public int getWaitComments() {
		return waitComments;
	}

	public void setWaitComments(int waitComments) {
		this.waitComments = waitComments;
	}
}
