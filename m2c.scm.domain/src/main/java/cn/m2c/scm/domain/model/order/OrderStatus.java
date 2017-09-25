package cn.m2c.scm.domain.model.order;

/**
 * 
 * @ClassName: OrderStatus
 * @Description: 订单状态
 * @author moyj
 * @date 2017年4月18日 上午10:39:56
 *
 */
public enum OrderStatus {
	
	//正常
	NORMAL(1, "正常"),
	//已取消
	CANCELED(2, "已取消"),
	//已取消
	DELETED(3, "已删除");
	
	private int id;

	private String name;

	OrderStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static OrderStatus get(int id) {
		switch (id) {
		case 1:
			return NORMAL;
		case 2:
			return CANCELED;
		case 3:
			return DELETED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
	
}
