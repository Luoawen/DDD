package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.classify.GoodsClassify;
import cn.m2c.goods.domain.classify.GoodsClassifyRepository;
import cn.m2c.goods.domain.dealer.Dealer;
import cn.m2c.goods.domain.goods.Goods;
import cn.m2c.goods.domain.goods.GoodsRepository;

/**
 * @ClassName: HibernateGoodsClassifyRepository
 * @Description: 品牌持久化实现
 * @author yezp
 * @date 2017年7月8日 下午3:41:10
 *
 */
@Repository
public class HibernateGoodsClassifyRepository extends HibernateSupperRepository implements  GoodsClassifyRepository{

	public HibernateGoodsClassifyRepository() {
		super();
	}

	@Override
	public GoodsClassify getGoodsClassifyDetail(String goodsClassifyId) {
		GoodsClassify goodsClassify = (GoodsClassify) this.session().createQuery(" FROM GoodsClassify WHERE goodsClassifyId = :goodsClassifyId")
				.setString("goodsClassifyId", goodsClassifyId).uniqueResult();
		return goodsClassify;
	}

	@Override
	public void save(GoodsClassify goodsClassify) {
		this.session().saveOrUpdate(goodsClassify);
	}





}
