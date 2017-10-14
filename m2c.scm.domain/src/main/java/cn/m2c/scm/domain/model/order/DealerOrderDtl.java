package cn.m2c.scm.domain.model.order;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
/**
 * 商家订单明细
 * @author fanjc
 *
 */
public class DealerOrderDtl extends ConcurrencySafeEntity {

	private static final long serialVersionUID = 1L;

	private String orderId;
	
	private DealerOrder dealerOrder;
	
	//private String dealerOrderId;
	/**订单状态 0待付款，1等发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消*/
	private Integer status;
	
	private String province;
	
	private String provinceCode;
	
	private String city;
	
	private String cityCode;
	
	private String area;
	
	private String areaCode;
	
	private String street;
	/**收货联系人*/
	private String revPerson;
	/**联系电话*/
	private String revPhone;
	/**抬头**/
	private String invoiceHeader;
	/**发票名称**/
	private String invoiceName;
	/**纳税人识别码**/
	private String invoiceCode;
	/**发票说明**/
	private String invoiceDes;
	/**快递单号**/
	private String expressNo;
	/**快递公司*/
	private String expressName;
	/**送货人姓名*/
	private String expressPerson;
	/**送货人电话*/
	private String expressPhone;
	/**配送方式 0:物流，1自有物流*/
	private Integer expressWay;
	/**配送说明*/
	private String expressNote;
	/**发货时间*/
	private Date expressTime;
	/**媒体资源ID*/
	private String mediaResId;
	/**促销员ID*/
	private String salerUserId;
	/**费率*/
	private Float rate;
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
	/**购买数量*/
	private Float sellNum;
	/**市场价(单价)*/
	private Integer price;
	/**供货价*/
	private Integer supplyPrice;
	/**拍获价(单价)*/
	private Integer discountPrice;
	/**商品小图url*/
	private String goodsIcon;
	/**以分为单位，商品金额*/
	private Integer goodsAmount;
	/**平台优惠*/
	private Integer plateformDiscount;
	/**商家优惠*/
	private Integer dealerDiscount;
	/**备注 留言*/
	private String noted;
	/**订单总运费*/
	private Integer freight;
	/***
	 * 计算商品金额
	 * @return
	 */
	public Integer calGoodsMoney() {
		goodsAmount = (int)(discountPrice * sellNum);
		return goodsAmount;
	}
	/***
	 * 计算运费
	 * @return
	 */
	public Integer calFreight() {
		
		return freight;
	}
}
