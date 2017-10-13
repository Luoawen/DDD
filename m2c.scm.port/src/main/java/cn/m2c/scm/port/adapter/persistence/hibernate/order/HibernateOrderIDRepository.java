package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.OrderIDRepository;
import cn.m2c.scm.domain.model.order.OrderIdBean;
/***
 * 订单号仓储
 * @author fanjc
 *
 */
@Repository
public class HibernateOrderIDRepository extends HibernateSupperRepository implements OrderIDRepository {

	@Override
	public void save(String strTime, String suffix) {
		// TODO Auto-generated method stub
		session().save(new OrderIdBean(strTime, suffix));
	}

	@Override
	public void delete(String strTime) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder(100);
		sql.append("delete from t_scm_order_order_no where time_str < :time");
		
		int r = session().createSQLQuery(sql.toString()).setString("time", strTime).executeUpdate();
		
		return;
	}

}
