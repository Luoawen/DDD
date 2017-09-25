package cn.m2c.scm.domain.model.goods.goods;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

public class ChangeGoodsName  extends AssertionConcern implements DomainEvent{

	private String goodsId;
	private String goodsName;
	private Date currentTime;
	
	
	
	
	public ChangeGoodsName() {
		super();
	}

	public ChangeGoodsName(String goodsId, String goodsName) {
		super();
		this.setGoodsId(goodsId);
		this.setGoodsName(goodsName);
		this.setCurrentTime(new Date());
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public String getGoodsName() {
		return goodsName;
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
