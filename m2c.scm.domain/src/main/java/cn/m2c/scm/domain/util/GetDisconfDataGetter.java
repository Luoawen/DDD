package cn.m2c.scm.domain.util;

import org.slf4j.Logger;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

/**
 * 获取配置文件信息
 * @author lqwen
 *
 */

public class GetDisconfDataGetter {
	
	/**
	 * 获取常量
	 * @param properties
	 * @param fileName
	 * @return
	 */
	public static String getFinalDisconfDataGetter(String fileName) {
		final String PROPERTIS_NAME = DisconfDataGetter.getByFileItem("constants.properties", fileName).toString().trim();
		return PROPERTIS_NAME;		
	}
	
	
	/**
	 * 获取变量
	 * @param properties
	 * @param fileName
	 * @return
	 */
	public String getDisconfDataGetters(String fileName) {
		String trim = DisconfDataGetter.getByFileItem("constants.properties", fileName).toString().trim();
		return trim;
		
	}
	
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
