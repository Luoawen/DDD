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

    /**
     * 原通过List保障id查询商品保障
     * @param ids
     * @return
     */
    public List<GoodsGuaranteeBean> queryGoodsGuaranteeByIds(List<String> ids) {
        if (null != ids && ids.size() > 0) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT ");
            sql.append(" * ");
            sql.append(" FROM ");
            sql.append(" t_scm_goods_guarantee WHERE 1 = 1 AND guarantee_status = 1");
            sql.append(" AND guarantee_id in (" + Utils.listParseString(ids) + ") ");
            return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsGuaranteeBean.class);
        }
        return null;
    }

    /**
     * 原查询商品保障(查询所有系统默认)
     * @return
     */
    public List<GoodsGuaranteeBean> queryGoodsGuarantee() {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_guarantee WHERE 1 = 1 AND is_default = 1 AND guarantee_status = 1 ");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsGuaranteeBean.class);
    }
}
