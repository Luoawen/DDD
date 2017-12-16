package cn.m2c.scm.application.goods.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSalesListBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品销量排行榜
 */
@Service
public class GoodsSalesListApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSalesListApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public List<GoodsSalesListBean> queryGoodsSalesListTop5(Integer month, String dealerId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sales_list WHERE 1 = 1 AND month = ? AND dealer_id = ?");
        sql.append(" AND goods_sale_num > 0 order by goods_sale_num desc limit 0,5");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSalesListBean.class, month, dealerId);
    }
}
