package cn.m2c.scm.port.adapter.restful.web.brand;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.brand.BrandApproveApplication;
import cn.m2c.scm.application.brand.command.BrandApproveAgreeCommand;
import cn.m2c.scm.application.brand.command.BrandApproveCommand;
import cn.m2c.scm.application.brand.command.BrandApproveRejectCommand;
import cn.m2c.scm.application.brand.data.bean.BrandApproveBean;
import cn.m2c.scm.application.brand.data.representation.BrandApproveDetailRepresentation;
import cn.m2c.scm.application.brand.data.representation.BrandApproveRepresentation;
import cn.m2c.scm.application.brand.query.BrandApproveQueryApplication;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 品牌审核信息
 */
@RestController
@RequestMapping("/brand/approve")
public class BrandApproveAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(BrandApproveAgent.class);

    @Autowired
    BrandApproveApplication brandApproveApplication;
    @Autowired
    BrandApproveQueryApplication brandApproveQueryApplication;

    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getBrandApproveId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_BRANDE_APPROVE_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getBrandApproveId Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }


    /**
     * 添加品牌/修改品牌库中品牌（商家平台，需审核）
     *
     * @param dealerId      经销商id
     * @param approveId     品牌id
     * @param brandName     品牌名称
     * @param brandNameEn   英文名称
     * @param brandLogo     品牌logo
     * @param firstAreaCode 一级区域编号
     * @param twoAreaCode   二级区域编号
     * @param threeAreaCode 三级区域编号
     * @param firstAreaName 一级区域名称
     * @param twoAreaName   二级区域名称
     * @param threeAreaName 三级区域名称
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MResult> addBrandApprove(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "dealerName", required = false) String dealerName,
            @RequestParam(value = "approveId", required = false) String approveId,
            @RequestParam(value = "brandId", required = false) String brandId,
            @RequestParam(value = "brandName", required = false) String brandName,
            @RequestParam(value = "brandNameEn", required = false) String brandNameEn,
            @RequestParam(value = "brandLogo", required = false) String brandLogo,
            @RequestParam(value = "firstAreaCode", required = false) String firstAreaCode,
            @RequestParam(value = "twoAreaCode", required = false) String twoAreaCode,
            @RequestParam(value = "threeAreaCode", required = false) String threeAreaCode,
            @RequestParam(value = "firstAreaName", required = false) String firstAreaName,
            @RequestParam(value = "twoAreaName", required = false) String twoAreaName,
            @RequestParam(value = "threeAreaName", required = false) String threeAreaName) {
        MResult result = new MResult(MCode.V_1);
        try {
            BrandApproveCommand command = new BrandApproveCommand(approveId, brandId, brandName, brandNameEn, brandLogo, firstAreaCode,
                    twoAreaCode, threeAreaCode, firstAreaName, twoAreaName, threeAreaName, dealerId, dealerName);
            brandApproveApplication.addBrandApprove(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addBrandApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addBrandApprove Exception e:", e);
            result = new MResult(MCode.V_400, "添加待审批品牌失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改审批中的品牌信息（商家平台）
     *
     * @param approveId     品牌id
     * @param brandName     品牌名称
     * @param brandNameEn   英文名称
     * @param brandLogo     品牌logo
     * @param firstAreaCode 一级区域编号
     * @param twoAreaCode   二级区域编号
     * @param threeAreaCode 三级区域编号
     * @param firstAreaName 一级区域名称
     * @param twoAreaName   二级区域名称
     * @param threeAreaName 三级区域名称
     * @return
     */
    @RequestMapping(value = "/{approveId}", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyBrandApprove(
            @PathVariable("approveId") String approveId,
            @RequestParam(value = "brandName", required = false) String brandName,
            @RequestParam(value = "brandNameEn", required = false) String brandNameEn,
            @RequestParam(value = "brandLogo", required = false) String brandLogo,
            @RequestParam(value = "firstAreaCode", required = false) String firstAreaCode,
            @RequestParam(value = "twoAreaCode", required = false) String twoAreaCode,
            @RequestParam(value = "threeAreaCode", required = false) String threeAreaCode,
            @RequestParam(value = "firstAreaName", required = false) String firstAreaName,
            @RequestParam(value = "twoAreaName", required = false) String twoAreaName,
            @RequestParam(value = "threeAreaName", required = false) String threeAreaName) {
        MResult result = new MResult(MCode.V_1);
        try {
            BrandApproveCommand command = new BrandApproveCommand(approveId, brandName, brandNameEn, brandLogo, firstAreaCode,
                    twoAreaCode, threeAreaCode, firstAreaName, twoAreaName, threeAreaName);
            brandApproveApplication.modifyBrandApprove(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyBrandApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyBrandApprove Exception e:", e);
            result = new MResult(MCode.V_400, "修改待审批品牌失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商家管理平台审核同意
     *
     * @param approveId
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    public ResponseEntity<MResult> brandApproveAgree(
            @RequestParam(value = "approveId", required = false) String approveId,
            @RequestParam(value = "brandId", required = false) String brandId
    ) {
        MResult result = new MResult(MCode.V_1);
        BrandApproveAgreeCommand command = new BrandApproveAgreeCommand(brandId, approveId);
        try {
            brandApproveApplication.agreeBrandApprove(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("brandApproveAgree NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("brandApproveAgree Exception e:", e);
            result = new MResult(MCode.V_400, "同意待审批品牌");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商家管理平台审核拒绝
     *
     * @param approveId
     * @param rejectReason
     * @return
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    public ResponseEntity<MResult> brandApproveReject(
            @RequestParam(value = "approveId", required = false) String approveId,
            @RequestParam(value = "rejectReason", required = false) String rejectReason
    ) {
        MResult result = new MResult(MCode.V_1);
        BrandApproveRejectCommand command = new BrandApproveRejectCommand(approveId, rejectReason);
        try {
            brandApproveApplication.rejectBrandApprove(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("brandApproveReject NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("brandApproveReject Exception e:", e);
            result = new MResult(MCode.V_400, "拒绝待审批品牌");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 删除品牌审核信息
     *
     * @param approveId
     * @return
     */
    @RequestMapping(value = "/{approveId}", method = RequestMethod.DELETE)
    public ResponseEntity<MResult> deleteBrandApprove(
            @PathVariable("approveId") String approveId) {
        MResult result = new MResult(MCode.V_1);
        try {
            brandApproveApplication.delBrandApprove(approveId);
        } catch (NegativeException ne) {
            LOGGER.error("deleteBrandApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("deleteBrandApprove Exception e:", e);
            result = new MResult(MCode.V_400, "删除品牌审核信息失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询品牌审核列表
     *
     * @param dealerId  商家ID
     * @param brandName 品牌名称
     * @param condition 搜索条件
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param pageNum   第几页
     * @param rows      每页多少行
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MPager> queryBrandApprove(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "brandName", required = false) String brandName,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = brandApproveQueryApplication.queryBrandApproveTotal(dealerId, brandName, condition, startTime,
                    endTime);
            if (total > 0) {
                List<BrandApproveBean> brandBeans = brandApproveQueryApplication.queryBrandApproves(dealerId, brandName, condition, startTime,
                        endTime, pageNum, rows);
                if (null != brandBeans && brandBeans.size() > 0) {
                    List<BrandApproveRepresentation> representations = new ArrayList<BrandApproveRepresentation>();
                    for (BrandApproveBean bean : brandBeans) {
                        representations.add(new BrandApproveRepresentation(bean));
                    }
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询品牌审核列表失败", e);
            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 品牌审核详情
     *
     * @param approveId
     * @return
     */
    @RequestMapping(value = "/{approveId}", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryBrandApproveDetail(@PathVariable("approveId") String approveId) {
        MResult result = new MResult(MCode.V_1);
        try {
            BrandApproveBean bean = brandApproveQueryApplication.queryBrandApprove(approveId);
            if (null != bean) {
                result.setContent(new BrandApproveDetailRepresentation(bean));
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询品牌审核详情失败", e);
            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
