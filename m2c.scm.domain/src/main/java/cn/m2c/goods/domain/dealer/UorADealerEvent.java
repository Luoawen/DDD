package cn.m2c.goods.domain.dealer;

import java.util.Date;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;
/**
 * 修改或者新增经销商事件
 * @author yezp
 *
 */
public class UorADealerEvent  extends AssertionConcern implements DomainEvent{
	
	private String dealerId;
	private String dealerName;
	private String dealerMobile;
	private Long createDate;
	private Date currentTime;

	public UorADealerEvent() {
		super();
	}

	public UorADealerEvent(String dealerId, String dealerName,
			String dealerMobile, Long createDate) {
		super();
		this.setDealerId(dealerId);
		this.setDealerName(dealerName);
		this.setDealerMobile(dealerMobile);
		this.setCreateDate(createDate);
		this.setCurrentTime(new Date());
	}



	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerMobile() {
		return dealerMobile;
	}

	public void setDealerMobile(String dealerMobile) {
		this.dealerMobile = dealerMobile;
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
