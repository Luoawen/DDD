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

    /**
     * 搜索
     * 商家平台搜索框：可按照商品名称、商品编码（商家自己的内部编码）、条形码、品牌查找，都是模糊搜索
     * 商家管理平台搜索框：可按照商品名称、平台sku（平台自己生成的内部唯一编码）、商家编码（商家自己的内部编码）、条形码、商家名称、品牌查找，都是模糊搜索
     *
     * @param dealerId
     * @param goodsClassifyId
     * @param goodsStatus
     * @param condition
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param rows
     * @return
     */
    public List<GoodsBean> searchGoodsByCondition(String dealerId, String goodsClassifyId, Integer goodsStatus,
                                                  String condition, String startTime, String endTime,
                                                  Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" goods_id ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1");
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
      /*  if (StringUtils.isNotEmpty(brandId)) {
            sql.append(" AND goods_brand_id = ? ");
            params.add(brandId);
        }*/
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {//商家平台
                sql.append(" AND (goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR goods_brand_name LIKE ?)");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            } else {//商家管理平台
                sql.append(" AND (sku_id LIKE ? OR goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR dealer_name LIKE ? OR goods_brand_name LIKE ? )");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND goods_created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" AND del_status= 1 group by goods_id ORDER BY goods_created_date DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);

        List<GoodsBean> goodsBeanList = new ArrayList<>();
        List<Integer> ids = supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), params.toArray(), Integer.class);
        if (null != ids && ids.size() > 0) {
            for (Integer id : ids) {
                goodsBeanList.add(queryGoodsByGoodsId(id));
            }
        }
        return goodsBeanList;
    }

    public Integer searchGoodsByConditionTotal(String dealerId, String goodsClassifyId, Integer goodsStatus,
                                               String condition, String startTime, String endTime) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(distinct goods_id) ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1");
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
    /*    if (StringUtils.isNotEmpty(brandId)) {
            sql.append(" AND goods_brand_id = ? ");
            params.add(brandId);
        }*/
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {//商家平台
                sql.append(" AND (goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR goods_brand_name LIKE ?)");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            } else {//商家管理平台
                sql.append(" AND (sku_id LIKE ? OR goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR dealer_name LIKE ? OR goods_brand_name LIKE ? )");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND goods_created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" AND del_status= 1");

        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public List<GoodsSkuBean> queryGoodsSKUsByGoodsId(Integer goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1 AND goods_id = ?");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSkuBean.class, goodsId);
    }

    public GoodsBean queryGoodsByGoodsId(Integer goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods WHERE 1 = 1 AND id = ?");
        GoodsBean goodsBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsBean.class, goodsId);
        if (null != goodsBean) {
            goodsBean.setGoodsSkuBeans(queryGoodsSKUsByGoodsId(goodsId));
        }
        return goodsBean;
    }
}