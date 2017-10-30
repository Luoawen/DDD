package cn.m2c.scm.domain.model.brand;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.brand.event.BrandApproveAgreeEvent;
import org.apache.commons.lang3.StringUtils;

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
    private Integer approveStatus;

    /**
     * 审批不通过原因
     */
    private String rejectReason;

    /**
     * 商家ID
     */
    private String dealerId;

    /**
     * 商家名称
     */
    private String dealerName;

    /**
     * 品牌审批状态，1:正常 2：删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createdDate;

    public BrandApprove() {
        super();
    }

    /**
     * 添加品牌信息/修改正式品牌信息（商家平台，需审批）
     */
    public BrandApprove(String approveId, String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                        String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                        String threeAreaName, String dealerId, String dealerName) {
        this.approveId = approveId;
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
        this.approveStatus = 1;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.createdDate = new Date();
    }

    /**
     * 修改审批中的品牌信息（商家平台）
     */
    public void modifyBrandApprove(String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
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
        this.approveStatus = 1;
        this.rejectReason = null;
    }


    /**
     * 商家管理平台审核同意
     */
    public void agree() {
        //1：品牌新增或待审批品牌修改，2：已审批品牌修改
        Integer optFlag = 1;
        if (StringUtils.isNotEmpty(this.brandId)) {
            optFlag = 2;
        }
        DomainEventPublisher
                .instance()
                .publish(new BrandApproveAgreeEvent(this.brandId, this.brandName, this.brandNameEn, this.brandLogo, this.firstAreaCode,
                        this.twoAreaCode, this.threeAreaCode, this.firstAreaName, this.twoAreaName,
                        this.threeAreaName, this.createdDate, this.dealerId, this.dealerName, optFlag));
    }

    /**
     * 商家管理平台审核拒绝
     */
    public void reject(String rejectReason) {
        this.approveStatus = 2;
        this.rejectReason = rejectReason;
    }

    /**
     * 删除
     */
    public void delete() {
        this.status = 2;
    }
}