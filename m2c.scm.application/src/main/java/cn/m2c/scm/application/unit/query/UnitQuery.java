package cn.m2c.scm.application.unit.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.unit.bean.UnitBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UnitQuery {
    private final static Logger log = LoggerFactory.getLogger(UnitQuery.class);

    @Resource
    SupportJdbcTemplate supportJdbcTemplate;

    /**
     * 查询计量单位List
     *
     * @param pageNum
     * @param rows
     * @return
     */
    public List<UnitBean> getUnitList(Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT");
        sql.append(" * ");
        sql.append(" FROM t_scm_unit u where 1 = 1 and unit_status = 1");
        sql.append(" ORDER BY created_date DESC ");
        if (pageNum != null && rows != null) {
			sql.append(" LIMIT ?,?");
			params.add(rows * (pageNum - 1));
			params.add(rows);
		}
        List<UnitBean> unitList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), UnitBean.class, params.toArray());
        return unitList;
    }

    /**
     * 查询计量单位总数
     *
     * @return
     */
    public Integer queryUnitTotal() {
        StringBuilder sql = new StringBuilder("Select COUNT(*) from t_scm_unit where 1 = 1 AND unit_status = 1");
        return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class);
    }


    public UnitBean getUnitByUnitId(String unitId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_unit WHERE unit_status = 1 AND unit_id = ?");
        UnitBean unitBean = this.supportJdbcTemplate.queryForBean(sql.toString(), UnitBean.class, unitId);
        return unitBean;
    }

    public String getUnitNameByUnitId(String unitId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_unit WHERE unit_status = 1 AND unit_id = ?");
        UnitBean unitBean = this.supportJdbcTemplate.queryForBean(sql.toString(), UnitBean.class, unitId);
        return null != unitBean ? unitBean.getUnitName() : null;
    }
}
