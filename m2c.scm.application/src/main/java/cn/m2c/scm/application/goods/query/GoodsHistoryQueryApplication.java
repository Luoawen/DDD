package cn.m2c.scm.application.goods.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.goods.query.data.bean.GoodsHistoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品变更历史
 */
@Service
public class GoodsHistoryQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsHistoryQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public Integer queryGoodsHistoryTotal(String goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(1) ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_history where goods_id = ?");
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, goodsId);
    }

    public List<GoodsHistoryBean> queryGoodsHistory(String goodsId, Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_history where goods_id = ? order by create_time desc");
        sql.append(" LIMIT ?,?");
        params.add(goodsId);
        params.add(rows * (pageNum - 1));
        params.add(rows);
        List<GoodsHistoryBean> histories = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsHistoryBean.class, params.toArray());
        return histories;
    }
}
