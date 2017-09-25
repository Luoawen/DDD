package cn.m2c.scm.domain.model.goods.seller;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class StaffReportCount extends ConcurrencySafeEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2274988450421584648L;
	
	private Integer reportId;
	private String staffId;
	private Integer objType;
	private Integer countNum;
	private Date reportDate;
	
	private Integer isValid;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public StaffReportCount() {
	}

	public StaffReportCount( String staffId, Integer objType, Integer countNum, Date reportDate) {
		super();
		this.staffId = staffId;
		this.objType = objType;
		this.countNum = countNum;
		this.reportDate = reportDate;
	}


	

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public void setObjType(Integer objType) {
		this.objType = objType;
	}

	public void setCountNum(Integer countNum) {
		this.countNum = countNum;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public Integer getReportId() {
		return reportId;
	}

	public String getStaffId() {
		return staffId;
	}

	public Integer getObjType() {
		return objType;
	}

	public Integer getCountNum() {
		return countNum;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	@Override
	public String toString() {
		return "StaffReportCount [reportId=" + reportId + ", staffId=" + staffId + ", objType=" + objType
				+ ", countNum=" + countNum + ", reportDate=" + reportDate + ", isValid=" + isValid + ", createdDate="
				+ createdDate + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}
	


}
