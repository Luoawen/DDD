package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class PropertyAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 2895057840582471832L;
	private String propertyId;
	private String modelName;
	private String  propertyValue;
	private String dealerId;
	
	
	public PropertyAddOrUpdateCommand() {
		super();
	}
	
	
	public PropertyAddOrUpdateCommand(String propertyId, String modelName,
			 String propertyValue, String dealerId) {
		super();
		assertArgumentNotNull(dealerId, "经销商不能为空");
		
		
		assertArgumentNotNull(modelName, "模板名称不能为空");
		assertArgumentLength(modelName, 100, "模板名称过长");
		
		assertArgumentNotNull(propertyValue, "属性值不能为空");
		
		
		
		this.propertyId = propertyId;
		this.modelName = modelName;
		this.propertyValue = propertyValue;
		this.dealerId = dealerId;
	}


	public String getPropertyId() {
		return propertyId;
	}
	public String getModelName() {
		return modelName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public String getDealerId() {
		return dealerId;
	}
	
	
	
}
