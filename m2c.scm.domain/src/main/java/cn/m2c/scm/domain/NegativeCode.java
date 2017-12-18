
package cn.m2c.scm.domain;

/**
 * 
 * @ClassName: NegativeCode
 * @Description: 此领域15开头
 * @author moyj
 * @date 2017年5月26日 下午4:55:01
 *
 */
public final class NegativeCode {
	/**
	 * 非待收货的订单不允许确认收货
	 */
	public static Integer UNWAIT_UNABLE_RECEIPT_GOOGS = 150101;
	/**
	 * 已取消的订单不允许再次取消
	 */
	public static Integer CANCELED_UNABLE_CANCEL = 150102;
	/**
	 * 非待付款的订单不允许取消
	 */
	public static Integer UNWAIT_PAY_UNABLE_CANCEL = 150103;
	/**
	 * 非待收货的订单不允许取消
	 */
	public static Integer UNWAIT_RECGOOGS_UNABLE_CANCEL = 150104;
	/**
	 * 申请已处理或处理中的订单不允许申请售后
	 */
	public static Integer HANDLED_UNABLE_APPLY_SALED = 150105;
	/**
	 * 已结算的订单不允许申请售后
	 */
	public static Integer SETTLED_UNABLE_APPLY_SALED = 150106;
	/**
	 * 未付款的订单不允许提交运单
	 */
	public static Integer UNWAIT_PAY_UNABLE_COMMIT_WAYBILL = 150107;
	/**
	 * 非待发货的订单不允许提交运单
	 */
	public static Integer UNWAIT_RECGOOGS_UNABLE_COMMIT_WAYBILL = 150108;
	/**
	 * 已结算的订单不允许修改状态
	 */
	public static Integer SETTLED_UNABLE_MOD_STATUS = 150109;
	/**
	 * 非申请售后的订单不允许审核
	 */
	public static Integer UN_APPLY_SALED_UNABLE_APPLY = 150110;
	/**
	 * 非审核通过的订单不允许退货
	 */
	public static Integer UN_APPLY_PASS_UNABLE_BACK_GOODS = 150111;
	/**
	 * 非审核通过的订单不允许换货
	 */
	public static Integer UN_APPLY_PASS_UNABLE_BARTER = 150112;
	/**
	 * 非换货中的订单不允许换货完成
	 */
	public static Integer UN_BARTERING_UNABLE_BARTERED = 150113;
	/**
	 * 不允许重复提交订单
	 */
	public static Integer UNABLE_REPEAT_COMMIT_ORDER = 150114;
	/**
	 * 订单不存在
	 */
	public static Integer ORDER_NOT_EXIST = 150115;	
	/**
	 * 非已收货的订单不允许申请售后
	 */
	public static Integer UNRECEIPTED_UNABLE_SALED = 150116;
	
	/**
	 * 已过售后期的订单不允许申请售后
	 */
	public static Integer PASSED_SALED_DEADLINE = 150117;
	

	/**
	 * 非申请售后的订单不允许审核
	 */
	public static Integer  ORDER_UNAPPLY_SALED = 150118;
	
	/**
	 * 未付款的订单不允许删除
	 */
	public static Integer UNPAYED_UNABLE_DELETE = 150119;
	/**
	 * 未确认收货的订单不允许删除
	 */
	public static Integer UNRECED_GOOGS_UNABLE_DELETE = 150120;
	/**
	 * 售后处理中的订单的订单不允许删除
	 */
	public static Integer AFTER_SALES_INHAND_UNABLE_DELETE = 150121;
	
	/**
	 * 未付款的订单不允许评论
	 */
	public static Integer UNPAYED_UNABLE_COMMENT = 150122;
	/**
	 * 未确认收货的订单不允许评论
	 */
	public static Integer UNRECED_GOOGS_UNABLE_COMMENT = 150123;
	/**
	 * 售后处理中的订单不允许评论
	 */
	public static Integer AFTER_SALES_INHAND_UNABLE_COMMENT= 150124;
	
	/**
	 * 订单不符合结算要求
	 */
	public static Integer ORDER_UNACCORD_SETTLE= 150125;
	
	/**
	 * 订单不符合支付要求
	 */
	public static Integer ORDER_UNACCORD_PAY = 150126;
	
	/**
	 * 非申请售后的订单退款
	 */
	public static Integer  ORDER_UNREFUND_SALED = 150127;
	
	/**
	 * 未付款的订单不允许确认收货
	 */
	public static Integer UNPAY_UNABLE_RECEIPT_GOOGS = 150128;
	/**
	 * 退款金额大于付款金额
	 */
	public static Integer REFUNDED_MORE_PAY = 150129;
	
	
	/**
	 * 请选择要操作的订单
	 */
	public static Integer OPER_NOT_CHOICE_ORDER = 150302;
	
	/**
	 * 评论不存在
	 */
	public static Integer COMMENT_NOT_EXIST = 150301;
	
	/**
	 * 请选择要操作的评论
	 */
	public static Integer OPER_NOT_CHOICE_COMMENT = 150302;
	
	/**
	 * 评论已经回复过
	 */
	public static Integer COMMENT_REPLYED = 150303;
	/**
	 * 评论已被删除
	 */
	public static Integer COMMENT_DELETED_BY_USER = 150304;
	
