package cn.m2c.scm.domain.model.order.comment;

public enum CommentLevel {
	//好
	GOOD(1, "好"),
	//中
	MIDDLE(2, "中"),
	//差
	DIFFERENCE(3, "差");
	
	private Integer id;

	private String name;

	CommentLevel(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static CommentLevel get(Integer id) {
		switch (id) {
		case 1:
			return GOOD;
		case 2:
			return MIDDLE;
		case 3:
			return DIFFERENCE;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
