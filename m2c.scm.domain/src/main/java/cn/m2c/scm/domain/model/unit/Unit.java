package cn.m2c.scm.domain.model.unit;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 计量单位
 * 
 * @author lqwen
 *
 */
public class Unit extends ConcurrencySafeEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4137714621787020003L;
	
	private String unitId;
	private String unitName;        //计量单位名
	private Date createdDate;
	private Date lastUpdatedDate;
	private Integer useNum;    //被使用  默认0，为0时可以删除，大于0不能删除
	
	/**
	 * 1 正常 2 删除
	 */
	private Integer unitStatus = 1;

	public Unit() {
		super();
	}

	public Unit(String unitId,String unitName) {
		this.unitId = unitId;
		this.unitName = unitName;
	}
	
	public void used() {
		this.useNum ++;
	}
	
	public void noUsed() {
		this.useNum --;
	}
	
	public Integer getUseNum() {
		return useNum;
	}
	
	/**
	 * 删除计量单位
	 */
	public void deleteUnit() {
		this.unitStatus = 2;
	}
	
	/**
	 * 增加计量单位
	 * @param unitName
	 */
	public void addUnit(String unitName) {
		this.unitName = unitName;
	}

	/**
	 * 修改计量单位
	 * @param unitName
	 */
	public void updateUnit(String unitName) {
		this.unitName = unitName;
		this.lastUpdatedDate = new Date();
	}
	
	public void modify(String unitId,String unitName,Integer unitStatus) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitStatus = unitStatus;
		this.lastUpdatedDate = new Date();
	} 
}
