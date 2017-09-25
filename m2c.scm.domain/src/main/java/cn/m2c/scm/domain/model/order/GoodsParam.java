package cn.m2c.scm.domain.model.order;

import java.util.List;

import cn.m2c.ddd.common.domain.model.ValueObject;

public class GoodsParam extends ValueObject{
	private static final long serialVersionUID = -5154848182282695828L;
	
	private String paramKey;
	private List<GoodsParamItem>  paramVal;
	
	public String getParamKey() {
		return paramKey;
	}
	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}
	public List<GoodsParamItem> getParamVal() {
		return paramVal;
	}
	public void setParamVal(List<GoodsParamItem> paramVal) {
		this.paramVal = paramVal;
	}
	
	
	
	
}
