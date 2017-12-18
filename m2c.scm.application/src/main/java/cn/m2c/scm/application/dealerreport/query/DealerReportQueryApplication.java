package cn.m2c.scm.application.dealerreport.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerreport.data.bean.DealerDayReportBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 商家首页报表
 */
@Repository
public class DealerReportQueryApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerReportQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public List<DealerDayReportBean> getDealerReportByTimeType(String dealerId, Integer timeType) {
        Date now = new Date();
        if (timeType == 2) { // 昨天
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            now = cal.getTime();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Integer day = Integer.parseInt(df.format(now));
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_dealer_day_report WHERE 1 = 1 AND day = ?");
        params.add(day);
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ?");
            params.add(dealerId);
        }
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), DealerDayReportBean.class, params.toArray());
    }

    public List<DealerDayReportBean> getDealerReportByDaySection(String dealerId, Integer startDay, Integer endDay) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_dealer_day_report WHERE 1 = 1 AND day >= ? AND day <= ?");
        params.add(startDay);
        params.add(endDay);
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ?");
            params.add(dealerId);
        }
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), DealerDayReportBean.class, params.toArray());
    }

    public List<DealerDayReportBean> getDealerReportByDay(String dealerId, Integer day) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_dealer_day_report WHERE 1 = 1 AND day = ?");
        params.add(day);
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ?");
            params.add(dealerId);
        }
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), DealerDayReportBean.class, params.toArray());
    }
}
