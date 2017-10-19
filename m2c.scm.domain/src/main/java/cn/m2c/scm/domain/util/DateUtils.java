package cn.m2c.scm.domain.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/***
 * 时间日期串
 * @author fanjc
 *
 */
public class DateUtils {
	/**类型 生成yyyyMMddHHmmss时间串*/
	public static final int TYPE_0 = 0;
	/**类型 生成yyyy/MM/dd HH:mm:ss时间串*/
	public static final int TYPE_1 = 1;
	/**类型 生成yy/MM/dd HH:mm:ss时间串*/
	public static final int TYPE_2 = 2;
	/**类型 生成yyyy-MM-dd HH:mm:ss时间串*/
	public static final int TYPE_3 = 3;
	
	private static SimpleDateFormat FORMAT0 = new SimpleDateFormat("yyyyMMddHHmmss");
	private static SimpleDateFormat FORMAT1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static SimpleDateFormat FORMAT2 = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	private static SimpleDateFormat FORMAT3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/***
	 * 返回当前时间的多种格式串
	 * @param type
	 * @return 默认返回 type0类型
	 */
	public static String getDateStr(int type) {
		switch (type) {
			case TYPE_0:
				return FORMAT0.format(Calendar.getInstance().getTime());
			case TYPE_1:
				return FORMAT1.format(Calendar.getInstance().getTime());
			case TYPE_2:
				return FORMAT2.format(Calendar.getInstance().getTime());
			case TYPE_3:
				return FORMAT3.format(Calendar.getInstance().getTime());
		}
		return FORMAT0.format(Calendar.getInstance().getTime());
	}
}
