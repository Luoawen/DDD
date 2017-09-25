package cn.m2c.scm.port.adapter.rpc.dubbo.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.m2c.scm.application.order.comment.query.SpringJdbcCommentQuery;
import cn.m2c.scm.application.order.fsales.AfterSalesApplication;
import cn.m2c.scm.application.order.fsales.command.BackedGoodsCommand;
import cn.m2c.scm.application.order.order.OrderApplication;
import cn.m2c.scm.application.order.order.command.PayedCommand;
import cn.m2c.scm.application.order.order.command.SettledCommand;
import cn.m2c.scm.application.order.order.query.SpringJdbcOrderQuery;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.order.dto.OrderDto;
import cn.m2c.scm.order.interfaces.OrderService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: OrderServiceImpl
 * @Description: 订单服务
 * @author moyj
 * @date 2017年4月25日 下午1:49:11
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private SpringJdbcOrderQuery springJdbcOrderQuery;
	@Autowired
	private OrderApplication orderApplication;
	@Autowired
	private AfterSalesApplication afterSalesApplication;
	@Autowired
	private SpringJdbcCommentQuery springJdbcCommentQuery;

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDto> settleListByIdList(List<String> orderIdList) {
		if(orderIdList == null || orderIdList.size() == 0){
			return null;
		}
		try{	
			List<Map<String, Object>> list =  (List<Map<String, Object>> )springJdbcOrderQuery.settleListByIdList(orderIdList);
			Gson gson = new Gson();
			List<OrderDto> result = gson.fromJson(gson.toJson(list), new TypeToken<List<OrderDto>>(){}.getType());
			return result;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.settleListByIdList >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.settleListByIdList >> error",e);
		}
		return null;
	}
	
	public boolean settled(List<String> orderIdList){
		try{	
			SettledCommand command = new SettledCommand(orderIdList);
			orderApplication.settled(command);
			return true;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.settled >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.settled >> error",e);
		}
		return false;	
	}
	@Override
	public OrderDto payOrderById(String orderId) {
		try{	
			Map<String, Object> map =  springJdbcOrderQuery.payOrderById(orderId);
			Gson gson = new Gson();
			OrderDto result = gson.fromJson(gson.toJson(map), new TypeToken<OrderDto>(){}.getType());
			return result;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}
		return null;
	}

	@Override
	public boolean payed(String orderId, Long payStartTime, Long payEndTime, String payTradeNo, Long payPrice,Integer payWay,Map<String,Object> appMsgMap) {
		
		try {
			PayedCommand command = new PayedCommand(orderId,payStartTime,payEndTime,payTradeNo,payPrice,payWay,appMsgMap);
			orderApplication.payed(command);
			return true;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.payed >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.payed >> error",e);
		}
		return false;
	}

	@Override
	public OrderDto payOrderByNo(String orderNo) {
		try{	
			Map<String, Object> map =  springJdbcOrderQuery.payOrderByNo(orderNo);
			Gson gson = new Gson();
			OrderDto result = gson.fromJson(gson.toJson(map), new TypeToken<OrderDto>(){}.getType());
			return result;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}
		return null;
	}

	@Override
	public boolean refunded(String orderId, Long refundAmount, String refbaredReason,String handManId,String handManName) {
		try {
			BackedGoodsCommand command = new BackedGoodsCommand(orderId,refundAmount,refbaredReason,handManId,handManName);
			afterSalesApplication.backedGoods(command);
			return true;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.Refunded >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.Refunded >> error",e);
		}
		return false;
	}

	@Override
	public OrderDto refundOrderById(String orderId) {
		try{	
			Map<String, Object> map =  springJdbcOrderQuery.refundOrderById(orderId);
			Gson gson = new Gson();
			OrderDto result = gson.fromJson(gson.toJson(map), new TypeToken<OrderDto>(){}.getType());
			return result;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}
		return null;
	}

	@Override
	public Map<String, Object> adminOrderStatistics() {
			Map<String,Object> retMap = new HashMap<String,Object>();
			Integer waitReplyCount =  springJdbcCommentQuery.waitReplyCount();
			Integer waitHandingCount =  springJdbcOrderQuery.waitHandingCount();
			retMap.put("waitReplyCount", waitReplyCount);
			retMap.put("waitHandingCount", waitHandingCount);
			return retMap;
			
	}

	@Override
	public Map<String, Object> mediaOrderStatistics(String mediaId){
		try{
			Map<String,Object> retMap = new HashMap<String,Object>();
			Integer curDayMediaOrderCount = springJdbcOrderQuery.curDayMediaOrderCount(mediaId);
			retMap.put("curDayMediaOrderCount", curDayMediaOrderCount);
			return retMap;
		}catch(Exception e){
			
		}
		return null;
	}

	@Override
	public Map<String, Object> mresOrderStatistics(String mresId, String mresNo) {
		try{
			Map<String,Object> retMap = new HashMap<String,Object>();
			Integer curDayMresOrderCount = springJdbcOrderQuery.curDayMresOrderCount(mresId,mresNo);
			retMap.put("curDayMresOrderCount", curDayMresOrderCount);
			return retMap;
		}catch(Exception e){
			
		}
		return null;
	}

	@Override
	public OrderDto orderById(String orderId) {
		try{	
			Map<String, Object> map =  springJdbcOrderQuery.orderById(orderId);
			Gson gson = new Gson();
			OrderDto result = gson.fromJson(gson.toJson(map), new TypeToken<OrderDto>(){}.getType());
			return result;
		}catch(NegativeException e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}catch(Exception e) {
			logger.error("OrderServiceImpl.payOrderById >> error",e);
		}
		return null;
	}

}
