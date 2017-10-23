package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
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


}
