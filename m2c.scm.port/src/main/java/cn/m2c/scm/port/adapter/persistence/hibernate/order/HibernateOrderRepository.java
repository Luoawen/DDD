package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.domain.model.order.OrderRepository;
/**
 * 订单仓储
 * @author fanjc
 * <br>created date 2017年10月14日
 * <br>copyrighted@m2c
 */
@Repository
public class HibernateOrderRepository extends HibernateSupperRepository implements OrderRepository {

	@Override
	public void save(MainOrder order) {
		// TODO Auto-generated method stub
		this.session().save(order);
	}

	@Override
	public MainOrder getOrderById(String orderId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select * from t_scm_order_main where order_id =:orderId");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(MainOrder.class);
		query.setParameter("orderId", orderId);
		return (MainOrder)query.uniqueResult();
	}

}
