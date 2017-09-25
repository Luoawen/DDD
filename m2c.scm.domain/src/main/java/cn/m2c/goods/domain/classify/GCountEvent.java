package cn.m2c.goods.domain.classify;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;
/**
 * 商品数量事件
 * @author yezp
 *
 */
public class GCountEvent  extends AssertionConcern implements DomainEvent {
	
	private String goodsClassifyId;
	private Integer changeCount;
	private Date loginDate;

	public GCountEvent(String goodsClassifyId, Integer changeCount) {
		super();
		this.setGoodsClassifyId(goodsClassifyId);
		this.setChangeCount(changeCount);
		this.setLoginDate(new Date());
	}

	public GCountEvent() {
		super();
	}

	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}

	public void setGoodsClassifyId(String goodsClassifyId) {
		this.goodsClassifyId = goodsClassifyId;
	}

	public Integer getChangeCount() {
		return changeCount;
	}

	public void setChangeCount(Integer changeCount) {
		this.changeCount = changeCount;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	@Override
	public int eventVersion() {
		return 0;
	}

	@Override
	public Date occurredOn() {
		return this.getLoginDate();
	}

}
