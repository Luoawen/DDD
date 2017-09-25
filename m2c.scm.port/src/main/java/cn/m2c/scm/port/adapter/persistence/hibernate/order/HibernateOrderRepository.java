package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.Order;
import cn.m2c.scm.domain.model.order.OrderRepository;


/**
 * 
 * @ClassName: HibernateOrderRepository
 * @Description:订单
 * @author moyj
 * @date 2017年4月19日 下午1:57:00
 *
 */
@Repository
public class HibernateOrderRepository extends HibernateSupperRepository implements OrderRepository{
	
	public HibernateOrderRepository(){
		super();
	}

	@Override
	public void save(Order order) throws NegativeException {
		try{
			this.session().merge(order);
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateOrderRepository.add(..) exception.", e);
		}
		
	}

	@Override
	public Order findT(String orderId) throws NegativeException {
		try{
			assertArgumentNotEmpty(orderId, "HibernateOrderRepository.orderT(..) orderId is must be not null");
			Order order = (Order)this.session().createQuery(" FROM Order WHERE orderId = :orderId ").setParameter("orderId", orderId).uniqueResult();
			return order;
		}catch(Exception e){
			throw new NegativeException(MCode.V_500,"HibernateOrderRepository.add(..) exception.", e);
		}
	}

}
