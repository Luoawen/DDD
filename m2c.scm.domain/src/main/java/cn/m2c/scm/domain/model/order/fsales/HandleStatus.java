package cn.m2c.scm.domain.model.order.fsales;

public enum HandleStatus {

	//处理中
	HANDLEING(1,"处理中 "),
	//已处理
	HANDLED(2,"已处理");

	private int id;

	private String name;

	HandleStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static HandleStatus get(int id) {
		switch (id) {
		case 1:
			return HANDLEING;
		case 2:
			return HANDLED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
