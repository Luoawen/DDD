package cn.m2c.scm.domain;

import java.util.UUID;

/**
 * @author wangyz
 * @version 1.0.0
 * @since 2017年4月18日 上午10:08:29
 */
public abstract class IDGenerator {

    private final static String DEFAULT_PREFIX_TITLE = "MC";
    public static final String USERS_PREFIX_TITLE = "HY";//会员中心前缀
    public static final String PAY_PREFIX_TITLE = "ZF";//支付中心前缀
    public static final String ORDER_PREFIX_TITLE = "DD";//订单前缀
    public static final String GOODS_PREFIX_TITLE = "SP";//订单前缀
    public static final String GOODS_BRAND_PREFIX_TITLE = "14GB";//品牌前缀
    public static final String SYSTEM_USER_PREFIX_TITLE = "SYSU"; // 系统用户
    public static final String SYSTEM_ROLE_PREFIX_TITLE = "SYSR"; // 系统角色
    public static final String SYSTEM_MENU_PREFIX_TITLE = "SYSM"; // 系统菜单
    public static final String SYSTEM_FORBID_PREFIX_TITLE = "SYSF"; // 系统禁用词汇
    public static final String DEALER_PREFIX_TITLE = "JXS";//经销商
    public static final String SALE_PREFIX_TITLE = "YWY";//
    public static final String GOODS_Property_PREFIX_TITLE = "14GP";//属性前缀
    public static final String DEALER_SELLER_PREFIX_TITLE = "14DS";
    public static final String REPORT_ID = "14RI";
    public static final String LOCATION_ID = "14LI";
    public static final String SCM_POSTAGE_PREFIX_TITLE = "YF";//运费模板前缀
    public static final String SCM_POSTAGE_RULE_PREFIX_TITLE = "YFGZ";//运费模板规则前缀
    public static final String SCM_AFTER_SALE_ADDRESS_PREFIX_TITLE = "SHDZ";//售后地址前缀
    public static final String SCM_BRAND_PREFIX_TITLE = "PP";//品牌前缀
    public static final String SCM_BRANDE_APPROVE_PREFIX_TITLE = "PPSP";//品牌审批前缀
    public static final String SCM_GOODS_CLASSIFY_PREFIX_TITLE = "SPFL";//商品分类前缀

    /**
     * 生成序列ID
     *
     * @return String
     * @规则 两位前缀标识+当前服务器时间戳+两位随机数字
     */
    public synchronized static String get() {
        return get(DEFAULT_PREFIX_TITLE);
    }

    /**
     * 自定义前缀标识生成序列ID
     *
     * @param prefix 前缀标识
     * @return String
     */
    public synchronized static String get(String prefix) {
        return String.format("%s%s", prefix, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }


    public static void main(String[] args) {
        String testID = IDGenerator.get("SJFL");
        System.err.println(testID.length());
        System.err.println(testID);
    }
}
