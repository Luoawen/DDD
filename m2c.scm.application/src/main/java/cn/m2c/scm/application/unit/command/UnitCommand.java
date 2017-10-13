package cn.m2c.scm.application.unit.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class UnitCommand extends AssertionConcern implements Serializable{

	//计量单位Id
	private String unitId;
	//计量单位名称
	private String unitName;
	
	private Integer unitStatus = 1;
	
	
	public UnitCommand(String unitId,String unitName) {
		this.unitId = unitId;
		this.unitName = unitName;
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

}
