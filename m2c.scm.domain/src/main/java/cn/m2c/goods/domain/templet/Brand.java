package cn.m2c.goods.domain.templet;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
/**
 * 品牌模板
 * @author yezp
 *
 */
public class Brand extends ConcurrencySafeEntity{

	private static final long serialVersionUID = -7147037206383356892L;
	private String modelName;
	private String brandId;
	private String brandName;
	private String brandPic;
	private String brandDesc;
	private String dealerId;
	private Integer brandStatus=1;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	
	public Brand() {
		super();
	}

	

	public Brand(String modelName, String brandId, String brandName,
			String brandPic, String brandDesc,String dealerId) {
		super();
		this.modelName = modelName;
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandPic = brandPic;
		this.brandDesc = brandDesc;
		this.dealerId = dealerId;
	}
	public String getModelName() {
		return modelName;
	}
	public String getBrandId() {
		return brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getBrandPic() {
		return brandPic;
	}

	public String getBrandDesc() {
		return brandDesc;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getDealerId() {
		return dealerId;
	}

	public Integer getBrandStatus() {
		return brandStatus;
	}



	/**
	 * 修改品牌信息
	 * @param modelName
	 * @param brandName
	 * @param brandPic
	 * @param brandDesc
	 */
	public void update(String modelName, String brandName, String brandPic,
			String brandDesc) {
		this.modelName = modelName;
		this.brandName = brandName;
		this.brandPic = brandPic;
		this.brandDesc = brandDesc;
	}


	/**
	 * 添加品牌
	 * @param modelName
	 * @param brandId
	 * @param brandName
	 * @param brandPic
	 * @param brandDesc
	 * @param dealerId
	 */
	public void add(String modelName, String brandId, String brandName,
			String brandPic, String brandDesc, String dealerId) {
		this.modelName = modelName;
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandPic = brandPic;
		this.brandDesc = brandDesc;
		this.dealerId = dealerId;
	}


	/**
	 * 删除
	 */

	public void delBrand() {
		this.brandStatus = 2;
	}
}
