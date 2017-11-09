package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderRepository;
/**
 * 商家订单仓储
 * @author yezp
 * <br>created date 2017年10月14日
 * <br>copyrighted@m2c
 */
@Repository
public class HibernateDealerOrderRepository extends HibernateSupperRepository implements DealerOrderRepository {

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	
	@Override
	public void save(DealerOrder dealerOrder) {
		this.session().saveOrUpdate(dealerOrder);
	}

	@Override
	public DealerOrder getDealerOrderById(String dealerOrderId) {
		DealerOrder dealerOrder = (DealerOrder) this.session().createQuery(" FROM DealerOrder WHERE  dealerOrderId = :dealerOrderId")
				.setString("dealerOrderId", dealerOrderId).uniqueResult();
		System.out.println("-------------执行到获取商家订单");
		return dealerOrder;
	}

	@Override
	public List<DealerOrder> getDealerOrderStatusQeury() {
		@SuppressWarnings("unchecked")
		List<DealerOrder> list = this.session().createQuery(" FROM DealerOrder WHERE status = 2 ").list();
		return list;
	}

	@Override
	public List<DealerOrder> getDealerOrderFinishied() {
		@SuppressWarnings("unchecked")
		List<DealerOrder> list = this.session().createQuery(" FROM DealerOrder WHERE status = 3 ").list();
		return list;
	}

	@Override
	public List<DealerOrder> getDealerOrderWaitPay() {
		@SuppressWarnings("unchecked")
		List<DealerOrder> list = this.session().createQuery(" FROM DealerOrder WHERE status = 0 ").list();
		System.out.println("获取出来的数据-------------------------------"+list);
		return list;
	}
	@Override
	public void updateFreight(DealerOrder dealerOrder) {
		this.session().saveOrUpdate(dealerOrder);
		session().createSQLQuery("update t_scm_order_main set order_freight = (select sum(a.order_freight) from t_scm_order_dealer a where a.order_id=:orderId) where order_id=:orderId")
		.setParameter("orderId", dealerOrder.getOrderNo()).executeUpdate();
	}
}
