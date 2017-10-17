package cn.m2c.scm.application.standstard.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.standstard.bean.StantardBean;
import cn.m2c.scm.domain.model.stantard.Stantard;

@Repository
public class StantardQuery {
	@Resource
	SupportJdbcTemplate supportJdbcTemplate;
	
	/**
	 * 查询规格总数
	 * 
	 * @return
	 */
	public Integer queryStantardTotal() {
		StringBuilder sql = new StringBuilder("Select COUNT(*) from t_scm_stantard where 1 = 1 AND stantard_status = 1");
		return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class);
	}
	
	
	/**
	 * 查询规格List
	 * 
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<StantardBean> getStantardList(Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT");
		sql.append(" * ");
		sql.append(" FROM t_scm_stantard where 1 = 1 and stantard_status = 1");
		sql.append(" LIMIT ?,?");
		params.add(rows * (pageNum - 1));
		params.add(rows);
		List<StantardBean> stantardList= this.supportJdbcTemplate.queryForBeanList(sql.toString(), StantardBean.class, params.toArray());
		return stantardList;
	}
	
	
	/**
	 * stantardId获取规格
	 * @param stantardId
	 * @return
	 */
	public StantardBean getStantardByStantardId(String stantardId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_stantard WHERE stantard_status = 1 AND stantard_id = ?");
		params.add(stantardId);
		StantardBean stantard = this.supportJdbcTemplate.queryForBean(sql.toString(), StantardBean.class,stantardId);
		return stantard;
	}

}
