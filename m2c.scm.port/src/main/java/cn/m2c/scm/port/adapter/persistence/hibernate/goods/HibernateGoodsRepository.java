package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.NegativeException;
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
        List<Goods> list = query.list();
        return null != list && list.size() > 0;
    }

    @Override
    public void save(Goods goods) {
        this.session().save(goods);
    }

    @Override
    public Goods queryGoodsById(Integer goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where id =:id and del_status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("id", goodsId);
        return (Goods) query.uniqueResult();
    }

    @Override
    public boolean brandIsUser(String brandId) {
        List<Goods> list = queryGoodsByBrandId(brandId);
        return null != list && list.size() > 0;
    }

    @Override
    public List<Goods> queryGoodsByBrandId(String brandId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where goods_brand_id=:goods_brand_id AND del_status = 1 ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("goods_brand_id", brandId);
        return query.list();
    }

    @Override
    public List<Goods> queryGoodsByDealerId(String dealerId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where dealer_id=:dealer_id AND del_status = 1 ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("dealer_id", dealerId);
        return query.list();
    }

    @Override
    public boolean classifyIdIsUser(List<String> classifyIds) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where del_status = 1");
        sql.append(" and goods_classify_id in (" + Utils.listParseString(classifyIds) + ")");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        return null != query.list() && query.list().size() > 0;
    }

    @Override
    public boolean postageIdIsUser(String postageId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where del_status = 1");
        sql.append(" and goods_postage_id = :goods_postage_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("goods_postage_id", postageId);
        return null != query.list() && query.list().size() > 0;
    }

    @Override
    public List<Goods> queryGoodsByIdList(List goodsIds) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where ");
        sql.append(" goods_id in (" + Utils.listParseString(goodsIds) + ") ");
        sql.append(" and del_status = 1 ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        return query.list();
    }

    @Override
    public void saveGoodsSalesList(Integer month, String dealerId, String goodsId, String goodsName, Integer goodsNum) throws NegativeException {
        StringBuilder sql = new StringBuilder("select concurrency_version from t_scm_goods_sales_list where month =:month and goods_id = :goods_id");
        Query query = this.session().createSQLQuery(sql.toString());
        query.setParameter("month", month);
        query.setParameter("goods_id", goodsId);
        Integer vId = null == query.uniqueResult() ? null : Integer.parseInt(String.valueOf(query.uniqueResult()));
        if (null == vId) {//不存在则新增一条数据
            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO t_scm_goods_sales_list (month,dealer_id,goods_id,goods_name,goods_sale_num) ");
            insertSql.append("VALUES (:month, :dealer_id, :goods_id, :goods_name, :goods_sale_num) ");
            Query insert = this.session().createSQLQuery(insertSql.toString());
            insert.setParameter("month", month);
            insert.setParameter("dealer_id", dealerId);
            insert.setParameter("goods_id", goodsId);
            insert.setParameter("goods_name", goodsName);
            insert.setParameter("goods_sale_num", goodsNum);
            insert.executeUpdate();
        } else {// 已存在则更新
            StringBuilder updateSql = new StringBuilder();
            updateSql.append("UPDATE t_scm_goods_sales_list ");
            updateSql.append("SET goods_sale_num = goods_sale_num + :goods_sale_num, ");
            updateSql.append("concurrency_version = concurrency_version + 1 ");
            updateSql.append("WHERE 1=1 ");
            updateSql.append("AND month = :month ");
            updateSql.append("AND goods_id = :goods_id ");
            updateSql.append("AND concurrency_version = :vId ");
            Query update = this.session().createSQLQuery(updateSql.toString());
            update.setParameter("goods_sale_num", goodsNum);
            update.setParameter("month", month);
            update.setParameter("goods_id", goodsId);
            update.setParameter("vId", vId);
            int rows = update.executeUpdate();
            if (rows <= 0) {
                throw new NegativeException(MCode.V_400, "商品销售榜数据更新失败");
            }
        }
    }

    /**
     * 查询商家含有指定保障的商品
     */
    @Override
    public List<Goods> queryGoodsByDealerIdAndGuaranteeId(String dealerId, String guaranteeId) {
        StringBuilder sql = new StringBuilder(" SELECT * FROM t_scm_goods WHERE del_status = 1 AND dealer_id = :dealerId AND goods_guarantee LIKE :guaranteeId ");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("dealerId", dealerId);
        query.setParameter("guaranteeId", "%" + guaranteeId + "%");
        return query.list();
    }

    @Override
    public Goods queryGoodsByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where goods_id =:goods_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("goods_id", goodsId);
        return (Goods) query.uniqueResult();
    }
}
