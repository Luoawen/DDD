package cn.m2c.scm.application.brand.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.brand.data.bean.BrandBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 品牌查询
 */
@Service
public class BrandQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }


    public BrandBean queryBrand(String brandId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand WHERE 1 = 1");
        sql.append(" AND brand_id = ? AND brand_status = 1");
        return this.getSupportJdbcTemplate().queryForBean(sql.toString(), BrandBean.class, brandId);
    }

    public List<BrandBean> queryBrands(String dealerId, String brandName, String condition, String startTime,
                                       String endTime, Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand WHERE 1 = 1  AND brand_status = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" AND brand_name LIKE ? ");
            params.add("%" + brandName + "%");
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {
                sql.append(" AND brand_name LIKE ?");
                params.add("%" + condition + "%");
            } else{
                sql.append(" AND dealer_id = ? OR dealer_name LIKE ? OR brand_name LIKE ?");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ?");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        sql.append(" ORDER BY created_date DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), BrandBean.class, params.toArray());
    }

    public Integer queryBrandTotal(String dealerId, String brandName, String condition, String startTime, String endTime) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(*) ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand WHERE 1 = 1  AND brand_status = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" AND brand_name LIKE ? ");
            params.add("%" + brandName + "%");
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {
                sql.append(" AND brand_name LIKE ?");
                params.add("%" + condition + "%");
            } else{
                sql.append(" AND dealer_id = ? OR dealer_name LIKE ? OR brand_name LIKE ?");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ?");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public List<BrandBean> queryBrandByName(String brandName) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand WHERE 1 = 1  AND brand_status = 1");
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" AND brand_name LIKE ? ");
            params.add("%" + brandName + "%");
        }
        sql.append(" ORDER BY created_date DESC ");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), BrandBean.class, params.toArray());
    }
}
