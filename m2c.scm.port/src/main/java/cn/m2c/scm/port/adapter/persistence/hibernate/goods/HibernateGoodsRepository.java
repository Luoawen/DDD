package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.goods.Goods;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品
 */
@Repository
public class HibernateGoodsRepository extends HibernateSupperRepository implements GoodsRepository {
    @Override
    public Goods queryGoodsById(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where goods_id =:goods_id and del_status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("goods_id", goodsId);
        return (Goods) query.uniqueResult();
    }

    @Override
    public boolean goodsNameIsRepeat(String goodsId, String dealerId, String goodsName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where dealer_id=:dealer_id AND goods_name =:goods_name AND del_status = 1 ");
        if (StringUtils.isNotEmpty(goodsId)) {
            sql.append(" and goods_id <> :goods_id");
        }
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("dealer_id", dealerId);
        query.setParameter("goods_name", goodsName);
        if (StringUtils.isNotEmpty(goodsId)) {
            query.setParameter("goods_id", goodsId);
        }
        List<Brand> list = query.list();
        return null != list && list.size() > 0;
    }

    @Override
    public void save(Goods goods) {
        this.session().save(goods);
    }


}
