package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.GoodsGuarantee;
import cn.m2c.scm.domain.model.goods.GoodsGuaranteeRepository;

/**
 * 商品保障
 * */
@Repository
public class HibernateGoodsGuaranteeRepository extends HibernateSupperRepository implements GoodsGuaranteeRepository{
	/**
	 * 获取商品保障
	 * */
	@Override
	public GoodsGuarantee queryGoodsGuaranteeById(String guaranteeId) {
		GoodsGuarantee goodsGuarantee = (GoodsGuarantee) this.session().createQuery(" FROM GoodsGuarantee WHERE guaranteeId=:guaranteeId")
				.setString("guaranteeId", guaranteeId).uniqueResult();
		return goodsGuarantee;
	}

	/**
	 * 查询是否有重名(true有重名)
	 * */
	@Override
	public boolean goodsGuaranteeNameIsRepeat(String guaranteeName, String dealerId) {
		StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_goods_guarantee WHERE ( guarantee_name=:guaranteeName AND dealer_id=:dealerId ) OR ( guarantee_name=:guaranteeName AND is_default = 1 )");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsGuarantee.class);
		query.setParameter("guaranteeName",guaranteeName);
		query.setParameter("dealerId",dealerId);
		List<GoodsGuarantee> list = query.list();
		return null != list && list.size() > 0;
	}

	@Override
	public void save(GoodsGuarantee goodsGuarantee) {
		this.session().saveOrUpdate(goodsGuarantee);
	}

}
