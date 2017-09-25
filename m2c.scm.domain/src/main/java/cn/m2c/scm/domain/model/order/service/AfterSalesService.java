package cn.m2c.scm.domain.model.order.service;

import java.util.List;

import cn.m2c.scm.domain.NegativeException;

/**
 * 
 * @ClassName: AfterSalesService
 * @Description: 领域服务 (售后相关操作)
 * @author moyj
 * @date 2017年7月7日 下午4:14:30
 *
 */
public interface AfterSalesService {
		/**
		 * 提交申请
		 * @param orderId		//订单编号
		 * @param applyReason	//售后理由
		 * @param applyFlag	//退换标识 1退货  2换货 
		 * @param saledDeadline	//过去时间(毫秒)
		 */
		public void applyAfterSales(String orderId,String applyReason,Long saledDeadline ) throws NegativeException ;
		
		/**
		 * 审核
		 * @param orderIdList //订单编码
		 * @param auditFlag	  //1审核通过	2审核不通过
		 */
		public void auditAfterSales(List<String> orderIdList,Integer auditFlag,String unauditReason,String handManId,String handManName)throws NegativeException ;
		/**
		 * 退货
		 * @param orderId
		 */
		public void backedGoods(String orderId,Long refundAmount, String refbaredReason,String handManId,String handManName) throws NegativeException ;
		
		/**
		 * 换货中
		 * @param orderId
		 */
		public void bartering(String orderId,String refbaredReason,String handManId,String handManName) throws NegativeException ;
		
		/**
		 * 换货完成
		 * @param orderId
		 */
		public void bartered(String orderId,String handManId,String handManName) throws NegativeException ;
		
		
}
