package cn.m2c.scm.domain.model.seller.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class SellerAddOrUpdateEvent implements DomainEvent {
	private String sellerId;
	private String sellerName;
	private String sellerPhone;
	private String sellerNo;
	private String sellerPass;//目前只有前端加密过
	private String sellerRemark;//备注信息
	private Integer operateFlag;//操作状态 1：新增 2：修改
	private Date currentDate;

	public SellerAddOrUpdateEvent() {
	}

	public SellerAddOrUpdateEvent(String sellerId, String sellerName, String sellerPhone, String sellerNo, String sellerPass, String sellerRemark, Integer operateFlag) {
		this.setSellerId(sellerId);
		this.setSellerName(sellerName);
		this.setSellerPhone(sellerPhone);
		this.setSellerNo(sellerNo);
		this.setSellerPass(sellerPass);
		this.setSellerRemark(sellerRemark);
		this.setOperateFlag(operateFlag);
		this.setCurrentDate(new Date());
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}

	public String getSellerPass() {
		return sellerPass;
	}

	public void setSellerPass(String sellerPass) {
		this.sellerPass = sellerPass;
	}

	public String getSellerRemark() {
		return sellerRemark;
	}

	public void setSellerRemark(String sellerRemark) {
		this.sellerRemark = sellerRemark;
	}

	public Integer getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(Integer operateFlag) {
		this.operateFlag = operateFlag;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public int eventVersion() {
		return 0;
	}

	@Override
	public Date occurredOn() {
		return this.getCurrentDate();
	}
}
