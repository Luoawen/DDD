package cn.m2c.scm.application.dealer.data.export;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.m2c.scm.application.seller.data.bean.SellerBean;
import cn.m2c.scm.application.utils.ExcelField;


/**
 * 业务员导出
 * @author ps
 *
 */
public class SellerExportModel {

	/**
	 * 业务员名字
	 */
	@ExcelField(title = "姓名")
	private String sellerName;

	/**
	 * 业务员手机
	 */
	@ExcelField(title = "手机号")
	private String sellerPhone;
	/**
	 * 业务员所在省
	 */
	@ExcelField(title = "所属区域")
	private String belongArea;
	/**
	 * 业务员所在市
	 */
	@ExcelField(title = "入职时间")
	private String createdDate;
	
	
	
	public SellerExportModel(SellerBean model) {
		// TODO Auto-generated constructor stub
		this.sellerName = model.getSellerName();
		this.sellerPhone = model.getSellerPhone();
		this.belongArea = model.getSellerProvince()+model.getSellerCity()+model.getSellerArea();
		this.createdDate = dealDate(model.getCreatedDate());
	}
	/**
	 * 处理date
	 * @param createdDate 成string
	 * @return
	 */
	private String dealDate(Date date) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		String str=sdf.format(date);  
		return str;
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
	public String getBelongArea() {
		return belongArea;
	}
	public void setBelongArea(String belongArea) {
		this.belongArea = belongArea;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	
	
}
