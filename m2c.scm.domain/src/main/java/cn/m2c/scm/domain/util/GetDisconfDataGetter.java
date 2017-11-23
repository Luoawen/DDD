package cn.m2c.scm.domain.util;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

/**
 * 获取配置文件信息
 * @author lqwen
 *
 */

public class GetDisconfDataGetter {
	
	
	/**
	 * 获取disonf配置constants中的属性值
	 * @param properties
	 * @param fileName
	 * @return
	 */
	public static String getDisconfProperty(String prop) {
		String pVal = DisconfDataGetter.getByFileItem("constants.properties", prop).toString().trim();
		return pVal;
	}

}
