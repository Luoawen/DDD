package cn.m2c.scm.domain.model.stantard;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 规格说明
 * 
 * @author lqwen
 *
 */
public class Stantard extends ConcurrencySafeEntity{

	
	private String stantardId;

	private String stantardName;

	private Integer stantardStatus = 1; // 状态：1 正常 2 删除
	
	private Integer useNum;//被使用  默认0，为0时可以删除，大于0不能删除

	
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
	
	public void used() {
		this.useNum++;
	}
	
	public void noUsed() {
		this.useNum --;
	}
	
	public Integer getUseNum() {
		return useNum;
	}
	
	public void modify(String stantardId,String stantardName,Integer stantardStatus) {
		this.stantardId = stantardId;
		this.stantardName = stantardName;
		this.stantardStatus = stantardStatus;
	}

}
