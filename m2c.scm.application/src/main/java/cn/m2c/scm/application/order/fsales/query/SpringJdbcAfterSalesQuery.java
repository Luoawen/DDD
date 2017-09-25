package cn.m2c.scm.application.order.fsales.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.fsales.FsalesStatus;
import cn.m2c.scm.domain.model.order.fsales.HandleStatus;

/**
 * 
 * @ClassName: SpringJdbcAfterSalesQuery
 * @Description: 售后服务查询
 * @author moyj
 * @date 2017年7月7日 下午7:31:08
 *
 */
@Repository
public class SpringJdbcAfterSalesQuery {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 售后详细
	 * @param commentId			//评论编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> findT(String orderId) throws NegativeException {
		try{
			String field = "s.fsales_id AS fsalesId,"
						+ " s.order_id AS orderId,"
						+ " s.apply_reason AS applyReason,"
						+ " s.fsales_status AS fsalesStatus,"
						+ " s.apply_time AS applyTime,"
						+ " s.barter_time AS barterTime,"
						+ " s.audit_time AS auditTime,"
						+ " s.finished_time AS finishedTime,"
						+ " s.refund_amount AS refundAmount,"
						+ " s.unaudit_reason AS unauditReason,"
						+ " s.refbared_reason AS refbaredReason,"
						+ " s.handle_status As handleStatus";
			
			String sql = "SELECT " + field + " FROM t_order_after_sales s WHERE s.order_id = ? AND s.handle_status = " + HandleStatus.HANDLEING.getId();
			List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql,new Object[]{orderId});	
			if(resultList == null || resultList.size() == 0){
				return null;
			}
			Map<String,Object> vo = resultList.get(0);
			return vo;

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 列表(退换货管理)
	 * @param orderId		//订单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待审核  2退换中  3退换完成
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//申请开始时间
	 * @param endTime		//申请结束时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> list(
			String dealerId,
			String orderId,
			String orderNo,
			String keyword,
			Integer statusFlag,
			Integer payWay,
			Long startTime,
			Long endTime,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
			String field = " s.fsales_id AS fsalesId,"
						 + " s.order_id AS orderId,"
						 + " s.apply_time AS applyTime,"
						 + " s.fsales_status AS fsalesStatus,"
						 + " s.refund_amount AS refundAmount,"
						 + " s.apply_reason AS applyReason,"
						 + " o.order_no AS orderNo,"
						 + " o.goods_name AS goodsName,"
						 + " o.pay_way AS payWay,"
					     + " o.pay_price AS payPrice";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_after_sales s,t_order_order o ")
					.append(" where 1=1 AND s.order_id = o.order_id ")
					.append(" AND o.dealer_id = ?");
			List<Object> params = new ArrayList<Object>();	
			params.add(dealerId);
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND s.order_id = ?");
				params.add(orderId);
			}	
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderNo);
			}	
			if(keyword != null && keyword.length() > 0){
				sql.append(" AND s.apply_reason like ?");
				params.add("%" + keyword +"%");
			}		
			if(statusFlag != null){
				//1待审核  2退换中  3退换完成
				if(statusFlag == 1){
					sql.append(" AND s.fsales_status = ").append(FsalesStatus.APPLY.getId());
				}else if(statusFlag == 2){
					sql.append(" AND s.fsales_status in ( ")
					.append(FsalesStatus.APPLY_PASS.getId()).append(",")
					.append(FsalesStatus.BARTERING.getId()).append(")");
				}else if(statusFlag == 3){
					sql.append(" AND s.fsales_status in ( ")
					.append(FsalesStatus.APPLY_UNPASS.getId()).append(",")
					.append(FsalesStatus.BACKED_GOODS.getId()).append(",")
					.append(FsalesStatus.BARTERED.getId()).append(")");
				}
			}	
			if(payWay != null){
				sql.append(" AND o.pay_way = ?");
				params.add(payWay);
			}
		
			if(startTime != null && endTime != null){
				sql.append(" AND (o.commit_time >= ? AND o.commit_time < ?)");
				params.add(startTime);
				params.add(endTime);
			}
			else if(startTime != null && endTime == null){
				sql.append(" AND o.commit_time >= ?");
				params.add(startTime);
			}	
			else if(endTime != null && startTime == null){
				sql.append(" AND o.commit_time < ?");
				params.add(endTime);
			}	
			sql.append(" ORDER BY o.commit_time DESC");
			sql.append(" limit ?,?");	
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			
			List<Map<String,Object>> resultList = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Integer trailStatus = 3;
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						if(key.equals("fsalesStatus")){
							trailStatus = (Integer)obj; 
							mapOfColValues.put(key, obj);
						}else{
							mapOfColValues.put(key, obj);
						}
						
					}
					//1待审核  2退换中  3退换完成
					if(trailStatus != null ){
						if(trailStatus == FsalesStatus.APPLY.getId()){	
							trailStatus = 1;
						}else if(trailStatus == FsalesStatus.APPLY_PASS.getId() || trailStatus == FsalesStatus.BARTERING.getId()){	
							trailStatus = 2;
						}else if(trailStatus.intValue() == FsalesStatus.BACKED_GOODS.getId()
								||trailStatus.intValue() == FsalesStatus.APPLY_UNPASS.getId()
								||trailStatus.intValue() == FsalesStatus.BARTERED.getId()){
	 						trailStatus = 3;
						}
					}		
					mapOfColValues.put("trailStatus", trailStatus);	
					return mapOfColValues;
				}
				
			});		
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 列表数(退换货管理)
	 * @param orderId		//订单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待审核  2退换中  3退换完成
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//申请开始时间
	 * @param endTime		//申请结束时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCount(
			String dealerId,
			String orderId,
			String orderNo,
			String keyword,
			Integer statusFlag,
			Integer payWay,
			Long startTime,
			Long endTime) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1) " )
					.append(" FROM t_order_after_sales s,t_order_order o ")
					.append(" where 1=1 AND s.order_id = o.order_id ")
					.append(" AND o.dealer_id = ?");
			List<Object> params = new ArrayList<Object>();	
			params.add(dealerId);
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND s.order_id = ?");
				params.add(orderId);
			}	
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderId);
			}	
			if(keyword != null && keyword.length() > 0){
				sql.append(" AND s.apply_reason like ?");
				params.add("%" + keyword +"%");
			}		
			if(statusFlag != null){
				//1待审核  2退换中  3退换完成
				if(statusFlag == 1){
					sql.append(" AND s.fsales_status = ").append(FsalesStatus.APPLY.getId());
				}else if(statusFlag == 2){
					sql.append(" AND s.fsales_status in ( ")
					.append(FsalesStatus.APPLY_PASS.getId()).append(",")
					.append(FsalesStatus.BARTERING.getId()).append(")");
				}else if(statusFlag == 3){
					sql.append(" AND s.fsales_status in ( ")
					.append(FsalesStatus.APPLY_UNPASS.getId()).append(",")
					.append(FsalesStatus.BACKED_GOODS.getId()).append(",")
					.append(FsalesStatus.BARTERED.getId()).append(")");
				}
			}	
			if(payWay != null){
				sql.append(" AND o.pay_way = ?");
				params.add(payWay);
			}
		
			if(startTime != null && endTime != null){
				sql.append(" AND (o.commit_time >= ? AND o.commit_time < ?)");
				params.add(startTime);
				params.add(endTime);
			}
			else if(startTime != null && endTime == null){
				sql.append(" AND o.commit_time >= ?");
				params.add(startTime);
			}	
			else if(endTime != null && startTime == null){
				sql.append(" AND o.commit_time < ?");
				params.add(endTime);
			}	
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	
	}
	
	/**
	 * 订单详细(退换货管理)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> detail(String fsalesId) throws NegativeException {

		try{
			String field =  " s.fsales_id AS fsalesId,"
					    +   " s.order_id AS orderId,"
					    +   " o.order_no AS orderNo,"
					  	+	" s.apply_time AS applyTime,"
					  	+	" s.audit_time AS auditTime,"
					  	+	" s.finished_time AS finishedTime,"
					  	+	" s.apply_reason AS applyReason,"
						+	" s.unaudit_reason AS unauditReason,"
						+	" s.refbared_reason AS refbaredReason,"
						+	" s.fsales_status AS fsalesStatus, "
					  	+	" o.pay_end_time AS payEndTime,"
						+	" o.pay_way AS payWay,"
						+	" o.pay_price AS payPrice,"
						+	" o.logistics_status AS logisticsStatus,"
						+	" o.receiver_id AS receiverId,"
						+	" o.receiver_name AS receiverName,"
						+   " o.province_code AS provinceCode,"
						+   " o.province_name AS provinceName,"
						+   " o.city_code AS cityCode,"
						+   " o.city_name AS cityName,"
						+   " o.area_code AS areaCode,"
						+   " o.area_name AS areaName,"
						+	" o.receiver_addr AS receiverAddr,"
						+	" o.receiver_phone AS receiverPhone,"
						+	" o.receiver_zip_code AS receiverZipCode,"
						+	" o.buyer_id AS	buyerId,"
						+	" o.buyer_name AS	buyerName,"
						+	" o.buyer_phone AS buyerPhone,"
						+	" o.goods_no AS goodsNo,"
						+	" o.goods_name AS goodsName,"
						+	" o.property_desc AS propertyDesc,"
						+	" o.goods_num AS goodsNum,"
						+	" o.goods_unit_price * o.goods_num AS totalPrice,"
						+	" o.commit_wayb_time AS commitWaybTime,"
						+	" o.receiver_time AS receiverTime";
					
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_after_sales s,t_order_order o  where s.order_id = o.order_id")
					.append(" AND s.fsales_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(fsalesId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Integer trailStatus = 3;
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						if(key.equals("fsalesStatus")){
							trailStatus = (Integer)obj; 
						}
						mapOfColValues.put(key, obj);

					}
					//1待审核  2退换中  3退换完成
					if(trailStatus != null ){
						if(trailStatus == FsalesStatus.APPLY.getId()){	
							trailStatus = 1;
						}else if(trailStatus == FsalesStatus.APPLY_PASS.getId() || trailStatus == FsalesStatus.BARTERING.getId()){	
							trailStatus = 2;
						}else if(trailStatus.intValue() == FsalesStatus.BACKED_GOODS.getId()
								||trailStatus.intValue() == FsalesStatus.APPLY_UNPASS.getId()
								||trailStatus.intValue() == FsalesStatus.BARTERED.getId()){
	 						trailStatus = 3;
						}
					}
					mapOfColValues.put("trailStatus", trailStatus);	
					return mapOfColValues;
				}				
			});	
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 退换货进度跟踪
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> progressTracking(String orderId) throws NegativeException {

		try{
			String field =  " s.unaudit_reason AS unauditReason,"
						+	" s.refbared_reason AS refbaredReason,"
						+	" s.fsales_status AS fsalesStatus, "
						+	" s.apply_time AS applyTime,"
						+	" s.audit_time AS auditTime,"
						+	" s.order_id AS orderId,"
						+	" o.order_no AS orderNo,"
						+	" s.barter_time AS barterTime,"
						+	" s.finished_time AS finishedTime,"
						+	" s.fsales_status AS fsalesStatus ";
				
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_after_sales s,t_order_order o  where s.order_id = o.order_id")
					.append(" AND s.order_id = ? ");
					//.append(" AND s.handle_status = ").append(HandleStatus.HANDLED.getId());
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap =  null;
			List<Map<String,Object>> resultList = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);					
						mapOfColValues.put(key, obj);
					}
					return mapOfColValues;
				}				
			});	
			//退款的单据
			if(resultList == null || resultList.size() == 0){
				StringBuffer reSql = new StringBuffer()
						.append("SELECT " + field)
						.append(" FROM t_order_after_sales s,t_order_order o  where s.order_id = o.order_id")
						.append(" AND s.order_id = ? ")
						.append(" AND s.handle_status = ").append(HandleStatus.HANDLED.getId())
						.append(" AND s.fsales_status = ").append(FsalesStatus.BACKED_GOODS.getId());
				List<Object> reParams = new ArrayList<Object>();
				reParams.add(orderId);
				resultList = jdbcTemplate.query(reSql.toString(), reParams.toArray(),new RowMapper<Map<String,Object>>(){
					@Override
					public Map<String,Object> mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						ResultSetMetaData rsmd = rs.getMetaData();
						int columnCount = rsmd.getColumnCount();	
						Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
						for (int i = 1; i <= columnCount; i++) {
							String key = JdbcUtils.lookupColumnName(rsmd, i);
							Object obj = JdbcUtils.getResultSetValue(rs, i);					
							mapOfColValues.put(key, obj);
						}
						return mapOfColValues;
					}				
				});	
			}
			if(resultList != null && resultList.size() > 0){
				resultMap = resultList.get(0);
			}
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
}
