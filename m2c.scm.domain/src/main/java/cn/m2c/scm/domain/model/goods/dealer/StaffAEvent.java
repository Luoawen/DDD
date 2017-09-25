package cn.m2c.scm.domain.model.goods.dealer;

import java.util.Date;






import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 经销商添加业务员操作
 * @author ps
 *
 */
public class StaffAEvent  extends AssertionConcern implements DomainEvent{

	private String staffId;
	private String dealerId;
	private String sellerName;
	private Long createDate;
	private Date currentTime;
	
	
	
	public StaffAEvent() {
		super();
	}
	



	public StaffAEvent(String staffId, String sellerName,String dealerId,Long createDate) {
		super();
		this.setStaffId(staffId);
		this.setSellerName(sellerName);
		this.setDealerId(dealerId);
		this.setCreateDate(createDate);
		this.setCurrentTime(new Date());
	}


	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
	
	

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	

	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return this.getCurrentTime();
	}

}
