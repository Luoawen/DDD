package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateOrderRepository.class);
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
	
	@Override
	public void updateMainOrder(MainOrder order) {
		Session s = this.session();
		s.save(order);
		/*int rs = s.createSQLQuery("update t_scm_order_dealer set _status=:status where order_id=:orderId")
				.setParameter("status", order.getStatus()).setParameter("orderId", order.getOrderId()).executeUpdate();
		LOGGER.info("===fanjc==更新商家订单状态成功条数:" + rs);
		rs = s.createSQLQuery("update t_scm_order_detail set _status=:status where order_id=:orderId")
				.setParameter("status", order.getStatus()).setParameter("orderId", order.getOrderId()).executeUpdate();
		LOGGER.info("===fanjc==更新商家订单状态成功条数:" + rs);*/
	}

	@Override
	public DealerOrder getDealerOrderByNo(String dealerOrderId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select * from t_scm_order_dealer where dealer_order_id =:dealerOrderId");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(DealerOrder.class);
		query.setParameter("dealerOrderId", dealerOrderId);
		return (DealerOrder)query.uniqueResult();
	}

	@Override
	public DealerOrderDtl getDealerOrderDtlBySku(String dealerOrderId, String sku) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select * from t_scm_order_detail where dealer_order_id =:dealerOrderId and sku_id=:skuId");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(DealerOrderDtl.class);
		query.setParameter("dealerOrderId", dealerOrderId);
		query.setParameter("skuId", sku);
		return (DealerOrderDtl)query.uniqueResult();
	}

	@Override
	public void updateDealerOrder(DealerOrder dealOrder) {
		// TODO Auto-generated method stub
		this.session().save(dealOrder);
	}
	@Override
	public <T> List<T> getOrderGoodsForCal(String orderNo, Class<T> clss) {
		StringBuilder sql = new StringBuilder("select sku_id skuId, sell_num purNum, marketing_id as marketingId, market_level as marketLevel, discount_price as discountPrice, is_change as isChange, "); 
		sql.append(" change_price as changePrice, freight ");
		sql.append(" from t_scm_order_detail where order_id=:orderNo");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(clss);
		query.setParameter("orderNo", orderNo);
		return (List<T>)query.list();
	}
}
