package cn.m2c.scm.application.order.order.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.AfterSalesStatus;
import cn.m2c.scm.domain.model.order.CommentStatus;
import cn.m2c.scm.domain.model.order.LogisticsStatus;
import cn.m2c.scm.domain.model.order.OrderStatus;
import cn.m2c.scm.domain.model.order.PayStatus;
import cn.m2c.scm.domain.model.order.SettleStatus;
import cn.m2c.scm.domain.model.order.fsales.FsalesStatus;

/**
 * 
 * @ClassName: SpringJdbcOrderQuery
 * @Description: 订单查询实现
 * @author moyj
 * @date 2017年4月27日 下午4:41:22
 *
 */
@Repository
public class SpringJdbcOrderQuery {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 我的订单详细
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> myOrderDetail(String orderId) throws NegativeException {
		String field=   "o.order_id AS orderId,"
						+ "o.order_no AS orderNo,"
						+ "o.dispatch_way AS dispatchWay,"
						+ "o.invoice_type AS invoiceType,"
						+ "o.invoice_title AS invoiceTitle,"
						+ "o.tax_iden_num AS taxIdenNum,"
						+ "o.order_price AS orderPrice,"
						+ "o.freight_price AS freightPrice,"
						+ "o.pay_price AS payPrice,"
						+ "o.pay_way AS payWay,"
						+ "o.commit_time AS commitTime,"
						+ "o.receiver_time AS receiverTime,"
						+ "o.after_sales_valid AS afterSalesValid,"
						+ "o.buyer_id AS buyerId,"
						+ "o.buyer_name AS buyerName,"
						+ "o.buyer_message AS buyerMessage,"
						+ "o.goods_id AS goodsId,"
						+ "o.goods_name AS goodsName,"
						+ "o.goods_icon AS goodsIcon,"
						+ "o.property_id AS propertyId,"
						+ "o.property_desc AS propertyDesc,"
						+ "o.goods_unit_price AS goodsUnitPrice,"
						+ "o.goods_market_price AS goodsMarketPrice,"
						+ "o.goods_num AS goodsNum,"
						+ "o.dealer_id AS dealerId,"
						+ "o.dealer_name AS dealerName,"
						+ "o.mres_id AS mresId,"
						+ "o.receiver_id AS receiverId,"
						+ "o.receiver_name AS receiverName,"
						+ "o.province_code AS provinceCode,"
						+ "o.province_name AS provinceName,"
						+ "o.city_code AS cityCode,"
						+ "o.city_name AS cityName,"
						+ "o.area_code AS areaCode,"
						+ "o.area_name AS areaName,"
						+ "o.receiver_addr AS receiverAddr,"
						+ "o.receiver_phone AS receiverPhone,"
						+ "o.receiver_zip_code AS receiverZipCode,"
						+ "o.order_status AS orderStatus,"
						+ "o.pay_status AS payStatus,"
						+ "o.logistics_status AS logisticsStatus,"
						+ "o.comment_status AS commentStatus,"
						+ "o.after_sales_status AS afterSalesStatus";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ")
					.append(" AND o.order_status != 2");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					
					Integer trailStatus = 0;
					Integer orderStatus = 1;
					Integer payStatus = 1;
					Integer logisticsStatus = 1;
					Integer afterSalesStatus = 1;
					Integer commentStatus = 1;
					Long receiverTime = null;
					Long afterSalesValid = 1296000000L;
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						
						if(key.equals("orderStatus")){
							orderStatus = (Integer)obj; 
						}
						else if(key.equals("payStatus")){
							payStatus = (Integer)obj; 
						}
						else if(key.equals("logisticsStatus")){
							logisticsStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesStatus")){
							afterSalesStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesValid")){
							if(obj != null){
								afterSalesValid	= (Long)obj; 
							}
						}
						else if(key.equals("receiverTime")){
							if(obj != null){
								receiverTime = (Long)obj; 
							}
						}
						else if(key.equals("commentStatus")){
							commentStatus = (Integer)obj; 
						}else{
							mapOfColValues.put(key, obj);
						}
					}
					//1待付款 2待发货 3待收货 4待评论 5退换货中 6交易完成
					if(
						orderStatus.intValue() == OrderStatus.NORMAL.getId()
						&&payStatus.intValue() == PayStatus.WAITING.getId()
						&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
					 ){	
						trailStatus = 1;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_SEND_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
					 ){	
						trailStatus = 2;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_RECEIPT_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
					 ){	
						trailStatus = 3;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId()		
							&&commentStatus.intValue() == CommentStatus.WAIT.getId()	
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&receiverTime != null &&receiverTime > (System.currentTimeMillis() - afterSalesValid)
					 ){	
						trailStatus = 4;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId()		
							&&(afterSalesStatus.intValue() == AfterSalesStatus.WAIT_AUDIT.getId() || afterSalesStatus.intValue() == AfterSalesStatus.AFTER_SALES_INHAND.getId())
					 ){	
						trailStatus = 5;
					}
					else{	
						if(afterSalesStatus == AfterSalesStatus.FINISHED.getId()){
							trailStatus = 7;
						}else{
							trailStatus = 6;
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
	 * 我的订单支付状态
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> myOrderPayStatus(String orderId) throws NegativeException {
		String field =  "o.pay_status AS payStatus";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ")
					.append(" AND o.order_status != 2");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 我的订单列表
	 * @param userId		//用户编号
	 * @param type			//1 所有 2待付款 3待收货 4待评论 5退款售后
	 * @param pageNumber	//当前页
	 * @param rows			//每页多少条记录
	 * @return
	 * @throws NegativeException
	 */
	public List<?> myOrderList(String userId,Integer type,Integer pageNumber,Integer rows) throws NegativeException{
		String field=   "o.order_id AS orderId,"
				+ "o.order_no AS orderNo,"
				+ "o.order_price AS orderPrice,"
				+ "o.freight_price AS freightPrice,"
				+ "o.pay_price AS payPrice,"
				+ "o.pay_way AS payWay,"
				+ "o.commit_time AS commitTime,"
				+ "o.receiver_time AS receiverTime,"
				+ "o.after_sales_valid AS afterSalesValid,"
				+ "o.goods_id AS goodsId,"
				+ "o.goods_name AS goodsName,"
				+ "o.goods_icon AS goodsIcon,"
				+ "o.property_desc AS propertyDesc,"
				+ "o.goods_unit_price AS goodsUnitPrice,"
				+ "o.goods_market_price AS goodsMarketPrice,"
				+ "o.goods_num AS goodsNum,"
				+ "o.dealer_id AS dealerId,"
				+ "o.dealer_name AS dealerName,"
				+ "o.order_status AS orderStatus,"
				+ "o.pay_status AS payStatus,"
				+ "o.logistics_status AS logisticsStatus,"
				+ "o.comment_status AS commentStatus,"
				+ "o.after_sales_status AS afterSalesStatus";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.buyer_id = ? ")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
			List<Object> params = new ArrayList<Object>();
			params.add(userId);
			
			
			//1所有 2待付款 3待收货 4待评论  5售后
			
			//1所有
			if(type != null && type.intValue() == 1){
				
			}
			//2待付款
			else if(type != null && type.intValue() == 2){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.pay_status = " + PayStatus.WAITING.getId());
				sql.append(" AND o.after_sales_status = " + AfterSalesStatus.WAIT_APPLY.getId());
			}
			//3 待收货
			else if(type != null && type.intValue() == 3){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.pay_status = " + PayStatus.COMMITED.getId());
				sql.append(" AND o.logistics_status in(")
				   .append(LogisticsStatus.WAIT_SEND_GOOGS.getId()).append(",")
				   .append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId()).append(")");
				sql.append(" AND o.after_sales_status = " + AfterSalesStatus.WAIT_APPLY.getId());
			}
			//4待评论
			else if(type != null && type.intValue() == 4){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.pay_status = " + PayStatus.COMMITED.getId());
				sql.append(" AND o.logistics_status = " + LogisticsStatus.RECEIPTED_GOOGS.getId());
				sql.append(" AND o.comment_status = " + CommentStatus.WAIT.getId());
				sql.append(" AND o.after_sales_status = " + AfterSalesStatus.WAIT_APPLY.getId());
				sql.append(" AND o.receiver_time > UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
			}
			//5退款售后
			else if(type != null && type.intValue() == 5){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.after_sales_status != " + AfterSalesStatus.WAIT_APPLY.getId());
			}else{
				return null;
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
					Integer trailStatus = 0;
					Integer orderStatus = 1;
					Integer payStatus = 1;
					Integer logisticsStatus = 1;
					Integer afterSalesStatus = 1;
					Integer commentStatus = 1;
					Long receiverTime = null;
					Long afterSalesValid = 1296000000L;
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						
						if(key.equals("orderStatus")){
							orderStatus = (Integer)obj; 
						}
						else if(key.equals("payStatus")){
							payStatus = (Integer)obj; 
						}
						else if(key.equals("logisticsStatus")){
							logisticsStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesStatus")){
							afterSalesStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesValid")){
							if(obj != null){
								afterSalesValid	= (Long)obj; 
							}
						}
						else if(key.equals("receiverTime")){
							if(obj != null){
								receiverTime = (Long)obj; 
							}
						}
						else if(key.equals("commentStatus")){
							commentStatus = (Integer)obj; 
						}else{
							mapOfColValues.put(key, obj);
						}
					}
					//1待付款 2待发货 3待收货 4待评论 5退换货中 6交易完成
					if(
						orderStatus.intValue() == OrderStatus.NORMAL.getId()
						&&payStatus.intValue() == PayStatus.WAITING.getId()
						&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
					 ){	
						trailStatus = 1;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_SEND_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
					 ){	
						trailStatus = 2;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_RECEIPT_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
					 ){	
						trailStatus = 3;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId()		
							&&commentStatus.intValue() == CommentStatus.WAIT.getId()	
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&receiverTime != null &&receiverTime > (System.currentTimeMillis() - afterSalesValid)
					 ){	
						trailStatus = 4;
					}
					else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId()		
							&&(afterSalesStatus.intValue() == AfterSalesStatus.WAIT_AUDIT.getId() || afterSalesStatus.intValue() == AfterSalesStatus.AFTER_SALES_INHAND.getId())
					 ){	
						trailStatus = 5;
					}
					else{	
						if(afterSalesStatus == AfterSalesStatus.FINISHED.getId()){
							trailStatus = 7;
						}else{
							trailStatus = 6;
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
	 * 我的订单列表
	 * @param userId		//用户编号
	 * @param type			//1 所有 2待付款 3待收货 4待评论 5退款售后
	 * @param pageNumber	//当前页
	 * @param rows			//每页多少条记录
	 * @return
	 * @throws NegativeException
	 */
	public Integer myOrderListCount(String userId,Integer type) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND o.buyer_id = ? ");
			List<Object> params = new ArrayList<Object>();
			params.add(userId);
			
			//1所有 2待付款 3待收货 4待评论  5售后
			
			//1所有
			if(type != null && type.intValue() == 1){
				
			}
			//2待付款
			else if(type != null && type.intValue() == 2){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.pay_status = " + PayStatus.WAITING.getId());
				sql.append(" AND o.after_sales_status = " + AfterSalesStatus.WAIT_APPLY.getId());
			}
			//3 待收货
			else if(type != null && type.intValue() == 3){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.pay_status = " + PayStatus.COMMITED.getId());
				sql.append(" AND o.logistics_status in(")
				   .append(LogisticsStatus.WAIT_SEND_GOOGS.getId()).append(",")
				   .append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId()).append(")");
				sql.append(" AND o.after_sales_status = " + AfterSalesStatus.WAIT_APPLY.getId());
			}
			//4待评论
			else if(type != null && type.intValue() == 4){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.pay_status = " + PayStatus.COMMITED.getId());
				sql.append(" AND o.logistics_status = " + LogisticsStatus.RECEIPTED_GOOGS.getId());
				sql.append(" AND o.comment_status = " + CommentStatus.WAIT.getId());
				sql.append(" AND o.after_sales_status = " + AfterSalesStatus.WAIT_APPLY.getId());
			}
			//5退款售后
			else if(type != null && type.intValue() == 5){
				sql.append(" AND o.order_status = " + OrderStatus.NORMAL.getId());
				sql.append(" AND o.after_sales_status != " + AfterSalesStatus.WAIT_APPLY.getId());
			}else{
				return null;
			}
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * 订单列表
	 * 
	 * @param orderId			//订单编号
	 * @param buyerKeyword		//买家关键字
	 * @param goodsKeyword		//货品关键字
	 * @param dealerKeyword		//商家关键字
	 * @param mediaKeyword		//媒体关键字
	 * @param mresKeyword		//媒体资源关键字
	 * @param salerKeyword		//促销员关键字
	 * @param goodsType			//销售货品类型 1实物货品 2 服务型 3虚拟货品
	 * @param orderStatus		//订单状态 1 正常 2 取消
	 * @param payStatus			//支付状态 1待支付 2已支付
	 * @param logisticsStatus	//物流状态 1待发货 2待收货 3确认收货
	 * @param afterSalesStatus	//售后状态 1待申请 2待审核 3 退换中 4.已退款
	 * @param commentStatus		//评论状态 1待评论 2已评论
	 * @param startTime			//下单开始时间
	 * @param endTime			//下单最后时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> list(
			String orderId,
			String orderNo,
			String buyerKeyword,
			String goodsKeyword,
			String dealerKeyword,
			String mediaKeyword,
			String mresKeyword,
			String salerKeyword,
			Integer goodsType,
			Integer orderStatus,
			Integer payStatus,
			Integer logisticsStatus,
			Integer afterSalesStatus,
			Integer commentStatus,
			Long startTime,
			Long endTime,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
		  String field =   "o.order_id AS orderId,"
				  			+ "o.order_no AS orderNo,"
				  			+ "o.order_price AS orderPrice,"
							+ "o.pay_way AS payWay,"
							+ "o.commit_time AS commitTime,"
							+ "o.buyer_name AS buyerName,"
							+ "o.goods_name AS goodsName,"
							+ "o.goods_type AS goodsType,"
							+ "o.goods_num AS goodsNum,"
							+ "o.dealer_name AS dealerName,"
							+ "o.mres_name AS mresName,"
							+ "o.media_name AS mediaName,"
							+ "o.saler_name AS salerName,"	
							+ "o.receiver_name AS receiverName,"						
							+ "o.province_code AS provinceCode,"
							+ "o.province_name AS provinceName,"
							+ "o.city_code AS cityCode,"
							+ "o.city_name AS cityName,"
							+ "o.area_code AS areaCode,"
							+ "o.area_name AS areaName,"
							+ "o.receiver_addr AS receiverAddr,"
							+ "o.order_status AS orderStatus,"
							+ "o.pay_status AS payStatus,"
							+ "o.logistics_status AS logisticsStatus,"
							+ "o.settle_status AS settleStatus,"
							+ "o.after_sales_status AS afterSalesStatus,"
							+ "o.comment_status AS commentStatus";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_order o where 1=1");
			List<Object> params = new ArrayList<Object>();	
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND o.order_id = ?");
				params.add(orderId);
			}
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderNo);
			}
			if(buyerKeyword != null && buyerKeyword.length() > 0){
				sql.append(" AND (o.buyer_id = ? or o.buyer_name = ? or o.buyer_phone = ? )");
				params.add(buyerKeyword);
				params.add(buyerKeyword);
				params.add(buyerKeyword);
			}
			if(goodsKeyword != null && goodsKeyword.length() > 0){
				sql.append(" AND (o.goods_id = ? or o.goods_name = ?)");
				params.add(goodsKeyword);
				params.add(goodsKeyword);
			}
			if(dealerKeyword != null && dealerKeyword.length() > 0){
				sql.append(" AND (o.dealer_id = ? or o.dealer_name = ? or o.dealer_phone = ? )");
				params.add(dealerKeyword);
				params.add(dealerKeyword);
				params.add(dealerKeyword);
			}
			if(mediaKeyword != null && mediaKeyword.length() > 0){
				sql.append(" AND (o.media_id = ? or o.media_name = ? or o.media_phone = ? )");
				params.add(mediaKeyword);
				params.add(mediaKeyword);
				params.add(mediaKeyword);
			}
			if(mresKeyword != null && mresKeyword.length() > 0){
				sql.append(" AND (o.mres_id = ? or o.mres_name = ? )");
				params.add(mresKeyword);
				params.add(mresKeyword);
			}
			if(salerKeyword != null && salerKeyword.length() > 0){
				sql.append(" AND (o.saler_id = ? or o.saler_name = ? or o.saler_phone = ? )");
				params.add(salerKeyword);
				params.add(salerKeyword);
				params.add(salerKeyword);
			}
			if(goodsType != null){
				sql.append(" AND o.goods_type = ?");
				params.add(goodsType);
			}
			if(orderStatus != null){
				sql.append(" AND o.order_status = ?");
				params.add(orderStatus);
			}
			if(payStatus != null){
				sql.append(" AND o.pay_status = ?");
				params.add(payStatus);
			}
			if(logisticsStatus != null){
				sql.append(" AND o.logistics_status = ?");
				params.add(logisticsStatus);
			}
			if(afterSalesStatus != null){
				sql.append(" AND o.after_sales_status = ?");
				params.add(afterSalesStatus);
			}
			if(commentStatus != null){
				sql.append(" AND o.comment_status = ?");
				params.add(commentStatus);
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
			sql.append(" ORDER BY commit_time DESC");
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
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
					}
					
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
	 * 订单列表数(订单管理)
	 * @param orderId			//订单编号
	 * @param buyerKeyword		//买家关键字
	 * @param goodsKeyword		//货品关键字
	 * @param dealerKeyword		//商家关键字
	 * @param mediaKeyword		//媒体关键字
	 * @param mresKeyword		//媒体资源关键字
	 * @param salerKeyword		//促销员关键字
	 * @param goodsType			//销售货品类型 1实物货品 2 服务型 3虚拟货品
	 * @param orderStatus		//订单状态 1 正常 2 取消
	 * @param payStatus			//支付状态 1待支付 2已支付
	 * @param logisticsStatus	//物流状态 1待发货 2待收货 3确认收货
	 * @param aftersalesStatus	//售后状态 1待申请 2待审核 3 退换中 4.已退款
	 * @param commentStatus		//评论状态 1待评论 2已评论
	 * @param startTime			//下单开始时间
	 * @param endTime			//下单最后时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCount(
			String orderId,
			String orderNo,
			String buyerKeyword,
			String goodsKeyword,
			String dealerKeyword,
			String mediaKeyword,
			String mresKeyword,
			String salerKeyword,
			Integer goodsType,
			Integer orderStatus,
			Integer payStatus,
			Integer logisticsStatus,
			Integer aftersalesStatus,
			Integer commentStatus,
			Long startTime,
			Long endTime) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1");
			List<Object> params = new ArrayList<Object>();	
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND o.order_id = ?");
				params.add(orderId);
			}
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderNo);
			}
			if(buyerKeyword != null && buyerKeyword.length() > 0){
				sql.append(" AND (o.buyer_id = ? or o.buyer_name = ? or o.buyer_phone = ? )");
				params.add(buyerKeyword);
				params.add(buyerKeyword);
				params.add(buyerKeyword);
			}
			if(goodsKeyword != null && goodsKeyword.length() > 0){
				sql.append(" AND (o.goods_id = ? or o.goods_name = ?)");
				params.add(goodsKeyword);
				params.add(goodsKeyword);
			}
			if(dealerKeyword != null && dealerKeyword.length() > 0){
				sql.append(" AND (o.dealer_id = ? or o.dealer_name = ? or o.dealer_phone = ? )");
				params.add(dealerKeyword);
				params.add(dealerKeyword);
				params.add(dealerKeyword);
			}
			if(mediaKeyword != null && mediaKeyword.length() > 0){
				sql.append(" AND (o.media_id = ? or o.media_name = ? or o.media_phone = ? )");
				params.add(mediaKeyword);
				params.add(mediaKeyword);
				params.add(mediaKeyword);
			}
			if(mresKeyword != null && mresKeyword.length() > 0){
				sql.append(" AND (o.mres_id = ? or o.mres_name = ? )");
				params.add(mresKeyword);
				params.add(mresKeyword);
			}
			if(salerKeyword != null && salerKeyword.length() > 0){
				sql.append(" AND (o.saler_id = ? or o.saler_name = ? or o.saler_phone = ? )");
				params.add(salerKeyword);
				params.add(salerKeyword);
				params.add(salerKeyword);
			}
			if(goodsType != null){
				sql.append(" AND o.goods_type = ?");
				params.add(goodsType);
			}
			if(orderStatus != null){
				sql.append(" AND o.order_status = ?");
				params.add(orderStatus);
			}
			if(payStatus != null){
				sql.append(" AND o.pay_status = ?");
				params.add(payStatus);
			}
			if(logisticsStatus != null){
				sql.append(" AND o.logistics_status = ?");
				params.add(logisticsStatus);
			}
			if(aftersalesStatus != null){
				sql.append(" AND o.after_sales_status = ?");
				params.add(aftersalesStatus);
			}
			if(commentStatus != null){
				sql.append(" AND o.comment_status = ?");
				params.add(commentStatus);
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
	 * 
	 * 订单列表(结算管理)
	 * 
	 * @param orderId			//订单编号
	 * @param buyerKeyword		//买家关键字
	 * @param dealerKeyword		//商家关键字
	 * @param mediaKeyword		//媒体关键字
	 * @param salerKeyword		//促销员关键字
	 * @param settleStatus		//订单状态 1 待结算 2 已结算
	 * @param commitsTime		//下单开始时间
	 * @param commiteTime		//下单最后时间
	 * @param receiversTime		//收货开始时间
	 * @param receivereTime		//收货最后时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listForSettle(
			String orderId,
			String orderNo,
			String buyerKeyword,
			String dealerKeyword,
			String mediaKeyword,
			String salerKeyword,
			Integer settleStatus,
			Long commitsTime,
			Long commiteTime,
			Long receiversTime,
			Long receivereTime,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
			String list =   "o.order_id AS orderId,"
							+ "o.order_no AS orderNo,"
							+ "o.order_price AS orderPrice,"
							+ "o.freight_price AS freightPrice,"
							+ "o.refund_amount AS refundAmount,"
							+ "IF(o.dealer_percent is null,0,FORMAT(o.dealer_percent/100,2)) AS dealerPercent,"
							+ "IF(o.media_percent is null,0,FORMAT(o.media_percent/100,2)) AS mediaPercent,"
							+ "IF(o.saler_percent is null,0,FORMAT(o.saler_percent/100,2)) AS salerPercent,"
							+ "IF(o.platform_percent is null,0,FORMAT(o.platform_percent/100,2)) AS platformPercent,"
							+ "o.settle_status AS settleStatus,"
							+ "o.buyer_name AS buyerName,"
							+ "o.buyer_phone AS buyerPhone,"
							+ "o.dealer_name AS dealerName,"
							+ "o.media_name AS mediaName,"
							+ "o.saler_name AS salerName,"
							+ "o.pay_end_time AS payEndTime,"
							+ "o.receiver_time AS receiverTime";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + list )
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND (o.settle_status = ").append(SettleStatus.COMMITED.getId())
					.append(" or o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId())
					.append(" or (")
					.append("  o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId())
					.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid")
					.append(" )").append(" )");
			List<Object> params = new ArrayList<Object>();	
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND o.order_id = ?");
				params.add(orderId);
			}
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderNo);
			}
			if(buyerKeyword != null && buyerKeyword.length() > 0){
				sql.append(" AND (o.buyer_id = ? or o.buyer_name = ? or o.buyer_phone = ? )");
				params.add(buyerKeyword);
				params.add(buyerKeyword);
				params.add(buyerKeyword);
			}
			if(dealerKeyword != null && dealerKeyword.length() > 0){
				sql.append(" AND (o.dealer_id = ? or o.dealer_name = ? or o.dealer_phone = ? )");
				params.add(dealerKeyword);
				params.add(dealerKeyword);
				params.add(dealerKeyword);
			}
			if(mediaKeyword != null && mediaKeyword.length() > 0){
				sql.append(" AND (o.media_id = ? or o.media_name = ? or o.media_phone = ? )");
				params.add(mediaKeyword);
				params.add(mediaKeyword);
				params.add(mediaKeyword);
			}
			if(salerKeyword != null && salerKeyword.length() > 0){
				sql.append(" AND (o.saler_id = ? or o.saler_name = ? or o.saler_phone = ? )");
				params.add(salerKeyword);
				params.add(salerKeyword);
				params.add(salerKeyword);
			}	
			if(settleStatus != null){
				sql.append(" AND o.settle_status = ?");
				params.add(settleStatus);
			}
			if(commitsTime != null && commiteTime != null){
				sql.append(" AND (o.commit_time >= ? AND o.commit_time < ?)");
				params.add(commitsTime);
				params.add(commiteTime);
			}
			else if(commitsTime != null && commiteTime == null){
				sql.append(" AND o.commit_time >= ?");
				params.add(commitsTime);
			}	
			else if(commiteTime != null && commitsTime == null){
				sql.append(" AND o.commit_time < ?");
				params.add(commiteTime);
			}	
			if(receiversTime != null && receivereTime != null){
				sql.append(" AND (o.commit_time >= ? AND o.commit_time < ?)");
				params.add(receiversTime);
				params.add(receivereTime);
			}
			else if(receiversTime != null && receivereTime == null){
				sql.append(" AND o.commit_time >= ?");
				params.add(receiversTime);
			}	
			else if(receivereTime != null && receiversTime == null){
				sql.append(" AND o.commit_time < ?");
				params.add(receivereTime);
			}	
			sql.append(" ORDER BY commit_time DESC");
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
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
						
					}
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
	 * 订单列表数(结算管理)
	 * @param orderId			//订单编号
	 * @param buyerKeyword		//买家关键字
	 * @param dealerKeyword		//商家关键字
	 * @param mediaKeyword		//媒体关键字
	 * @param salerKeyword		//促销员关键字
	 * @param settleStatus		//订单状态 1 待结算 2 已结算
	 * @param commitsTime		//下单开始时间
	 * @param commiteTime		//下单最后时间
	 * @param receiversTime		//收货开始时间
	 * @param receivereTime		//收货最后时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCountForSettle(
			String orderId,
			String orderNo,
			String buyerKeyword,
			String dealerKeyword,
			String mediaKeyword,
			String salerKeyword,
			Integer settleStatus,
			Long commitsTime,
			Long commiteTime,
			Long receiversTime,
			Long receivereTime) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
				.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
				.append(" AND (o.settle_status = ").append(SettleStatus.COMMITED.getId())
				.append(" or o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId())
				.append(" or (")
				.append("  o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId())
				.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
				.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid")
				.append(" )").append(" )");
			List<Object> params = new ArrayList<Object>();	
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND o.order_id = ?");
				params.add(orderId);
			}
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_No = ?");
				params.add(orderNo);
			}
			if(buyerKeyword != null && buyerKeyword.length() > 0){
				sql.append(" AND (o.buyer_id = ? or o.buyer_name = ? or o.buyer_phone = ? )");
				params.add(buyerKeyword);
				params.add(buyerKeyword);
				params.add(buyerKeyword);
			}
			if(dealerKeyword != null && dealerKeyword.length() > 0){
				sql.append(" AND (o.dealer_id = ? or o.dealer_name = ? or o.dealer_phone = ? )");
				params.add(dealerKeyword);
				params.add(dealerKeyword);
				params.add(dealerKeyword);
			}
			if(mediaKeyword != null && mediaKeyword.length() > 0){
				sql.append(" AND (o.media_id = ? or o.media_name = ? or o.media_phone = ? )");
				params.add(mediaKeyword);
				params.add(mediaKeyword);
				params.add(mediaKeyword);
			}
			if(salerKeyword != null && salerKeyword.length() > 0){
				sql.append(" AND (o.saler_id = ? or o.saler_name = ? or o.saler_phone = ? )");
				params.add(salerKeyword);
				params.add(salerKeyword);
				params.add(salerKeyword);
			}	
			if(settleStatus != null){
				sql.append(" AND o.settle_status = ?");
				params.add(settleStatus);
			}
			if(commitsTime != null && commiteTime != null){
				sql.append(" AND (o.commit_time >= ? AND o.commit_time < ?)");
				params.add(commitsTime);
				params.add(commiteTime);
			}
			else if(commitsTime != null && commiteTime == null){
				sql.append(" AND o.commit_time >= ?");
				params.add(commitsTime);
			}	
			else if(commiteTime != null && commitsTime == null){
				sql.append(" AND o.commit_time < ?");
				params.add(commiteTime);
			}	
			if(receiversTime != null && receivereTime != null){
				sql.append(" AND (o.commit_time >= ? AND o.commit_time < ?)");
				params.add(receiversTime);
				params.add(receivereTime);
			}
			else if(receiversTime != null && receivereTime == null){
				sql.append(" AND o.commit_time >= ?");
				params.add(receiversTime);
			}	
			else if(receivereTime != null && receiversTime == null){
				sql.append(" AND o.commit_time < ?");
				params.add(receivereTime);
			}	
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	
	}
	
	/**
	 * 订单列表(供应商订单管理)
	 * 
	 * @param orderId		//订单号
	 * @param keywordType	//关键字类型 1收货人 2收货人电话 3运单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待付款 2待发货 3待收货 4取消 5退换中6已退 7已换
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单结束时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listForDealer(
			String dealerId,
			String orderId,
			String orderNo,
			Integer keywordType,
			String keyword,
			Integer statusFlag,
			Integer payWay,
			Long startTime,
			Long endTime,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{

			String list =   "o.order_id AS orderId,"
					    + "o.order_no AS orderNo,"
						+ "o.pay_trade_no AS payTradeNo,"
						+ "o.pay_price AS payPrice,"
						+ "o.pay_way AS payWay,"
						+ "o.commit_time AS commitTime,"
						+ "o.goods_name AS goodsName,"
						+ "o.receiver_time AS receiverTime,"
						+ "o.receiver_time AS receiverTime,"
						+ "o.after_sales_valid AS afterSalesValid,"
						+ "o.receiver_name AS receiverName,"
						+ "o.receiver_phone AS receiverPhone,"
						+ "o.waybill_no AS waybillNo,"
						+ "o.order_status AS orderStatus,"
						+ "o.pay_status AS payStatus,"
						+ "o.logistics_status AS logisticsStatus,"
						+ "o.settle_status AS settleStatus,"
						+ "o.after_sales_status AS afterSalesStatus";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + list )
					.append(" FROM t_order_order o where 1=1")
					.append(" AND (o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" or o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId()).append(")")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.dealer_id = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(dealerId);
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND o.order_id = ?");
				params.add(orderId);
			}	
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderNo);
			}	
			if(keyword != null && keyword.length() > 0){
				if(keywordType != null && keywordType == 1){
					sql.append(" AND o.receiver_name = ? ");
					params.add(keyword);
				}else if(keywordType != null && keywordType == 2){
					sql.append(" AND o.receiver_phone = ? ");
					params.add(keyword);
				}else if(keywordType != null && keywordType == 3){
					sql.append(" AND o.waybill_no = ? ");
					params.add(keyword);
				}
				
			}
			if(statusFlag != null){
				//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
				if(statusFlag == 1){
					sql.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
				}else if(statusFlag == 2){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
				}else if(statusFlag == 3){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
				}else if(statusFlag == 4){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					sql.append(" AND o.receiver_time > UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
				}else if(statusFlag == 5){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND (");
					sql.append("  (o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId());
					sql.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					sql.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
					sql.append(" )"); 
					sql.append("  OR o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId());
					sql.append("  OR o.settle_status = ").append(SettleStatus.COMMITED.getId());
					sql.append(" )");
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
			sql.append(" ORDER BY commit_time DESC");	
			sql.append(" limit ?,?");	
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			
			List<Map<String,Object>> resultList = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Integer trailStatus = 0;
					Integer orderStatus = 1;
					Integer payStatus = 1;
					Integer logisticsStatus = 1;
					Integer afterSalesStatus = 1;
					Integer settleStatus = 1;
					Long afterSalesValid = 1296000000L;
					Long receiverTime = null;
					
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						
						if(key.equals("orderStatus")){
							orderStatus = (Integer)obj; 
						}
						else if(key.equals("payStatus")){
							payStatus = (Integer)obj; 
						}
						else if(key.equals("logisticsStatus")){
							logisticsStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesStatus")){
							afterSalesStatus = (Integer)obj; 
						}
						else if(key.equals("settleStatus")){
							settleStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesValid")){
							if(obj != null){
								afterSalesValid	= (Long)obj; 
							}
						}
						else if(key.equals("receiverTime")){
							if(obj != null){
								receiverTime = (Long)obj; 
							}
						}else{
							mapOfColValues.put(key, obj);
						}
					}
					//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
					if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.WAITING.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_SEND_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()){	
						trailStatus = 1;
					}else if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_SEND_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()){	
						trailStatus = 2;
					}else if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_RECEIPT_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()){	
 						trailStatus = 3;
					}else if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&receiverTime != null &&receiverTime > (System.currentTimeMillis() - afterSalesValid)
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()
							){
						
						trailStatus = 4;
 						
					}else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&(logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId() 
							  &&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId() 
							  &&receiverTime !=null &&receiverTime <= (System.currentTimeMillis() - afterSalesValid))
							  ||afterSalesStatus.intValue() == AfterSalesStatus.FINISHED.getId()
							  ||settleStatus.intValue() == SettleStatus.COMMITED.getId()
							){
						trailStatus = 5;		
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
	 * 订单列表数(供应商订单管理)
	 * @param orderId		//订单号
	 * @param keywordType	//关键字类型 1收货人 2收货人电话 3运单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待付款 2待发货 3待收货 4取消 5退换中6已退 7已换
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单结束时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCountForDealer(
			String dealerId,
			String orderId,
			String orderNo,
			Integer keywordType,
			String keyword,
			Integer statusFlag,
			Integer payWay,
			Long startTime,
			Long endTime) throws NegativeException {
		try{

			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND (o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
				.append(" or o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId()).append(")")
				.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
				.append(" AND o.dealer_id = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(dealerId);
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND o.order_id = ?");
				params.add(orderId);
			}	
			if(orderNo != null && orderNo.length() > 0){
				sql.append(" AND o.order_no = ?");
				params.add(orderNo);
			}	
			if(keyword != null && keyword.length() > 0){
				if(keywordType != null && keywordType == 1){
					sql.append(" AND o.receiver_name = ? ");
					params.add(keyword);
				}else if(keywordType != null && keywordType == 2){
					sql.append(" AND o.receiver_phone = ? ");
					params.add(keyword);
				}else if(keywordType != null && keywordType == 3){
					sql.append(" AND o.waybill_no = ? ");
					params.add(keyword);
				}
				
			}
			if(statusFlag != null){
				//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
				if(statusFlag == 1){
					sql.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
				}else if(statusFlag == 2){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
				}else if(statusFlag == 3){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
				}else if(statusFlag == 4){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId());
					sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					sql.append(" AND o.receiver_time > UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
				}else if(statusFlag == 5){
					sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
					sql.append(" AND (");
					sql.append("  (o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId());
					sql.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					sql.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
					sql.append(" )"); 
					sql.append("  OR o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId());
					sql.append("  OR o.settle_status = ").append(SettleStatus.COMMITED.getId());
					sql.append(" )");
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
	 * 订单详细(订单管理)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> detail(String orderId) throws NegativeException {
		String field = "";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	/**
	 * 订单详细(订单付款时)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> payOrderById(String orderId) throws NegativeException {
		String field = "o.order_id AS orderId,"
				+ "o.order_no AS orderNo,"
				+ "o.dispatch_way AS dispatchWay,"
				+ "o.invoice_type AS invoiceType,"
				+ "o.invoice_title AS invoiceTitle,"
				+ "o.tax_iden_num AS taxIdenNum,"
				+ "o.order_price AS orderPrice,"
				+ "o.freight_price AS freightPrice,"
				+ "o.pay_price AS payPrice,"
				+ "o.pay_way AS payWay,"
				+ "o.commit_time AS commintTime,"
				+ "o.pay_start_time AS payStartTime,"
				+ "o.pay_end_time AS payEndTime,"
				+ "o.pay_trade_no AS payTradeNo,"
				+ "o.buyer_id AS buyerId,"
				+ "o.buyer_name AS buyerName,"
				+ "o.buyer_phone AS buyerPhone,"
				+ "o.buyer_message AS buyerMessage,"
				+ "o.goods_id AS goodsId,"
				+ "o.goods_no AS goodsNo,"
				+ "o.goods_name AS goodsName,"
				+ "o.property_id AS propertyId,"
				+ "o.property_desc AS propertyDesc,"
				+ "o.goods_unit_price AS goodsUnitPrice,"
				+ "o.goods_market_price AS goodsMarketPrice,"
				+ "o.goods_num AS goodsNum,"
				+ "o.goods_type AS goodsType,"
				+ "o.dealer_user_id AS dealerUserId,"
				+ "o.dealer_id AS dealerId,"
				+ "o.dealer_name AS dealerName,"
				+ "o.dealer_phone AS dealerPhone,"
				+ "o.dealer_strategy AS dealerStrategy,"
				+ "o.dealer_percent AS dealerPercent,"
				+ "o.mres_id AS mresId,"
				+ "o.mres_no AS mresNo,"
				+ "o.mres_name AS mresName,"
				+ "o.media_user_id AS mediaUserId,"
				+ "o.media_id AS mediaId,"
				+ "o.media_name AS mediaName,"
				+ "o.media_phone AS mediaPhone,"
				+ "o.media_strategy AS mediaStrategy,"
				+ "o.media_percent AS mediaPercent,"
				+ "o.saler_user_id AS salerUserId,"
				+ "o.saler_id AS salerId,"
				+ "o.saler_name AS salerName,"
				+ "o.saler_phone AS salerPhone,"
				+ "o.saler_strategy AS salerStrategy,"
				+ "o.saler_percent AS salerPercent,"
				+ "o.receiver_id AS receiverId,"
				+ "o.receiver_name AS receiverName,"
				+ "o.province_code AS provinceCode,"
				+ "o.province_name AS provinceName,"
				+ "o.city_code AS cityCode,"
				+ "o.city_name AS cityName,"
				+ "o.area_code AS areaCode,"
				+ "o.area_name AS areaName,"
				+ "o.receiver_addr AS receiverAddr,"
				+ "o.receiver_phone AS receiverPhone,"
				+ "o.receiver_zip_code AS receiverZipCode,"
				+ "o.exp_company_code AS expCompanyCode,"
				+ "o.exp_company_name AS expCompanyName,"
				+ "o.waybill_no AS waybillNo,"
				+ "o.commit_wayb_time AS commitWaybTime,"
				+ "o.receiver_time AS receiverTime,"
				+ "o.order_status AS orderStatus,"
				+ "o.pay_status AS payStatus,"
				+ "o.logistics_status AS logisticsStatus,"
				+ "o.settle_status AS settleStatus,"
				+ "o.after_sales_status AS afterSalesStatus,"
				+ "o.comment_status AS commentStatus,"
				+ "o.platform_strategy AS platformStrategy,"
				+ "o.platform_percent AS platformStrategy";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId());
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	/**
	 * 订单详细(供应商订单管理)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> detailForDealer(String orderId) throws NegativeException {

		try{
			String field =  "o.order_id AS orderId,"
						+ "o.order_no AS orderNo,"
						+ "o.pay_trade_no AS payTradeNo,"
						+ "o.commit_time AS commitTime,"
						+ "o.pay_end_time AS payEndTime,"
						+ "o.pay_way AS payWay,"
						+ "o.pay_price AS payPrice,"
						+ "o.buyer_id AS buyerId,"
						+ "o.buyer_name AS buyerName,"
						+ "o.buyer_message AS buyerMessage,"
						+ "o.buyer_phone AS buyerPhone,"
						+ "o.goods_id AS goodsId,"
						+ "o.goods_id AS goodsNo,"
						+ "o.goods_name AS goodsName,"
						+ "o.property_desc AS propertyDesc,"
						+ "o.goods_num AS goodsNum,"
						+ "o.goods_unit_price * o.goods_num AS totalPrice,"
						+ "o.receiver_id AS receiverId,"
						+ "o.receiver_name AS receiverName,"
						+ "o.province_code AS provinceCode,"
						+ "o.province_name AS provinceName,"
						+ "o.city_code AS cityCode,"
						+ "o.city_name AS cityName,"
						+ "o.area_code AS areaCode,"
						+ "o.area_name AS areaName,"
						+ "o.receiver_addr AS receiverAddr,"
						+ "o.receiver_phone AS receiverPhone,"
						+ "o.receiver_zip_code AS receiverZipCode,"
						+ "o.logistics_status AS logisticsStatus,"
						+ "o.order_status AS orderStatus,"
						+ "o.after_sales_status AS afterSalesStatus,"
						+ "o.settle_status AS settleStatus,"
						+ "o.exp_company_code AS expCompanyCode,"
						+ "o.exp_company_name AS expCompanyName,"
						+ "o.waybill_no AS waybillNo,"
						+ "o.commit_wayb_time AS commitWaybTime,"
						+ "o.receiver_time AS receiverTime,"
						+ "o.after_sales_valid AS afterSalesValid,"
						+ "o.refund_time AS refundTime,"
						+ "o.pay_status AS payStatus";
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					Integer trailStatus = 0;
					Integer orderStatus = 1;
					Integer payStatus = 1;
					Integer logisticsStatus = 1;
					Integer afterSalesStatus = 1;
					Integer settleStatus = 1;
					Long afterSalesValid = 1296000000L;
					Long receiverTime = null;
					
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						
						if(key.equals("orderStatus")){
							orderStatus = (Integer)obj; 
						}
						else if(key.equals("payStatus")){
							payStatus = (Integer)obj; 
						}
						else if(key.equals("logisticsStatus")){
							logisticsStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesStatus")){
							afterSalesStatus = (Integer)obj; 
							mapOfColValues.put(key, obj);
						}
						else if(key.equals("settleStatus")){
							settleStatus = (Integer)obj; 
						}
						else if(key.equals("afterSalesValid")){
							if(obj != null){
								afterSalesValid	= (Long)obj; 
							}
						}
						else if(key.equals("receiverTime")){
							if(obj != null){
								receiverTime = (Long)obj; 
							}
							mapOfColValues.put(key, obj);
						}else{
							mapOfColValues.put(key, obj);
						}
					}
					//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
					if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.WAITING.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_SEND_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()){	
						trailStatus = 1;
					}else if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_SEND_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()){	
						trailStatus = 2;
					}else if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.WAIT_RECEIPT_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()){	
 						trailStatus = 3;
					}else if(orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId()
							&&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId()
							&&receiverTime != null &&receiverTime > (System.currentTimeMillis() - afterSalesValid)
							&&settleStatus.intValue() == SettleStatus.WAITING.getId()
							){
						
						trailStatus = 4;
 						
					}else if(
							orderStatus.intValue() == OrderStatus.NORMAL.getId()
							&&payStatus.intValue() == PayStatus.COMMITED.getId()
							&&(logisticsStatus.intValue() == LogisticsStatus.RECEIPTED_GOOGS.getId() 
							  &&afterSalesStatus.intValue() == AfterSalesStatus.WAIT_APPLY.getId() 
							  &&receiverTime !=null &&receiverTime <= (System.currentTimeMillis() - afterSalesValid))
							  ||afterSalesStatus.intValue() == AfterSalesStatus.FINISHED.getId()
							  ||settleStatus.intValue() == SettleStatus.COMMITED.getId()
							){
						trailStatus = 5;		
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
	 * 收获地址详细(修改收货地址)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> recaddrDetail(String orderId) throws NegativeException {

		try{
			String field =  "o.order_id AS orderId,"
					+ "o.receiver_id AS receiverId,"
					+ "o.receiver_name AS receiverName,"
					+ "o.province_code AS provinceCode,"
					+ "o.province_name AS provinceName,"
					+ "o.city_code AS cityCode,"
					+ "o.city_name AS cityName,"
					+ "o.area_code AS areaCode,"
					+ "o.area_name AS areaName,"
					+ "o.receiver_addr AS receiverAddr,"
					+ "o.receiver_phone AS receiverPhone,"
					+ "o.receiver_zip_code AS receiverZipCode";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 订单状态详细(修改状态)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> statusDetail(String orderId) throws NegativeException {

		try{
			String field =  "o.order_id AS orderId,"
					+ "o.order_status AS orderStatus,"
					+ "o.pay_status AS payStatus,"
					+ "o.logistics_status AS logisticsStatus,"
					+ "o.after_sales_status AS afterSalesStatus";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 物流信息详情(绑定快递单)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> expressDetail(String orderId) throws NegativeException {

		try{
			String field =  "o.order_id AS orderId,"
							+ "o.exp_company_code AS expCompanyCode,"
							+ "o.exp_company_name AS expCompanyName,"
							+ "o.waybill_no AS waybillNo,"
							+ "o.commit_wayb_time AS commitWaybTime";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	

	 /** 
	 * 打印发货单
	 * @param orderIdList	//订单号
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listForPrintDelivery (
			List<String> orderIdList,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
			String field =  "o.order_id AS orderId,"
					    + "o.order_no AS orderNo,"
						+ "o.pay_end_time AS payEndTime,"
						+ "o.pay_price AS payPrice,"
						+ "o.order_price AS orderPrice,"
						+ "o.freight_price AS freightPrice,"
						+ "o.buyer_id AS buyerId,"
						+ "o.buyer_name AS buyerName,"
						+ "o.buyer_message AS buyerMessage,"
						+ "o.goods_id AS goodsId,"
						+ "o.goods_id AS goodsNo,"
						+ "o.goods_name AS goodsName,"
						+ "o.property_desc AS propertyDesc,"
						+ "o.goods_num AS goodsNum,"
						+ "o.goods_unit_price * o.goods_num AS totalPrice,"
						+ "o.receiver_id AS receiverId,"
						+ "o.receiver_name AS receiverName,"
						+ "o.province_code AS provinceCode,"
						+ "o.province_name AS provinceName,"
						+ "o.city_code AS cityCode,"
						+ "o.city_name AS cityName,"
						+ "o.area_code AS areaCode,"
						+ "o.area_name AS areaName,"
						+ "o.receiver_addr AS receiverAddr,"
						+ "o.receiver_phone AS receiverPhone,"
						+ "o.receiver_zip_code AS receiverZipCode";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_order o where 1=1");
			List<Object> params = new ArrayList<Object>();		
			if(orderIdList == null || orderIdList.size() == 0){
				return null;
			}
			String orderIds = "'";
			for(String orderId :orderIdList){
				orderIds = orderIds + orderId + "','";
			}
			sql.append(" AND o.order_id in(").append(orderIds.substring(0, orderIds.length() - 2)).append(")");
			sql.append(" ORDER BY commit_time DESC");
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
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
					}
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
	 * 打印发货单
	 * @param orderIdList		//订单号

	 * @return
	 * @throws NegativeException
	 */
	public Integer listCountForPrintDelivery(List<String> orderIdList) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o where 1=1");
			List<Object> params = new ArrayList<Object>();		
			if(orderIdList == null || orderIdList.size() == 0){
				return null;
			}
			String orderIds = "'";
			for(String orderId :orderIdList){
				orderIds = orderIds + orderId + "','";
			}
			sql.append(" AND o.order_id in(").append(orderIds.substring(0, orderIds.length() - 2)).append(")");
			
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	
	}
	 
	/** 
	 * 导出订单
	 * @param orderIdList   //订单列表		
	 * @param orderId		//订单号
	 * @param keywordType	//关键字类型 1收货人 2收货人电话 3运单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待付款 2待发货 3待收货 4取消 5退换中6已退 7已换
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单结束时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listForExport(
			List<String> orderIdList,
			String orderId,
			Integer keywordType,
			String keyword,
			Integer statusFlag,
			Integer payWay,
			Long startTime,
			Long endTime,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
			String list =   "o.order_id AS orderId,"
						+ "o.receiver_name AS receiverName,"
						+ "o.province_code AS provinceCode,"
						+ "o.province_name AS provinceName,"
						+ "o.city_code AS cityCode,"
						+ "o.city_name AS cityName,"
						+ "o.area_code AS areaCode,"
						+ "o.area_name AS areaName,"
						+ "o.receiver_addr AS receiverAddr,"
						+ "o.receiver_phone AS receiverPhone,"
						+ "o.receiver_zip_code AS receiverZipCode,"
						+ "o.goods_name AS goodsName,"
						+ "o.goods_num AS goodsNum,"
						+ "o.pay_status AS payStatus,"
						+ "o.pay_end_time AS payEndTime,"
						+ "o.invoice_type AS invoiceType,"
						+ "o.invoice_title AS invoiceTitle,"
						+ "o.tax_iden_num AS taxIdenNum,"
						+ "o.buyer_message AS buyerMessage";
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + list )
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
			List<Object> params = new ArrayList<Object>();
			if(orderIdList != null && orderIdList.size() > 0){
				String orderIds = "'";
				for(String tOrderId :orderIdList){
					orderIds = orderIds + tOrderId + "','";
				}
				sql.append(" AND o.order_id in(").append(orderIds.substring(0, orderIds.length() - 2)).append(")");
			}else{
				if(orderId != null && orderId.length() > 0){
					sql.append(" AND o.order_id = ?");
					params.add(orderId);
				}			
				if(keyword != null && keyword.length() > 0){
					if(keywordType != null && keywordType == 1){
						sql.append(" AND o.receiver_name = ? ");
						params.add(keyword);
					}else if(keywordType != null && keywordType == 2){
						sql.append(" AND o.receiver_phone = ? ");
						params.add(keyword);
					}else if(keywordType != null && keywordType == 3){
						sql.append(" AND o.waybill_no = ? ");
						params.add(keyword);
					}
					
				}
				if(statusFlag != null){
					//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
					if(statusFlag == 1){
						sql.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					}else if(statusFlag == 2){
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					}else if(statusFlag == 3){
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					}else if(statusFlag == 4){
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
						sql.append(" AND o.receiver_time > UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
					}else if(statusFlag == 5){
						sql.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND (");
						sql.append("(");
						sql.append("  (o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());
						sql.append("   or o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId()).append(")");
						sql.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
						sql.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
						sql.append(" )"); 
						sql.append("  OR o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId());
						sql.append(" )");
						
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
			}
			sql.append(" ORDER BY commit_time DESC");
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
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
						
					}
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
	 * 导出订单
	 * @param orderIdList   //订单列表	
	 * @param orderId		//订单号
	 * @param keywordType	//关键字类型 1收货人 2收货人电话 3运单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待付款 2待发货 3待收货 4取消 5退换中6已退 7已换
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单结束时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCountForExport(
			List<String> orderIdList,
			String orderId,
			Integer keywordType,
			String keyword,
			Integer statusFlag,
			Integer payWay,
			Long startTime,
			Long endTime) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND (o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
				.append(" or o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId()).append(")");
			List<Object> params = new ArrayList<Object>();
			if(orderIdList != null && orderIdList.size() > 0){
				String orderIds = "'";
				for(String tOrderId :orderIdList){
					orderIds = orderIds + tOrderId + "','";
				}
				sql.append(" AND o.order_id in(").append(orderIds.substring(0, orderIds.length() - 2)).append(")");
			}else{
				if(orderId != null && orderId.length() > 0){
					sql.append(" AND o.order_id = ?");
					params.add(orderId);
				}			
				if(keyword != null && keyword.length() > 0){
					if(keywordType != null && keywordType == 1){
						sql.append(" AND o.receiver_name = ? ");
						params.add(keyword);
					}else if(keywordType != null && keywordType == 2){
						sql.append(" AND o.receiver_phone = ? ");
						params.add(keyword);
					}else if(keywordType != null && keywordType == 3){
						sql.append(" AND o.waybill_no = ? ");
						params.add(keyword);
					}
					
				}
				if(statusFlag != null){
					//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
					if(statusFlag == 1){
						sql.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
						sql.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					}else if(statusFlag == 2){
						sql.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					}else if(statusFlag == 3){
						sql.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
					}else if(statusFlag == 4){
						sql.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId());
						sql.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
						sql.append(" AND o.receiver_time > UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
					}else if(statusFlag == 5){
						sql.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId());
						sql.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
						sql.append(" AND (");
						sql.append("(");
						sql.append("  (o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());
						sql.append("   or o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId()).append(")");
						sql.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
						sql.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid");
						sql.append(" )"); 
						sql.append("  OR o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId());
						sql.append("  OR o.settle_status = ").append(SettleStatus.COMMITED.getId());
						sql.append(" )");
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
			}
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 订单详细(订单管理)
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> findT(String orderId) throws NegativeException {
		String field = "o.order_id AS orderId,"
				+ "o.dispatch_way AS dispatchWay,"
				+ "o.invoice_type AS invoiceType,"
				+ "o.invoice_title AS invoiceTitle,"
				+ "o.tax_iden_num AS taxIdenNum,"
				+ "o.order_price AS orderPrice,"
				+ "o.freight_price AS freightPrice,"
				+ "o.pay_price AS payPrice,"
				+ "o.pay_way AS payWay,"
				+ "o.commit_time AS commintTime,"
				+ "o.pay_start_time AS payStartTime,"
				+ "o.pay_end_time AS payEndTime,"
				+ "o.pay_trade_no AS payTradeNo,"
				+ "o.buyer_id AS buyerId,"
				+ "o.buyer_name AS buyerName,"
				+ "o.buyer_phone AS buyerPhone,"
				+ "o.buyer_message AS buyerMessage,"
				+ "o.goods_id AS goodsId,"
				+ "o.goods_no AS goodsNo,"
				+ "o.goods_name AS goodsName,"
				+ "o.property_id AS propertyId,"
				+ "o.property_desc AS propertyDesc,"
				+ "o.goods_unit_price AS goodsUnitPrice,"
				+ "o.goods_market_price AS goodsMarketPrice,"
				+ "o.goods_num AS goodsNum,"
				+ "o.goods_type AS goodsType,"
				+ "o.dealer_user_id AS dealerUserId,"
				+ "o.dealer_id AS dealerId,"
				+ "o.dealer_name AS dealerName,"
				+ "o.dealer_phone AS dealerPhone,"
				+ "o.dealer_strategy AS dealerStrategy,"
				+ "o.dealer_percent AS dealerPercent,"
				+ "o.mres_id AS mresId,"
				+ "o.mres_name AS mresName,"
				+ "o.media_user_id AS mediaUserId,"
				+ "o.media_id AS mediaId,"
				+ "o.media_name AS mediaName,"
				+ "o.media_phone AS mediaPhone,"
				+ "o.media_strategy AS mediaStrategy,"
				+ "o.media_percent AS mediaPercent,"
				+ "o.saler_user_id AS salerUserId,"
				+ "o.saler_id AS salerId,"
				+ "o.saler_name AS salerName,"
				+ "o.saler_phone AS salerPhone,"
				+ "o.saler_strategy AS salerStrategy,"
				+ "o.saler_percent AS salerPercent,"
				+ "o.receiver_id AS receiverId,"
				+ "o.receiver_name AS receiverName,"
				+ "o.province_code AS provinceCode,"
				+ "o.province_name AS provinceName,"
				+ "o.city_code AS cityCode,"
				+ "o.city_name AS cityName,"
				+ "o.area_code AS areaCode,"
				+ "o.area_name AS areaName,"
				+ "o.receiver_addr AS receiverAddr,"
				+ "o.receiver_phone AS receiverPhone,"
				+ "o.receiver_zip_code AS receiverZipCode,"
				+ "o.exp_company_code AS expCompanyCode,"
				+ "o.exp_company_name AS expCompanyName,"
				+ "o.waybill_no AS waybillNo,"
				+ "o.commit_wayb_time AS commitWaybTime,"
				+ "o.receiver_time AS receiverTime,"
				+ "o.order_status AS orderStatus,"
				+ "o.pay_status AS payStatus,"
				+ "o.logistics_status AS logisticsStatus,"
				+ "o.settle_status AS settleStatus,"
				+ "o.after_sales_status AS afterSalesStatus,"
				+ "o.comment_status AS commentStatus,"
				+ "o.platform_strategy AS platformStrategy,"
				+ "o.platform_percent AS platformPercent";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	

	/**
	 * 供应商 待付款数
	 * @param commentId
	 * @return
	 */
	public Integer waitpayCount(String dealerId)throws NegativeException {
		return this.listCountForDealer(dealerId,null,null,null,null,1,null,null,null);
		/*try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o ")
					.append(" WHERE 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId())
					.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId())
					.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" AND o.dealer_id = ?");
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}*/
		
	}
	
	/**
	 * 供应商 待发货数
	 * @param commentId
	 * @return
	 */
	public Integer waitSendGoodsCount(String dealerId) throws NegativeException {
		return this.listCountForDealer(dealerId,null,null,null,null,2,null,null,null);
		/*try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o ")
					.append(" WHERE 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId())
					.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" AND o.dealer_id = ?");
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}*/
		
	}
	
	/**
	 * 供应商待收货数
	 * @param commentId
	 * @return
	 */
	public Integer waitReceiverGoodsCount(String dealerId)throws NegativeException{
		return this.listCountForDealer(dealerId,null,null,null,null,3,null,null,null);
		/*try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o ")
					.append(" WHERE 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId())
					.append(" AND o.after_sales_status != ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" AND o.after_sales_status != ").append(AfterSalesStatus.FINISHED.getId())
					.append(" AND o.dealer_id = ?");
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}*/
		
	}
	
	/**
	 * 今日下单
	 * @param commentId
	 * @return
	 */
	public Integer curDayCommitCount(String dealerId) throws NegativeException{
		try{
			return this.listCountForDealer(dealerId,null,null,null,null,null,null,getdayStart(),getdayEnd());
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		/*try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o ")
					.append(" WHERE 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					//.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId())
					//.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId())
					//.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" AND o.dealer_id = ?")
					.append(" AND o.commit_time > ").append(getdayStart())
					.append(" AND o.commit_time < ").append(getdayEnd());
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}*/
		
	}
	
	/**
	 * 今日发货
	 * @param commentId
	 * @return
	 */
	public Integer curDaySendedGoodsCount(String dealerId){
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o ")
					.append(" WHERE 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId())
					.append(" AND o.after_sales_status =").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" AND o.dealer_id = ?")
					.append(" AND o.commit_time > ").append(getdayStart())
					.append(" AND o.commit_time < ").append(getdayEnd());
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}

	/**
	 * 通过Id列表获取要结算的订单信息
	 * @param orderIdList
	 * @return
	 * @throws NegativeException
	 */
	public List<?> settleListByIdList(List<String> orderIdList) throws NegativeException{
		try{
			String field = "o.order_id AS orderId,"
					+ "o.order_no AS orderNo,"
					+ "o.dispatch_way AS dispatchWay,"
					+ "o.invoice_type AS invoiceType,"
					+ "o.invoice_title AS invoiceTitle,"
					+ "o.tax_iden_num AS taxIdenNum,"
					+ "o.order_price AS orderPrice,"
					+ "o.freight_price AS freightPrice,"
					+ "o.pay_price AS payPrice,"
					+ "o.pay_way AS payWay,"
					+ "o.commit_time AS commintTime,"
					+ "o.pay_start_time AS payStartTime,"
					+ "o.pay_end_time AS payEndTime,"
					+ "o.pay_trade_no AS payTradeNo,"
					+ "o.buyer_id AS buyerId,"
					+ "o.buyer_name AS buyerName,"
					+ "o.buyer_phone AS buyerPhone,"
					+ "o.buyer_message AS buyerMessage,"
					+ "o.goods_id AS goodsId,"
					+ "o.goods_no AS goodsNo,"
					+ "o.goods_name AS goodsName,"
					+ "o.property_id AS propertyId,"
					+ "o.property_desc AS propertyDesc,"
					+ "o.goods_unit_price AS goodsUnitPrice,"
					+ "o.goods_market_price AS goodsMarketPrice,"
					+ "o.goods_num AS goodsNum,"
					+ "o.goods_type AS goodsType,"
					+ "o.dealer_user_id AS dealerUserId,"
					+ "o.dealer_id AS dealerId,"
					+ "o.dealer_name AS dealerName,"
					+ "o.dealer_phone AS dealerPhone,"
					+ "o.dealer_strategy AS dealerStrategy,"
					+ "o.dealer_percent AS dealerPercent,"
					+ "o.mres_id AS mresId,"
					+ "o.mres_no AS mresNo,"
					+ "o.mres_name AS mresName,"
					+ "o.media_user_id AS mediaUserId,"
					+ "o.media_id AS mediaId,"
					+ "o.media_name AS mediaName,"
					+ "o.media_phone AS mediaPhone,"
					+ "o.media_strategy AS mediaStrategy,"
					+ "o.media_percent AS mediaPercent,"
					+ "o.saler_user_id AS salerUserId,"
					+ "o.saler_id AS salerId,"
					+ "o.saler_name AS salerName,"
					+ "o.saler_phone AS salerPhone,"
					+ "o.saler_strategy AS salerStrategy,"
					+ "o.saler_percent AS salerPercent,"
					+ "o.receiver_id AS receiverId,"
					+ "o.receiver_name AS receiverName,"
					+ "o.province_code AS provinceCode,"
					+ "o.province_name AS provinceName,"
					+ "o.city_code AS cityCode,"
					+ "o.city_name AS cityName,"
					+ "o.area_code AS areaCode,"
					+ "o.area_name AS areaName,"
					+ "o.receiver_addr AS receiverAddr,"
					+ "o.receiver_phone AS receiverPhone,"
					+ "o.receiver_zip_code AS receiverZipCode,"
					+ "o.exp_company_code AS expCompanyCode,"
					+ "o.exp_company_name AS expCompanyName,"
					+ "o.waybill_no AS waybillNo,"
					+ "o.commit_wayb_time AS commitWaybTime,"
					+ "o.receiver_time AS receiverTime,"
					+ "o.order_status AS orderStatus,"
					+ "o.pay_status AS payStatus,"
					+ "o.logistics_status AS logisticsStatus,"
					+ "o.settle_status AS settleStatus,"
					+ "o.after_sales_status AS afterSalesStatus,"
					+ "o.comment_status AS commentStatus,"
					+ "o.platform_strategy AS platformStrategy,"
					+ "o.platform_percent AS platformPercent,"
					+ "o.refund_amount AS refundAmount";		
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.settle_status = ").append(SettleStatus.WAITING.getId())
					.append(" AND (o.after_sales_status = ").append(AfterSalesStatus.FINISHED.getId())
					.append(" or (")
					.append("  o.logistics_status = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId())
					.append("  AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append("  AND o.receiver_time <= UNIX_TIMESTAMP(now())*1000 - o.after_sales_valid")
					.append(" )").append(" )");
			
			if(orderIdList == null || orderIdList.size() == 0){
				return null;
			}
			String orderIds = "'";
			for(String orderId :orderIdList){
				orderIds = orderIds + orderId + "','";
			}
			sql.append(" AND o.order_id in(").append(orderIds.substring(0, orderIds.length() - 2)).append(")");
			
			List<Object> params = new ArrayList<Object>();	
			
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
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 订单详细
	 * @param orderId		//订单 ID
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> orderById(String orderId) throws NegativeException {
		String field = "o.order_id AS orderId,"
				+ "o.order_no AS orderNo,"
				+ "o.dispatch_way AS dispatchWay,"
				+ "o.invoice_type AS invoiceType,"
				+ "o.invoice_title AS invoiceTitle,"
				+ "o.tax_iden_num AS taxIdenNum,"
				+ "o.order_price AS orderPrice,"
				+ "o.freight_price AS freightPrice,"
				+ "o.pay_price AS payPrice,"
				+ "o.pay_way AS payWay,"
				+ "o.commit_time AS commintTime,"
				+ "o.pay_start_time AS payStartTime,"
				+ "o.pay_end_time AS payEndTime,"
				+ "o.pay_trade_no AS payTradeNo,"
				+ "o.buyer_id AS buyerId,"
				+ "o.buyer_name AS buyerName,"
				+ "o.buyer_phone AS buyerPhone,"
				+ "o.buyer_message AS buyerMessage,"
				+ "o.goods_id AS goodsId,"
				+ "o.goods_no AS goodsNo,"
				+ "o.goods_name AS goodsName,"
				+ "o.property_id AS propertyId,"
				+ "o.property_desc AS propertyDesc,"
				+ "o.goods_unit_price AS goodsUnitPrice,"
				+ "o.goods_market_price AS goodsMarketPrice,"
				+ "o.goods_num AS goodsNum,"
				+ "o.goods_type AS goodsType,"
				+ "o.dealer_user_id AS dealerUserId,"
				+ "o.dealer_id AS dealerId,"
				+ "o.dealer_name AS dealerName,"
				+ "o.dealer_phone AS dealerPhone,"
				+ "o.dealer_strategy AS dealerStrategy,"
				+ "o.dealer_percent AS dealerPercent,"
				+ "o.mres_id AS mresId,"
				+ "o.mres_no AS mresNo,"
				+ "o.mres_name AS mresName,"
				+ "o.media_user_id AS mediaUserId,"
				+ "o.media_id AS mediaId,"
				+ "o.media_name AS mediaName,"
				+ "o.media_phone AS mediaPhone,"
				+ "o.media_strategy AS mediaStrategy,"
				+ "o.media_percent AS mediaPercent,"
				+ "o.saler_user_id AS salerUserId,"
				+ "o.saler_id AS salerId,"
				+ "o.saler_name AS salerName,"
				+ "o.saler_phone AS salerPhone,"
				+ "o.saler_strategy AS salerStrategy,"
				+ "o.saler_percent AS salerPercent,"
				+ "o.receiver_id AS receiverId,"
				+ "o.receiver_name AS receiverName,"
				+ "o.province_code AS provinceCode,"
				+ "o.province_name AS provinceName,"
				+ "o.city_code AS cityCode,"
				+ "o.city_name AS cityName,"
				+ "o.area_code AS areaCode,"
				+ "o.area_name AS areaName,"
				+ "o.receiver_addr AS receiverAddr,"
				+ "o.receiver_phone AS receiverPhone,"
				+ "o.receiver_zip_code AS receiverZipCode,"
				+ "o.exp_company_code AS expCompanyCode,"
				+ "o.exp_company_name AS expCompanyName,"
				+ "o.waybill_no AS waybillNo,"
				+ "o.commit_wayb_time AS commitWaybTime,"
				+ "o.receiver_time AS receiverTime,"
				+ "o.order_status AS orderStatus,"
				+ "o.pay_status AS payStatus,"
				+ "o.logistics_status AS logisticsStatus,"
				+ "o.settle_status AS settleStatus,"
				+ "o.after_sales_status AS afterSalesStatus,"
				+ "o.comment_status AS commentStatus,"
				+ "o.platform_strategy AS platformStrategy,"
				+ "o.platform_percent AS platformStrategy";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_id = ? ");
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 订单详细(订单付款时)
	 * @param orderNo		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> payOrderByNo(String orderNo) throws NegativeException {
		String field = "o.order_id AS orderId,"
				+ "o.order_no AS orderNo,"
				+ "o.dispatch_way AS dispatchWay,"
				+ "o.invoice_type AS invoiceType,"
				+ "o.invoice_title AS invoiceTitle,"
				+ "o.tax_iden_num AS taxIdenNum,"
				+ "o.order_price AS orderPrice,"
				+ "o.freight_price AS freightPrice,"
				+ "o.pay_price AS payPrice,"
				+ "o.pay_way AS payWay,"
				+ "o.commit_time AS commintTime,"
				+ "o.pay_start_time AS payStartTime,"
				+ "o.pay_end_time AS payEndTime,"
				+ "o.pay_trade_no AS payTradeNo,"
				+ "o.buyer_id AS buyerId,"
				+ "o.buyer_name AS buyerName,"
				+ "o.buyer_phone AS buyerPhone,"
				+ "o.buyer_message AS buyerMessage,"
				+ "o.goods_id AS goodsId,"
				+ "o.goods_no AS goodsNo,"
				+ "o.goods_name AS goodsName,"
				+ "o.property_id AS propertyId,"
				+ "o.property_desc AS propertyDesc,"
				+ "o.goods_unit_price AS goodsUnitPrice,"
				+ "o.goods_market_price AS goodsMarketPrice,"
				+ "o.goods_num AS goodsNum,"
				+ "o.goods_type AS goodsType,"
				+ "o.dealer_user_id AS dealerUserId,"
				+ "o.dealer_id AS dealerId,"
				+ "o.dealer_name AS dealerName,"
				+ "o.dealer_phone AS dealerPhone,"
				+ "o.dealer_strategy AS dealerStrategy,"
				+ "o.dealer_percent AS dealerPercent,"
				+ "o.mres_id AS mresId,"
				+ "o.mres_no AS mresNo,"
				+ "o.mres_name AS mresName,"
				+ "o.media_user_id AS mediaUserId,"
				+ "o.media_id AS mediaId,"
				+ "o.media_name AS mediaName,"
				+ "o.media_phone AS mediaPhone,"
				+ "o.media_strategy AS mediaStrategy,"
				+ "o.media_percent AS mediaPercent,"
				+ "o.saler_user_id AS salerUserId,"
				+ "o.saler_id AS salerId,"
				+ "o.saler_name AS salerName,"
				+ "o.saler_phone AS salerPhone,"
				+ "o.saler_strategy AS salerStrategy,"
				+ "o.saler_percent AS salerPercent,"
				+ "o.receiver_id AS receiverId,"
				+ "o.receiver_name AS receiverName,"
				+ "o.province_code AS provinceCode,"
				+ "o.province_name AS provinceName,"
				+ "o.city_code AS cityCode,"
				+ "o.city_name AS cityName,"
				+ "o.area_code AS areaCode,"
				+ "o.area_name AS areaName,"
				+ "o.receiver_addr AS receiverAddr,"
				+ "o.receiver_phone AS receiverPhone,"
				+ "o.receiver_zip_code AS receiverZipCode,"
				+ "o.exp_company_code AS expCompanyCode,"
				+ "o.exp_company_name AS expCompanyName,"
				+ "o.waybill_no AS waybillNo,"
				+ "o.commit_wayb_time AS commitWaybTime,"
				+ "o.receiver_time AS receiverTime,"
				+ "o.order_status AS orderStatus,"
				+ "o.pay_status AS payStatus,"
				+ "o.logistics_status AS logisticsStatus,"
				+ "o.settle_status AS settleStatus,"
				+ "o.after_sales_status AS afterSalesStatus,"
				+ "o.comment_status AS commentStatus,"
				+ "o.platform_strategy AS platformStrategy,"
				+ "o.platform_percent AS platformStrategy";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_no = ? ")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.WAITING.getId());
			List<Object> params = new ArrayList<Object>();
			params.add(orderNo);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 获取要退款的订单
	 * @param orderId		//订单编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> refundOrderById(String orderId) throws NegativeException {
		String field = "o.order_id AS orderId,"
				+ "o.order_no AS orderNo,"
				+ "o.dispatch_way AS dispatchWay,"
				+ "o.invoice_type AS invoiceType,"
				+ "o.invoice_title AS invoiceTitle,"
				+ "o.tax_iden_num AS taxIdenNum,"
				+ "o.order_price AS orderPrice,"
				+ "o.freight_price AS freightPrice,"
				+ "o.pay_price AS payPrice,"
				+ "o.pay_way AS payWay,"
				+ "o.commit_time AS commintTime,"
				+ "o.pay_start_time AS payStartTime,"
				+ "o.pay_end_time AS payEndTime,"
				+ "o.pay_trade_no AS payTradeNo,"
				+ "o.buyer_id AS buyerId,"
				+ "o.buyer_name AS buyerName,"
				+ "o.buyer_phone AS buyerPhone,"
				+ "o.buyer_message AS buyerMessage,"
				+ "o.goods_id AS goodsId,"
				+ "o.goods_no AS goodsNo,"
				+ "o.goods_name AS goodsName,"
				+ "o.property_id AS propertyId,"
				+ "o.property_desc AS propertyDesc,"
				+ "o.goods_unit_price AS goodsUnitPrice,"
				+ "o.goods_market_price AS goodsMarketPrice,"
				+ "o.goods_num AS goodsNum,"
				+ "o.goods_type AS goodsType,"
				+ "o.dealer_user_id AS dealerUserId,"
				+ "o.dealer_id AS dealerId,"
				+ "o.dealer_name AS dealerName,"
				+ "o.dealer_phone AS dealerPhone,"
				+ "o.dealer_strategy AS dealerStrategy,"
				+ "o.dealer_percent AS dealerPercent,"
				+ "o.mres_id AS mresId,"
				+ "o.mres_no AS mresNo,"
				+ "o.mres_name AS mresName,"
				+ "o.media_user_id AS mediaUserId,"
				+ "o.media_id AS mediaId,"
				+ "o.media_name AS mediaName,"
				+ "o.media_phone AS mediaPhone,"
				+ "o.media_strategy AS mediaStrategy,"
				+ "o.media_percent AS mediaPercent,"
				+ "o.saler_user_id AS salerUserId,"
				+ "o.saler_id AS salerId,"
				+ "o.saler_name AS salerName,"
				+ "o.saler_phone AS salerPhone,"
				+ "o.saler_strategy AS salerStrategy,"
				+ "o.saler_percent AS salerPercent,"
				+ "o.receiver_id AS receiverId,"
				+ "o.receiver_name AS receiverName,"
				+ "o.province_code AS provinceCode,"
				+ "o.province_name AS provinceName,"
				+ "o.city_code AS cityCode,"
				+ "o.city_name AS cityName,"
				+ "o.area_code AS areaCode,"
				+ "o.area_name AS areaName,"
				+ "o.receiver_addr AS receiverAddr,"
				+ "o.receiver_phone AS receiverPhone,"
				+ "o.receiver_zip_code AS receiverZipCode,"
				+ "o.exp_company_code AS expCompanyCode,"
				+ "o.exp_company_name AS expCompanyName,"
				+ "o.waybill_no AS waybillNo,"
				+ "o.commit_wayb_time AS commitWaybTime,"
				+ "o.receiver_time AS receiverTime,"
				+ "o.order_status AS orderStatus,"
				+ "o.pay_status AS payStatus,"
				+ "o.logistics_status AS logisticsStatus,"
				+ "o.settle_status AS settleStatus,"
				+ "o.after_sales_status AS afterSalesStatus,"
				+ "o.comment_status AS commentStatus,"
				+ "o.platform_strategy AS platformStrategy,"
				+ "o.platform_percent AS platformStrategy";
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field)
					.append(" FROM t_order_order o,t_order_after_sales f where o.order_id = f.order_id ")
					.append(" AND o.order_id = ? ")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.logistics_status  = ").append(LogisticsStatus.RECEIPTED_GOOGS.getId())
					.append(" AND o.settle_status = ").append(SettleStatus.WAITING.getId())
					.append(" AND o.after_sales_status = ").append(AfterSalesStatus.AFTER_SALES_INHAND.getId())
					.append(" AND f.fsales_status = ").append(FsalesStatus.APPLY_PASS.getId());
			
			List<Object> params = new ArrayList<Object>();
			params.add(orderId);
			Map<String,Object> resultMap = jdbcTemplate.queryForObject(sql.toString(),params.toArray(),new RowMapper<Map<String,Object>>(){
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
			return resultMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}	
	
	/**
	 * 查询未确认收货的订单
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> waitReceiptList(Integer pageNumber,Integer rows) throws NegativeException{
		try{

			String list =   "o.order_id AS orderId";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + list )
					.append(" FROM t_order_order o where 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());			
			List<Object> params = new ArrayList<Object>();
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
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
					}
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
	 * 查询未确认收货的订单数
	 * @return
	 * @throws NegativeException
	 */
	public Integer waitReceiptCount() throws NegativeException{
		StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
				.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId())
				.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
				.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_RECEIPT_GOOGS.getId());	
			List<Object> params = new ArrayList<Object>();	
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
	}
	
	/**
	 * 待处理订单数
	 * @return
	 */
	public Integer waitHandingCount(){
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_order o ")
					.append(" WHERE 1=1")
					.append(" AND o.order_status = ").append(OrderStatus.NORMAL.getId())
					.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
					.append(" AND o.logistics_status = ").append(LogisticsStatus.WAIT_SEND_GOOGS.getId())
					.append(" AND o.after_sales_status = ").append(AfterSalesStatus.WAIT_APPLY.getId());
			Integer count = jdbcTemplate.queryForObject(sql.toString(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 今日媒体订单数
	 * @param mediaId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws NegativeException
	 */
	public Integer curDayMediaOrderCount(
			String mediaId) throws NegativeException {
		try{
			Long startTime = getdayStart();
			Long endTime = getdayEnd();
			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId())
				.append(" AND o.media_id = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(mediaId);
			
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
	
	
	public Integer curDayMresOrderCount(String mresId,String mresNo){
		try{
			Long startTime = getdayStart();
			Long endTime = getdayEnd();
			StringBuffer sql = new StringBuffer()
				.append("SELECT count(1)")
				.append(" FROM t_order_order o where 1=1")
				.append(" AND o.pay_status = ").append(PayStatus.COMMITED.getId());
			
			List<Object> params = new ArrayList<Object>();
			if(mresId != null && mresId != null){
				sql.append(" AND o.mres_id = ?");
				params.add(mresId);
			}
			if(mresNo != null && mresNo != null){
				sql.append(" AND o.mres_no = ?");
				params.add(mresNo);
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
 	private Long getdayStart() throws ParseException{
		SimpleDateFormat dayDf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String sCurDay = dayDf.format(new Date());
		SimpleDateFormat minDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Date date = minDf.parse(sCurDay + " 00:00:00");
		return date.getTime();
	}
	
	private Long getdayEnd() throws ParseException{
		SimpleDateFormat dayDf = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String sCurDay = dayDf.format(new Date());
		SimpleDateFormat minDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		Date date = minDf.parse(sCurDay + " 23:59:59");
		return date.getTime();
	}
	
	public static void main(String[] args) throws ParseException { 

	}

}
