package cn.m2c.scm.domain.model.stantard;

import java.util.Date;

/**
 * 规格说明
 * 
 * @author lqwen
 *
 */
public class Stantard {

	private Integer id;
	
	private String stantardId;

	private String stantardName;

	private Integer stantardStatus = 1; // 状态：1 正常 2 删除

	
	private Integer concurrencyVersion;
	private Date createdDate;
	private Date lastUpdatedDate;

	public Stantard() {
		super();
	}

	/**
	 * 添加规格
	 * 
	 * @param stantardId
	 * @param stantardName
	 */
	public void addStanstard(String stantardId, String stantardName, Integer stantardStatus) {
		this.stantardId = stantardId;
		this.stantardName = stantardName;
		this.stantardStatus = stantardStatus;
	}

	public Stantard(String stantardId, String stantardName, Integer stantardStatus) {
		super();
		this.stantardId = stantardId;
		this.stantardName = stantardName;
		this.stantardStatus = stantardStatus;
	}

	/**
	 * 删除规格
	 */
	public void delStanstard() {
		this.stantardStatus = 2;
	}
	
	public void modify(String stantardId,String stantardName,Integer stantardStatus) {
		this.stantardId = stantardId;
		this.stantardName = stantardName;
		this.stantardStatus = stantardStatus;
	}

}
