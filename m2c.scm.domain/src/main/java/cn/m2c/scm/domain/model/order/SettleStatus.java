package cn.m2c.scm.domain.model.order;

public enum SettleStatus {
	//待结算
	WAITING(1, "待结算"),
	//已结算
	COMMITED(2, "已结算");
	
	private int id;

	private String name;

	SettleStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static SettleStatus get(int id) {
		switch (id) {
		case 1:
			return WAITING;
		case 2:
			return COMMITED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
