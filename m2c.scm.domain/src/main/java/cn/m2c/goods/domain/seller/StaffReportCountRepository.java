package cn.m2c.goods.domain.seller;

import cn.m2c.goods.exception.NegativeException;

public interface StaffReportCountRepository {

	public StaffReportCount getStaffReport(String reportId) throws NegativeException;
	
	public void save(StaffReportCount staffReport) throws NegativeException;
	
	public void remove(StaffReportCount staffReport) throws NegativeException;


}
