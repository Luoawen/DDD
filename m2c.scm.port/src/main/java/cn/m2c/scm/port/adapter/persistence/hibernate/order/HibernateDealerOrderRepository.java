package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
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
	/***
	 * 设置评论状态
	 */
	@Override
	public void updateComment(String orderId, String skuId, int flag) {
		session().createSQLQuery("update t_scm_order_detail set comment_status = :flag where order_id=:orderId and sku_id=:skuId")
		.setParameter("flag", flag).setParameter("orderId", orderId)
		.setParameter("skuId", skuId).executeUpdate();
	}
	@Override
	public List<String> getSpecifiedDtlStatus(int hour) {
		/*List<Long> rs = this.session().createSQLQuery("select id from t_scm_order_dealer where dealer_order_id NOT IN\r\n" + 
				"(select DISTINCT b.dealer_order_id from t_scm_order_dealer b, t_scm_order_detail a\r\n" + 
				"where a.order_id = b.order_id\r\n" + 
				"and a.dealer_order_id=b.dealer_order_id\r\n" + 
				"and a._status NOT IN (4, 5, -1)\r\n" + 
				"and b._status NOT IN (-1, 4, 5)\r\n" + 
				") and _status NOT IN (-1, 4, 5)").list();
		
		if (rs != null && rs.size() > 0) {
			this.session().createSQLQuery("UPDATE t_scm_order_dealer SET _status=4 WHERE id IN(:idList)").setParameterList("idList", rs).executeUpdate();
		}*/
		List<String> rs = this.session().createSQLQuery("select dealer_order_id from t_scm_order_dealer where dealer_order_id NOT IN\r\n" + 
				"(select DISTINCT b.dealer_order_id from t_scm_order_dealer b, t_scm_order_detail a\r\n" + 
				"where a.order_id = b.order_id\r\n" + 
				"and a.dealer_order_id=b.dealer_order_id\r\n" + 
				"and a._status NOT IN (4, 5, -1)\r\n" + 
				"and b._status NOT IN (-1, 4, 5)\r\n" + 
				") and _status NOT IN (-1, 4, 5)").list();
		return rs;
	}
	
	@Override
	public boolean judgeHasAfterSale(String orderId) {		
		Object o = this.session().createSQLQuery("select count(1) from t_scm_order_after_sell where dealer_order_id=:orderId and _status IN (10, 11, 12)")
		.setParameter("orderId", orderId).uniqueResult();
		BigInteger b = (BigInteger)o;
		return b != null ? b.intValue() > 0 : false;
	}
}
