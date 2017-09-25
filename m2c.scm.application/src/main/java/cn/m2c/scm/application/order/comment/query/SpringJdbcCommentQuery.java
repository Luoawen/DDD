package cn.m2c.scm.application.order.comment.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.comment.ReplyStatus;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

/**
 * 
 * @ClassName: SpringJdbcOrderQuery
 * @Description: 订单查询实现
 * @author moyj
 * @date 2017年4月27日 下午4:41:22
 *
 */
@Repository
public class SpringJdbcCommentQuery {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final String field = "c.comment_id AS commentId,"
							+ "c.order_id AS orderId,"
							+ "c.order_no AS orderNo,"
							+ "c.goods_id AS goodsId,"
							+ "c.goods_name AS goodsName,"
							+ "c.dealer_id AS dealerId,"
							+ "c.dealer_name AS dealerName,"
							+ "c.buyer_id AS buyerId,"
							+ "c.buyer_name AS buyerName,"
							+ "c.comment_content AS commentContent,"
							+ "c.comment_level AS commentLevel,"
							+ "c.reply_content AS replyContent,"
							+ "c.comment_level AS commentLevel,"
							+ "c.star_level AS starLevel,"
							+ "c.comment_status AS commentStatus,"
							+ "c.reply_status AS replyStatus,"
							+ "c.top_status AS topStatus,"
							+ "c.hide_status AS hideStatus,"
							+ "c.deleted_by_admin AS deletedByAdmin,"
							+ "c.comment_time AS commentTime,"
							+ "c.reply_time AS replyTime,"
							+ "c.comment_time AS commentTime,"
							+ "c.buying_time AS buyingTime";
	
