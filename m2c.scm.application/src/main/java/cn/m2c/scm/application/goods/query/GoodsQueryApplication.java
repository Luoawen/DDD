package cn.m2c.scm.application.goods.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品查询
 */
@Service
public class GoodsQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;

    public List<GoodsBean> searchGoodsByCondition(String dealerId, String goodsClassifyId, Integer goodsStatus,
                                                  String brandId, String condition, String startTime, String endTime,
                                                  Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods WHERE 1 = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        // 根据商品分类id,找到所有下级分类ID
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND goods_classify_id in (?) ");
            params.add(Utils.listParseString(goodsClassifyIds));
        }
        if (null != goodsStatus) {
            sql.append(" AND goods_status = ? ");
            params.add(goodsStatus);
        }
        if (StringUtils.isNotEmpty(brandId)) {
            sql.append(" AND goods_brand_id = ? ");
            params.add(brandId);
        }
        if (StringUtils.isNotEmpty(condition)) {
            List<Integer> ids = queryGoodsIdByCondition(dealerId, condition, startTime, endTime);
            if (null != ids) {
                sql.append(" AND (id IN (?) OR goods_name = ? OR goods_bar_code LIKE ?)");
                params.add(Utils.listParseString(ids));
            } else {
                sql.append(" AND (goods_name = ? OR goods_bar_code LIKE ?)");
            }
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" ORDER BY created_date DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);

        List<GoodsBean> goodsBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsBean.class, params.toArray());
        if (null != goodsBeans && goodsBeans.size() > 0) {
            for (GoodsBean bean : goodsBeans) {
                bean.setGoodsSkuBeans(queryGoodsSKUsByGoodsId(bean.getId()));
            }
        }
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsBean.class, params.toArray());
    }

    public Integer searchGoodsByConditionTotal(String dealerId, String goodsClassifyId, Integer goodsStatus,
                                                  String brandId, String condition, String startTime, String endTime) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods WHERE 1 = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        // 根据商品分类id,找到所有下级分类ID
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND goods_classify_id in (?) ");
            params.add(Utils.listParseString(goodsClassifyIds));
        }
        if (null != goodsStatus) {
            sql.append(" AND goods_status = ? ");
            params.add(goodsStatus);
        }
        if (StringUtils.isNotEmpty(brandId)) {
            sql.append(" AND goods_brand_id = ? ");
            params.add(brandId);
        }
        if (StringUtils.isNotEmpty(condition)) {
            List<Integer> ids = queryGoodsIdByCondition(dealerId, condition, startTime, endTime);
            if (null != ids) {
                sql.append(" AND (id IN (?) OR goods_name = ? OR goods_bar_code LIKE ?)");
                params.add(Utils.listParseString(ids));
            } else {
                sql.append(" AND (goods_name = ? OR goods_bar_code LIKE ?)");
            }
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" ORDER BY created_date DESC ");

        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public List<Integer> queryGoodsIdByCondition(String dealerId, String condition, String startTime, String endTime) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" goods_id ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1 ");
        if (StringUtils.isNotEmpty(dealerId)) { //商家平台
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
            sql.append(" AND goods_code = ?");
            params.add("%" + condition + "%");
        } else {//商家管理平台
            sql.append(" AND (goods_code = ? or sku_id = ?)");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), Integer.class, params.toArray());
    }

    public List<GoodsSkuBean> queryGoodsSKUsByGoodsId(Integer goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" goods_id ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1 AND goods_id = ?");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSkuBean.class, goodsId);
    }
}
