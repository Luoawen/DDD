package cn.m2c.scm.application.unit.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class UnitBean {

	@ColumnAlias(value = "id")
	private Integer id;
	
	@ColumnAlias(value = "unit_id")
	private String unitId;

	@ColumnAlias(value = "unit_name")
	private String unitName;

	@ColumnAlias(value = "unit_status")
	private Integer unitStatus;

	@ColumnAlias(value = "created_date")
	private Date createDate;

	@ColumnAlias(value = "last_update_date")
	private Date lastUpdateDate;

	public Integer getId() {
		return id;
	}
	
	public String getUnitId() {
		return unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public Integer getUnitStatus() {
		return unitStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setUnitStatus(Integer unitStatus) {
		this.unitStatus = unitStatus;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	@Override
	public String toString() {
		return "UnitBean [unitId=" + unitId + ", unitName=" + unitName + ", unitStatus=" + unitStatus + "]";
	}

}
