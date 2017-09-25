package cn.m2c.scm.domain.model.goods.templet;

import java.util.Date;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
/**
 * 属性模板
 * @author yezp
 * 
 */
public class Property extends ConcurrencySafeEntity{

	private static final long serialVersionUID = -1689799781813951950L;

	private String propertyId;
	private String modelName;
	private Integer propertyCount;
	private String  propertyValue;
	private String dealerId;
	private Integer propertyStatus = 1;//1：正常 2：删除
	private Date createdDate;
	private Date lastUpdatedDate;
	public Property() {
		super();
	}
	
	
	public String getPropertyId() {
		return propertyId;
	}
	public String getModelName() {
		return modelName;
	}
	public Integer getPropertyCount() {
		return propertyCount;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public String getDealerId() {
		return dealerId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public Integer getPropertyStatus() {
		return propertyStatus;
	}

	/**
	 * 添加属性操作
	 * @param propertyId
	 * @param modelName
	 * @param propertyValue
	 * @param dealerId
	 */
	public void add(String propertyId, String modelName,
			String propertyValue, String dealerId) {
		this.propertyId = propertyId;
		this.modelName = modelName;
		this.propertyValue = propertyValue;
		this.dealerId = dealerId;
		Map<String,String> pvalueList = new ObjectSerializer(true).deserialize(propertyValue, new TypeToken<Map<String,String>>(){}.getType());
		this.propertyCount = pvalueList.size();
		
	}

	/**
	 * 更新属性操作
	 * @param modelName
	 * @param propertyValue
	 */
	public void update(String modelName, String propertyValue) {
		this.modelName = modelName;
		this.propertyValue = propertyValue;
		Map<String,String> pvalueList = new ObjectSerializer(true).deserialize(propertyValue, new TypeToken<Map<String,String>>(){}.getType());
		this.propertyCount = pvalueList.size();
		
	}


	public void delProperty() {
		this.propertyStatus = 2;
	}
	
	
}
