package cn.m2c.scm.port.adapter.persistence.hibernate.order;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.DealerOrderDtlRepository;
/**
 * 商家详情订单仓储
 * @author fanjc
 * <br>created date 2017年10月14日
 * <br>copyrighted@m2c
 */
@Repository
public class HibernateDealerOrderDtlRepository extends HibernateSupperRepository implements DealerOrderDtlRepository {

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	
	@Override
	public void save(DealerOrderDtl dealerOrder) {
		this.session().saveOrUpdate(dealerOrder);
	}

	@Override
	public List<DealerOrderDtl> getOrderDtlStatusQeury(int hour, int status) {
		List<DealerOrderDtl> list = this.session().createSQLQuery(" SELECT b.* FROM t_scm_order_detail b WHERE b._status = :status AND round((UNIX_TIMESTAMP(now())-UNIX_TIMESTAMP(b.last_updated_date))/60)/60/"+hour+" > 1 "
				+ " AND NOT EXISTS (SELECT a.sku_id FROM t_scm_order_after_sell a WHERE a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id=b.sku_id AND a._status NOT IN(-1, 3))")
				.addEntity(DealerOrderDtl.class).setParameter("status", status).list();
		return list;
	}
}
