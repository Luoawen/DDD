package cn.m2c.scm.application.order.query.dto;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.scm.domain.model.order.GoodsInfo;
import cn.m2c.scm.domain.model.order.SimpleMarketInfo;

/**
 * 商品数据传输对象
 * @author fanjc
 * created date 2017年10月18日
 * copyrighted@m2c
 */
public class GoodsDto {
	/**分类费率*/
	private float rate = 0;
	/**商品id*/
	private String goodsId;
	/**商品名称*/
	private String goodsName;
	/**商品副标题*/
	private String goodsTitle;
	/**商品分类名称*/
	private String goodsType;
	/**商品分类id*/
	private String goodsTypeId;
	/**计量单位*/
	private String goodsUnit;
	/**规格id*/
	private String skuId;
	/**规格名称*/
	private String skuName;
	/**市场价(单价)*/
	private long price;
	/**重量*/
	private float weight = 0;
	/**供货价*/
	private long supplyPrice;
	/**拍获价(单价)*/
	private long discountPrice;
	/**商品小图url*/
	private String goodsIcon;
	
	private String dealerId;
	
	private long freight;
	
	private String marketingId;
	/**营销层级*/
	private int marketLevel;
	/**营销层级门槛*/
	private int threshold;
	
	/**平台优惠*/
	private long plateformDiscount = 0;
	
	private int isChange = 0;
	
	private int changePrice = 0;
	/**营销类型*/
	private int marketType;
	/**门槛类型*/
	private int thresholdType;
	/**优惠平摊*/
	private String sharePercent;
	/**营销名称*/
	private String marketName;	
	/**优惠额度*/
	private int discount;
	/**是否执行特惠价 1是*/
	private int isSpecial;
	/**特惠价*/
	private long specialPrice;
	/**广告位id*/
	private String mresId;
	
	private int index;
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIsChange() {
		return isChange;
	}

	public void setMarketLevel(int marketLevel) {
		this.marketLevel = marketLevel;
	}

	public String getMresId() {
		return mresId;
	}

	public void setMresId(String mresId) {
		this.mresId = mresId;
	}

	public int getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(int isSpecial) {
		this.isSpecial = isSpecial;
	}

	public long getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getMarketType() {
		return marketType;
	}

	public void setMarketType(int marketType) {
		this.marketType = marketType;
	}

	public int getThresholdType() {
		return thresholdType;
	}

	public void setThresholdType(int thresholdType) {
		this.thresholdType = thresholdType;
	}

	public String getSharePercent() {
		return sharePercent;
	}

	public void setSharePercent(String sharePercent) {
		this.sharePercent = sharePercent;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	public int isChange() {
		return isChange;
	}
	
	public void setIsChange(int s) {
		isChange = s;
	}
	
	public int getChangePrice() {
		return changePrice;
	}
	
	public void setChangePrice(int s) {
		changePrice = s;
	}
	
	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	/**购买数量*/
	private int purNum = 0;
	
	
	public Integer getMarketLevel() {
		return marketLevel;
	}

	public void setMarketLevel(Integer marketLevel) {
		this.marketLevel = marketLevel;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public long getFreight() {
		return freight;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public String getMarketingId() {
		return marketingId;
	}

	public void setMarketingId(String marketingId) {
		this.marketingId = marketingId;
	}

	public int getPurNum() {
		return purNum;
	}
	
	public void setPurNum(int num) {
		purNum = num;
	}
	
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public float getRate() {
		return rate;
	}
	public void setRate(float rate) {
		this.rate = rate;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsTitle() {
		return goodsTitle;
	}
	public void setGoodsTitle(String goodsTitle) {
		this.goodsTitle = goodsTitle;
	}
	public String getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}
	public String getGoodsTypeId() {
		return goodsTypeId;
	}
	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	public String getGoodsUnit() {
		return goodsUnit;
	}
	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		/*if (StringUtils.isEmpty(skuName)) 
			skuName = "默认";*/
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public long getSupplyPrice() {
		return supplyPrice;
	}
	public void setSupplyPrice(long supplyPrice) {
		this.supplyPrice = supplyPrice;
	}
	public long getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getGoodsIcon() {
		return goodsIcon;
	}
	public void setGoodsIcon(String goodsIcon) {
		this.goodsIcon = goodsIcon;
	}
	/***
	 * 获取需要执行的价格
	 * @return
	 */
	public long getThePrice() {
		if (isSpecial == 1)
			return specialPrice;
		return discountPrice;
	}
	/**
	 * 转换成业务层需要的对象
	 * @return
	 */
	public GoodsInfo toGoodsInfo() {
		return new GoodsInfo(rate, goodsId, goodsName, goodsTitle
				,goodsType, goodsTypeId, goodsUnit, skuId
				,skuName, price, supplyPrice, discountPrice, goodsIcon
				,weight, purNum, freight, plateformDiscount, isChange, changePrice
				,isSpecial, specialPrice);		
	}
	
	public SimpleMarketInfo toMarketInfo() {
		if (!StringUtils.isEmpty(marketingId)) {
			return new SimpleMarketInfo(marketingId, marketLevel, threshold, 
					marketType, thresholdType, sharePercent, marketName, discount);
		}
		return null; 
	}
	/***
	 * 从src copy相对应的属性到本对象
	 * @param src
	 */
	public void copyField(GoodsDto src) {
		rate = src.getRate();
		goodsId = src.getGoodsId();
		goodsName = src.getGoodsName();
		goodsTitle = src.getGoodsTitle();
		goodsType = src.getGoodsType();
		goodsTypeId = src.getGoodsTypeId();
		goodsUnit = src.getGoodsUnit();		
		skuName = src.getSkuName();
		price = src.getPrice();
		weight = src.getWeight();
		supplyPrice = src.getSupplyPrice();
		discountPrice = src.getDiscountPrice();
		goodsIcon = src.getGoodsIcon();
		dealerId = src.getDealerId();		
	}
}
