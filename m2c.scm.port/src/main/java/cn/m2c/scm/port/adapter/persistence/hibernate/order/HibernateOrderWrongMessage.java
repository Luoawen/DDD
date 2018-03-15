package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.OrderWrongMessage;
import cn.m2c.scm.domain.model.order.OrderWrongMessageRepository;

@Repository
public class HibernateOrderWrongMessage extends HibernateSupperRepository implements OrderWrongMessageRepository{

	@Override
	public void save(OrderWrongMessage orderWrongMessage) {
		this.session().saveOrUpdate(orderWrongMessage);
		
	}

}
