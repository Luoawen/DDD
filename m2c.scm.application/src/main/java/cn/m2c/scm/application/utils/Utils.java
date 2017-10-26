package cn.m2c.scm.application.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    /*
   * 将时间戳转换为时间
   */
    public static String stampToDate(String s, SimpleDateFormat dateFormat) {
        if (StringUtils.isNotEmpty(s)) {
            long lt = new Long(s);
            Date date = new Date(lt);
            return dateFormat.format(date);
        }
        return null;
    }
}
