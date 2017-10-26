package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.model.goods.GoodsSku;
import cn.m2c.scm.domain.model.goods.GoodsSkuApprove;
import cn.m2c.scm.domain.model.goods.GoodsSkuRepository;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品sku
 */
@Repository
public class HibernateGoodsSkuRepository extends HibernateSupperRepository implements GoodsSkuRepository {
    @Autowired
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return this.supportJdbcTemplate;
    }

    @Override
    public GoodsSku queryGoodsSkuById(String skuId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_sku where sku_id =:sku_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSku.class);
        query.setParameter("sku_id", skuId);
        return (GoodsSku) query.uniqueResult();
    }

    @Override
    public int outInventory(String skuId, Integer num, Integer concurrencyVersion) {
        StringBuilder sql = new StringBuilder("UPDATE `m2c_scm`.`t_scm_goods_sku`");
        sql.append(" SET `available_num`=`available_num`-?,concurrency_version = concurrency_version + 1");
        sql.append(" WHERE  `sku_id` = ? AND concurrency_version = ?");
        int result = this.getSupportJdbcTemplate().jdbcTemplate().update(sql.toString(), new Object[]{num, skuId, concurrencyVersion});
        return result;
    }

    @Override
    public boolean goodsCodeIsRepeat(String dealerId, List<String> codes) {
        StringBuilder sql = new StringBuilder("select s.* from t_scm_goods g,t_scm_goods_sku s where g.id=s.goods_id");
        sql.append(" and g.dealer_id =:dealer_id ");
        sql.append(" AND s.goods_code in (" + Utils.listParseString(codes) + ") ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSku.class);
        query.setParameter("dealer_id", dealerId);
        List<GoodsSku> list = query.list();
        if (null != list && list.size() > 0) {
            return true;
        }

        StringBuilder approveSql = new StringBuilder("select s.* from t_scm_goods_approve g,t_scm_goods_sku_approve s where g.id=s.goods_id");
        approveSql.append(" and g.dealer_id =:dealer_id ");
        approveSql.append(" AND s.goods_code in (" + Utils.listParseString(codes) + ") ");
        Query approveQuery = this.session().createSQLQuery(approveSql.toString()).addEntity(GoodsSkuApprove.class);
        approveQuery.setParameter("dealer_id", dealerId);
        List<GoodsSkuApprove> approveList = approveQuery.list();
        if (null != approveList && approveList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean goodsCodeIsRepeat(String dealerId, String skuId, String code) {
        StringBuilder sql = new StringBuilder("select s.* from t_scm_goods g,t_scm_goods_sku s where g.id=s.goods_id");
        sql.append(" and g.dealer_id = :dealer_id ");
        sql.append(" AND s.goods_code = :goods_code ");
        sql.append(" AND s.sku_id <> :sku_id ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSku.class);
        query.setParameter("dealer_id", dealerId);
        query.setParameter("goods_code", code);
        query.setParameter("sku_id", skuId);
        List<GoodsSku> list = query.list();
        if (null != list && list.size() > 0) {
            return true;
        }

        StringBuilder approveSql = new StringBuilder("select s.* from t_scm_goods_approve g,t_scm_goods_sku_approve s where g.id=s.goods_id");
        approveSql.append(" and g.dealer_id =:dealer_id ");
        approveSql.append(" AND s.goods_code = :goods_code ");
        approveSql.append(" AND s.sku_id <> :sku_id ");
        Query approveQuery = this.session().createSQLQuery(approveSql.toString()).addEntity(GoodsSkuApprove.class);
        approveQuery.setParameter("dealer_id", dealerId);
        approveQuery.setParameter("goods_code", code);
        approveQuery.setParameter("sku_id", skuId);
        List<GoodsSkuApprove> approveList = approveQuery.list();
        if (null != approveList && approveList.size() > 0) {
            return true;
        }
        return false;
    }
}
