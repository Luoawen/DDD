package cn.m2c.scm.domain.model.brand;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.brand.event.BrandDeleteEvent;

import java.util.Date;

/**
 * 品牌信息
 */
public class Brand extends ConcurrencySafeEntity {
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
     * 0:商家申请，需审批，1:商家管理平台添加，无需审批
     */
    private Integer isSysAdd;

    /**
     * 1:正常 2：删除
     */
    private Integer brandStatus;

    public Brand() {
        super();
    }

    public Brand(String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                 String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                 String threeAreaName, Date applyDate, String dealerId, Integer isSysAdd) {
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
        this.isSysAdd = isSysAdd;
    }

    public void modify(String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                       String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                       String threeAreaName) {
        this.brandName = brandName;
        this.brandNameEn = brandNameEn;
        this.brandLogo = brandLogo;
        this.firstAreaCode = firstAreaCode;
        this.twoAreaCode = twoAreaCode;
        this.threeAreaCode = threeAreaCode;
        this.firstAreaName = firstAreaName;
        this.twoAreaName = twoAreaName;
        this.threeAreaName = threeAreaName;
    }

    public void delete() {
        this.brandStatus = 2;
        DomainEventPublisher
                .instance()
                .publish(new BrandDeleteEvent(this.brandId));
    }
}