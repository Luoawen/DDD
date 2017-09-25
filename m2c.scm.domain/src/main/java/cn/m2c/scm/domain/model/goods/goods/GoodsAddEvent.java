package cn.m2c.scm.domain.model.goods.goods;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

public class GoodsAddEvent  extends AssertionConcern implements DomainEvent{
	private String goodsId;
	private Long regisDate;
	private String staffId;
	
	

	public GoodsAddEvent() {
		super();
	}

	public GoodsAddEvent(String goodsId,String staffId, Long regisDate) {
		super();
		this.goodsId = goodsId;
		this.staffId = staffId;
		this.regisDate = regisDate;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public Long getRegisDate() {
		return regisDate;
	}
	
	public String getStaffId() {
		return staffId;
	}

	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date occurredOn() {
		return new Date(System.currentTimeMillis());
	}

}
