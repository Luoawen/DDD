package cn.m2c.scm.domain.util;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-09-07.
 */
public class GetMapValueUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";


    public static Integer getIntFromMapKey(Map map, String key) {
        Integer intValue;
        try {
            intValue = new Double(map.get(key).toString()).intValue();
        } catch (Exception e) {
            return null;
        }
        return intValue;
    }

    public static Float getFloatFromMapKey(Map map, String key) {
        Float floatValue;
        try {
            floatValue = Float.parseFloat(map.get(key).toString());
        } catch (Exception e) {
            return null;
        }
        return floatValue;
    }

    public static String getStringFromMapKey(Map map, String key) {
        String strValue;
        try {
            strValue = map.get(key).toString();
        } catch (Exception e) {
            return null;
        }
        return strValue;
    }

    public static Map getMapFromMapKey(Map map, String key) {
        Map mapValue;
        try {
            mapValue = (Map) map.get(key);
        } catch (Exception e) {
            return null;
        }
        return mapValue;
    }

    public static List getListFromMapKey(Map map, String key) {
        List listValue;
        try {
            listValue = (List) map.get(key);
        } catch (Exception e) {
            return null;
        }
        return listValue;
    }

    public static Date getDateLongFromMapKey(Map map, String key) {
        Date dateValue;
        try {
            long dateLong = Long.parseLong(map.get(key).toString());
            dateValue = new Date(dateLong);
        } catch (Exception e) {
            return null;
        }
        return dateValue;
    }

    public static Date getDateFromMapKey(Map map, String key) {
        Date dateValue;
        try {
            dateValue = (Date) map.get(key);
        } catch (Exception e) {
            return null;
        }
        return dateValue;
    }

    public static Date getDateFromMap(Map map, String key) {
        Date dateValue;
        try {
            String date = map.get(key).toString();
            dateValue = new Date(date);
        } catch (Exception e) {
            return null;
        }
        return dateValue;
    }

    public static Time getTimeFromMapKey(Map map, String key) {
        Time value;
        try {
            value = (Time) map.get(key);
        } catch (Exception e) {
            return null;
        }
        return value;
    }

    public static boolean getBooleanFromMapKey(Map map, String key) {
        Boolean value;
        try {
            value = (Boolean) map.get(key);
        } catch (Exception e) {
            return false;
        }
        return value;
    }

    public static Long getLongFromMapKey(Map map, String key) {
        Long intValue;
        try {
            intValue = new Double(map.get(key).toString()).longValue();
        } catch (Exception e) {
            return null;
        }
        return intValue;
    }

}
