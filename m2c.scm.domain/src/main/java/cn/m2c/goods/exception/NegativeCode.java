
package cn.m2c.goods.exception;

/**
 * @ClassName: NegativeCode
 * @Description: 此领域错误代码以14开头
 * @author xiaogl
 * @date 2017年5月26日 下午4:52:24
 *
 */
public final class NegativeCode {
	
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
}
