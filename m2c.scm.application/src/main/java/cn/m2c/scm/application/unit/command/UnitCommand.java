package cn.m2c.scm.application.unit.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class UnitCommand extends AssertionConcern implements Serializable{

	//计量单位Id
	private String unitId;
	//计量单位名称
	private String unitName;
	
	private Integer unitStatus = 1;
	
	
	public UnitCommand(String unitId,String unitName) throws NegativeException {
		if (StringUtils.isEmpty(unitId)) {
			throw new NegativeException(MCode.V_1, "计量ID名称没有参数(unitId)");
		}
		if (StringUtils.isEmpty(unitName)) {
			throw new NegativeException(MCode.V_1, "计量单位名称没有参数(unitName)");
		}
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
