package cn.m2c.scm.application.dealer.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.data.bean.DealerClassifyNameBean;
import cn.m2c.scm.domain.NegativeException;

@Repository
public class DealerQuery {
	private final static Logger log = LoggerFactory.getLogger(DealerQuery.class);
	 @Resource
	  SupportJdbcTemplate supportJdbcTemplate;
	 

	public List<DealerBean> getDealerList(String dealerClassify,
			Integer cooperationMode, Integer countMode, Integer isPayDeposit,
			String dealerName, String dealerId, String userPhone,
			String sellerPhone, String startTime, String endTime,
			Integer pageNum, Integer rows) throws NegativeException {
		List<DealerBean> dealerList = null;
		try {
			 StringBuilder sql = new StringBuilder();
			 List<Object> params = new ArrayList<Object>();
	            sql.append(" SELECT ");
	            sql.append(" * ");
	            sql.append(" FROM ");
	            sql.append(" t_scm_dealer sd ");
	            sql.append(" WHERE ");
	            sql.append(" dealer_status = 1 ");
	            if(dealerClassify!=null && !"".equals(dealerClassify)){
	            	sql.append(" AND sd.dealer_classify LIKE concat('%', ?,'%') ");
	            	params.add(dealerClassify);
	            }
	            if(cooperationMode!=null){
	            	sql.append(" AND sd.cooperation_mode = ? ");
	            	params.add(cooperationMode);
	            }
	            if(countMode!=null){
	            	sql.append(" AND sd.count_mode = ? ");
	            	params.add(countMode);
	            }
	            if(isPayDeposit!=null){
	            	sql.append(" AND sd.is_pay_deposit = ? ");
	            	params.add(isPayDeposit);
	            }
	            if(dealerName!=null && !"".equals(dealerName)){
	            	sql.append(" AND sd.dealer_name LIKE concat('%', ?,'%')  ");
	            	params.add(dealerName);
	            }
	            if(dealerId!=null && !"".equals(dealerId)){
	            	sql.append(" AND sd.dealer_id LIKE concat('%', ?,'%')  ");
	            	params.add(dealerId);
	            }
	            if(userPhone!=null && !"".equals(userPhone)){
	            	sql.append(" AND sd.user_phone LIKE concat('%', ?,'%')  ");
	            	params.add(userPhone);
	            }
	            if(sellerPhone!=null && !"".equals(sellerPhone)){
	            	sql.append(" AND sd.seller_phone LIKE concat('%', ?,'%')  ");
	            	params.add(sellerPhone);
	            }
	        	if(startTime !=null&&!"".equals(startTime)){
					
					sql.append(" AND sd.created_date>=?");
					params.add(startTime);
				}
				if(endTime!=null&&!"".equals(endTime)){
					
					sql.append(" AND sd.created_date<=?");
					params.add(endTime+" 23:59:59");
				}
				sql.append(" ORDER BY sd.created_date DESC ");
				sql.append(" LIMIT ?,?");
				params.add(rows*(pageNum - 1));
				params.add(rows);
				System.out.println("----查询经销商列表："+sql.toString());
				dealerList =  this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerBean.class, params.toArray());
				//-----------处理bean
				if(dealerList!=null && dealerList.size()>0){
					for (DealerBean dealer : dealerList) {
						dealer.setDealerClassifyBean(getDealerClassify(dealer.getDealerClassify()));
					}
				}
		} catch (Exception e) {
			log.error("查询经销商列表出错",e);
			throw new NegativeException(500, "经销商查询出错");
		}
		return dealerList;
	}


	/**
	 * 查询经销商分类信息
	 * @param dealerClassify
	 * @return
	 */
	private DealerClassifyNameBean getDealerClassify(String dealerClassify) {
		String sql = "select secondc.dealerSecondClassifyName dealerSecondClassifyName,secondc.parentClassifyId dealerClassifyId,firstc.dealer_classify_name dealerFirstClassifyName from"
				+"(SELECT dealer_classify_id dealerClassifyId,dealer_classify_name dealerSecondClassifyName,parent_classify_id parentClassifyId FROM t_scm_dealer_classify WHERE 1 = 1 AND dealer_classify_id =?)"
				+" secondc,t_scm_dealer_classify firstc where firstc.dealer_classify_id=secondc.parentClassifyId";
		System.out.println("------------"+sql);
		DealerClassifyNameBean bean = this.supportJdbcTemplate.queryForBean(sql, DealerClassifyNameBean.class,dealerClassify);
		return bean;
	}


	public Integer getDealerCount(String dealerClassify,
			Integer cooperationMode, Integer countMode, Integer isPayDeposit,
			String dealerName, String dealerId, String userPhone,
			String sellerPhone, String startTime, String endTime,
			Integer pageNum, Integer rows) throws NegativeException {
		Integer result = 0;
		try {
			 StringBuilder sql = new StringBuilder();
			 List<Object> params = new ArrayList<Object>();
	            sql.append(" SELECT ");
	            sql.append(" COUNT(*) ");
	            sql.append(" FROM ");
	            sql.append(" t_scm_dealer sd ");
	            sql.append(" WHERE ");
	            sql.append(" dealer_status = 1 ");
	            if(dealerClassify!=null && !"".equals(dealerClassify)){
	            	sql.append(" AND sd.dealer_classify LIKE concat('%', ?,'%') ");
	            	params.add(dealerClassify);
	            }
	            if(cooperationMode!=null){
	            	sql.append(" AND sd.cooperation_mode = ? ");
	            	params.add(cooperationMode);
	            }
	            if(countMode!=null){
	            	sql.append(" AND sd.count_mode = ? ");
	            	params.add(countMode);
	            }
	            if(isPayDeposit!=null){
	            	sql.append(" AND sd.is_pay_deposit = ? ");
	            	params.add(isPayDeposit);
	            }
	            if(dealerName!=null && !"".equals(dealerName)){
	            	sql.append(" AND sd.dealer_name LIKE concat('%', ?,'%')  ");
	            	params.add(dealerName);
	            }
	            if(dealerId!=null && !"".equals(dealerId)){
	            	sql.append(" AND sd.dealer_id LIKE concat('%', ?,'%')  ");
	            	params.add(dealerId);
	            }
	            if(userPhone!=null && !"".equals(userPhone)){
	            	sql.append(" AND sd.user_phone LIKE concat('%', ?,'%')  ");
	            	params.add(userPhone);
	            }
	            if(sellerPhone!=null && !"".equals(sellerPhone)){
	            	sql.append(" AND sd.seller_phone LIKE concat('%', ?,'%')  ");
	            	params.add(sellerPhone);
	            }
	        	if(startTime !=null&&!"".equals(startTime)){
					
					sql.append(" AND sd.created_date>=?");
					params.add(startTime);
				}
				if(endTime!=null&&!"".equals(endTime)){
					
					sql.append(" AND sd.created_date<=?");
					params.add(endTime+" 23:59:59");
				}
				System.out.println("----查询经销商总数："+sql.toString());
				result =  this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());
				//-------------------循环bean获取商家分类
				
		} catch (Exception e) {
			log.error("查询经销商总数出错",e);
			throw new NegativeException(500, "经销商查询总数出错");
		}
		return result;
	}


	/**
	 * 根据id查询商家详情
	 * @param dealerId
	 * @return
	 * @throws NegativeException 
	 */
	public DealerBean getDealer(String dealerId) throws NegativeException {
		DealerBean bean = null;
		try {
			
			String sql =  " SELECT  *  FROM  t_scm_dealer sd  WHERE  dealer_status = 1 and dealer_id=?";
			bean = this.supportJdbcTemplate.queryForBean(sql, DealerBean.class,dealerId);
			if(bean.getDealerClassify()!=null && !"".equals(bean.getDealerClassify())){
				bean.setDealerClassifyBean(getDealerClassify(bean.getDealerClassify()));
			}
		} catch (Exception e) {
			log.error("查询经销商详情出错",e);
			throw new NegativeException(500, "经销商查询不存在");
		}
		return bean;
	}
	/**
	 * 根据id查询商家结算方式
	 * @param dealerId
	 * @return -1 表示失败    1：按供货价 2：按服务费率'
	 * @throws NegativeException 
	 */
	public Integer getDealerCountMode(String dealerId) throws NegativeException{
		Integer defaultValue = -1;
		DealerBean bean = null;
		try {
			String sql =  " SELECT  *  FROM  t_scm_dealer sd  WHERE  dealer_status = 1 and dealer_id=?";
			bean = this.supportJdbcTemplate.queryForBean(sql, DealerBean.class,dealerId);
		} catch (Exception e) {
			log.error("查询经销商详情出错",e);
			throw new NegativeException(500, "经销商查询不存在");
		}
		return bean==null?defaultValue:bean.getCountMode();
	}

	/**
	 * 根据多个dealerid获取经销商列表
	 * @param dealerIds
	 * @return
	 * @throws NegativeException 
	 */
	public List<DealerBean> getDealers(String dealerIds) throws NegativeException {
		List<DealerBean> result = null;
		try {
			StringBuffer sql = new StringBuffer( " SELECT  *  FROM  t_scm_dealer sd  WHERE  dealer_status = 1 and dealer_id in (");
			String[] dealer = dealerIds.split(",");
			for (int i = 0; i < dealer.length; i++) {
				if(i==(dealer.length-1)){
					sql.append("'");
					sql.append(dealer[i]);
					sql.append("'");
				}else{
					sql.append("'");
					sql.append(dealer[i]);
					sql.append("'");
					sql.append(",");
				}
			}
			sql.append(")");
			System.out.println("sql==="+sql.toString());
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerBean.class);
		} catch (Exception e) {
			log.error("查询经销商列表出错",e);
			throw new NegativeException(500, "经销商查询列表出错");
		}
		return result;
	}
	
	/**
	 * 业务员Id获取经销商列表
	 * @param sellerId
	 * @return
	 * @throws NegativeException
	 */
	public List<DealerBean> getDealerBySellerId(String sellerId) throws NegativeException {
		StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer WHERE dealer_status = 1 AND seller_id = ?");
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerBean.class,sellerId);
		
	}

}