	/**
	 * 评论详细
	 * @param commentId			//评论编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> findT(String commentId) throws NegativeException {
		try{
			String sql = "SELECT " + field + " FROM t_order_comment c WHERE c.comment_id = ? ";
			Map<String,Object> vo = jdbcTemplate.queryForMap(sql, new Object[]{commentId});		
			return vo;

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 评论列表
	 * @param commentId			//评论编号
	 * @param orderId			//订单编号
	 * @param goodsKeyword		//商品关键字
	 * @param dealerKeyword		//供应商关键字
	 * @param buyerKeyword		//买家关键字
	 * @param keyword			//关键字
	 * @param commentLevel		//评论级别
	 * @param replyStatus		//回复状态
	 * @param startTime			//开始时间
	 * @param endTime			//结算时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> list(
			String commentId,
			String orderId,
			String goodsKeyword,
			String dealerKeyword,
			String buyerKeyword,
			Integer commentLevel,
			Integer replyStatus,
			Long startTime,
			Long endTime,
			Integer pageNumber,Integer rows) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1");
			List<Object> params = new ArrayList<Object>();
			
			if(commentId != null && commentId.length() > 0){
				sql.append(" AND c.comment_id = ?");
				params.add(commentId);
			}
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND c.order_id = ?");
				params.add(orderId);
			}
			if(goodsKeyword != null && goodsKeyword.length() > 0){
				sql.append(" AND (c.goods_id = ? or c.goods_name = ?)");
				params.add(goodsKeyword);
				params.add(goodsKeyword);
			}
			if(dealerKeyword != null && dealerKeyword.length() > 0){
				sql.append(" AND (c.dealer_id = ? or c.dealer_name = ?)");
				params.add(dealerKeyword);
				params.add(dealerKeyword);
			}
			if(buyerKeyword != null && buyerKeyword.length() > 0){
				sql.append(" AND (c.buyer_id = ? or c.buyer_name = ?)");
				params.add(buyerKeyword);
				params.add(buyerKeyword);
			}
			
			if(replyStatus != null){
				sql.append(" AND c.reply_status  = ?");
				params.add(replyStatus);
			}
			if(commentLevel != null){
				sql.append(" AND c.comment_level = ?");
				params.add(commentLevel);
			}	
			if(startTime != null && endTime != null){
				sql.append(" AND (c.comment_time >= ? AND c.comment_time < ?)");
				params.add(startTime);
				params.add(endTime);
			}
			else if(startTime != null && endTime == null){
				sql.append(" AND c.comment_time >= ?");
				params.add(startTime);
			}	
			else if(endTime != null && startTime == null){
				sql.append(" AND c.comment_time < ?");
				params.add(endTime);
			}
			sql.append(" ORDER BY comment_time DESC");
			sql.append(" limit ?,?");
			
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql.toString(), params.toArray());		
			return resultList;
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 评论列表数
	 * @param commentId			//评论编号
	 * @param orderId			//订单编号
	 * @param goodsKeyword		//商品关键字
	 * @param dealerKeyword		//供应商关键字
	 * @param buyerKeyword		//买家关键字
	 * @param keyword			//关键字
	 * @param commentLevel		//评论级别
	 * @param replyStatus		//回复状态
	 * @param startTime			//开始时间
	 * @param endTime			//结算时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCount(
			String commentId,
			String orderId,
			String goodsKeyword,
			String dealerKeyword,
			String buyerKeyword,
			Integer commentLevel,
			Integer replyStatus,
			Long startTime,
			Long endTime
			) throws NegativeException {
		try{	
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1");
			List<Object> params = new ArrayList<Object>();
			
			if(commentId != null && commentId.length() > 0){
				sql.append(" AND c.comment_id = ?");
				params.add(commentId);
			}
			if(orderId != null && orderId.length() > 0){
				sql.append(" AND c.order_id = ?");
				params.add(orderId);
			}
			if(goodsKeyword != null && goodsKeyword.length() > 0){
				sql.append(" AND (c.goods_id = ? or c.goods_name = ?)");
				params.add(goodsKeyword);
				params.add(goodsKeyword);
			}
			if(dealerKeyword != null && dealerKeyword.length() > 0){
				sql.append(" AND (c.dealer_id = ? or c.dealer_name = ?)");
				params.add(dealerKeyword);
				params.add(dealerKeyword);
			}
			if(buyerKeyword != null && buyerKeyword.length() > 0){
				sql.append(" AND (c.buyer_id = ? or c.buyer_name = ?)");
				params.add(buyerKeyword);
				params.add(buyerKeyword);
			}
			
			if(replyStatus != null){
				sql.append(" AND c.reply_status  = ?");
				params.add(replyStatus);
			}
			if(commentLevel != null){
				sql.append(" AND c.comment_level = ?");
				params.add(commentLevel);
			}	
			if(startTime != null && endTime != null){
				sql.append(" AND (c.comment_time >= ? AND c.comment_time < ?)");
				params.add(startTime);
				params.add(endTime);
			}
			else if(startTime != null && endTime == null){
				sql.append(" AND c.comment_time >= ?");
				params.add(startTime);
			}	
			else if(endTime != null && startTime == null){
				sql.append(" AND c.comment_time < ?");
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
	 * 评论列表(供应商)
	 * @param keyword			//关键字
	 * @param commentLevel		//评论级别
	 * @param replyStatus		//回复状态
	 * @param startTime			//开始时间
	 * @param endTime			//结算时间
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listForDealer(
			String dealerId,
			String keyword,
			Integer commentLevel,
			Integer replyStatus,
			Long startTime,
			Long endTime,
			Integer pageNumber,Integer rows) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1")
			        .append(" AND c.dealer_id = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(dealerId);
			if(keyword != null && keyword.length() > 0){
				sql.append(" AND c.comment_content like ? ");
				params.add("%" + keyword + "%");
			}
			if(replyStatus != null){
				sql.append(" AND c.reply_status  = ?");
				params.add(replyStatus);
			}
			if(commentLevel != null){
				sql.append(" AND c.comment_level = ?");
				params.add(commentLevel);
			}	
			if(startTime != null && endTime != null){
				sql.append(" AND (c.comment_time >= ? AND c.comment_time < ?)");
				params.add(startTime);
				params.add(endTime);
			}
			else if(startTime != null && endTime == null){
				sql.append(" AND c.comment_time >= ?");
				params.add(startTime);
			}	
			else if(endTime != null && startTime == null){
				sql.append(" AND c.comment_time < ?");
				params.add(endTime);
			}
			sql.append(" ORDER BY comment_time DESC");
			sql.append(" limit ?,?");
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql.toString(), params.toArray());		
			return resultList;
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 评论列表数(供应商)
	 * @param keyword			//关键字
	 * @param commentLevel		//评论级别
	 * @param replyStatus		//回复状态
	 * @param startTime			//开始时间
	 * @param endTime			//结算时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCountForDealer(
			String dealerId,
			String keyword,
			Integer commentLevel,
			Integer replyStatus,
			Long startTime,
			Long endTime
			) throws NegativeException {
		try{	
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1")
			   		.append(" AND c.dealer_id = ?");
			List<Object> params = new ArrayList<Object>();
			params.add(dealerId);
			
			if(keyword != null && keyword.length() > 0){
				sql.append(" AND c.comment_content like ? ");
				params.add("%" + keyword + "%");
			}
			if(replyStatus != null){
				sql.append(" AND c.reply_status  = ?");
				params.add(replyStatus);
			}
			if(commentLevel != null){
				sql.append(" AND c.comment_level = ?");
				params.add(commentLevel);
			}	
			if(startTime != null && endTime != null){
				sql.append(" AND (c.comment_time >= ? AND c.comment_time < ?)");
				params.add(startTime);
				params.add(endTime);
			}
			else if(startTime != null && endTime == null){
				sql.append(" AND c.comment_time >= ?");
				params.add(startTime);
			}	
			else if(endTime != null && startTime == null){
				sql.append(" AND c.comment_time < ?");
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
	 * 评论详细(供应商)
	 * @param commentId			//评论编号
	 * @return
	 * @throws NegativeException
	 */
	public Map<String,Object> detailForDealer(String commentId) throws NegativeException {
		try{
			String sql = "SELECT " + field + " FROM t_order_comment c WHERE c.comment_id = ? ";
			Map<String,Object> vo = jdbcTemplate.queryForMap(sql, new Object[]{commentId});		
			return vo;

		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 对应供应商的评论数 
	 * @param commentId
	 * @return
	 */
	public Integer commentCountForDealer(String dealerId) throws NegativeException {
		return this.listCountForDealer(dealerId, null, null, null, null, null);
		/*try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c,t_order_order o")
					.append(" WHERE c.order_id = o.order_id ")
					.append(" AND c.comment_status = ").append(CommentStatus.NORMAL.getId())
					.append(" AND c.reply_status = ").append(ReplyStatus.UNREPLY.getId())
					.append(" AND o.dealer_id = ? ");
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}*/
		
	}
	/**
	 * 对应供应商的评论回复数
	 * @param commentId
	 * @return
	 */
	public Integer replyCountForDealer(String dealerId)throws NegativeException{
		return this.listCountForDealer(dealerId, null, null, ReplyStatus.REPLYED.getId(), null, null);
		/*try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c,t_order_order o")
					.append(" WHERE c.order_id = o.order_id ")
					.append(" AND c.comment_status = ").append(CommentStatus.NORMAL.getId())
					.append(" AND c.reply_status = ").append(ReplyStatus.REPLYED.getId())
					.append(" AND o.dealer_id = ?");
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{dealerId},Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}*/
		
	}
	
	/**
	 * 所以未回复的评论数
	 * @return
	 */
	public Integer waitReplyCount(){
		try{	
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1 AND c.reply_status  = ")
					.append(ReplyStatus.UNREPLY.getId());
			List<Object> params = new ArrayList<Object>();
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 评论列表(APP)
	 * @param goodsId			//订单编号
	 * @param commentLevel		//评论级别
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listForApp(
			String goodsId,
			Integer commentLevel,
			Integer pageNumber,Integer rows) throws NegativeException {
		try{
			String field = "c.goods_id AS goodsId,"
					+ "c.goods_name AS goodsName,"
					+ "c.buyer_id AS buyerId,"
					+ "c.buyer_name AS buyerName,"
					+ "c.buyer_icon AS buyerIcon,"
					+ "c.comment_content AS commentContent,"
					+ "c.comment_level AS commentLevel,"
					+ "c.reply_content AS replyContent,"
					+ "c.comment_level AS commentLevel,"
					+ "c.star_level AS starLevel,"
					+ "c.reply_status AS replyStatus,"
					+ "c.comment_time AS commentTime,"
					+ "c.reply_time AS replyTime,"
					+ "o.property_desc AS propertyDesc,"
					+ "c.buying_time AS buyingTime";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_comment c LEFT JOIN t_order_order o ON c.order_id = o.order_id ")
					.append(" where 1=1 and c.comment_status = 1 and c.deleted_by_admin = 1 and c.deleted_by_user = 1 ");
			List<Object> params = new ArrayList<Object>();
			
			if(goodsId != null && goodsId.length() > 0){
				sql.append(" AND c.goods_id = ?");
				params.add(goodsId);
			}
			if(commentLevel != null && commentLevel != 0 ){
				sql.append(" AND c.comment_level = ?");
				params.add(commentLevel);
			}
			Long deadline = (Long) DisconfDataGetter.getByFileItem("constants.properties", "comment.modify.deadline");
			sql.append(" AND (c.comment_level in(1,2)");
			sql.append(" OR (c.comment_level = 3 AND (c.mod_comment_time is null OR c.mod_comment_time < UNIX_TIMESTAMP(DATE_SUB(date_format(now(),'%y-%m-%d %H:%i:%s'), INTERVAL " + deadline + " HOUR))*1000 )))");
			
			sql.append(" ORDER BY top_status DESC,comment_time DESC");
			sql.append(" limit ?,?");
			
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql.toString(), params.toArray());		
			return resultList;
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 评论列表(APP)
	 * @param goodsId			//订单编号
	 * @param commentLevel		//评论级别
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCountForApp(
			String goodsId,
			Integer commentLevel
			) throws NegativeException {
		try{	
			Long deadline = (Long) DisconfDataGetter.getByFileItem("constants.properties", "comment.modify.deadline");
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1 and c.deleted_by_admin = 1 and c.deleted_by_user = 1");
			List<Object> params = new ArrayList<Object>();

			if(goodsId != null && goodsId.length() > 0){
				sql.append(" AND c.goods_id = ?");
				params.add(goodsId);
			}
			if(commentLevel != null && commentLevel != 0){
				sql.append(" AND c.comment_level = ? ");
				params.add(commentLevel);
			}
			sql.append(" AND (c.comment_level in(1,2)");
			sql.append(" OR (c.comment_level = 3 AND (c.mod_comment_time is null OR c.mod_comment_time < UNIX_TIMESTAMP(DATE_SUB(date_format(now(),'%y-%m-%d %H:%i:%s'), INTERVAL " + deadline + " HOUR))*1000 )))");
					
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	

	/**
	 * 评论总数
	 * @return
	 * @throws NegativeException
	 */
	public Integer totalCount(String goodsId) throws NegativeException {
		try{	
			Long deadline = (Long) DisconfDataGetter.getByFileItem("constants.properties", "comment.modify.deadline");
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1 and c.comment_status = 1 and c.deleted_by_admin = 1 and c.deleted_by_user = 1")
					.append(" AND c.goods_id = ?");
			sql.append(" AND (c.comment_level in(1,2)");
			sql.append(" OR (c.comment_level = 3 AND (c.mod_comment_time is null OR c.mod_comment_time < UNIX_TIMESTAMP(DATE_SUB(date_format(now(),'%y-%m-%d %H:%i:%s'), INTERVAL " + deadline + " HOUR))*1000 )))");
			Integer count = jdbcTemplate.queryForObject(sql.toString(),new Object[]{goodsId},Integer.class);
			return count;
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	/** 我的评论列表(APP)
	 * @param userId			//用户ID
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> myList(
			String userId,
			Integer pageNumber,Integer rows) throws NegativeException {
		try{
			String field = 
					"c.comment_id AS commentId,"
					+ "c.goods_id AS goodsId,"
					+ "c.goods_name AS goodsName,"
					+ "c.buyer_id AS buyerId,"
					+ "c.buyer_name AS buyerName,"
					+ "c.buyer_icon AS buyerIcon,"
					+ "c.comment_content AS commentContent,"
					+ "c.comment_level AS commentLevel,"
					+ "c.reply_content AS replyContent,"
					+ "c.comment_level AS commentLevel,"
					+ "c.goods_icon AS goodsIcon,"
					+ "c.goods_unit_price AS goodsUnitPrice,"
					+ "c.star_level AS starLevel,"
					+ "c.reply_status AS replyStatus,"
					+ "c.deleted_by_user AS deletedByUser,"
					+ "c.deleted_by_admin AS deletedByAdmin,"
					+ "c.comment_time AS commentTime,"
					+ "c.reply_time AS replyTime,"
					+ "c.mod_comment_time AS modCommentTime,"
					+ "o.property_desc AS propertyDesc,"
					+ "c.buying_time AS buyingTime";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_comment c LEFT JOIN t_order_order o ON c.order_id = o.order_id ")
					.append(" where 1=1 and c.deleted_by_user = 1");
			List<Object> params = new ArrayList<Object>();
			
			if(userId != null && userId.length() > 0){
				sql.append(" AND c.buyer_id = ?");
				params.add(userId);
			}
			sql.append(" ORDER BY comment_time DESC");
			sql.append(" limit ?,?");
			
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			List<Map<String,Object>> resultList = jdbcTemplate.queryForList(sql.toString(), params.toArray());		
			return resultList;
		
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 我的评论列表(APP)
	 * @param userId			//用户ID
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public Integer myListCount(
			String userId
			) throws NegativeException {
		try{	
			
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)" )
					.append(" FROM t_order_comment c ")
					.append(" where 1=1");
			List<Object> params = new ArrayList<Object>();

			if(userId != null && userId.length() > 0){
				sql.append(" AND c.buyer_id = ?");
				params.add(userId);
			}
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
}
