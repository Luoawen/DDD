package cn.m2c.scm.domain.model.goods.classify;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class GoodsClassify extends ConcurrencySafeEntity{

	private static final long serialVersionUID = 5087865369084298155L;
	
	private String goodsClassifyId;//分类id
	private String goodsClassifyName;//分类名
	private Integer goodsCount; //商品总数
	private Integer orderBy;//同父级分类下展示顺序
	private Integer isParent;//1表示含有子分类,0表示没有子分类
	private String parentId = "0";//父级分类编号,最上层的分类的父级分类编号默认为0
	
	private Date createdDate;
	private Date lastUpdatedDate;
	
	
	public GoodsClassify() {
		super();
	}
	
	
	public GoodsClassify(String goodsClassifyId, String goodsClassifyName,
			 Integer goodsCount, Integer orderBy,
			Integer isParent, String parentId, Date createdDate,
			Date lastUpdatedDate) {
		super();
		this.goodsClassifyId = goodsClassifyId;
		this.goodsClassifyName = goodsClassifyName;
		this.goodsCount = goodsCount;
		this.orderBy = orderBy;
		this.isParent = isParent;
		this.parentId = parentId;
		this.createdDate = createdDate;
		this.lastUpdatedDate = lastUpdatedDate;
	}


	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}
	public Integer getGoodsCount() {
		return goodsCount;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public Integer getIsParent() {
		return isParent;
	}
	public String getParentId() {
		return parentId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public String getGoodsClassifyName() {
		return goodsClassifyName;
	}

	public void changeCount(int changecount) {
		this.goodsCount+=changecount;
	}


	
	
	
}
