package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: CommitOrderCommand
 * @Description: 提交订单命令
 * @author moyj
 * @date 2017年4月18日 下午3:09:22
 *
 */
public class CommitOrderCommand extends AssertionConcern implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String orderId ;							//订单编号
	private String dispatchWay;							//配送方式
	private Integer invoiceType;						//发票类型 1不开 2个人 3公司
	private String invoiceTitle;						//发票抬头
	private String taxIdenNum;							//纳税人识别号
	private String buyerId;								//买家编号
	private String buyerName;							//买家名称
	private String buyerMessage;						//买家留言
	private String goodsId;								//货品编号
	private String propertyId;							//货品规格属性编号
	private List<Map<String,Object>> propertyList;		//货品规格属性列表
	private Integer goodsNum;							//货品订购数量
	private String mresId;								//媒体资源编号
	private String receiverId;							//收货地址编号
	
	public CommitOrderCommand(String orderId,String dispatchWay,Integer invoiceType,String invoiceTitle,
			String taxIdenNum,String buyerId,String buyerName,String buyerMessage,String goodsId,
			String propertyId,List<Map<String,Object>> propertyList ,Integer goodsNum,String mresId,String receiverId){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		assertArgumentNotEmpty(dispatchWay, "dispatchWay is not be null.");
		assertArgumentLength(dispatchWay,32,"orderId is longer than 32.");
		assertArgumentNotNull(invoiceType, "invoiceType is not be null.");
		assertArgumentRange(invoiceType,1,3,"invoiceType is not in 1 2 3.");	
		assertArgumentNotEmpty(buyerId, "buyerId is not be null.");
		assertArgumentLength(buyerId,36,"buyerId is longer than 32.");
		assertArgumentNotEmpty(buyerName, "buyerName is not be null.");
		assertArgumentLength(buyerName,100,"buyerName is longer than 100.");
		assertArgumentNotEmpty(propertyId, "propertyId is not be null.");
		assertArgumentLength(goodsId,36,"goodsId is longer than 36.");
		assertArgumentNotNull(goodsId,"goodsId is not be null.");
		assertArgumentLength(propertyId,36,"propertyId is longer than 36.");
		assertArgumentNotNull(propertyList,"propertyList is not be null.");
		assertArgumentNotNull(goodsNum, "goodsNum is not be null.");
		assertArgumentRange(goodsNum,0,2147483647,"goodsNum is no in range.");	
		//assertArgumentNotEmpty(mresId, "mresId is not be null.");
		if(mresId != null && mresId.length() > 0){
			assertArgumentLength(mresId,36,"mresId is longer than 36.");
		}
		assertArgumentNotEmpty(receiverId, "receiverId is not be null.");
		assertArgumentLength(receiverId,36,"receiverId is longer than 36.");
		if(invoiceTitle != null && invoiceTitle.length() > 0){
			assertArgumentLength(invoiceTitle,100,"invoiceTitle is longer than 100.");
		}
		if(buyerMessage != null && buyerMessage.length() > 0){
			assertArgumentLength(buyerMessage,256,"buyerMessage is longer than 256.");
		}
		this.orderId = orderId;
		this.dispatchWay = dispatchWay;
		this.invoiceType = invoiceType;
		this.invoiceTitle = invoiceTitle;
		this.taxIdenNum = taxIdenNum;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerMessage = buyerMessage;
		this.propertyId = propertyId;
		this.propertyList = propertyList;
		this.goodsNum = goodsNum;
		this.mresId = mresId;
		this.receiverId = receiverId;
		this.goodsId = goodsId;
		
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public String getMresId() {
		return mresId;
	}
	public String getReceiverId() {
		return receiverId;
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
	public String getPropertyId() {
		return propertyId;
	}
	public List<Map<String, Object>> getPropertyList() {
		return propertyList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTaxIdenNum() {
		return taxIdenNum;
	}
	
	
	
	


	
	
}
