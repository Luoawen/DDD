package cn.m2c.scm.port.adapter.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.m2c.common.IDGenerator;
import cn.m2c.scm.application.order.fsales.query.SpringJdbcAfterSalesQuery;
import cn.m2c.scm.domain.IDPrefix;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.Order;
import cn.m2c.scm.domain.model.order.OrderRepository;
import cn.m2c.scm.domain.model.order.fsales.AfterSales;
import cn.m2c.scm.domain.model.order.fsales.AfterSalesRepository;
import cn.m2c.scm.domain.model.order.service.AfterSalesService;

@Service
public class AfterSalesServiceImpl implements AfterSalesService{
	
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AfterSalesRepository afterSalesRepository;
	@Autowired
	private SpringJdbcAfterSalesQuery springJdbcAfterSalesQuery;
	
	@Override
	public void applyAfterSales(String orderId, String applyReason,Long fsaleDeadline) throws NegativeException {
		//售后
		AfterSales afterSales = new AfterSales();
		afterSales.applyAfterSales(IDGenerator.get(IDPrefix.ORDER_AFTER_SALES_PREFIX), orderId, applyReason);
		//对于处理订单的状态
		Order order = orderRepository.findT(orderId);
		if(order == null){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		order.applyAfterSales(fsaleDeadline);
		
		afterSalesRepository.save(afterSales);
		orderRepository.save(order);
		
	}

	@Override
	public void auditAfterSales(List<String> orderIdList, Integer auditFlag,String unauditReason,String handManId,String handManName) throws NegativeException {
		if(orderIdList == null){
			return;
		}

		for(String orderId : orderIdList){
			Map<String,Object> aferSalesMap = springJdbcAfterSalesQuery.findT(orderId);
			if(aferSalesMap == null){
				throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许审核");
			}
			String fsalesId = aferSalesMap.get("fsalesId") == null?"":(String)aferSalesMap.get("fsalesId");
			AfterSales afterSales = afterSalesRepository.findT(fsalesId);
			if(afterSales == null){
				throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许审核");
			}
			afterSales.auditAfterSales(orderId, auditFlag, unauditReason,handManId,handManName);
			//对于处理订单的状态
			Order order = orderRepository.findT(orderId);
			if(order == null){
				throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
			}
			order.auditAfterSales(auditFlag);
			afterSalesRepository.save(afterSales);
			orderRepository.save(order);
		}
		
	}

	@Override
	public void backedGoods(String orderId,Long refundAmount, String refbaredReason,String handManId,String handManName) throws NegativeException {
		Map<String,Object> aferSalesMap = springJdbcAfterSalesQuery.findT(orderId);
		if(aferSalesMap == null){
			throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许退货");
		}
		String fsalesId = aferSalesMap.get("fsalesId") == null?"":(String)aferSalesMap.get("fsalesId");
		AfterSales afterSales = afterSalesRepository.findT(fsalesId);
		if(afterSales == null){
			throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许退货");
		}
		//对于处理订单的状态
		Order order = orderRepository.findT(orderId);
		if(order == null){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		afterSales.backedGoods(order.getPayPrice(),refundAmount, refbaredReason,handManId,handManName);
		order.backedGoods(refundAmount,handManId,handManName);
		afterSalesRepository.save(afterSales);
		orderRepository.save(order);
	}

	@Override
	public void bartering(String orderId,String refbaredReason,String handManId,String handManName) throws NegativeException {
		Map<String,Object> aferSalesMap = springJdbcAfterSalesQuery.findT(orderId);
		if(aferSalesMap == null){
			throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许换货");
		}
		String fsalesId = aferSalesMap.get("fsalesId") == null?"":(String)aferSalesMap.get("fsalesId");
		AfterSales afterSales = afterSalesRepository.findT(fsalesId);
		if(afterSales == null){
			throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许换货");
		}
		afterSales.bartering(orderId, refbaredReason,handManId,handManName);
	
		afterSalesRepository.save(afterSales);
	}

	@Override
	public void bartered(String orderId,String handManId,String handManName) throws NegativeException {
		Map<String,Object> aferSalesMap = springJdbcAfterSalesQuery.findT(orderId);
		if(aferSalesMap == null){
			throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许换货");
		}
		String fsalesId = aferSalesMap.get("fsalesId") == null?"":(String)aferSalesMap.get("fsalesId");
		AfterSales afterSales = afterSalesRepository.findT(fsalesId);
		if(afterSales == null){
			throw new NegativeException(NegativeCode.ORDER_UNAPPLY_SALED,"非申请售后的订单不允许换货");
		}
		afterSales.bartered(orderId,handManId,handManName);
		//对于处理订单的状态
		Order order = orderRepository.findT(orderId);
		if(order == null){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		order.bartered();
		afterSalesRepository.save(afterSales);
		orderRepository.save(order);
	}

}