	/**
	 * 评论已被删除
	 */
	public static Integer COMMENT_DELETED_BY_ADMIN = 150305;
	
	/**
	 * 不允许修改
	 */
	public static Integer COMMENT_UNALLOW_MOD = 150306;
	
	
	/**
	 * 远程获取货品信息失败
	 */
	public static Integer REMOTE_GET_GOODS_FAIL = 150901;
	/**
	 * 远程获取媒体信息失败
	 */
	public static Integer REMOTE_GET_MEDIA_FAIL = 150902;
	/**
	 * 远程获取收货地址信息失败
	 */
	public static Integer REMOTE_GET_RECEIVERR_FAIL = 150903;
	/**
	 * 远程获取结算策略失败
	 */
	public static Integer REMOTE_GET_STRATEGY_FAIL = 150904;
	/**
	 * 远程获取用户信息失败
	 */
	public static Integer REMOTE_GET_USER_FAIL = 150905;
	
	
	
	/**
	 * 经销商不存在
	 */
	public static final Integer DEALER_IS_NOT_EXIST = 140001;
	/**
	 * 品牌不存在
	 */
	public static final Integer BRAND_IS_NOT_EXIST = 140002;
	/**、
	 * 属性模板不存在
	 */
	public static final Integer PROPERTY_IS_NOT_EXIST = 140003;
	/**
	 * 物流模板不存在
	 */
	public static final Integer TRANSPORTFEE_IS_NOT_EXIST = 140004;
	/**
	 * 商品不存在
	 */
	public static final Integer GOODS_IS_NOT_EXIST = 140005;
	/**
	 * 商品分类不存在
	 */
	public static final Integer GOOD_CLASSIFY_IS_NOT_EXIST = 140006;
	
	/**
	 * 经销商已存在
	 */
	public static  Integer DEALER_IS_EXIST = 140007;
	/**
	 * 业务员不存在
	 */
	public static  Integer SELLER_IS_NOT_EXIST = 140008;
	/**
	 * 业务员已存在
	 */
	public static Integer SELLER_IS_EXIST = 140009;
	public static Integer COUNT_GOODS_ERROR = 140010;
	/**
	 * 指定要更新的商品信息不存在
	 */
	public static Integer UPDATE_GOODS_NOT_EXIST = 140011;
	
	/**
	 * 查询商品列表集合失败
	 */
	public static Integer LIST_GOODS_NOT_EXIST = 140012;
	
	/**
	 * 查询商品详情失败
	 */
	public static Integer DETAIL_GOODS_NOT_EXIST = 140013;
	
	/**
	 * 查询商品分类详情失败
	 */
	public static Integer DETAIL_CATEGORY_NOT_EXIST = 140014;
	
	/**
	 * 查询商品分类详情失败
	 */
	public static Integer DETAIL_CATEGORY_IS_EXIST = 140015;
	
	/**
	 * 指定要更新的商品分类信息不存在
	 */
	public static Integer UPDATE_CATEGORY_NOT_EXIST = 140016;
	
	/**
	 * 查询商品分类列表集合失败
	 */
	public static Integer LIST_CATEGORY_NOT_EXIST = 140017;
	
	/**
	 * 商品分类节点含有子节点，不能直接删除
	 */
	public static Integer DELETE_CATEGORY_HAS_CHILDS = 140018;
	
	/**
	 * 商品分类节点含有子节点，不能直接删除
	 */
	public static Integer DELETE_CATEGORY_IS_USED = 140019;
	
	/**
	 * 供应商已经存在
	 */
	public static Integer DETAIL_SELLER_IS_EXIST = 140020;
	
	/**
	 * 供应商不存在
	 */
	public static Integer DETAIL_SELLER_NOT_EXIST = 140021;
	
	/**
	 * 规格已经存在
	 */
	public static Integer DETAIL_SPEC_IS_EXIST = 140022;
	/**
	 * 商品的此规格不存在
	 */
	public static Integer DETAIL_SPEC_NOT_EXIST = 140023;
	
	/**
	 * create pay account fail
	 */
	public static Integer CREATE_PAY_ACCOUNT_FAIL = 140024;
	/**
	 * 下架商品识别图片切换失败
	 */
	
	public static final Integer GOODS_DOWN_RECOGNIZED_PIC_FAILED = 140025;
	/**
	 * 上架商品识别图片切换失败
	 */
	public static final Integer GOODS_UP_RECOGNIZED_PIC_FAILED = 140026;
	/**
	 * 经销商用户删除失败
	 */
	public static final Integer DEALER_DELETE_FAILED = 140027;
	
	/**
	 * 位置已存在
	 */
	public static final Integer LOCATION_IS_NOT_EXIST = 140028;
	
	public static final Integer LOCATION_IS_EXIST = 140029;
	/**
	 * 经销商店铺不存在
	 */
	public static final Integer DEALER_SHOP_IS_NOT_EXIST = 140030;
	/**
	 * 店铺已存在
	 */
	
	public static final Integer DEALER_SHOP_IS_EXIST = 140031;
	/**
	 * 商家订单不存在
	 */
	public static final Integer DEALER_ORDER_IS_NOT_EXIST = 140032;
	/**
	 * 业务员手机号已存在
	 */
	public static final Integer SELLER_PHONE_IS_EXIST = null;
}
