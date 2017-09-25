package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.fsales.AfterSales;
import cn.m2c.scm.domain.model.order.fsales.AfterSalesRepository;

@Repository
public class HibernateAfterSalesRepository  extends HibernateSupperRepository implements AfterSalesRepository{

	@Override
	public void save(AfterSales afterSales) throws NegativeException {
		try{
			this.session().merge(afterSales);
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateOrderRepository.add(..) exception.", e);
		}		
	}

	@Override
	public AfterSales findT(String fsalesId) throws NegativeException {
		try{
			assertArgumentNotEmpty(fsalesId, "HibernateCommentRepository.findT(..) fsalesId is must be not null");
			AfterSales afterSales = (AfterSales)this.session().createQuery(" FROM AfterSales WHERE fsalesId = :fsalesId ").setParameter("fsalesId", fsalesId).uniqueResult();
			return afterSales;
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateCommentRepository.findT(..) exception.", e);
		}
	}

}
