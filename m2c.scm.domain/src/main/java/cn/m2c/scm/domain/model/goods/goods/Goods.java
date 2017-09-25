package cn.m2c.scm.domain.model.goods.goods;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class Goods  extends ConcurrencySafeEntity{

	private static final long serialVersionUID = 532222159686722695L;
	
	private String goodsId;
	private String dealerId;
	private String dealerName;
	private Integer cooperationMode;//合作方式: 1技术型 2平台型 3运营型
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
	private String guarantee;//格式为：[0,1,0,1,1]
	private String advertisementPic;//广告图片
	private String gallery;//图库，支持多张["url1","url2"]
	private String goodsDesc;//描述
	private String recognizedId;//识别图id
	private String recognizedUrl;//识别图片
	private Integer orderNum=0;//订单数
	private Integer sellerNum=0;//销售数
	private Integer collectionNum=0;//收藏数
	private Integer shareNum=0;//分享数
	private String mediaId;//媒体id
	private String mediaName;//媒体名称
	private String mresId;//媒体资源id
	private String mresName;//媒体资源名称
	private Integer mresNum=0;//媒体资源数量
	
	private String sellerId;
	private String sellerName;
	private String divideScale;
	
	private Integer payGoodsNum=0;//支付的商品数销售数
	private Integer changeGoodsNum=0;//换货商品数
	private Integer returnGoodsNum=0;//退货货商品数
	
	private Integer goodsStatus=1;//1:未上架 2：删除3：销售中4：已下架5：缺货中   （其中1是默认状态）
	private Date upGoodsDate;
	private Date downGoodsDate;
	
	private Date createdDate;
	private Date lastUpdatedDate;
	
	
	public Goods() {
		super();
	}
	
	
	public Integer getMresNum() {
		return mresNum;
	}


	public String getMediaName() {
		return mediaName;
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
	public Integer getGoodsStatus() {
		return goodsStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getDealerName() {
		return dealerName;
	}

	public Integer getCooperationMode() {
		return cooperationMode;
	}

	public String getRecognizedId() {
		return recognizedId;
	}

	public String getRecognizedUrl() {
		return recognizedUrl;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public Integer getSellerNum() {
		return sellerNum;
	}

	public Integer getCollectionNum() {
		return collectionNum;
	}

	public Integer getShareNum() {
		return shareNum;
	}
	
	public String getMediaId() {
		return mediaId;
	}
	
	public Long getSalePrice() {
		return salePrice;
	}

	public Long getMarketPrice() {
		return marketPrice;
	}

	public String getSellerId() {
		return sellerId;
	}


	public String getDivideScale() {
		return divideScale;
	}

	public String getSellerName() {
		return sellerName;
	}

	
	public String getMresId() {
		return mresId;
	}

	public String getMresName() {
		return mresName;
	}
	

	public Date getUpGoodsDate() {
		return upGoodsDate;
	}

	public void setUpGoodsDate(Date upGoodsDate) {
		this.upGoodsDate = upGoodsDate;
	}

	public Date getDownGoodsDate() {
		return downGoodsDate;
	}

	public void setDownGoodsDate(Date downGoodsDate) {
		this.downGoodsDate = downGoodsDate;
	}
	
	

	public Integer getPayGoodsNum() {
		return payGoodsNum;
	}

	public Integer getChangeGoodsNum() {
		return changeGoodsNum;
	}

	public Integer getReturnGoodsNum() {
		return returnGoodsNum;
	}

	/**
	 * 添加操作
	 * @param goodsId
	 * @param dealerId
	 * @param firstClassify
	 * @param secondClassify
	 * @param goodsNo
	 * @param barNo
	 * @param goodsName
	 * @param subtitle
	 * @param salePrice
	 * @param marketPrice
	 * @param brandId
	 * @param propertyId
	 * @param transportFeeId
	 * @param guarantee
	 * @param advertisementPic
	 * @param gallery
	 * @param goodsDesc
	 */
	public void add(String goodsId, String dealerId, String dealerName,String sellerId , String sellerName,Integer cooperationMode,String firstClassify,
			String secondClassify, String goodsNo, String barNo,
			String goodsName, String subtitle, Long salePrice,
			Long marketPrice, String brandId, String propertyId,
			String transportFeeId, String guarantee,
			String advertisementPic, String gallery, String goodsDesc,String divideScale) {
			
		this.goodsId = goodsId;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.cooperationMode = cooperationMode;
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

	public void update(String dealerId,String dealerName,String sellerId,String sellerName,Integer cooperationMode, String firstClassify,
			String secondClassify, String goodsNo, String barNo,
			String goodsName, String subtitle, Long salePrice,
			Long marketPrice, String brandId, String propertyId,
			String transportFeeId, String guarantee,
			String advertisementPic, String gallery, String goodsDesc,String divideScale) {
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
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

	/**
	 * 删除商品
	 */
	public void delete() {
		this.goodsStatus = 2;
	}

	/**
	 * 修改商品状态
	 * @param goodStatus
	 */
	public void updateStatus(Integer goodStatus) {
		this.goodsStatus = goodStatus;
		if(goodStatus==3){
			this.upGoodsDate = new Date();
		}else if(goodStatus==4){
			this.downGoodsDate = new Date();
		}
	}

	/**
	 * 绑定媒体和商品的关系
	 * @param mediaId
	 * @param mediaName
	 * @param mresId
	 * @param mresName
	 */
	public void updateMediaInfo(String mediaId, String mediaName,
			String mresId, String mresName) {
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.mresId = mresId;
		this.mresName = mresName;
		this.mresNum = this.mresNum+1;
		
	}

	public void updateRecognizedPic(String goodsId, String goodsName,
			String dealerId, String dealerName, String recognizedId,
			String recognizedUrl) {
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		if(!"".equals(recognizedId)){
			this.recognizedId = recognizedId;
		}
		if(!"".equals(recognizedUrl))
			this.recognizedUrl = recognizedUrl;
	}

	/**
	 * 收藏事件更新
	 * @param favoriteCountChange
	 */
	public void changeFavoriteCount(Integer favoriteCountChange) {
		this.collectionNum+=favoriteCountChange;
	}

	/**
	 * 修改订单数据信息
	 * @param goodsNum
	 */
	public void orderInfo(Integer goodsNum) {
		this.orderNum+=1;
		this.sellerNum+=goodsNum;
	}

	/**
	 * 分享商品数量
	 * 
	 */
	public void updateShare() {
		this.shareNum+=1;
	}

	/**
	 * 换货数量
	 * @param goodsNum
	 */
	public void changeGoods(Integer goodsNum) {
		this.changeGoodsNum+=goodsNum;
	}

	/**
	 * 退货数量
	 * @param goodsNum
	 */
	public void returnGoods(Integer goodsNum) {
		this.returnGoodsNum+=goodsNum;
	}

	/**
	 * 
	 * @param goodsNum
	 */
	public void payGoods(Integer goodsNum) {
		// TODO Auto-generated method stub
		this.payGoodsNum+=goodsNum;
	}


	/**
	 * 媒体资源数减一
	 */
	public void bind() {
		System.out.println("-------媒体资源数+1");
		this.mresNum = this.mresNum+1;
	}


	public void unbind() {
		System.out.println("-------媒体资源数-1");
		this.mresNum = this.mresNum-1;
		
	}

	/**
	 * 批量跟新
	 * @param sellerId2
	 * @param sellerName2
	 */
	public void changeSellName(String sellerId, String sellerName) {
		this.sellerId = sellerId;
		this.sellerName = sellerName;
	}


	public void changeDealerName(String dealerName) {
		// TODO Auto-generated method stub
		this.dealerName = dealerName;
	}

}
