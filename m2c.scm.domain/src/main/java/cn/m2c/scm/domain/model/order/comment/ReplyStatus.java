package cn.m2c.scm.domain.model.order.comment;

public enum ReplyStatus {
	//显示
	UNREPLY(1, "未回复"),
	//待收货
	REPLYED(2, "已回复");
	
	private Integer id;

	private String name;

	ReplyStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static ReplyStatus get(Integer id) {
		switch (id) {
		case 1:
			return UNREPLY;
		case 2:
			return REPLYED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
