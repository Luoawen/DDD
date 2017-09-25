package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.seller.StaffReportCount;
import cn.m2c.scm.domain.model.goods.seller.StaffReportCountRepository;

/**
 * @ClassName: HibernateSalerRepository
 * @Description: 业务员报表数据持久化组件
 * @author yezo
 * @date 2017年6月1日 上午10:34:25
 *
 */
@Repository
public class HibernateStaffReportCountRepository extends HibernateSupperRepository implements StaffReportCountRepository {

	public HibernateStaffReportCountRepository() {
		super();
	}

	@Override
	public StaffReportCount getStaffReport(String reportId) throws NegativeException {
		Object result = this.session().createQuery(" FROM StaffReportCount WHERE reportId = :reportId ")
				.setString("reportId", reportId).uniqueResult();
		StaffReportCount staffReport = (StaffReportCount)result;
		return staffReport;
	}

	@Override
	public void save(StaffReportCount staffReport) throws NegativeException {
		this.session().saveOrUpdate(staffReport);
	}

	@Override
	public void remove(StaffReportCount staffReport) throws NegativeException {
		this.session().delete(staffReport);
	}

}
