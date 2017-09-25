package cn.m2c.scm.domain.model.order;
/**
 * 
 * @ClassName: SaledStatus
 * @Description: 售后状态
 * @author moyj
 * @date 2017年6月30日 下午4:33:10
 *
 */
public enum AfterSalesStatus {
	//1待申请 2待审核 3 退换中 4.退换完成
	//待申请
	WAIT_APPLY(1,"待申请"),
	//待审核
	WAIT_AUDIT(2,"待审核 "),
	//申请退货通过
	AFTER_SALES_INHAND(3,"退换中  "),
	//退换完成
	FINISHED(4,"退换完成");
	
	private int id;

	private String name;

	AfterSalesStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public static AfterSalesStatus get(int id) {
		switch (id) {
		case 1:
			return WAIT_APPLY;
		case 2:
			return WAIT_AUDIT;
		case 3:
			return AFTER_SALES_INHAND;
		case 4:
			return FINISHED;
		default:
			return null;
		}
	}

	public String stringValue() {
		return this.name.toUpperCase();
	}
}
