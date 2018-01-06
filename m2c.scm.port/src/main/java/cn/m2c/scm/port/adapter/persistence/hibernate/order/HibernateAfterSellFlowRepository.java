package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.AfterSellFlow;
import cn.m2c.scm.domain.model.order.AfterSellFlowRepository;

@Repository
public class HibernateAfterSellFlowRepository extends HibernateSupperRepository implements AfterSellFlowRepository{

	@Override
	public void save(AfterSellFlow afterSellFlow) {
		this.session().saveOrUpdate(afterSellFlow);
	}

}
