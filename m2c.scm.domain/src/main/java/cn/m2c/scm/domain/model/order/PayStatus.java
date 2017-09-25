package cn.m2c.scm.domain.model.order;

/**
 * 
 * @ClassName: PayStatus
 * @Description: 付款状态
 * @author moyj
 * @date 2017年4月18日 上午10:39:48
 *
 */
public enum PayStatus {

	//待支付
	WAITING(1, "待支付"),
	//已支付
	COMMITED(2, "已支付");
	
	private int id;

	private String name;

	PayStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static PayStatus get(int id) {
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
