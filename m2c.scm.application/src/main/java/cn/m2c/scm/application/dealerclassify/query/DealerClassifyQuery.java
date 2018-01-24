package cn.m2c.scm.application.dealerclassify.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyBean;
import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyedBean;
import cn.m2c.scm.domain.NegativeException;

@Repository
public class DealerClassifyQuery {
	private final static Logger log = LoggerFactory.getLogger(DealerClassifyQuery.class);
	 @Resource
	 SupportJdbcTemplate supportJdbcTemplate;
	 
	 /**
	  * 查询所有的一级分类
	  * @return
	  * @throws NegativeException
	  */
	public List<DealerClassifyBean> getFirstClassifyList() throws NegativeException {
		List<DealerClassifyBean> firstClassifyList = null;
		String sql = "SELECT * FROM t_scm_dealer_classify WHERE 1=1 AND dealer_level=1";
		try {
			firstClassifyList =  this.supportJdbcTemplate.queryForBeanList(sql, DealerClassifyBean.class);
		} catch (Exception e) {
			log.error("查询一级分类出错",e);
			throw new NegativeException(500, "查询一级分类出错");
		}
		return firstClassifyList;
	}

	/**
	 * 查询一级分类下面的子分类
	 * @param dealerFirstClassifyId
	 * @return
	 * @throws NegativeException 
	 */
	public List<DealerClassifyBean> getSecondClassifyList(
			String dealerFirstClassifyId) throws NegativeException {
		List<DealerClassifyBean> secondClassifyList = null;
		String sql = "SELECT * FROM t_scm_dealer_classify WHERE 1=1 AND dealer_level=2 AND parent_classify_id=?";
		try {
			secondClassifyList =  this.supportJdbcTemplate.queryForBeanList(sql, DealerClassifyBean.class,dealerFirstClassifyId);
		} catch (Exception e) {
			log.error("查询一级分类出错",e);
			throw new NegativeException(500, "查询2级分类出错");
		}
		return secondClassifyList;
	}
	
	
	/**
	 * 查询商家所有二级分类
	 * @return
	 * @throws NegativeException
	 */
	public List<DealerClassifyedBean> getDealerClassify() throws NegativeException{
		List<DealerClassifyedBean> result = new ArrayList<DealerClassifyedBean>();
		StringBuilder sql = new StringBuilder();
		try {
			sql.append(" SELECT * FROM t_scm_dealer_classify WHERE dealer_level = 2 ORDER BY order_by ");
			result =  this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerClassifyedBean.class);
		} catch (Exception e) {
			throw new NegativeException(MCode.V_400,"查询商家所有二级分类失败出错");
		}
		
		return result;
	}
}
