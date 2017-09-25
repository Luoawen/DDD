package cn.m2c.goods.domain.event;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

public class UpdateStockEvent extends AssertionConcern implements DomainEvent {
	private String goodsId;
	private Integer stock;

	public UpdateStockEvent() {
		super();
	}
	
	public UpdateStockEvent(String goodsId, Integer stock) {
		super();
		this.goodsId = goodsId;
		this.stock = stock;
	}

	public String getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}



	public void setStock(Integer stock) {
		this.stock = stock;
	}


	public Integer getStock() {
		return stock;
	}

	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

}
