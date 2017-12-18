package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.IdentifiedDomainObject;
/***
 * 下单的app信息
 * @author 89776
 * created date 2017年12月11日
 * copyrighted@m2c
 */
public class AppOrdInfo extends IdentifiedDomainObject {
	
	private static final long serialVersionUID = 1L;

	private String orderId;
	
	private String appVer;
	
	private String os;
	
	private String osVer;
	
	private String sn;
	
	public AppOrdInfo() {
		super();
	}
	
	public AppOrdInfo(String orderId, String appVer, String os, String osVer, String sn) {
		this.orderId = orderId;
		this.appVer = appVer;
		this.os = os;
		this.osVer = osVer;
		this.sn = sn;
	}
	
}
