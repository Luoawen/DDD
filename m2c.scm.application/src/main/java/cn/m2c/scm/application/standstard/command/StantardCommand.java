package cn.m2c.scm.application.standstard.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class StantardCommand extends AssertionConcern implements Serializable {

	private String stantardId;
	private String stantardName;
	private Integer stantardStatus;

	public String getStantardId() {
		return stantardId;
	}

	public String getStantardName() {
		return stantardName;
	}

	public Integer getStantardStatus() {
		return stantardStatus;
	}

	public StantardCommand(String stantardId, String stantardName) {
		super();
		this.stantardId = stantardId;
		this.stantardName = stantardName;
	}

	
}
