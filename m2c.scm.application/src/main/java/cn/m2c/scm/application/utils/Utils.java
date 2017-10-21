package cn.m2c.scm.application.utils;

import java.util.List;

/**
 * 工具
 */
public class Utils {
    public static String listParseString(List<String> list) {
        StringBuilder idBuffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            idBuffer = idBuffer.append("'").append(list.get(i)).append("'").append(",");
        }
        String idStr = idBuffer.toString();
        idStr = idStr.substring(0, idStr.length() - 1);
        return idStr;
    }

}
