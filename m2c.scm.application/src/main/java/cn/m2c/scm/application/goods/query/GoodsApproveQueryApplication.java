package cn.m2c.scm.application.goods.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsApproveBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuApproveBean;
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
public class GoodsApproveQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsApproveQueryApplication.class);

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
    public List<GoodsApproveBean> searchGoodsApproveByCondition(String dealerId, String goodsClassifyId, Integer approveStatus,
                                                                String condition, String startTime, String endTime,
                                                                Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select g.* from t_scm_goods_approve g,t_scm_goods_sku_approve s where g.id=s.goods_id");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND g.dealer_id = ? ");
            params.add(dealerId);
        }
        // 根据商品分类id,找到所有下级分类ID
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND g.goods_classify_id in (" + Utils.listParseString(goodsClassifyIds) + ") ");
        }
        if (null != approveStatus) {
            sql.append(" AND g.approve_status = ? ");
            params.add(approveStatus);
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {//商家平台
                sql.append(" AND (g.goods_name LIKE ? OR g.goods_bar_code LIKE ? OR s.goods_code LIKE ? OR g.goods_brand_name LIKE ?)");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            } else {//商家管理平台
                sql.append(" AND (s.sku_id LIKE ? OR g.goods_name LIKE ? OR g.goods_bar_code LIKE ? OR s.goods_code LIKE ? OR g.dealer_name LIKE ? OR g.goods_brand_name LIKE ? )");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND g.created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" AND g.del_status= 1 group by goods_id ORDER BY created_date desc ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);

        List<GoodsApproveBean> goodsBeanList = supportJdbcTemplate.queryForBeanList(sql.toString(), GoodsApproveBean.class, params.toArray());
        if (null != goodsBeanList && goodsBeanList.size() > 0) {
            for (GoodsApproveBean bean : goodsBeanList) {
                bean.setGoodsSkuApproves(queryGoodsSkuApproveByGoodsId(bean.getId()));
            }
        }
        return goodsBeanList;
    }

    public Integer searchGoodsApproveByConditionTotal(String dealerId, String goodsClassifyId, Integer approveStatus,
                                                      String condition, String startTime, String endTime) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(distinct g.goods_id) from t_scm_goods_approve g,t_scm_goods_sku_approve s where g.id=s.goods_id");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND g.dealer_id = ? ");
            params.add(dealerId);
        }
        // 根据商品分类id,找到所有下级分类ID
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND g.goods_classify_id in (" + Utils.listParseString(goodsClassifyIds) + ") ");
        }
        if (null != approveStatus) {
            sql.append(" AND g.approve_status = ? ");
            params.add(approveStatus);
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {//商家平台
                sql.append(" AND (g.goods_name LIKE ? OR g.goods_bar_code LIKE ? OR s.goods_code LIKE ? OR g.goods_brand_name LIKE ?)");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            } else {//商家管理平台
                sql.append(" AND (s.sku_id LIKE ? OR g.goods_name LIKE ? OR g.goods_bar_code LIKE ? OR s.goods_code LIKE ? OR g.dealer_name LIKE ? OR g.goods_brand_name LIKE ? )");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND g.created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" AND g.del_status= 1");
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public List<GoodsSkuApproveBean> queryGoodsSkuApproveByGoodsId(Integer goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku_approve WHERE 1 = 1 AND goods_id = ?");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSkuApproveBean.class, goodsId);
    }


    public GoodsApproveBean queryGoodsApproveByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_approve WHERE 1 = 1 AND goods_id = ?");
        GoodsApproveBean goodsBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsApproveBean.class, goodsId);
        if (null != goodsBean) {
            goodsBean.setGoodsSkuApproves(queryGoodsSkuApproveByGoodsId(goodsBean.getId()));
        }
        return goodsBean;
    }

}
