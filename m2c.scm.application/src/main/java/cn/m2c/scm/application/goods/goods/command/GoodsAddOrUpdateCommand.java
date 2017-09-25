package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;


public class GoodsAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 1464280834020062408L;
	private String goodsId;
	private String dealerId;
	private String firstClassify;
	private String secondClassify;
	private String goodsNo;
	private String barNo;//条形码
	private String goodsName;//商品名称
	private String subtitle;//副标题
	private Long salePrice;//售价
	private Long marketPrice;//市场价
	private String brandId;//品牌id
	private String propertyId;//属性id
	private String transportFeeId;//运费id
	private String guarantee;//格式为：{0,1,0,1,1}
	private String advertisementPic;//广告图片
	private String gallery;//图库，支持多张{"url1","url2"}
	private String goodsDesc;//描述
	
	private String divideScale;//分成比例
	
	
	public GoodsAddOrUpdateCommand() {
		super();
	}
	
	
	public GoodsAddOrUpdateCommand(String goodsId, String dealerId,
			 String firstClassify, String secondClassify,
			String goodsNo, String barNo, String goodsName, String subtitle,
			Long salePrice, Long marketPrice, String brandId,
			String propertyId, String transportFeeId, String guarantee,
			String advertisementPic, String gallery, String goodsDesc,String divideScale) {
		super();
		assertArgumentNotNull(dealerId, "请选择经销商");
		assertArgumentLength(dealerId, 36, "经销商不合法");
		
		assertArgumentNotNull(firstClassify, "请选择商品一级分类");
		assertArgumentLength(firstClassify, 36, "一级分类过长");
		
		assertArgumentNotNull(secondClassify, "请选择商品二级分类");
		assertArgumentLength(secondClassify, 36, "二级分类过长");
		
		if(hasText(goodsNo)){
			assertArgumentLength(goodsNo, 50, "商品编号过长");
		}
		
		if(hasText(barNo)){
			assertArgumentLength(barNo, 50, "条形码过长");
		}
		
		assertArgumentNotNull(goodsName, "请填写商品名称");
		assertArgumentLength(goodsName, 50, "商品名过长");
		
		if(hasText(subtitle)){
			assertArgumentLength(subtitle, 100, "副标题过长");
		}
		assertArgumentNotNull(brandId, "请选择商品品牌模板");
		assertArgumentLength(brandId, 36, "品牌模板id过长");
		
		assertArgumentNotNull(propertyId, "请选择商品属性模板");
		assertArgumentLength(propertyId, 36, "属性模板id过长");
		
		assertArgumentNotNull(transportFeeId, "请选择商品运费模板");
		assertArgumentLength(transportFeeId, 36, "运费模板id过长");
		
		
		assertArgumentNotNull(advertisementPic, "广告图片不存在");
		assertArgumentLength(advertisementPic, 100, "广告图片url过长");
		
		assertArgumentNotNull(goodsDesc, "商品描述不能为空");
		
		this.goodsId = goodsId;
		this.dealerId = dealerId;
		this.firstClassify = firstClassify;
		this.secondClassify = secondClassify;
		this.goodsNo = goodsNo;
		this.barNo = barNo;
		this.goodsName = goodsName;
		this.subtitle = subtitle;
		this.salePrice = salePrice;
		this.marketPrice = marketPrice;
		this.brandId = brandId;
		this.propertyId = propertyId;
		this.transportFeeId = transportFeeId;
		this.guarantee = guarantee;
		this.advertisementPic = advertisementPic;
		this.gallery = gallery;
		this.goodsDesc = goodsDesc;
		this.divideScale = divideScale;
	}


	public String getDivideScale() {
		return divideScale;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getDealerId() {
		return dealerId;
	}
	public String getFirstClassify() {
		return firstClassify;
	}
	public String getSecondClassify() {
		return secondClassify;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public String getBarNo() {
		return barNo;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getSubtitle() {
		return subtitle;
	}
	public Long getSalePrice() {
		return salePrice;
	}
	public Long getMarketPrice() {
		return marketPrice;
	}
	public String getBrandId() {
		return brandId;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public String getTransportFeeId() {
		return transportFeeId;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public String getAdvertisementPic() {
		return advertisementPic;
	}
	public String getGallery() {
		return gallery;
	}
	public String getGoodsDesc() {
		return goodsDesc;
	}
	
	
	private boolean hasText(String param){
		boolean result = false;
		if(param!=null && !"".equals(param)){
			result = true;
		}
		return result;
		
	}
}
