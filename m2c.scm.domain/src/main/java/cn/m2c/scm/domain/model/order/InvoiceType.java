package cn.m2c.scm.domain.model.order;

/**
 * 
 * @ClassName: InvoiceType
 * @Description: 发票类型
 * @author moyj
 * @date 2017年7月5日 上午9:54:09
 *
 */
public enum InvoiceType {
	//不开
	WITHOUT(1, "不开"),
	//个人
	PERSONAL(2, "个人"),	
	//公司
	COMPANY(3, "公司");
	
	private Integer id;

	private String name;

	InvoiceType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static InvoiceType get(Integer id) {
		switch (id) {
		case 1:
			return WITHOUT;
		case 2:
			return PERSONAL;
		case 3:
			return COMPANY;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
