package cn.m2c.scm.domain.model.order.fsales;
/**
 * 
 * @ClassName: SaledStatus
 * @Description: 售后状态
 * @author moyj
 * @date 2017年6月30日 下午4:33:10
 *
 */
public enum FsalesStatus {
	//1申请售后 2申请售后通过 3申请售后不通过 4已退  5换货中 6已换货

	//申请售后 
	APPLY(1,"申请售后  "),
	//申请售后通过 
	APPLY_PASS(2,"申请售后通过  "),
	//申请售后不通过
	APPLY_UNPASS(3,"申请售后不通过 "),
	//已退
	BACKED_GOODS(4,"已退 "),
	//换货中
	BARTERING(5,"换货中"),
	//已换货
	BARTERED(6,"已换货");
	
	
	private int id;

	private String name;

	FsalesStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static FsalesStatus get(int id) {
		switch (id) {
		case 1:
			return APPLY;
		case 2:
			return APPLY_PASS;
		case 3:
			return APPLY_UNPASS;
		case 4:
			return BACKED_GOODS;
		case 5:
			return BARTERING;		
		case 6:
			return BARTERED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
