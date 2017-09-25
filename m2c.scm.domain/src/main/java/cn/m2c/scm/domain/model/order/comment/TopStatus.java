package cn.m2c.scm.domain.model.order.comment;

public enum TopStatus {
	//显示
	UNTOP(1, "不置顶"),
	//待收货
	TOP(2, "置顶");
	
	private Integer id;

	private String name;

	TopStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static TopStatus get(Integer id) {
		switch (id) {
		case 1:
			return UNTOP;
		case 2:
			return TOP;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
