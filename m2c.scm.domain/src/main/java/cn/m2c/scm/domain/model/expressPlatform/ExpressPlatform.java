package cn.m2c.scm.domain.model.expressPlatform;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class ExpressPlatform extends ConcurrencySafeEntity{

	private static final long serialVersionUID = -3309154872696560231L;
	
	private String com;
	private String nu;
	private String resData;
	private Date createdDate;
	private Date lastUpdatedDate;
	private Date shipGoodsTime;         //发货时间
	private Integer shipType;           //发货类型：0 商家发货物流信息 1 用户售后寄回物流信息
	
	public ExpressPlatform() {
		super();
	}

	public void add(String com, String nu, String resData) {
		this.com = com;
		this.nu = nu;
		this.resData = resData;
		this.lastUpdatedDate = new Date();
	}
	
	public void save(String com,String nu,Integer shipType) {
		this.com = com;
		this.nu = nu;
		this.shipGoodsTime = new Date();
		this.shipType = shipType;
	}
}
