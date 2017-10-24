package cn.m2c.scm.domain.model.dealer;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEvent;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.dealer.event.DealerAddEvent;
import cn.m2c.scm.domain.model.dealer.event.DealerUpdateEvent;

public class Dealer extends ConcurrencySafeEntity{
	private static final long serialVersionUID = -8935103789948109354L;
	private String dealerId;//经销商id
	private String userId;//管理员用户id
	private String userName;
	private String userPhone;
	private String dealerName;//经销商名称
	private String dealerClassify;//商家类型
	private Integer cooperationMode=0;//合作方式 0：未选则 1：包销 2：代销 3：经销 4：入驻
	private String startSignDate;//签约开始时间
	private String endSignDate;//签约结束时间
	private String dealerProvince;//经销商省信息
	private String dealerCity;//经销商市信息
	private String dealerArea;//经销商区信息
	private String dealerPcode;//经销商省code
	private String dealerCcode;//经销商市code
	private String dealerAcode;//经销商区code
	private String dealerDetailAddress;//经销商详细地址
	private Integer countMode=0;//结算模式 0：未选择 1：按供货价 2：按服务费率
	private Long deposit=0L;//平台押金金额
	private Integer isPayDeposit=0; //是否缴纳平台押金 0：未缴纳 1：已缴纳
	private String managerName;//商家负责人姓名
	private String managerPhone;//商家负责人手机
	private String managerqq;//商家负责人qq
	private String managerWechat;//商家负责人微信
	private String managerEmail;//商家负责人邮箱
	private String managerDepartment;//商家负责人部门
	private String sellerId;//业务员id
	private String sellerName;
	private String sellerPhone;
	private Integer dealerStatus=1;//1:正常 2：删除
	
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public Dealer() {
		super();
	}


	public void add(String dealerId, String userId,
			String userName, String userPhone, String dealerName,
			String dealerClassify, Integer cooperationMode,
			String startSignDate, String endSignDate, String dealerProvince,
			String dealerCity, String dealerArea, String dealerPcode,
			String dealerCcode, String dealerAcode, String dealerDetailAddress,
			Integer countMode, Long deposit, Integer isPayDeposit,
			String managerName, String managerPhone, String managerqq,
			String managerWechat, String managerEmail,
			String managerDepartment, String sellerId, String sellerName,
			String sellerPhone) {
		DomainEvent dealerAddEvent= new DealerAddEvent(userId,userName,userPhone,dealerId,dealerName);
		DomainEventPublisher.instance().publish(dealerAddEvent);
		this.dealerId = dealerId;
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.dealerName = dealerName;
		this.dealerClassify = dealerClassify;
		this.cooperationMode = cooperationMode;
		this.startSignDate = startSignDate;
		this.endSignDate = endSignDate;
		this.dealerProvince = dealerProvince;
		this.dealerCity = dealerCity;
		this.dealerArea = dealerArea;
		this.dealerPcode = dealerPcode;
		this.dealerCcode = dealerCcode;
		this.dealerAcode = dealerAcode;
		this.dealerDetailAddress = dealerDetailAddress;
		this.countMode = countMode;
		this.deposit = deposit;
		this.isPayDeposit = isPayDeposit;
		this.managerName = managerName;
		this.managerPhone = managerPhone;
		this.managerqq = managerqq;
		this.managerWechat = managerWechat;
		this.managerEmail = managerEmail;
		this.managerDepartment = managerDepartment;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
	}

	public void update(String userId,
			String userName, String userPhone, String dealerName,
			String dealerClassify, Integer cooperationMode,
			String startSignDate, String endSignDate, String dealerProvince,
			String dealerCity, String dealerArea, String dealerPcode,
			String dealerCcode, String dealerAcode, String dealerDetailAddress,
			Integer countMode, Long deposit, Integer isPayDeposit,
			String managerName, String managerPhone, String managerqq,
			String managerWechat, String managerEmail,
			String managerDepartment, String sellerId, String sellerName,
			String sellerPhone) {
		DomainEvent dealerUpdateEvent= new DealerUpdateEvent(this.userId,userId,userName,userPhone,dealerId,dealerName);
		DomainEventPublisher.instance().publish(dealerUpdateEvent);
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.dealerName = dealerName;
		this.dealerClassify = dealerClassify;
		this.cooperationMode = cooperationMode;
		this.startSignDate = startSignDate;
		this.endSignDate = endSignDate;
		this.dealerProvince = dealerProvince;
		this.dealerCity = dealerCity;
		this.dealerArea = dealerArea;
		this.dealerPcode = dealerPcode;
		this.dealerCcode = dealerCcode;
		this.dealerAcode = dealerAcode;
		this.dealerDetailAddress = dealerDetailAddress;
		this.countMode = countMode;
		this.deposit = deposit;
		this.isPayDeposit = isPayDeposit;
		this.managerName = managerName;
		this.managerPhone = managerPhone;
		this.managerqq = managerqq;
		this.managerWechat = managerWechat;
		this.managerEmail = managerEmail;
		this.managerDepartment = managerDepartment;
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
	}




	
	/**
	 * 更新业务员信息
	 * @param sellerName
	 */
	public void updateSellerInfo(String sellerName,String sellerPhone) {
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
	}


	
	
	

	
}
