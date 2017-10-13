package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.GoodsApprove;
import cn.m2c.scm.domain.model.goods.GoodsApproveRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 商品审核
 */
@Repository
public class HibernateGoodsApproveRepository extends HibernateSupperRepository implements GoodsApproveRepository {
    @Override
    public GoodsApprove queryGoodsApproveById(String approveId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where approve_id =:approve_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("approve_id", approveId);
        return (GoodsApprove) query.uniqueResult();
    }

    @Override
    public GoodsApprove queryGoodsApproveByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where goods_id =:goods_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("goods_id", goodsId);
        return (GoodsApprove) query.uniqueResult();
    }

    @Override
    public void save(GoodsApprove goodsApprove) {
        this.session().save(goodsApprove);
    }
}
