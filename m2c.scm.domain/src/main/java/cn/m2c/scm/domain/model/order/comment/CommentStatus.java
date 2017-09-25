package cn.m2c.scm.domain.model.order.comment;



public enum CommentStatus {

	//正常
	NORMAL(1, "正常"),
	//删除
	DELETED(2, "已删除");
	
	private Integer id;

	private String name;

	CommentStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static CommentStatus get(Integer id) {
		switch (id) {
		case 1:
			return NORMAL;
		case 2:
			return DELETED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
