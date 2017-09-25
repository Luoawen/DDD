package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.goods.Goods;
import cn.m2c.goods.domain.goods.GoodsRepository;

/**
 * @ClassName: HibernateDealerRepository
 * @Description: 品牌持久化实现
 * @author yezp
 * @date 2017年6月30日 下午3:41:10
 *
 */
@Repository
public class HibernateGoodsRepository extends HibernateSupperRepository implements  GoodsRepository{

	public HibernateGoodsRepository() {
		super();
	}

	@Override
	public Goods getGoodsDetail(String goodsId) {
//		Goods goods = (Goods) this.session().createQuery(" FROM Goods WHERE goodsId = :goodsId")
//				.setString("goodsId", goodsId).uniqueResult();
		
		return (Goods)this.session().createCriteria(Goods.class).add(Restrictions.eq("goodsId", goodsId)).uniqueResult();
	}

	@Override
	public void save(Goods goods) {
			
		this.session().merge(goods);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Goods> getGoodsList(String dealerId) {
		return (List<Goods>)this.session().createQuery("FROM Goods WHERE dealerId=:dealerId")
                .setString("dealerId", dealerId)
                .list();
	}


}
