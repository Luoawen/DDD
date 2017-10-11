package cn.m2c.scm.order.dto;


/**
 * @ClassName: OrderDto
 * @Description:订单信息
 * @author moyj
 * @date 2017年4月26日 下午3:13:59
 *
 */
public class OrderDto implements java.io.Serializable{

	private static final long serialVersionUID = 5621196057963774712L;
	   
		private String orderId;					//订单ID
		private String orderNo;					//订单编号
		private String dispatchWay;				//配送方式
		private Integer invoiceType;			//发票类型 1不开 2个人 3公司
		private String invoiceTitle;			//发票抬头
		private String taxIdenNum;				//纳税人识别号
		private Long orderPrice;				//订单价格
		private Long freightPrice;				//运费
		private Long payPrice;					//付款价格
		private Integer payWay;					//支付方式 (1支付宝,2微信)
		private Long commitTime;				//下单时间
		private Long payStartTime;				//支付生成时间
		private Long payEndTime;				//支付完成时间,即实际付款时间
		private String payTradeNo;				//外部支付交易号

		private String buyerId;					//买家编号
		private String buyerName;				//买家名称
		private String buyerPhone;				//买家电话
		private String buyerMessage;			//买家留言
		
		private String dealerUserId;			//供应商用户编号
		private String dealerId;				//商家编号
		private String dealerName;				//商家名称
		private String dealerPhone;				//商家电话
		private Integer dealerStrategy;			//商家结算策略（1、无需结算 2、按比例 3、底价）
		private Integer dealerPercent;			//商家结算比例
		private String goodsId;					//货品编号
		private String goodsNo;					//货品编码
		private String goodsName;				//货品名称
		private String propertyId;				//货品规格属性
		private String propertyDesc;			//规格描述
		private Long goodsUnitPrice;			//货品单价
		private Long goodsMarketPrice;			//货品市场价格
		private Integer goodsNum;				//货品订购数量
		private Integer goodsType;				//货品类型(1、实体货品  2服务类型  3 虚拟商品)
		
		private String mresId;					//媒体资源编号
		private String mresNo;					//媒体资源编号
		private String mresName;				//媒体资源名称
		private String mediaUserId;				//媒体用户编号
		private String mediaId;					//媒体编号
		private String mediaName;				//媒体名称
		private String mediaPhone;				//媒体电话
		private Integer mediaStrategy;			//媒体结算策略   （1、入驻<无需结算> 2<按比例>）
		private Integer mediaPercent;			//媒体结算比例
		private String salerUserId;				//促销员用户编号
		private String salerId;					//促销员编号
		private String salerName;				//促销员名称
		private String salerPhone;				//促销员电话
		private Integer salerStrategy;			//促销员结算策略   （1、按比例）
		private Integer salerPercent;				//促销员结算比例
		
		private String receiverId;				//收货地址编号
		private String receiverName;			//收货人
		private String provinceCode;			//省Code
		private String provinceName;			//省
		private String cityCode;				//市code
		private String cityName;				//市
		private String areaCode;				//区code
		private String areaName;				//区
		private String receiverAddr;			//收获地址
		private String receiverPhone;			//收货人电话
		private String receiverZipCode;			//邮编
		
		private String expCompanyCode;			//快递公司代码
		private String expCompanyName;			//快递公司名称
		private String waybillNo;				//运单号
		private Long commitWaybTime;			//提交运单时间
		private Long receiverTime;				//收货时间
		
		private Integer orderStatus;			//订单状态 1正常 2取消
		private Integer payStatus;				//支付状态 1待支付 2已支付
		private Integer logisticsStatus;		//物流状态 1待发货 2待收货 3 已收货
		private Integer settleStatus;			//结算状态 1待结算 2已结算
		private Integer afterSalesStatus;		//售后状态 1待申请 2待审核 3 退换中 4.已退款
		private Integer commentStatus;			//评论状态 1待评论,2已经评论
		
		private Integer platformStrategy;		//平台结算策略   （1、按比例）
		private Integer platformPercent;		//平台结算比例
		
		private Long refundAmount;				//退款金额
		
