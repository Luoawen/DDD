package cn.m2c.scm.domain.model.order.comment;

public enum HideStatus {
	//显示
	SHOW(1, "显示"),
	//待收货
	HIDE(2, "隐藏");
	
	private Integer id;

	private String name;

	HideStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static HideStatus get(Integer id) {
		switch (id) {
		case 1:
			return SHOW;
		case 2:
			return HIDE;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
