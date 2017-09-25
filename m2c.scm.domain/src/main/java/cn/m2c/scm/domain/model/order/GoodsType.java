package cn.m2c.scm.domain.model.order;

public enum GoodsType {

	//虚拟货品
	MATERIAL_OBJECT(1, "虚拟货品"),
	//服务型
	SERVICE(2, "服务型"),	
	//虚拟货品
	FICTITIOUS(3, "虚拟货品");
	
	private Integer id;

	private String name;

	GoodsType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static GoodsType get(Integer id) {
		switch (id) {
		case 1:
			return MATERIAL_OBJECT;
		case 2:
			return SERVICE;
		case 3:
			return FICTITIOUS;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
