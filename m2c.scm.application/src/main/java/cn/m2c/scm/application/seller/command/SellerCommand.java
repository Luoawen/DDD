package cn.m2c.scm.application.seller.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class SellerCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 8142409278133098063L;
	
	/**
	 * 业务员id
	 */
	private String sellerId;
	
	/**
	 * 业务员名字
	 */
	private String sellerName;
	
	/**
	 * 业务员手机
	 */
	private String sellerPhone;
	
	/**
	 * 业务员性别
	 */
	private Integer sellerSex;
	/**
	 * 业务员编号
	 */
	private String sellerNo;
	/**
	 * 业务员密码
	 */
	private String sellerPass;
	/**
	 * 确认密码
	 */
	private String sellerConfirmPass;
	/**
	 * 业务员所在省
	 */
	private String sellerProvince;
	/**
	 * 业务员所在市
	 */
	private String sellerCity;
	/**
	 * 业务员所在区
	 */
	private String sellerArea;
	/**
	 * 业务员所在省code
	 */
	private String sellerPcode;
	/**
	 * 业务员所在市code
	 */
	private String sellerCcode;
	/**
	 * 业务员所在区code
	 */
	private String sellerAcode;
	/**
	 * 业务员qq
	 */
	private String sellerqq;
	
	/**
	 * 业务员微信
	 */
	private String sellerWechat;
	/**
	 * 业务员备注
	 */
	private String sellerRemark;
	
	public SellerCommand() {
		super();
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public Integer getSellerSex() {
		return sellerSex;
	}

	public String getSellerNo() {
		return sellerNo;
	}


	public String getSellerConfirmPass() {
		return sellerConfirmPass;
	}

	public String getSellerProvince() {
		return sellerProvince;
	}

	public String getSellerCity() {
		return sellerCity;
	}

	public String getSellerArea() {
		return sellerArea;
	}

	public String getSellerPcode() {
		return sellerPcode;
	}

	public String getSellerCcode() {
		return sellerCcode;
	}

	public String getSellerAcode() {
		return sellerAcode;
	}

	public String getSellerqq() {
		return sellerqq;
	}

	public String getSellerWechat() {
		return sellerWechat;
	}

	public String getSellerRemark() {
		return sellerRemark;
	}

	public String getSellerPass() {
		return sellerPass;
	}

	
	
	public SellerCommand(String sellerId, String sellerName, String sellerPhone) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
	}
	
	public SellerCommand(String sellerId,String sellerName,String sellerPhone,String sellerRemark) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerRemark = sellerRemark;
	}

	public SellerCommand(String sellerId, String sellerName,
			String sellerPhone, Integer sellerSex, String sellerNo,
			String sellerPass, String sellerConfirmPass, String sellerProvince,
			String sellerCity, String sellerArea, String sellerPcode,
			String sellerCcode, String sellerAcode, String sellerqq,
			String sellerWechat, String sellerRemark) throws NegativeException {
		super();
		assertArgumentNotNull(sellerId, "业务员id不能为空");
		assertArgumentLength(sellerId, 36, "业务员id过长");

		if (StringUtils.isEmpty(sellerName.replaceAll(" ", ""))) {
			throw new NegativeException(400,"请输入业务员姓名");
		}
		if (null == sellerSex) {
			throw new NegativeException(400,"请选择业务员性别");
		}
		if (StringUtils.isEmpty(sellerPhone.replaceAll(" ", ""))) {
			throw new NegativeException(400,"请输入业务员电话");
		}
		if (StringUtils.isEmpty(sellerProvince.replaceAll(" ", "")) || StringUtils.isEmpty(sellerPcode.replaceAll(" ", ""))) {
			throw new NegativeException(400,"请输入省信息");
		}
		if (StringUtils.isEmpty(sellerCity.replaceAll(" ", "")) || StringUtils.isEmpty(sellerCcode.replaceAll(" ", ""))) {
			throw new NegativeException(400,"请输入市信息");
		}
		if (StringUtils.isEmpty(sellerArea.replaceAll(" ", "")) || StringUtils.isEmpty(sellerAcode.replaceAll(" ", ""))) {
			throw new NegativeException(400,"请输入区信息");
		}
		if (sellerName.length() > 20) {
			throw new NegativeException(400,"业务员姓名不能超过20位");
		}
		if (sellerPhone.length() > 11) {
			throw new NegativeException(400,"业务员电话不能超过11位");
		}
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerSex = sellerSex;
		this.sellerNo = sellerNo;
		this.sellerPass = sellerPass;
		this.sellerConfirmPass = sellerConfirmPass;
		this.sellerProvince = sellerProvince;
		this.sellerCity = sellerCity;
		this.sellerArea = sellerArea;
		this.sellerPcode = sellerPcode;
		this.sellerCcode = sellerCcode;
		this.sellerAcode = sellerAcode;
		this.sellerqq = sellerqq;
		this.sellerWechat = sellerWechat;
		this.sellerRemark = sellerRemark;
	}


	@Override
	public String toString() {
		return "SellerAddOrUpdateCommand [sellerId=" + sellerId
				+ ", sellerName=" + sellerName + ", sellerPhone=" + sellerPhone
				+ ", sellerSex=" + sellerSex + ", sellerNo=" + sellerNo
				+ ", sellerPass=" + sellerPass + ", sellerConfirmPass="
				+ sellerConfirmPass + ", sellerProvince=" + sellerProvince
				+ ", sellerCity=" + sellerCity + ", sellerArea=" + sellerArea
				+ ", sellerPcode=" + sellerPcode + ", sellerCcode="
				+ sellerCcode + ", sellerAcode=" + sellerAcode + ", sellerqq="
				+ sellerqq + ", sellerWechat=" + sellerWechat
				+ ", sellerRemark=" + sellerRemark + "]";
	}


	
	
}
