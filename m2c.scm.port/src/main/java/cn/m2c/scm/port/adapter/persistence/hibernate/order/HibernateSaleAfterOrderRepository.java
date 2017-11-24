package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.SaleAfterOrder;
import cn.m2c.scm.domain.model.order.SaleAfterOrderRepository;
/**
 * 售后订单仓储
 * @author fanjc
 * <br>created date 2017年10月14日
 * <br>copyrighted@m2c
 */
@Repository
public class HibernateSaleAfterOrderRepository extends HibernateSupperRepository implements SaleAfterOrderRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(HibernateSaleAfterOrderRepository.class);
	@Override
	public void save(SaleAfterOrder order) {
		// TODO Auto-generated method stub
		this.session().save(order);
	}

	@Override
	public SaleAfterOrder getSaleAfterOrderByNo(String saleAfterNo) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select * from t_scm_order_after_sell where after_sell_order_id =:saleAfterNo");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(SaleAfterOrder.class);
		query.setParameter("saleAfterNo", saleAfterNo);
		return (SaleAfterOrder)query.uniqueResult();
	}
	
	@Override
	public void updateSaleAfterOrder(SaleAfterOrder order) {
		Session s = this.session();
		s.save(order);
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
	public DealerOrderDtl getDealerOrderDtlBySku(String dealerOrderId, String skuId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder("select * from t_scm_order_detail where dealer_order_id =:dealerOrderId and sku_id=:skuId");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(DealerOrderDtl.class);
		query.setParameter("dealerOrderId", dealerOrderId);
		query.setParameter("skuId", skuId);
		return (DealerOrderDtl)query.uniqueResult();
	}
	@Override
	public SaleAfterOrder getSaleAfterOrderByNo(String saleAfterNo, String dealerId) {
		StringBuilder sql = new StringBuilder("select * from t_scm_order_after_sell where after_sell_order_id =:saleAfterNo");
		sql.append(" and dealer_id=:dealerId");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(SaleAfterOrder.class);
		query.setParameter("saleAfterNo", saleAfterNo);
		query.setParameter("dealerId", dealerId);
		return (SaleAfterOrder)query.uniqueResult();
	}

	
	@Override
	public List<SaleAfterOrder> getSaleAfterOrderStatusAgree(int hour) {
		// 商家同意退款或是换货商家已发出态下7天变更为交易完成 //60/
		return this.session().createSQLQuery("SELECT a.* FROM t_scm_order_after_sell a WHERE ((a._status = 10) or (a._status = 7 AND a.order_type=0)) AND round((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(a.last_updated_date))/60)/"+hour+" > 1").addEntity(SaleAfterOrder.class).list();
	}

	@Override
	public int disabledOrderMarket(String orderId, String marketId) {
		// TODO Auto-generated method stub
		int rs = this.session().createSQLQuery("UPDATE t_scm_order_marketing_used set _status=0 WHERE order_id=:orderId AND marketing_id=:marketId")
				.setParameter("orderId", orderId).setParameter("marketId", marketId)
				.executeUpdate();
		return rs;
	}
	
	@Override
	public List<SaleAfterOrder> getSaleAfterApplyed(int hour) {
		return (List<SaleAfterOrder>)this.session().createSQLQuery("SELECT * FROM t_scm_order_after_sell WHERE _status IN(0,1,2) AND round((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(last_updated_date))/60)/"+hour+" > 1")//60/
				.addEntity(SaleAfterOrder.class).list();
	}
	
	@Override
	public List<SaleAfterOrder> getAgreeRtMoney(int hour) {
		return (List<SaleAfterOrder>)this.session().createSQLQuery("SELECT * FROM t_scm_order_after_sell WHERE _status=4 AND order_type = 2 AND round((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(last_updated_date))/60)/"+hour+" > 1")//60/
				.addEntity(SaleAfterOrder.class).list();
	}
	/***
	 * 获取用户已发货，商家需要自动收货的数据
	 */
	@Override
	public List<SaleAfterOrder> getUserSend(int hour) {
		return (List<SaleAfterOrder>)this.session().createSQLQuery("SELECT * FROM t_scm_order_after_sell WHERE _status=5 AND order_type IN(0,1) AND round((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(last_updated_date))/60)/"+hour+" > 1")//60/
				.addEntity(SaleAfterOrder.class).list();
	}
	/***
	 * 获取商家已发货，用户需要自动收货的数据
	 */
	@Override
	public List<SaleAfterOrder> getDealerSend(int hour) {
		return (List<SaleAfterOrder>)this.session().createSQLQuery("SELECT * FROM t_scm_order_after_sell WHERE _status=7 AND order_type=0 AND round((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(last_updated_date))/60)/"+hour+" > 1")//60/
				.addEntity(SaleAfterOrder.class).list();
	}
	
	@Override
	public List<Long> getSpecifiedDtlGoods(int hour) {
		List<Long> rs = this.session().createSQLQuery("SELECT a.id from t_scm_order_detail a\r\n" + 
				",t_scm_order_after_sell b \r\n" + 
				"WHERE a.order_id = b.order_id\r\n" + 
				"AND a.dealer_order_id = b.dealer_order_id\r\n" + 
				"AND a.sku_id = b.sku_id\r\n" + 
				"AND a._status NOT IN (4,5, -1)\r\n" + 
				"AND b._status IN (11, 12)")
		.list();
		
		if (rs != null && rs.size() > 0) {
			this.session().createSQLQuery("UPDATE t_scm_order_detail SET _status=5 WHERE id IN(:idList)").setParameterList("idList", rs).executeUpdate();
		}
		return rs;
	}
	
	@Override
	public int getSaleAfterOrderBySkuId(String dealerOrderId, String skuId) {
		
		Object o = this.session().createSQLQuery("SELECT count(1) FROM t_scm_order_after_sell WHERE dealer_order_id = :d1 AND sku_id = :skuId AND _status NOT IN(-1, 3)").setParameter("d1", dealerOrderId).setParameter("skuId", skuId).uniqueResult();
		
		return o == null? 0: ((BigInteger)o).intValue();
	}
}
