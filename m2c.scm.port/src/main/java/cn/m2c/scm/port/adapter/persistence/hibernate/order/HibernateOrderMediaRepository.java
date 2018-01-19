package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.OrderMedia;
import cn.m2c.scm.domain.model.order.OrderMediaRepository;

@Repository
public class HibernateOrderMediaRepository extends HibernateSupperRepository implements OrderMediaRepository{

	/**
	 * 保存广告位订单信息
	 */
	@Override
	public void save(OrderMedia orderMedia) {
		this.session().save(orderMedia);
	}

}
