package cn.m2c.scm.application.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

    //获取格式化的时间,参数都为空则返回当前时间
    public static final String getFormatDate(String style, Date date)
    {
        return new SimpleDateFormat(StringUtils.isEmpty(style) ? "yyyy-MM-dd" : style).format(date == null ? new Date() : date);
    }

    //URL 编码, Encode默认为UTF-8.
    public static final String urlEncode(String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8");
    }
}
