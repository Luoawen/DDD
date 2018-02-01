package cn.m2c.scm.domain.util;

import java.util.Collections;
import java.util.List;

/**
 * 工具类
 */
public class StringOptUtils {
    public static String listJoinString(List<String> obj) {
        Collections.reverse(obj);
        String[] type = new String[obj.size()];
        String[] newGoodsClassify = (String[]) obj.toArray(type);
        String goodsClassify = String.join(",", newGoodsClassify);
        return goodsClassify;
    }
}
