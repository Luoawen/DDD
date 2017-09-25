package cn.m2c.scm.domain.model.goods.seller;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 经销商业务员
 * @author yezp
 *
 */
public class Seller extends ConcurrencySafeEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1496215251919728896L;
	private String staffId;
	private String userId;
	private String accNo;
	private String staffName;
	private String phone;
	private String mail;
	private Integer sex;
	private Integer age;
	private String proCode;
	private String cityCode;
	private String areaCode;
	private String proName;
	private String cityName;
	private String areaName;
	private Long regisDate;
	private Integer orderNum;
	
	private Integer isValid;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public Seller() {
	}

	public Seller(String staffId, String userId, String accNo, String staffName, String phone, String mail, 
			Integer sex, Integer age, String proCode,String cityCode,String areaCode,String proName, String cityName,String areaName, Long regisDate, Integer orderNum) {
		super();
		this.staffId = staffId;
		this.userId = userId;
		this.accNo = accNo;
		this.staffName = staffName;
		this.phone = phone;
		this.mail = mail;
		this.sex = sex;
		this.age = age;
		this.proCode = proCode;
		this.cityCode = cityCode;
		this.areaCode = areaCode;
		this.proName = proName;
		this.cityName = cityName;
		this.areaName = areaName;
		this.regisDate = regisDate;
		this.orderNum = orderNum;
	}
	
	public void update(String accNo, String staffName, String phone, String mail,
			Integer sex, Integer age,  String proCode,String cityCode,String areaCode,String proName, String cityName,String areaName) {
		this.accNo = accNo;
		this.staffName = staffName;
		this.phone = phone;
		this.mail = mail;
		this.sex = sex;
		this.age = age;
		this.proCode = proCode;
		this.cityCode = cityCode;
		this.areaCode = areaCode;
		this.proName = proName;
		this.cityName = cityName;
		this.areaName = areaName;
	}

	public void delete(){
		this.isValid = 0;
	}
	

	public String getStaffId() {
		return staffId;
	}

	public final String getUserId() {
		return userId;
	}

	public String getAccNo() {
		return accNo;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public Integer getSex() {
		return sex;
	}

	public Integer getAge() {
		return age;
	}


	public String getProCode() {
		return proCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public Long getRegisDate() {
		return regisDate;
	}

	public final Integer getIsValid() {
		return isValid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public Integer getOrderNum() {
		return orderNum;
	}
	

	public String getProName() {
		return proName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void addOrderNum() {
		System.out.println("------支付成功后业务员订单数加一-----------");
		// TODO Auto-generated method stub
		this.orderNum ++;
	}


}
