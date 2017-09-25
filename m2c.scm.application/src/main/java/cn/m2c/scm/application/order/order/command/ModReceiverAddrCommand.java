package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class ModReceiverAddrCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = -3174777396314839103L;
	
	private String orderId;				//订单编号
	private String provinceCode;		
	private String provinceName; 
	private String cityCode;
	private String cityName;
	private String areaCode;
	private String areaName;
	private	String receiverAddr;		//收货地址
	
	public ModReceiverAddrCommand(String orderId,
			String provinceCode,
			String provinceName,
			String cityCode,
			String cityName,
			String areaCode,
			String areaName,
			String receiverAddr){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		if(provinceCode != null && provinceCode.length() > 0){
			assertArgumentLength(provinceCode,6,"provinceCode is longer than 6.");
		}
		if(provinceName != null && provinceName.length() > 0){
			assertArgumentLength(provinceName,20,"provinceName is longer than 20.");
		}
		if(cityCode != null && cityCode.length() > 0){
			assertArgumentLength(cityCode,6,"cityCode is longer than 6.");
		}
		if(cityName != null && cityName.length() > 0){
			assertArgumentLength(cityName,20,"cityName is longer than 20.");
		}
		if(areaCode != null && areaCode.length() > 0){
			assertArgumentLength(areaCode,6,"areaCode is longer than 6.");
		}
		if(areaName != null && areaName.length() > 0){
			assertArgumentLength(areaName,20,"areaName is longer than 20.");
		}
		if(receiverAddr != null && receiverAddr.length() > 0){
			assertArgumentLength(receiverAddr,256,"receiverAddr is longer than 256.");
		}
		this.orderId = orderId;
		this.receiverAddr = receiverAddr;
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.areaCode = areaCode;
		this.areaName = areaName;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getReceiverAddr() {
		return receiverAddr;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getAreaName() {
		return areaName;
	}
	
	
}
