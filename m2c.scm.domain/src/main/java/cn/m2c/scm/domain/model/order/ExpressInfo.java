package cn.m2c.scm.domain.model.order;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ValueObject;
/***
 * 快递信息
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
public class ExpressInfo extends ValueObject {
	/**快递单号**/
	private String expressNo;
	/**快递公司*/
	private String expressName;
	/**快递公司编码*/
	private String expressCode;
	/**送货人姓名*/
	private String expressPerson;
	/**送货人电话*/
	private String expressPhone;
	/**配送方式 0:物流，1自有物流*/
	private Integer expressWay;
	/**配送说明*/
	private String expressNote;
	/**发货时间*/
	private Date expressTime;
	
	public ExpressInfo(String no, String name, String pName, String expressCode,
			String phone, int way, String note, Date expTime) {
		expressNo = no;
		expressName = name;
		expressPerson = pName;
		expressPhone = phone;
		expressWay = way;
		expressNote = note;
		expressTime = expTime;
		this.expressCode = expressCode;
	}
	
	public ExpressInfo(String no, String name, String expressCode) {
		expressNo = no;
		expressName = name;
		this.expressCode = expressCode;
	}
	
	public ExpressInfo() {
		super();
	}
	/**
	 * 更新值对象的物流信息
	 * @param expressName
	 * @param expressNo
	 * @param expressNote
	 * @param expressPerson
	 * @param expressPhone
	 * @param expressWay
	 */
	public void updateExpress(String expressName, String expressNo,
			String expressNote, String expressPerson, String expressPhone,
			Integer expressWay, String expressCode) {
		this.expressName = expressName;
		this.expressNo = expressNo;
		this.expressNote = expressNote;
		this.expressPerson = expressPerson;
		this.expressPhone = expressPhone;
		this.expressWay = expressWay;
		this.expressCode = expressCode;
	}
	
	String getExpressNo() {
		return expressNo;
	}
}
