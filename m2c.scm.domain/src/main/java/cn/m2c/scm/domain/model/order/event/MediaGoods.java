package cn.m2c.scm.domain.model.order.event;
/***
 * 订单中媒体与商品关联数据
 * @author 89776
 * created date 2018年3月15日
 * copyrighted@m2c
 */
public class MediaGoods {
	/**商家订单号*/
	private String dealerOrderId;
	/**媒体id*/
	private String mediaId;
	/** 广告位id*/
	private String mediaResId;
	/**sort no商家在订单中的插入位置*/
	private int sortNo;
	
	public MediaGoods(String dealerOrderId, String mediaId,
			String mediaResId, int sortNo) {
		this.dealerOrderId = dealerOrderId;
		this.mediaId = mediaId;
		this.mediaResId = mediaResId;
		this.sortNo = sortNo;
	}
	
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaResId() {
		return mediaResId;
	}
	public void setMediaResId(String mediaResId) {
		this.mediaResId = mediaResId;
	}
	public int getSortNo() {
		return sortNo;
	}
	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}
}
