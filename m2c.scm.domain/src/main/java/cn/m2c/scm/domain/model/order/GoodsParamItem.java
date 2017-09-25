package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;

public class GoodsParamItem extends ValueObject{
	private static final long serialVersionUID = -4851045979673624432L;
	
	private String item;

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}
	
	
}
