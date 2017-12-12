package cn.m2c.scm.port.adapter.persistence.hibernate.special;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.model.special.GoodsSkuSpecial;
import cn.m2c.scm.domain.model.special.GoodsSpecial;
import cn.m2c.scm.domain.model.special.GoodsSpecialRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特惠价
 */
@Repository
public class HibernateGoodsSpecialRepository extends HibernateSupperRepository implements GoodsSpecialRepository {
    @Override
    public void save(GoodsSpecial goodsSpecial) {
        this.session().save(goodsSpecial);
    }

    @Override
    public GoodsSpecial queryGoodsSpecialBySpecialId(String specialId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where special_id =:special_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("special_id", specialId);
        return (GoodsSpecial) query.uniqueResult();
    }

    @Override
    public GoodsSpecial queryEffectiveGoodsSpecialByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where goods_id =:goods_id and status <> 2");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("goods_id", goodsId);
        return (GoodsSpecial) query.uniqueResult();
    }

    @Override
    public List<GoodsSpecial> getStartGoodsSpecial() {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where start_time <= :start_time and status = 0");
        Date currentTime = new Date(System.currentTimeMillis());
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("start_time", currentTime);
        return query.list();
    }

    @Override
    public List<GoodsSpecial> getEndGoodsSpecial() {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where end_time <= :end_time and status = 1");
        Date currentTime = new Date(System.currentTimeMillis());
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("end_time", currentTime);
        return query.list();
    }

    /**
     * 获取sku有效的特惠价
     *
     * @param skuIds
     * @return
     */
    public Map getEffectiveGoodsSkuSpecial(List<String> skuIds) {
        if (skuIds == null || skuIds.size() < 1)
            return null;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" s.* ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_special g,t_scm_goods_sku_special s WHERE g.id = s.special_id AND 1 = 1");
        sql.append(" AND g.status = 1 ");
        sql.append(" AND s.sku_id in (" + Utils.listParseString(skuIds) + ")");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSkuSpecial.class);
        List<GoodsSkuSpecial> goodsSkuSpecials = query.list();
        if (null != goodsSkuSpecials && goodsSkuSpecials.size() > 0) {
            Map map = new HashMap<>();
            for (GoodsSkuSpecial skuSpecial : goodsSkuSpecials) {
                map.put(skuSpecial.skuId(), skuSpecial);
            }
            return map;
        }
        return null;
    }

    @Override
    public List<GoodsSpecial> queryGoodsSpecialByDealerId(String dealerId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where dealer_id =:dealer_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("dealer_id", dealerId);
        return query.list();
    }

    @Override
    public List<GoodsSpecial> queryGoodsSpecialByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where goods_id =:goods_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("goods_id", goodsId);
        return query.list();
    }
}
