package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.logger.OperLogger;
import cn.m2c.scm.domain.model.order.logger.OperLoggerRepository;

@Repository
public class HibernateOperLoggerRepository  extends HibernateSupperRepository implements OperLoggerRepository{

	@Override
	public void save(OperLogger operLogger) throws NegativeException {
		try{
			this.session().merge(operLogger);
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateOperLoggerRepository.add(..) exception.", e);
		}		
	}

	@Override
	public OperLogger findT(String operLoggerId) throws NegativeException {
		try{
			assertArgumentNotEmpty(operLoggerId, "HibernateOperLoggerRepository.findT(..) fsalesId is must be not null");
			OperLogger operLogger = (OperLogger)this.session().createQuery(" FROM AfterSales WHERE fsalesId = :fsalesId ").setParameter("fsalesId", operLoggerId).uniqueResult();
			return operLogger;
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateOperLoggerRepository.findT(..) exception.", e);
		}
	}

}
