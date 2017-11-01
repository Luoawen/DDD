package cn.m2c.scm.application.goods.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSpecValueBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格值
 */
@Service
public class GoodsSpecValueQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecValueQueryApplication.class);
    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public List<GoodsSpecValueBean> queryGoodsSpecValueByName(String dealerId, String specValue) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_spec_value WHERE 1 = 1");
        sql.append(" AND dealer_id = ?");
        params.add(dealerId);
        if (StringUtils.isNotEmpty(specValue)) {
            sql.append(" AND spec_value like ?");
            params.add("%" + specValue + "%");
        }

        List<GoodsSpecValueBean> goodsClassifyBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSpecValueBean.class, params.toArray());
        return goodsClassifyBeans;
    }
}
