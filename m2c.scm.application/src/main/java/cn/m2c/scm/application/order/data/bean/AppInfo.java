package cn.m2c.scm.application.order.data.bean;
/***
 * 客户端信息
 * @author fanjc
 * created date 2017年12月11日
 * copyrighted@m2c
 */

import org.apache.commons.lang3.StringUtils;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.domain.model.order.AppOrdInfo;

public class AppInfo {
	
	@ColumnAlias(value = "app_version")
	private String appVer;
	@ColumnAlias(value = "os")
	private String os;
	@ColumnAlias(value = "os_version")
	private String osVer;
	@ColumnAlias(value = "sn")
	private String sn;
	@ColumnAlias(value = "order_id")
	private String orderNo;
	
	public AppInfo(String appVer, String os, String osVer, String sn, String orderNo) {
		this.os = os;
		this.appVer = appVer;
		this.osVer = osVer;
		this.sn = sn;
		this.orderNo = orderNo;		
	}

	public String getAppVer() {
		return appVer;
	}

	public String getOs() {
		return os;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOsVer() {
		return osVer;
	}

	public String getSn() {
		return sn;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	public AppOrdInfo toAppInfo() {
		if (StringUtils.isEmpty(os) || StringUtils.isEmpty(appVer)) {
			return null;
		}
		AppOrdInfo appInfo = new AppOrdInfo(orderNo, appVer, os, osVer, sn);
		return appInfo;
	}
}
