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
	private String invoiceHeader;
	/**发票名称**/
	private String invoiceName;
	/**纳税人识别码**/
	private String invoiceCode;
	/**发票说明**/
	private String invoiceDes;
	
	public InvoiceInfo() {
		super();
	}
	
	public InvoiceInfo(String header, String name, String code, String des) {
		invoiceCode = code;
		invoiceHeader = header;
		invoiceName = name;
		invoiceDes = des;
	}
}
