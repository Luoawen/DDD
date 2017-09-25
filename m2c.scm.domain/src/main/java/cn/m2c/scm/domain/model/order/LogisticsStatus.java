package cn.m2c.scm.domain.model.order;

public enum LogisticsStatus {

	//待发货
	WAIT_SEND_GOOGS(1, "待发货"),
	//待收货
	WAIT_RECEIPT_GOOGS(2, "待收货"),	
	//已收货
	RECEIPTED_GOOGS(3, "待收货");
	
	private Integer id;

	private String name;

	LogisticsStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static LogisticsStatus get(Integer id) {
		switch (id) {
		case 1:
			return WAIT_SEND_GOOGS;
		case 2:
			return WAIT_RECEIPT_GOOGS;
		case 3:
			return RECEIPTED_GOOGS;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
