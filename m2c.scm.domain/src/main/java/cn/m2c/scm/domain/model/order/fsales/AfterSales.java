package cn.m2c.scm.domain.model.order.fsales;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.logger.Opered;

public class AfterSales extends ConcurrencySafeEntity {
	
	private static final long serialVersionUID = 3277607196537335380L;
	private String fsalesId;		//售后服务ID
	private String orderId;			//订单Id
	private String applyReason;		//申请售后理由
	private Integer fsalesStatus;  	//售后状态  1申请售后 2申请售后通过 3申请售后不通过 4已退  5换货中 6已换货
	private Long applyTime;			//售后申请时间
	private Long barterTime;		//换货时间
	private Long auditTime;			//审核时间
	private Long finishedTime;		//退换完成时间
	private Long refundAmount;		//退款金额
	private String unauditReason;	//审核不通过理由
	private String refbaredReason;	//退/换理由
	private Integer handleStatus;	//处理状态 1处理中 2已处理
	
	/**
	 * 申请售后
	 * @param fsalesId		//售后服务ID
	 * @param orderId		//订单ID
	 * @param applyReason	//申请售后理由
	 * @param applyFlag	//退货标识 1退货  2换货 
	 */
	public void applyAfterSales(String fsalesId,String orderId,String applyReason) throws NegativeException{
		assertArgumentNotEmpty(fsalesId, "fsalesId is not be null.");
		assertArgumentLength(fsalesId,36,"fsalesId is longer than 36.");
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		assertArgumentNotEmpty(applyReason, "saledReason is not be null.");
		assertArgumentLength(applyReason,64,"saledReason is longer than 64.");
		this.fsalesId = fsalesId;
		this.orderId = orderId;
		this.applyReason = applyReason;
		//申请退货
		this.fsalesStatus = FsalesStatus.APPLY.getId();
		this.applyTime = System.currentTimeMillis();
		this.handleStatus = HandleStatus.HANDLEING.getId();
		
	}
	
	/**
	 * 审核
	 * @param orderId 			//订单编码
	 * @param auditFlag	  		//1审核通过	2审核不通过
	 * @param unauditReason		//审核不通过理由
	 */
	public void auditAfterSales(String orderId,Integer auditFlag,String unauditReason,String handManId,String handManName) throws NegativeException{
		assertArgumentNotNull(auditFlag, "auditFlag is not be null.");
		assertArgumentRange(auditFlag,1,2,"auditFlag is not in 1 2.");
		
		// 已申请过
		if(!(this.fsalesStatus.intValue() == FsalesStatus.APPLY.getId())){
			throw new NegativeException(NegativeCode.UN_APPLY_SALED_UNABLE_APPLY,"非申请售后的订单不允许审核");
		}	
		//审核通过
		if(auditFlag == 1){
			this.fsalesStatus = FsalesStatus.APPLY_PASS.getId();
			//操作日志事件
			Opered opered = new Opered(this.orderId, 1, "审核通过", "", "操作成功", handManId, handManName);
			DomainEventPublisher.instance().publish(opered);
			
		}
		//审核不通过
		else if(auditFlag == 2){		
			assertArgumentNotEmpty(unauditReason, "unauditReason is not be null.");
			assertArgumentLength(unauditReason,64,"unauditReason is longer than 64.");
			this.fsalesStatus = FsalesStatus.APPLY_UNPASS.getId();
			this.handleStatus = HandleStatus.HANDLED.getId();
			this.unauditReason = unauditReason;
			//操作日志事件
			Opered opered = new Opered(this.orderId, 1, "审核不通过", unauditReason, "操作成功", handManId, handManName);
			DomainEventPublisher.instance().publish(opered);
		}
		this.auditTime = System.currentTimeMillis();
	}
	/**
	 * 退货
	 */
	public void backedGoods(Long payAmount,Long refundAmount,String refbaredReason,String handManId,String handManName) throws NegativeException{
		assertArgumentNotEmpty(refbaredReason, "refbaredReason is not be null.");
		assertArgumentLength(refbaredReason,64,"refbaredReason is longer than 64.");
		assertArgumentNotNull(refundAmount, "refundAmount is not be null.");
		
		//非审核通过
		if(this.fsalesStatus != FsalesStatus.APPLY_PASS.getId()){
			throw new NegativeException(NegativeCode.UN_APPLY_PASS_UNABLE_BACK_GOODS,"非退货审核通过的订单不允许退货");
		}
		//退款金额大于付款金额
		if(refundAmount > payAmount){
			throw new NegativeException(NegativeCode.REFUNDED_MORE_PAY,"退款金额大于付款金额");
		}			
		this.fsalesStatus = FsalesStatus.BACKED_GOODS.getId();
		this.handleStatus = HandleStatus.HANDLED.getId();
		this.refbaredReason = refbaredReason;
		this.refundAmount = refundAmount;
		this.finishedTime = System.currentTimeMillis();
		//操作日志事件
		Opered opered = new Opered(this.orderId, 1, "退货", refbaredReason, "操作成功", handManId, handManName);
		DomainEventPublisher.instance().publish(opered);
	}
	
	/**
	 * 换货中
	 * @param orderId
	 */
	public void bartering(String orderId,String refbaredReason,String handManId,String handManName) throws NegativeException{
		assertArgumentNotEmpty(refbaredReason, "refbaredReason is not be null.");
		assertArgumentLength(refbaredReason,64,"refbaredReason is longer than 64.");
		//非审核通过
		if(this.fsalesStatus != FsalesStatus.APPLY_PASS.getId()){
			throw new NegativeException(NegativeCode.UN_APPLY_PASS_UNABLE_BARTER,"非换货审核通过的订单不允许换货");
		}
		this.fsalesStatus = FsalesStatus.BARTERING.getId();
		this.refbaredReason = refbaredReason;
		//操作日志事件
		Opered opered = new Opered(this.orderId, 1, "换货中", refbaredReason, "操作成功", handManId, handManName);
		DomainEventPublisher.instance().publish(opered);
	}
	
	/**
	 * 换货完成
	 * @param orderId
	 */
	public void bartered(String orderId,String handManId,String handManName) throws NegativeException{
		//换货中
		if(this.fsalesStatus != FsalesStatus.BARTERING.getId()){
			throw new NegativeException(NegativeCode.UN_BARTERING_UNABLE_BARTERED,"非换货中的订单不允许换货完成");
		}
		this.fsalesStatus = FsalesStatus.BARTERED.getId();
		this.handleStatus = HandleStatus.HANDLED.getId();
		this.finishedTime = System.currentTimeMillis();
		//操作日志事件
		Opered opered = new Opered(this.orderId, 1, "换货完成", "", "操作成功", handManId, handManName);
		DomainEventPublisher.instance().publish(opered);
	}
	
	public String getFsalesId() {
		return fsalesId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getApplyReason() {
		return applyReason;
	}
	public Integer getFsalesStatus() {
		return fsalesStatus;
	}
	public Long getApplyTime() {
		return applyTime;
	}
	public Long getBarterTime() {
		return barterTime;
	}
	public Long getAuditTime() {
		return auditTime;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}

	public String getUnauditReason() {
		return unauditReason;
	}

	public String getRefbaredReason() {
		return refbaredReason;
	}
	public Integer getHandleStatus() {
		return handleStatus;
	}

	public Long getFinishedTime() {
		return finishedTime;
	}
	
	
	
	

}
