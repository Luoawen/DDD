package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;
/***
 * 收货地址值对象
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
public class ReceiveAddr extends ValueObject {
	
	private String province;
	
	private String provinceCode;
	
	private String city;
	
	private String cityCode;
	
	private String area;
	
	private String areaCode;
	
	private String street;
	/**收货联系人*/
	private String revPerson;
	/**联系电话*/
	private String phone;
	
	public ReceiveAddr() {
		super();
	}
	
	public ReceiveAddr(String prov, String provCode, String city,
			String cityCode, String area, String areaCode, 
			String street, String pName, String phone) {
		this.province = prov;
		provinceCode = provCode;
		this.city = city;
		this.cityCode = cityCode;
		this.area = area;
		this.areaCode = areaCode;
		this.street = street;
		revPerson = pName;
		this.phone = phone;
	}
	
	public String getCityCode() {
		return cityCode;
	}
}