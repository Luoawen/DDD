package cn.m2c.scm.domain.model.goods;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 商品保障
 * */
public class GoodsGuarantee extends ConcurrencySafeEntity {
	private String guaranteeId;    //商品保障id
	private String guaranteeName;  //商品保障名
	private String guaranteeDesc;  //商品保障内容
	private Integer guaranteeOrder;//商品保障排序
	private String guaranteePic;   //商品保障图
	private String dealerId;       //商家id
	private Integer isDefault;     //是否系统默认 0非默认(商家自定义)  1默认(系统初始化数据)
	private Date createdDate;       //创建时间
	private Date lastUpdatedDate;   //最后更新时间
	private Integer guaranteeStatus;//状态  1正常  2删除
	
	public GoodsGuarantee(){
		super();
	}
	
	public GoodsGuarantee(String guaranteeId, String guaranteeName, String guaranteeDesc, String dealerId){
		this.guaranteeId   = guaranteeId;
		this.guaranteeName = guaranteeName;
		this.guaranteeDesc = guaranteeDesc;
		this.dealerId      = dealerId;
		this.createdDate    = new Date();
		this.lastUpdatedDate = new Date();
		this.guaranteeOrder= 0;
		this.isDefault = 0;        //商家自定义
		this.guaranteeStatus = 1;  //状态  正常
	}
	
	public void modifyGoodsGuarantee(String guaranteeName, String guaranteeDesc){
		this.guaranteeName = guaranteeName;
		this.guaranteeDesc = guaranteeDesc;
		this.lastUpdatedDate = new Date();
	}
	
	/**
     * 删除商品保障
     */
    public void remove() {
    	this.guaranteeStatus = 2;
    }
}
