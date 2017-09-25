package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class BrandAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = -2710207931101187659L;
	private String brandId;
	private String modelName;
	private String brandName;
	private String brandPic;
	private String brandDesc;
	private String dealerId;
	public BrandAddOrUpdateCommand() {
		super();
	}
	
	
	public BrandAddOrUpdateCommand(String brandId,String modelName, 
			String brandName, String brandPic, String brandDesc ,String dealerId) {
		super();
		
		assertArgumentNotNull(dealerId, "经销商不能为空");
		
		
		assertArgumentNotNull(modelName, "模板名称不能为空");
		assertArgumentLength(modelName, 50, "模板名称过长");
		
		assertArgumentNotNull(brandName, "品牌名不能为空");
		assertArgumentLength(brandName, 50, "品牌名过长");
		
		
		assertArgumentNotNull(brandPic, "品牌图片不能为空");
		assertArgumentLength(brandPic, 100, "品牌图片过长");
		
		assertArgumentNotNull(brandDesc, "品牌描述不能为空");
		this.brandId = brandId;
		this.modelName = modelName;
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

	public String getDealerId() {
		return dealerId;
	}
	
	
	
}
