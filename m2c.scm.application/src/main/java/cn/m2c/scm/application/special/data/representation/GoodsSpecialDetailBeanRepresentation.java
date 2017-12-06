package cn.m2c.scm.application.special.data.representation;

import java.util.Date;
import java.util.List;

import cn.m2c.scm.application.special.data.bean.GoodsSpecialDetailBean;

/**
 * 特惠价详表述对象
 */
public class GoodsSpecialDetailBeanRepresentation {
    private Integer id;
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
    private String congratulations;
    private String activityDescription;
    private Integer status;
    private List<GoodsSkuSpecialDetailAllBeanRepresentation> goodsSkuSpecials;

	public GoodsSpecialDetailBeanRepresentation(GoodsSpecialDetailBean bean) {
		this.id = bean.getId();
		this.specialId = bean.getSpecialId();
		this.goodsId = bean.getGoodsId();
		this.goodsName = bean.getGoodsName();
		this.skuFlag = bean.getSkuFlag();
		this.dealerId = bean.getDealerId();
		this.dealerName = bean.getDealerName();
		this.startTime = bean.getStartTime();
		this.endTime = bean.getEndTime();
		this.congratulations = bean.getCongratulations();
		this.activityDescription = bean.getActivityDescription();
		this.status = bean.getStatus();
		this.goodsSkuSpecials = getGoodsSkuSpecials();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCongratulations() {
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
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<GoodsSkuSpecialDetailAllBeanRepresentation> getGoodsSkuSpecials() {
		return goodsSkuSpecials;
	}

	public void setGoodsSkuSpecials(List<GoodsSkuSpecialDetailAllBeanRepresentation> goodsSkuSpecials) {
		this.goodsSkuSpecials = goodsSkuSpecials;
	}
	
}
