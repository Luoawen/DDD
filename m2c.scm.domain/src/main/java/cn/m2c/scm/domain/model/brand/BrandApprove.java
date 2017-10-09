package cn.m2c.scm.domain.model.brand;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.brand.event.BrandApproveAgreeAddEvent;

import java.util.Date;

/**
 * 品牌信息审批
 */
public class BrandApprove extends ConcurrencySafeEntity {
    /**
     * 品牌审批id
     */
    private String approveId;

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
     * 品牌审批状态，1：审批中，2：审批不通过
     */
    private Integer brandStatus;

    /**
     * 审批不通过原因
     */
    private String rejectReason;

    /**
     * 商户ID
     */
    private String dealerId;

    /**
     * 创建时间
     */
    private Date createdDate;

    public BrandApprove() {
        super();
    }

    public BrandApprove(String approveId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                        String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                        String threeAreaName, String dealerId) {
        this.approveId = approveId;
        this.brandName = brandName;
        this.brandNameEn = brandNameEn;
        this.brandLogo = brandLogo;
        this.firstAreaCode = firstAreaCode;
        this.twoAreaCode = twoAreaCode;
        this.threeAreaCode = threeAreaCode;
        this.firstAreaName = firstAreaName;
        this.twoAreaName = twoAreaName;
        this.threeAreaName = threeAreaName;
        this.brandStatus = 1;
        this.dealerId = dealerId;
        this.createdDate = new Date();
    }

    /**
     * 同意品牌添加
     */
    public void agreeAddBrand() {
        DomainEventPublisher
                .instance()
                .publish(new BrandApproveAgreeAddEvent(this.brandName, this.brandNameEn, this.brandLogo, this.firstAreaCode,
                        this.twoAreaCode, this.threeAreaCode, this.firstAreaName, this.twoAreaName,
                        this.threeAreaName, this.createdDate, this.dealerId));
    }

    /**
     * 同意品牌修改
     */
    public void agreeModifyBrand() {

    }

    /**
     * 拒绝
     */
    public void reject(String rejectReason) {
        this.brandStatus = 2;
        this.rejectReason = rejectReason;
    }
}