		public String getOrderId() {
			return orderId;
		}
		public String getDispatchWay() {
			return dispatchWay;
		}
		public Integer getInvoiceType() {
			return invoiceType;
		}
		public String getInvoiceTitle() {
			return invoiceTitle;
		}
		public String getTaxIdenNum() {
			return taxIdenNum;
		}
		public Long getOrderPrice() {
			return orderPrice;
		}
		public Long getFreightPrice() {
			return freightPrice;
		}
		public Long getPayPrice() {
			return payPrice;
		}
		public Integer getPayWay() {
			return payWay;
		}
		public Long getCommitTime() {
			return commitTime;
		}
		public Long getPayStartTime() {
			return payStartTime;
		}
		public Long getPayEndTime() {
			return payEndTime;
		}
		public String getPayTradeNo() {
			return payTradeNo;
		}
		public String getBuyerId() {
			return buyerId;
		}
		public String getBuyerName() {
			return buyerName;
		}
		public String getBuyerPhone() {
			return buyerPhone;
		}
		public String getBuyerMessage() {
			return buyerMessage;
		}
		public String getDealerUserId() {
			return dealerUserId;
		}
		public String getDealerId() {
			return dealerId;
		}
		public String getDealerName() {
			return dealerName;
		}
		public String getDealerPhone() {
			return dealerPhone;
		}
		public Integer getDealerStrategy() {
			return dealerStrategy;
		}
		public Integer getDealerPercent() {
			return dealerPercent;
		}
		public String getGoodsId() {
			return goodsId;
		}
		public String getGoodsNo() {
			return goodsNo;
		}
		public String getGoodsName() {
			return goodsName;
		}
		public String getPropertyId() {
			return propertyId;
		}
		public String getPropertyDesc() {
			return propertyDesc;
		}
		public Long getGoodsUnitPrice() {
			return goodsUnitPrice;
		}
		public Long getGoodsMarketPrice() {
			return goodsMarketPrice;
		}
		public Integer getGoodsNum() {
			return goodsNum;
		}
		public Integer getGoodsType() {
			return goodsType;
		}
		public String getMresId() {
			return mresId;
		}
		public String getMresName() {
			return mresName;
		}
		public String getMediaUserId() {
			return mediaUserId;
		}
		public String getMediaId() {
			return mediaId;
		}
		public String getMediaName() {
			return mediaName;
		}
		public String getMediaPhone() {
			return mediaPhone;
		}
		public Integer getMediaStrategy() {
			return mediaStrategy;
		}
		public Integer getMediaPercent() {
			return mediaPercent;
		}
		public String getSalerUserId() {
			return salerUserId;
		}
		public String getSalerId() {
			return salerId;
		}
		public String getSalerName() {
			return salerName;
		}
		public String getSalerPhone() {
			return salerPhone;
		}
		public Integer getSalerStrategy() {
			return salerStrategy;
		}
		public Integer getSalerPercent() {
			return salerPercent;
		}
		public String getReceiverId() {
			return receiverId;
		}
		public String getReceiverName() {
			return receiverName;
		}
		public String getReceiverAddr() {
			return receiverAddr;
		}
		public String getReceiverPhone() {
			return receiverPhone;
		}
		public String getReceiverZipCode() {
			return receiverZipCode;
		}
		public String getExpCompanyCode() {
			return expCompanyCode;
		}
		public String getExpCompanyName() {
			return expCompanyName;
		}
		public String getWaybillNo() {
			return waybillNo;
		}
		public Long getCommitWaybTime() {
			return commitWaybTime;
		}
		public Long getReceiverTime() {
			return receiverTime;
		}
		public Integer getOrderStatus() {
			return orderStatus;
		}
		public Integer getPayStatus() {
			return payStatus;
		}
		public Integer getLogisticsStatus() {
			return logisticsStatus;
		}
		public Integer getSettleStatus() {
			return settleStatus;
		}
		public Integer getAfterSalesStatus() {
			return afterSalesStatus;
		}
		public Integer getCommentStatus() {
			return commentStatus;
		}
		public Integer getPlatformStrategy() {
			return platformStrategy;
		}
		public Integer getPlatformPercent() {
			return platformPercent;
		}
		public Long getRefundAmount() {
			return refundAmount;
		}
		public String getProvinceCode() {
			return provinceCode;
		}
		public String getProvinceName() {
			return provinceName;
		}
		public String getCityCode() {
			return cityCode;
		}
		public String getCityName() {
			return cityName;
		}
		public String getAreaCode() {
			return areaCode;
		}
		public String getAreaName() {
			return areaName;
		}
		public String getOrderNo() {
			return orderNo;
		}
		public String getMresNo() {
			return mresNo;
		}
		
		
	

}
