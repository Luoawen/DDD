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
	
	private String skuId;
	/**商品优惠后的价钱*/
	private long goodsAmount;
	
	private String mediaId;
	
	private String sellerId;
	
	public SimpleMediaRes(String resId, String bdsRate, String skuId,
			long goodsAmount, String mediaId, String sellerId) {
		this.mresId = resId;
		this.bdsRate = bdsRate;
		this.skuId = skuId;
		this.goodsAmount = goodsAmount;
		
		this.mediaId = mediaId;
		this.sellerId = sellerId;
	}

	public String getMresId() {
		return mresId;
	}

	public String getBdsRate() {
		return bdsRate;
	}

	public String getSkuId() {
		return skuId;
	}

	public long getGoodsAmount() {
		return goodsAmount;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getSellerId() {
		return sellerId;
	}
}
