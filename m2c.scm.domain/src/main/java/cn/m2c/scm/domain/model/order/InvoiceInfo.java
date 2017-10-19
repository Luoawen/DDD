package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;
/***
 * 发票信息值对象
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
public class InvoiceInfo extends ValueObject {
	/**抬头**/
	private String header;
	/**发票名称**/
	private String name;
	/**纳税人识别码**/
	private String code;
	/**发票说明**/
	private String invoiceDes;
	
	public InvoiceInfo() {
		super();
	}
	
	public InvoiceInfo(String header, String name, String code, String des) {
		this.code = code;
		this.header = header;
		this.name = name;
		invoiceDes = des;
	}
}
