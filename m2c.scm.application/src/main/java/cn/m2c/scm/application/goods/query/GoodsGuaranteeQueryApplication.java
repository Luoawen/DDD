package cn.m2c.scm.application.goods.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品保障
 */
@Service
public class GoodsGuaranteeQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGuaranteeQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public List<GoodsGuaranteeBean> queryGoodsGuaranteeByIds(List<String> ids) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_guarantee WHERE 1 = 1");
        sql.append(" AND guarantee_id in (" + Utils.listParseString(ids) + ") ");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsGuaranteeBean.class);
    }

    public List<GoodsGuaranteeBean> queryGoodsGuarantee() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_guarantee WHERE 1 = 1");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsGuaranteeBean.class);
    }
}
