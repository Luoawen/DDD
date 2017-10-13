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
	
	private Integer id;
	private String unitId;
	private String unitName;        //计量单位名
	private Date createdDate;
	private Date lastUpdatedDate;
	
	/**
	 * 1 正常 2 删除
	 */
	private Integer unitStatus = 1;

	public Unit() {
		super();
	}

	public Unit(String unitId,String unitName,Integer unitstatues) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitStatus = unitstatues;
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
	}
	
	public void modify(String unitId,String unitName,Integer unitStatus) {
		this.unitId = unitId;
		this.unitName = unitName;
		this.unitStatus = unitStatus;
	} 
}
