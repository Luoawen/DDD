package cn.m2c.scm.application.special.data.representation;

import java.util.Date;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialListBean;

/**
 * 特惠价列表表述对象
 */
public class GoodsSpecialListRepresentation {
	private String specialId;
    private String goodsId;
    private String goodsName;
    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    private Integer skuFlag;
    private String dealerId;
    private String dealerName;
    private Date startTime;
    private Date endTime;
    //private String congratulations;
    //private String activityDescription;
    private Integer status; //状态（0未生效，1已生效，2已失效）
    //private Date createTime;
    //private List<Map> goodsSkuSpecials;
    /**
     * 页面展示最小特惠价
     */
    private Long specialPriceMin;
    
    public GoodsSpecialListRepresentation(GoodsSpecialListBean goodsSpecialBean){
    	this.specialId = goodsSpecialBean.getSpecialId().toString();
    	this.goodsId = goodsSpecialBean.getGoodsId();
    	this.goodsName = goodsSpecialBean.getGoodsName();
    	this.skuFlag = goodsSpecialBean.getSkuFlag();
    	this.dealerId = goodsSpecialBean.getDealerId();
    	this.dealerName = goodsSpecialBean.getDealerName();
    	this.startTime = goodsSpecialBean.getStartTime();
    	this.endTime = goodsSpecialBean.getEndTime();
    	//this.congratulations = goodsSpecialBean.getCongratulations();
    	//this.activityDescription = goodsSpecialBean.getActivityDescription();
    	this.status = goodsSpecialBean.getStatus();
    	//this.createTime = goodsSpecialBean.getCreateTime();
    	//this.goodsSkuSpecials = JsonUtils.toList(JsonUtils.toStr(goodsSpecialBean.getGoodsSpecialSkuBeans()),Map.class);
    	/*Long specialPriceMinCompare = Long.MAX_VALUE;
    	for(GoodsSkuSpecialBean goodsSkuSpecialBean : goodsSpecialBean.getGoodsSpecialSkuBeans()) {
    		if(goodsSkuSpecialBean.getSpecialPrice()< specialPriceMinCompare ) {
    			specialPriceMinCompare = goodsSkuSpecialBean.getSpecialPrice();
    		}
    	}*/
    	this.specialPriceMin = goodsSpecialBean.getSpecialPriceMin();
    }
    
	public String getSpecialId() {
		return specialId;
	}
	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getSkuFlag() {
		return skuFlag;
	}
	public void setSkuFlag(Integer skuFlag) {
		this.skuFlag = skuFlag;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/*public String getCongratulations() {
		return congratulations;
	}
	public void setCongratulations(String congratulations) {
		this.congratulations = congratulations;
	}
	public String getActivityDescription() {
		return activityDescription;
	}
	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}*/
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	/*public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<Map> getGoodsSkuSpecials() {
		return goodsSkuSpecials;
	}
	public void setGoodsSkuSpecials(List<Map> goodsSkuSpecials) {
		this.goodsSkuSpecials = goodsSkuSpecials;
	}*/
	public Long getSpecialPriceMin() {
		return specialPriceMin;
	}
	public void setSpecialPriceMin(Long specialPriceMin) {
		this.specialPriceMin = specialPriceMin;
	}
}
