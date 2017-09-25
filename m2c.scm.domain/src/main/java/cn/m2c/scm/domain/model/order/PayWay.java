package cn.m2c.scm.domain.model.order;


/** 
 * @ClassName: PayWay 
 * @Description: 支付方式
 * @author: moyj
 * @date: 2017年4月18日 上午10:20:50  
 */
public enum PayWay {
	
	//支付宝
	ZHI_FU_BAO(1, "支付宝"),
	//微信
	WEI_XIN(2, "微信");

	private int id;

	private String name;

	PayWay(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static PayWay get(int id) {
		switch (id) {
		case 1:
			return ZHI_FU_BAO;
		case 2:
			return WEI_XIN;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
