package cn.m2c.scm.domain;

import java.util.UUID;

/**
 * @author wangyz
 * @version 1.0.0
 * @since 2017骞�4鏈�18鏃� 涓婂崍10:08:29
 */
public abstract class IDGenerator {

    private final static String DEFAULT_PREFIX_TITLE = "MC";
    public static final String USERS_PREFIX_TITLE = "HY";//浼氬憳涓績鍓嶇紑
    public static final String PAY_PREFIX_TITLE = "ZF";//鏀粯涓績鍓嶇紑
    public static final String ORDER_PREFIX_TITLE = "DD";//璁㈠崟鍓嶇紑
    public static final String GOODS_BRAND_PREFIX_TITLE = "14GB";//鍝佺墝鍓嶇紑
    public static final String SYSTEM_USER_PREFIX_TITLE = "SYSU"; // 绯荤粺鐢ㄦ埛
    public static final String SYSTEM_ROLE_PREFIX_TITLE = "SYSR"; // 绯荤粺瑙掕壊
    public static final String SYSTEM_MENU_PREFIX_TITLE = "SYSM"; // 绯荤粺鑿滃崟
    public static final String SYSTEM_FORBID_PREFIX_TITLE = "SYSF"; // 绯荤粺绂佺敤璇嶆眹
    public static final String DEALER_PREFIX_TITLE = "JXS";//缁忛攢鍟�
    public static final String SHOP_PREFIX_TITLE = "SHOP";//缁忛攢鍟�
    public static final String SALE_PREFIX_TITLE = "YWY";//
    public static final String GOODS_Property_PREFIX_TITLE = "14GP";//灞炴�у墠缂�
    public static final String DEALER_SELLER_PREFIX_TITLE = "14DS";
    public static final String REPORT_ID = "14RI";
    public static final String LOCATION_ID = "14LI";
    public static final String SCM_POSTAGE_PREFIX_TITLE = "YF";//杩愯垂妯℃澘鍓嶇紑
    public static final String SCM_POSTAGE_RULE_PREFIX_TITLE = "YFGZ";//杩愯垂妯℃澘瑙勫垯鍓嶇紑
    public static final String SCM_AFTER_SALE_ADDRESS_PREFIX_TITLE = "SHDZ";//鍞悗鍦板潃鍓嶇紑
    public static final String SCM_BRAND_PREFIX_TITLE = "PP";//鍝佺墝鍓嶇紑
    public static final String SCM_BRANDE_APPROVE_PREFIX_TITLE = "PPSP";//鍝佺墝瀹℃牳鍓嶇紑
    public static final String SCM_GOODS_CLASSIFY_PREFIX_TITLE = "SPFL";//鍟嗗搧鍒嗙被鍓嶇紑
    public static final String SCM_GOODS_PREFIX_TITLE = "SP";//商品前缀
    public static final String SCM_GOODS_SPEC_VALUE_PREFIX_TITLE = "SPGG";//商品规格值
    public static final String SCM_STANTARD_PREFIX_TITLE = "GG";//规格前缀
    public static final String SCM_UNIT_PREFIX_TITLE = "JLDW";//计量单位前缀
    public static final String SCM_GOODS_COMMENT_PREFIX_TITLE = "SPPL";//商品评论前缀
    public static final String SCM_GOODS_REPLY_COMMENT_PREFIX_TITLE = "SPHP";//商品回评前缀
    public static final String SCM_SUIBIAN_PREFIX_TITLE = "DE";
    public static final String SCM_GOODS_SPECIAL_PREFIX_TITLE = "SPYH";//商品优惠价前缀

    /**
     * 鐢熸垚搴忓垪ID
     *
     * @return String
     * @瑙勫垯 涓や綅鍓嶇紑鏍囪瘑+褰撳墠鏈嶅姟鍣ㄦ椂闂存埑+涓や綅闅忔満鏁板瓧
     */
    public synchronized static String get() {
        return get(DEFAULT_PREFIX_TITLE);
    }

    /**
     * 鑷畾涔夊墠缂�鏍囪瘑鐢熸垚搴忓垪ID
     *
     * @param prefix 鍓嶇紑鏍囪瘑
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
