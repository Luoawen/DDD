package cn.m2c.scm.domain.model.goods.seller;

import cn.m2c.scm.domain.NegativeException;

public interface StaffReportCountRepository {

	public StaffReportCount getStaffReport(String reportId) throws NegativeException;
	
	public void save(StaffReportCount staffReport) throws NegativeException;
	
	public void remove(StaffReportCount staffReport) throws NegativeException;


}
