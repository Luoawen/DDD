package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;

/**
 * 商品保障
 */
public class GoodsGuaranteeRepresentation {
    private String guaranteeId;

    private String guaranteeName;

    private String guaranteeDesc;
    
    private Integer isDefault; //是否系统默认 0非默认(商家自定义)  1默认(系统初始化数据)

    public GoodsGuaranteeRepresentation(GoodsGuaranteeBean bean) {
        this.guaranteeId   = bean.getGuaranteeId();
        this.guaranteeName = bean.getGuaranteeName();
        this.guaranteeDesc = bean.getGuaranteeDesc();
        this.isDefault     = bean.getIsDefault();
    }

    public String getGuaranteeId() {
        return guaranteeId;
    }

    public void setGuaranteeId(String guaranteeId) {
        this.guaranteeId = guaranteeId;
    }

    public String getGuaranteeName() {
        return guaranteeName;
    }

    public void setGuaranteeName(String guaranteeName) {
        this.guaranteeName = guaranteeName;
    }

    public String getGuaranteeDesc() {
        return guaranteeDesc;
    }

    public void setGuaranteeDesc(String guaranteeDesc) {
        this.guaranteeDesc = guaranteeDesc;
    }

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
}
