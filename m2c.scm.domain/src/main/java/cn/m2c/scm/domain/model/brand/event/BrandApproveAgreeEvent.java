package cn.m2c.scm.domain.model.brand.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 同意品牌添加
 */
public class BrandApproveAgreeEvent implements DomainEvent {
    /**
     * 品牌id
     */
    private String brandId;
    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 英文名称
     */
    private String brandNameEn;

    /**
     * 品牌logo
     */
    private String brandLogo;

    /**
     * 一级区域编号
     */
    private String firstAreaCode;

    /**
     * 二级区域编号
     */
    private String twoAreaCode;

    /**
     * 三级区域编号
     */
    private String threeAreaCode;

    /**
     * 一级区域名称
     */
    private String firstAreaName;

    /**
     * 二级区域名称
     */
    private String twoAreaName;

    /**
     * 三级区域名称
     */
    private String threeAreaName;

    /**
     * 申请时间
     */
    private Date applyDate;

    /**
     * 商户ID
     */
    private String dealerId;

    /**
     * 商家名称
     */
    private String dealerName;

    /**
     * 操作标识，1：品牌新增或待审批品牌修改，2：已审批品牌修改
     */
    private Integer optFlag;

    private Date occurredOn;
    private int eventVersion;

    public BrandApproveAgreeEvent(String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                                  String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                                  String threeAreaName, Date applyDate, String dealerId, String dealerName, Integer optFlag) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandNameEn = brandNameEn;
        this.brandLogo = brandLogo;
        this.firstAreaCode = firstAreaCode;
        this.twoAreaCode = twoAreaCode;
        this.threeAreaCode = threeAreaCode;
        this.firstAreaName = firstAreaName;
        this.twoAreaName = twoAreaName;
        this.threeAreaName = threeAreaName;
        this.applyDate = applyDate;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.optFlag = optFlag;
        this.occurredOn = new Date();
        this.eventVersion = 1;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
