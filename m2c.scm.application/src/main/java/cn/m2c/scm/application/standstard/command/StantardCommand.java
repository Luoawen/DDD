package cn.m2c.scm.application.standstard.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class StantardCommand extends AssertionConcern implements Serializable {

	private String stantardId;
	private String stantardName;
	private Integer stantardStatus = 1;

	public String getStantardId() {
		return stantardId;
	}

	public String getStantardName() {
		return stantardName;
	}

	public Integer getStantardStatus() {
		return stantardStatus;
	}

	public StantardCommand(String stantardId, String stantardName) throws NegativeException {
		if (StringUtils.isEmpty(stantardId)) {
			throw new NegativeException(400, "请刷新页面获取规格ID");
		}
		if (StringUtils.isEmpty(stantardName.replaceAll(" ", ""))) {
			throw new NegativeException(400, "规格名称不能为空");
		}
		this.stantardId = stantardId;
		this.stantardName = stantardName;
	}

	
}
