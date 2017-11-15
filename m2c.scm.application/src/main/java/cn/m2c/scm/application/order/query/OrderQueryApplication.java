package cn.m2c.scm.application.order.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.command.GetOrderCmd;
import cn.m2c.scm.application.order.data.bean.AppOrderBean;
import cn.m2c.scm.application.order.data.bean.AppOrderDtl;
import cn.m2c.scm.application.order.data.bean.DealerOrderBean;
import cn.m2c.scm.application.order.data.bean.OrderDetailBean;
import cn.m2c.scm.application.order.data.bean.OrderExpressBean;
import cn.m2c.scm.application.order.data.bean.SkuNumBean;
import cn.m2c.scm.application.order.data.representation.OptLogBean;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.order.OrderService;

/**
 * 订单查询
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
@Service
public class OrderQueryApplication {
	/**调试打日志用*/
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryApplication.class);
	
	@Resource
    private SupportJdbcTemplate supportJdbcTemplate;
	@Autowired
	OrderService orderService;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }
    /***
     * 商家平台，获取订单列表
     * @return
     */
    public List<DealerOrderBean> getDealerOrderList(String dealerId) {
    	return null;
    }
    
    /***
     * 获取商家订单操作日志列表
     * @return
     * @throws NegativeException 
     */
    public List<OptLogBean> getDealerOrderOptLog(String orderId, String dealerOrderId,Integer pageNum,Integer rows) throws NegativeException {
    	List<OptLogBean> logList = null;
		try {
			StringBuilder sql = new StringBuilder(200);
			List<Object> params = new ArrayList<>(2);
			sql.append("SELECT order_no, dealer_order_no, opt_content, opt_user, created_date FROM t_scm_order_opt_log ");
			sql.append(" WHERE order_no=?");
			if(orderId!= null && !"".equals(orderId)){
				params.add(orderId);
			}
			
			/*if (!StringUtils.isEmpty(dealerOrderId)) {
				sql.append(" AND dealer_order_no=?");
				params.add(dealerOrderId);
			}*/
			sql.append(" ORDER BY created_date DESC ");
			sql.append(" LIMIT ?,?");
			params.add(rows*(pageNum - 1));
			params.add(rows);
			System.out.println("----操作日志列表："+sql.toString());
			logList = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), OptLogBean.class, params.toArray());
		} catch (Exception e) {
			LOGGER.error("---查询日志时出错 ",e);
			throw new NegativeException(500, "查询操作日志时出错 ");
		}
		return logList;
    }
    
    /***
     * 满足条件的记录条数
     * @return
     * @throws NegativeException 
     */
    public Integer getDealerOrderOptLogTotal(String orderId, String dealerOrderId,Integer pageNum,Integer rows) throws NegativeException {
    	Integer total = 0;
		try {
			StringBuilder sql = new StringBuilder(200);
			List<Object> params = new ArrayList<>(2);
			sql.append("SELECT count(1) FROM t_scm_order_opt_log ");
			sql.append(" WHERE order_no=?");
			params.add(orderId);
			/*if (!StringUtils.isEmpty(dealerOrderId)) {
				sql.append(" AND dealer_order_no=?");
				params.add(dealerOrderId);
			}*/
			total = this.getSupportJdbcTemplate().jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("---查询日志条数时出错 ",e);
			throw new NegativeException(500, "查询操作日志条数时出错 ");
		}
		return total;
    }
    
    /***
     * 根据订单号获取订单下的优惠券
     * @param orderId
     * @return
     */
    public List<String> getCouponsByOrderId(String orderId) throws NegativeException {
    	if (StringUtils.isEmpty(orderId))
    		return null;
    	List<String> rs = null;
    	try {
    		rs = supportJdbcTemplate.queryForBeanList("select coupon_id from t_scm_order_coupon_used where order_id=? and _status=1 ", String.class, orderId);
    	}
    	catch (Exception e) {
    		LOGGER.error("===fanjc==获取订单下的优惠券出错",e);
			throw new NegativeException(500, "获取订单下的优惠券出错");
    	}
    	return rs;
    }
    
    /***
     * 根据订单号获取订单下的商品ID及数量
     * @param orderId
     * @return
     */
    public Map<String, Integer> getSkusByOrderId(String orderId) throws NegativeException {
    	if (StringUtils.isEmpty(orderId))
    		return null;
    	Map<String, Integer> rs = null;
    	try {
    		List<SkuNumBean> ls = supportJdbcTemplate.queryForBeanList("select sku_id, sell_num from t_scm_order_detail where order_id=?", SkuNumBean.class, orderId);
    		
    		if (ls == null || ls.size() < 1)
    			return rs;
    		
    		rs = new HashMap<String, Integer>();
    		for (SkuNumBean sb : ls) {
    			rs.put(sb.getSkuId(), sb.getNum());
    		}
    	}
    	catch (Exception e) {
    		LOGGER.error("===fanjc==获取订单下的SKU及数量出错",e);
			throw new NegativeException(500, "获取订单下的SKU及数量出错");
    	}
    	return rs;
    }
    /**
     * 获取商家订单详情
     * @param dealerOrderId
     * @throws NegativeException 
     */
	public DealerOrderBean getDealerOrder(String dealerOrderId) throws NegativeException {
		DealerOrderBean dealerOrderBean = null;
		String sql = "SELECT * FROM t_scm_order_dealer WHERE 1=1 AND dealer_order_id=?";
		try {
			dealerOrderBean = supportJdbcTemplate.queryForBean(sql, DealerOrderBean.class,dealerOrderId);
			if(dealerOrderBean!=null){
				dealerOrderBean.setOrderDtls(getOrderDetail(dealerOrderBean.getDealerOrderId()));
			}
		} catch (Exception e) {
			LOGGER.error("商家订单查询出错",e);
			throw new NegativeException(500, "商家订单查询出错");
		}
		return dealerOrderBean;
	}
	/**
	 * 根据商家订单id获取
	 * @param dealerOrderId
	 * @return
	 * @throws NegativeException 
	 */
	private List<OrderDetailBean> getOrderDetail(String dealerOrderId) throws NegativeException {
		List<OrderDetailBean> orderList = null;
		String sql = "SELECT * FROM t_scm_order_detail WHERE 1=1 AND dealer_order_id=?";
		try {
			orderList = this.supportJdbcTemplate.queryForBeanList(sql, OrderDetailBean.class, dealerOrderId);
			//去掉订单中的审核通过的退货单
			if(orderList!=null && orderList.size()>0){
				for (int i = 0; i < orderList.size(); i++) {
					if(checkIsReturnOrder(orderList.get(i).getSkuId(),orderList.get(i).getDealerOrderId())){
						orderList.remove(i);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("订单查询出错",e);
			throw new NegativeException(500, "订单查询出错");
		}
		return orderList;
	}
	/**
	 * 判断此sku是否走售后流程（如果售后流程就不显示出来）
	 * @param skuId
	 * @param dealerOrderId
	 * @throws NegativeException 
	 */
	private boolean checkIsReturnOrder(String skuId, String dealerOrderId) throws NegativeException {
		boolean isReturnOrder = false;
		List<Object> param = new ArrayList<Object>();
		try {
			param.add(skuId);
			param.add(dealerOrderId);
			String sql = "SELECT count(*) FROM t_scm_order_after_sell WHERE _status=9 AND sku_id=? AND dealer_order_id=?";
			Integer returnOrderCount =this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql,Integer.class,param.toArray()); 
			if(returnOrderCount!=null && returnOrderCount==1){
				isReturnOrder =  true;
			}
		} catch (Exception e) {
			LOGGER.error("---判断sku是否是售后单出错",e);
			throw new NegativeException(500, "判断sku是否是售后单出错");
		}
		return isReturnOrder;
	}
	/**
	 * 查询所有的物流公司信息
	 * @return
	 * @throws NegativeException 
	 */
	public List<OrderExpressBean> getAllExpress() throws NegativeException {
		List<OrderExpressBean> expressList = null;
		try {
			String sql = "SELECT * FROM t_scm_order_exp_company WHERE exp_status=1";
			expressList = this.getSupportJdbcTemplate().queryForBeanList(sql, OrderExpressBean.class);
		} catch (Exception e) {
			LOGGER.error("---查询物流公司列表出错",e);
			throw new NegativeException(500, "查询物流公司列表出错");
		}
		return expressList;
	}
	/***
	 * 获取APP订单列表页面
	 * @param userId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws NegativeException
	 */
	public List<AppOrderBean> getAppOrderList(String userId, Integer status, Integer commentStatus,
			int pageIndex, int pageSize, String keyword) throws NegativeException {
		List<AppOrderBean> result = null;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.province_code, a.province, a.city, a.city_code, a.area_code, a.area_county, a.street_addr\r\n")
			.append(", a.order_freight, a.order_id, a.goods_amount, a.plateform_discount, a.dealer_discount\r\n")
			.append(", b.invoice_code, b.invoice_header, b.invoice_name, b.invoice_type, a.created_date, b._status\r\n") 
			.append(", b.dealer_id, c.dealer_name, b.dealer_order_id\r\n") 
			.append("FROM t_scm_order_dealer b \r\n")
			.append("LEFT OUTER JOIN t_scm_order_main a ON a.order_id=b.order_id \r\n") 
			.append("LEFT OUTER JOIN t_scm_dealer c ON c.dealer_id = b.dealer_id \r\n")
			.append("WHERE a.user_id=?  AND a.del_flag=0 AND b.del_flag=0 ");
			params.add(userId);
			
			if (commentStatus != null && commentStatus == 1) {
				sql.append(" AND b.dealer_order_id in (SELECT e.dealer_order_id FROM t_scm_order_detail e WHERE e.comment_status=0 AND e._status=?)");
				status = 3;
				params.add(status);
			}
			else if (status != null && status> -2) {
				sql.append(" AND b._status=?");
				params.add(status);
			}
			
			if (!StringUtils.isEmpty(keyword)) {
				sql.append(" AND (b.order_id LIKE concat('%',?,'%') OR (b.dealer_order_id IN (SELECT e.dealer_order_id FROM t_scm_order_detail e WHERE e.goods_name LIKE concat('%',?,'%'))))");
				params.add(keyword);
				params.add(keyword);
			}
			sql.append(" ORDER BY a.order_id DESC, a.created_date DESC ");
			
			sql.append(" LIMIT ?,? ");
			params.add((pageIndex - 1) * pageSize);
			params.add(pageSize);
			
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), AppOrderBean.class, params.toArray());
			
			if (result != null) {
				int sz = result.size();
				String tmpOrderId = null; 
				AppOrderBean tmp = null;
				for (int i=sz - 1; i> -1; i--) {
					AppOrderBean o = result.get(i);
					if (o.getStatus() == 0 && !o.getOrderId().equals(tmpOrderId)) {
						tmp = o;
						tmpOrderId = o.getOrderId();
						sql.delete(0, sql.length());
						sql.append("SELECT a.goods_icon, a.goods_name, a.goods_title, a.sku_name, a.sku_id, a.sell_num, a.discount_price, a.freight, "
								+ " a.goods_amount, b._status afterStatus, a.goods_id, a.goods_type_id\r\n") 
						.append(" FROM t_scm_order_detail a LEFT OUTER JOIN t_scm_order_after_sell b ON b.order_id=a.order_id AND b.dealer_order_id = a.dealer_order_id AND b._status != -1 WHERE a.order_id=? ");
						o.setGoodses(this.supportJdbcTemplate.queryForBeanList(sql.toString(), 
								OrderDetailBean.class, new Object[] {tmpOrderId}));
					}
					else if (o.getStatus() == 0 && o.getOrderId().equals(tmpOrderId)) {
						result.remove(i);
						tmp.setDealerDiscount(tmp.getDealerDiscount() + o.getDealerDiscount());
						tmp.setPlateFormDiscount(tmp.getPlateFormDiscount() + o.getPlateFormDiscount());
						tmp.setGoodAmount(tmp.getGoodAmount() + o.getGoodAmount());
						tmp.setOderFreight(tmp.getOderFreight() + o.getOderFreight());
					}
					else {
						tmpOrderId = o.getOrderId();
						sql.delete(0, sql.length());
						sql.append("SELECT a.goods_icon, a.goods_name, a.goods_title, a.sku_name, a.sku_id, a.sell_num, a.discount_price, a.freight, a.goods_amount\r\n") 
						.append(", a.comment_status , b._status afterStatus, a.goods_id, a.goods_type_id FROM t_scm_order_detail a LEFT OUTER JOIN t_scm_order_after_sell b ON b.order_id=a.order_id AND b.dealer_order_id = a.dealer_order_id AND b._status != -1"
								+ " WHERE a.order_id=? AND a.dealer_order_id=?");
						o.setGoodses(this.supportJdbcTemplate.queryForBeanList(sql.toString(), 
								OrderDetailBean.class, new Object[] {tmpOrderId, o.getDealerOrderId()}));
					}
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("---查询APP订单列表出错"+e.getMessage(),e);
	           StackTraceElement stackTraceElement= e.getStackTrace()[0]; 
	           LOGGER.error("File=" + stackTraceElement.getFileName()); 
	           LOGGER.error("Line=" + stackTraceElement.getLineNumber()); 
	           LOGGER.error("Method=" + stackTraceElement.getMethodName()); 
	           
			throw new NegativeException(500, "查询APP订单列表出错");
		}
		return result;
	}
	
	/***
	 * 获取APP订单列表页面 总数
	 * @param userId
	 * @param status
	 * @return
	 * @throws NegativeException
	 */
	public Integer getAppOrderListTotal(String userId, Integer status, Integer commentStatus
			,String keyword) throws NegativeException {
		Integer result = 0;
		try {
			List<Object> params = new ArrayList<>(2);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(1) FROM t_scm_order_dealer b \r\n")
			.append("LEFT OUTER JOIN t_scm_order_main a ON a.order_id=b.order_id \r\n") 
			.append("LEFT OUTER JOIN t_scm_dealer c ON c.dealer_id = b.dealer_id \r\n")
			.append("WHERE a.user_id=? AND a.del_flag=0 ");
			params.add(userId);
			
			if (commentStatus != null && commentStatus == 1) {
				sql.append(" AND b.dealer_order_id in (SELECT e.dealer_order_id FROM t_scm_order_detail e WHERE e.comment_status=0 AND e._status=?)");
				status = 3;
				params.add(status);
			}
			else if (status != null) {
				sql.append(" AND b._status=?");
				params.add(status);
			}
			
			if (!StringUtils.isEmpty(keyword)) {
				sql.append(" AND (b.order_id LIKE concat('%',?,'%') OR (b.dealer_order_id IN (SELECT e.dealer_order_id FROM t_scm_order_detail e WHERE e.goods_name LIKE concat('%',?,'%'))))");
				params.add(keyword);
				params.add(keyword);
			}
			
			result = this.getSupportJdbcTemplate().jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
		} catch (Exception e) {
			LOGGER.error("---查询APP订单列表出错 ",e);
			throw new NegativeException(500, "查询APP订单列表出错");
		}
		return result;
	}
	/***
	 * 获取订单详情
	 * @param cmd
	 * @return
	 * @throws NegativeException 
	 */
	public AppOrderDtl getOrderDtl(GetOrderCmd cmd) throws NegativeException {
		AppOrderDtl result = null;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			
			if (StringUtils.isEmpty(cmd.getDealerOrderId())) {
				
				sql.append("SELECT a.province_code, a.province, a.city, a.city_code, a.area_code, a.area_county, a.street_addr\r\n")
				.append(", a.order_freight, a.order_id, a.goods_amount, a.plateform_discount, a.dealer_discount\r\n")
				.append(", b.invoice_code, b.invoice_header, b.invoice_name, b.invoice_type, a.created_date, b._status\r\n") 
				.append(", b.dealer_id, c.dealer_name, b.dealer_order_id, b.rev_phone, b.rev_person, a.pay_way, a.pay_no\r\n") 
				.append("FROM t_scm_order_dealer b \r\n")
				.append("LEFT OUTER JOIN t_scm_order_main a ON a.order_id=b.order_id \r\n") 
				.append("LEFT OUTER JOIN t_scm_dealer c ON c.dealer_id = b.dealer_id \r\n")
				.append("WHERE a.user_id=? ");
				params.add(cmd.getUserId());		
				
				if (!StringUtils.isEmpty(cmd.getOrderId())) {
					sql.append(" AND b.order_id =?");
					params.add(cmd.getOrderId());
				}
				if (!StringUtils.isEmpty(cmd.getDealerOrderId())) {
					sql.append(" AND b.dealer_order_id =?");
					params.add(cmd.getDealerOrderId());
				}
				sql.append(" limit 1");
			}
			else {
				sql.append("SELECT a.province_code, a.province, a.city, a.city_code, a.area_code, a.area_county, a.street_addr\r\n")
				.append(", a.order_freight, a.order_id, a.goods_amount, a.plateform_discount, a.dealer_discount, d.customer_service_tel\r\n")
				.append(", b.invoice_code, b.invoice_header, b.invoice_name, b.invoice_type, a.created_date, b._status\r\n") 
				.append(", b.dealer_id, c.dealer_name, b.dealer_order_id,b.rev_phone, b.rev_person, a.pay_way, a.pay_no\r\n") 
				.append("FROM t_scm_order_dealer b \r\n")
				.append("LEFT OUTER JOIN t_scm_order_main a ON a.order_id=b.order_id \r\n") 
				.append("LEFT OUTER JOIN t_scm_dealer c ON c.dealer_id = b.dealer_id \r\n")
				.append("LEFT OUTER JOIN t_scm_dealer_shop d ON b.dealer_id = d.dealer_id \r\n")
				.append("WHERE a.user_id=? ");
				params.add(cmd.getUserId());		
				
				if (!StringUtils.isEmpty(cmd.getOrderId())) {
					sql.append(" AND b.order_id =?");
					params.add(cmd.getOrderId());
				}
				if (!StringUtils.isEmpty(cmd.getDealerOrderId())) {
					sql.append(" AND b.dealer_order_id =?");
					params.add(cmd.getDealerOrderId());
				}
			}
			
			result = this.supportJdbcTemplate.queryForBean(sql.toString(), AppOrderDtl.class, params.toArray());
			
			if (result != null) {
				sql.delete(0, sql.length());
				sql.append("SELECT a.goods_icon, a.goods_name, a.goods_title, a.sku_name, a.sku_id, a.sell_num, a.discount_price, a.freight, a.goods_amount\r\n")
				.append(", b._status afterStatus, a.goods_id, a.goods_type_id, a.express_no, a.express_code, a.express_name, a.express_way, a.comment_status ")
				.append(" FROM t_scm_order_detail a LEFT OUTER JOIN t_scm_order_after_sell b ON b.order_id=a.order_id AND b.dealer_order_id = a.dealer_order_id AND b._status != -1")
				.append(" WHERE a.order_id=? AND a.dealer_order_id=?");
				result.setGoodses(this.supportJdbcTemplate.queryForBeanList(sql.toString(), 
						OrderDetailBean.class, new Object[] {result.getOrderId(), result.getDealerOrderId()}));
			}
			
		} catch (Exception e) {
			LOGGER.error("---查询APP订单详情出错",e);
			throw new NegativeException(500, "查询APP订单详情出错");
		}
		return result;
	}
	
	/***
	 * 获取APP可售后的订单列表
	 * @param userId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws NegativeException
	 */
	public List<AppOrderBean> getMaySaleAfterList(String userId, int pageIndex, int pageSize) throws NegativeException {
		List<AppOrderBean> result = null;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.province_code, a.province, a.city, a.city_code, a.area_code, a.area_county, a.street_addr\r\n")
			.append(", a.order_freight, a.order_id, a.goods_amount, a.plateform_discount, a.dealer_discount\r\n")
			.append(", b.invoice_code, b.invoice_header, b.invoice_name, b.invoice_type, a.created_date, b._status\r\n") 
			.append(", b.dealer_id, c.dealer_name, b.dealer_order_id\r\n") 
			.append("FROM t_scm_order_dealer b \r\n")
			.append("LEFT OUTER JOIN t_scm_order_main a ON a.order_id=b.order_id \r\n") 
			.append("LEFT OUTER JOIN t_scm_dealer c ON c.dealer_id = b.dealer_id \r\n")
			.append("WHERE a.user_id=?  AND b.del_flag=0 AND (b._status IN (1, 2, 3))")
			.append("AND b.dealer_order_id IN (SELECT DISTINCT aa.dealer_order_id FROM t_scm_order_detail aa \r\n")
			.append("WHERE aa.sku_id NOT IN (SELECT bb.sku_id FROM t_scm_order_after_sell bb WHERE bb._status != -1 AND bb.dealer_order_id=aa.dealer_order_id AND bb.order_id=aa.order_id))");
			
			params.add(userId);
			
			sql.append(" ORDER BY a.order_id DESC, a.created_date DESC ");
			
			sql.append(" LIMIT ?,? ");
			params.add((pageIndex - 1) * pageSize);
			params.add(pageSize);
			
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), AppOrderBean.class, params.toArray());
			
			if (result != null) {
				int sz = result.size();
				String tmpOrderId = null; 
				for (int i=sz - 1; i> -1; i--) {
					AppOrderBean o = result.get(i);
					tmpOrderId = o.getOrderId();
					sql.delete(0, sql.length());
					sql.append("SELECT a.goods_icon, a.goods_name, a.goods_title, a.sku_name, a.sku_id, a.sell_num, a.discount_price, a.freight, a.goods_amount\r\n")
					.append(", a.comment_status ,a.goods_id, a.goods_type_id, a.is_change, a.change_price FROM t_scm_order_detail a \r\n")
					.append(" WHERE a.order_id=? AND a.dealer_order_id=? AND a.sku_id NOT IN (SELECT b.sku_id FROM t_scm_order_after_sell b WHERE b._status != -1 AND b.dealer_order_id=a.dealer_order_id AND b.order_id=a.order_id)")
					;
					o.setGoodses(this.supportJdbcTemplate.queryForBeanList(sql.toString(), 
							OrderDetailBean.class, new Object[] {tmpOrderId, o.getDealerOrderId()}));
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("---查询APP可售后的订单列表出错"+e.getMessage(),e);
			throw new NegativeException(500, "查询APP可售后的订单列表出错");
		}
		return result;
	}
	
	/***
	 * 获取APP可售后的订单列表
	 * @param userId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws NegativeException
	 */
	public Integer getMaySaleAfterListTotal(String userId) throws NegativeException {
		Integer result = 0;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(1) FROM t_scm_order_dealer b \r\n")
			.append("LEFT OUTER JOIN t_scm_order_main a ON a.order_id=b.order_id \r\n") 
			.append("LEFT OUTER JOIN t_scm_dealer c ON c.dealer_id = b.dealer_id \r\n")
			.append("WHERE a.user_id=?  AND b.del_flag=0 AND (b._status=2 OR b._status=3)")
			.append("AND b.dealer_order_id IN (SELECT DISTINCT aa.dealer_order_id FROM t_scm_order_detail aa \r\n")
			.append("WHERE aa.sku_id NOT IN (SELECT bb.sku_id FROM t_scm_order_after_sell bb WHERE bb._status != -1 AND bb.dealer_order_id=aa.dealer_order_id AND bb.order_id=aa.order_id))");
			
			params.add(userId);
			
			result = this.getSupportJdbcTemplate().jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
			
		} catch (Exception e) {
			LOGGER.error("---查询APP可售后的订单列表出错"+e.getMessage(),e);
			throw new NegativeException(500, "查询APP可售后的订单列表出错");
		}
		return result;
	}
	/**
	 * 调用第三方查询物流信息
	 * @param com
	 * @param nu
	 * @return
	 * @throws NegativeException 
	 */
	public String getExpressJson(String com, String nu) throws NegativeException {
		String expressInfo = null;
		try {
			 expressInfo = orderService.getExpressInfo(com, nu);
		} catch (Exception e) {
			LOGGER.error("---查询APP物流列表出错"+e.getMessage(),e);
			throw new NegativeException(500, "查询APP物流列表出错");
		}
		return expressInfo;
	}
}

