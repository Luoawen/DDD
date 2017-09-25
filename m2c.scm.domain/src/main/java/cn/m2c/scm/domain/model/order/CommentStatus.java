package cn.m2c.scm.domain.model.order;

public enum CommentStatus {
	//待评论
	WAIT(1, "正常 "),
	//已评论
	COMMENTED(2, "删除");
	
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
			return WAIT;
		case 2:
			return COMMENTED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
