package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.model.goods.GoodsApprove;
import cn.m2c.scm.domain.model.goods.GoodsApproveRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品审核
 */
@Repository
public class HibernateGoodsApproveRepository extends HibernateSupperRepository implements GoodsApproveRepository {
    @Override
    public GoodsApprove queryGoodsApproveById(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where goods_id =:goods_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("goods_id", goodsId);
        return (GoodsApprove) query.uniqueResult();
    }

    @Override
    public void save(GoodsApprove goodsApprove) {
        this.session().save(goodsApprove);
    }

    @Override
    public void remove(GoodsApprove goodsApprove) {
        this.session().delete(goodsApprove);
    }

    @Override
    public boolean goodsNameIsRepeat(String goodsId, String dealerId, String goodsName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where dealer_id=:dealer_id AND goods_name =:goods_name AND del_status = 1 ");
        if (StringUtils.isNotEmpty(goodsId)) {
            sql.append(" and goods_id <> :goods_id");
        }
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("dealer_id", dealerId);
        query.setParameter("goods_name", goodsName);
        if (StringUtils.isNotEmpty(goodsId)) {
            query.setParameter("goods_id", goodsId);
        }
        List<GoodsApprove> list = query.list();
        return null != list && list.size() > 0;
    }

    @Override
    public boolean brandIsUser(String brandId) {
        List<GoodsApprove> list = queryGoodsApproveByBrandId(brandId);
        return null != list && list.size() > 0;
    }

    @Override
    public List<GoodsApprove> queryGoodsApproveByBrandId(String brandId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where goods_brand_id=:goods_brand_id AND del_status = 1 ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("goods_brand_id", brandId);
        return query.list();
    }

    @Override
    public List<GoodsApprove> queryGoodsApproveByDealerId(String dealerId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where dealer_id=:dealer_id AND del_status = 1 ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("dealer_id", dealerId);
        return query.list();
    }

    @Override
    public boolean postageIdIsUser(String postageId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where del_status = 1");
        sql.append(" and goods_postage_id = :goods_postage_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
        query.setParameter("goods_postage_id", postageId);
        return null != query.list() && query.list().size() > 0;
    }

	@Override
	public List<GoodsApprove> queryGoodsApproveByIdList(List goodsIds) {
		StringBuilder sql = new StringBuilder("select * from t_scm_goods_approve where del_status = 1");
		sql.append(" and goods_id in ("+Utils.listParseString(goodsIds)+")");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsApprove.class);
		return query.list();
	}
}
