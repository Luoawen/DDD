package cn.m2c.scm.application.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 工具
 */
public class Utils {
	/**需要除的数据， 把金额变成元为单位*/
	public static final double DIVIDE = 10000;
	
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

    public static String listToString(List list, char separator) {
        return StringUtils.join(list.toArray(), separator);
    }
    /***主要是针对金额的数据<br>
     * 格式化成2位小数的串返回
     * @param a
     * @param divide
     * @return a/divide
     */
    public static String moneyFormatCN(long a, double divide) {
        return String.format("%1$.2f", a/divide);
    }
    
    /***主要是针对金额的数据<br>
     * 格式化成2位小数的串返回
     * @param a
     */
    public static String moneyFormatCN(long a) {
        return String.format("%1$.2f", a/DIVIDE);
    }
    
    /***转换成数据库需要的金额<br>
     * @param d 字符串
     */
    public static long convertNeedMoney(String d) throws NumberFormatException {
    	double dMoney = Double.parseDouble(d);
        return (long)(dMoney * DIVIDE);
    }
    
    /***转换成数据库需要的金额<br>
     * @param d
     */
    public static long convertNeedMoney(double d) {
        return (long)(d * DIVIDE);
    }
    
    /***主要是针对金额的数据<br>
     * 格式化成2位小数的串返回
     * @param divide
     */
    public static String moneyFormatCN(double divide) {
        return String.format("%1$.2f", divide);
    }

    /**
     * 根据秒数获取时间串
     * @param second (eg: 100)
     * @return (eg: 01:40 / 00:01:40)
     */
    public static String getTimeStrBySecond(Integer second) {
    	if(null != second) {
    		if (second <= 0) {
                //return "00:00:00";
            	return "00:00";
            }

            StringBuilder sb = new StringBuilder();
            Integer hours = second / (60 * 60);
            /*if (hours > 0) {
                second -= hours * (60 * 60);
            }*/

            Integer minutes = second / 60;
            if (minutes > 0) {
                second -= minutes * 60;
            }
            /*return (hours >= 10 ? (hours + "")
                    : ("0" + hours)) + ":" + (minutes >= 10 ? (minutes + "") : ("0" + minutes)) + ":"
                            + (second >= 10 ? (second + "") : ("0" + second));*/
            return (minutes >= 10 ? (minutes + "") : ("0" + minutes)) + ":"
                            + (second >= 10 ? (second + "") : ("0" + second));
    	}
        return null;
    }
    
    public static void main(String[] args) {
    	System.out.println(Utils.moneyFormatCN(1100, 10000));
    	System.out.println(getTimeStrBySecond(659));
    }

}
