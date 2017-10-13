package cn.m2c.scm.application.standstard.bean;

import java.io.Serializable;
import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class StantardBean extends AssertionConcern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ColumnAlias(value = "id")
	private Integer id;
	
	@ColumnAlias(value = "stantard_id")
	private String stantardId;
	
	@ColumnAlias(value = "stantard_name")
	private String stantardName;
	
	@ColumnAlias(value = "stantard_status")
	private Integer stantardStatus;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	
	@ColumnAlias(value = "last_update_date")
	private Date lastUpdatedDate;

	public Integer getId() {
		return id;
	}

	public String getStantardId() {
		return stantardId;
	}

	public String getStantardName() {
		return stantardName;
	}

	public Integer getStantardStatus() {
		return stantardStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setStantardId(String stantardId) {
		this.stantardId = stantardId;
	}

	public void setStantardName(String stantardName) {
		this.stantardName = stantardName;
	}

	public void setStantardStatus(Integer stantardStatus) {
		this.stantardStatus = stantardStatus;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	
}
