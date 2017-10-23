package cn.m2c.scm.application.brand.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.brand.data.bean.BrandApproveBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 品牌审核查询
 */
@Service
public class BrandApproveQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandApproveQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }


    public BrandApproveBean queryBrandApprove(String approveId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand_approve WHERE 1 = 1");
        sql.append(" AND approve_id = ? AND status = 1");
        return this.getSupportJdbcTemplate().queryForBean(sql.toString(), BrandApproveBean.class, approveId);
    }

    public List<BrandApproveBean> queryBrandApproves(String dealerId, String brandName, String condition, String startTime,
                                                     String endTime, Integer pageNum, Integer rows, Integer approveStatus) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand_approve WHERE 1 = 1  AND status=1 ");
        if (null != approveStatus) {
            sql.append(" AND approve_status = ? ");
            params.add(approveStatus);
        }
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" AND brand_name = ? ");
            params.add(brandName);
        }
        if (StringUtils.isNotEmpty(condition)) {
            sql.append(" AND dealer_id = ? OR dealer_name LIKE ? OR brand_name LIKE ?");
            params.add(condition);
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
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
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), BrandApproveBean.class, params.toArray());
    }

    public Integer queryBrandApproveTotal(String dealerId, String brandName, String condition, String startTime, String endTime, Integer approveStatus) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(*) ");
        sql.append(" FROM ");
        sql.append(" t_scm_brand_approve WHERE 1 = 1 AND status=1 ");
        if (null != approveStatus) {
            sql.append(" AND approve_status = ? ");
            params.add(approveStatus);
        }
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        if (StringUtils.isNotEmpty(brandName)) {
            sql.append(" AND brand_name = ? ");
            params.add(brandName);
        }
        if (StringUtils.isNotEmpty(condition)) {
            sql.append(" AND dealer_id = ? OR dealer_name LIKE ? OR brand_name LIKE ?");
            params.add(condition);
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ?");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }
}
