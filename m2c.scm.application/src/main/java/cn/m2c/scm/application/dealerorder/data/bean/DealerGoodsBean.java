package cn.m2c.scm.application.dealerorder.data.bean;

/***
 * 订单中 商家 商品Bean
 * 
 * @author fanjc created date 2017年10月27日 copyrighted@m2c
 */
public class DealerGoodsBean {
	/**
	 * 商品信息<图片>
	 */
	private String goodsImage;
	/**
	 * 商品信息<名称>
	 */
	private String goodsName;
	/**
	 * 商品信息<规格>
	 */
	private String skuName;
	/**
	 * 商品数量
	 */
	private Integer sellNum;
	/**
	 * 单价
	 */
	private long discountPrice;
	/** sku ID */
	private String skuId;

	private String goodsTitle;

	private String goodsIcon;

	private String saleAfterNo;

	private String rejectReason;

	private Integer afOrderType;

	private Long backMoney;

	private Integer isSpecial;

	private long specialPrice;

	private Integer isChange;

	/**
	 * 运费
	 */
	private long freight;

	public Long getBackMoney() {
		if (backMoney == null)
			backMoney = 0l;
		return backMoney;
	}

	public void setBackMoney(Long backMoney) {
		this.backMoney = backMoney;
	}

	public Integer getAfOrderType() {
		if (afOrderType == null)
			afOrderType = -1;
		return afOrderType;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public long getSpecialPrice() {
		return specialPrice;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public void setSpecialPrice(long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public void setAfOrderType(Integer afOrderType) {
		this.afOrderType = afOrderType;
	}

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public void setSaleAfterNo(String saleAfterNo) {
		this.saleAfterNo = saleAfterNo;
	}

	public String getGoodsTitle() {
		return goodsTitle;
	}

	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}

	/**
	 * 售后状态
	 */
	private Integer afStatus = -2;

	public String getGoodsName() {
		return goodsName;
	}

	public String getSkuName() {
		return skuName;
	}

	public String getGoodsIcon() {
		String arr = this.goodsImage;

		return arr;
	}

	public void setGoodsIcon(String goodsIcon) {
		this.goodsIcon = goodsIcon;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setSkuName(String stantardName) {
		this.skuName = stantardName;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public long getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getAfStatus() {
		return afStatus;
	}

	public void setAfStatus(Integer afStatus) {
		if (afStatus != null)
			this.afStatus = afStatus;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}
}
