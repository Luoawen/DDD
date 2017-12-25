package cn.m2c.scm.port.adapter.restful.web.brand;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.brand.BrandApplication;
import cn.m2c.scm.application.brand.command.BrandCommand;
import cn.m2c.scm.application.brand.data.bean.BrandBean;
import cn.m2c.scm.application.brand.data.representation.BrandChoiceRepresentation;
import cn.m2c.scm.application.brand.data.representation.BrandDetailRepresentation;
import cn.m2c.scm.application.brand.data.representation.BrandRepresentation;
import cn.m2c.scm.application.brand.query.BrandQueryApplication;
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
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 品牌信息
 */
@RestController
@RequestMapping("/brand")
public class BrandAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(BrandAgent.class);

    @Autowired
    BrandApplication brandApplication;
    @Autowired
    BrandQueryApplication brandQueryApplication;

    @Autowired
	private  HttpServletRequest request;
    
    /**
     * 获取ID
     *
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getBrandId() {
        MResult result = new MResult(MCode.V_1);
        try {
            String id = IDGenerator.get(IDGenerator.SCM_BRAND_PREFIX_TITLE);
            result.setContent(id);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getBrandId Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }


    /**
     * 添加品牌信息（商家管理平台，无需审批）
     *
     * @param brandId       品牌id
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
    @RequestMapping(value = "/mng", method = RequestMethod.POST)
    @RequirePermissions(value ={"scm:brand:add"})
    public ResponseEntity<MResult> addBrand(
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
            BrandCommand command = new BrandCommand(brandId, brandName, brandNameEn, brandLogo, firstAreaCode,
                    twoAreaCode, threeAreaCode, firstAreaName, twoAreaName,
                    threeAreaName, new Date(), null, "系统", 1);
            brandApplication.addBrand(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addBrand NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addBrand Exception e:", e);
            result = new MResult(MCode.V_400, "添加品牌失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改品牌信息（商家管理平台，无需审批）
     *
     * @param brandId       品牌id
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
    @RequestMapping(value = "/mng/{brandId}", method = RequestMethod.PUT)
    @RequirePermissions(value ={"scm:brand:modify"})
    public ResponseEntity<MResult> modifyBrand(
            @PathVariable("brandId") String brandId,
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
            BrandCommand command = new BrandCommand(brandId, brandName, brandNameEn, brandLogo, firstAreaCode,
                    twoAreaCode, threeAreaCode, firstAreaName, twoAreaName,
                    threeAreaName);
            String _attach= request.getHeader("attach");
            brandApplication.modifyBrand(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyBrand NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyBrand Exception e:", e);
            result = new MResult(MCode.V_400, "修改品牌失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 删除品牌
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = {"/{brandId}","/mng/{brandId}"}, method = RequestMethod.DELETE)
    @RequirePermissions(value ={"scm:brand:delete"})
    public ResponseEntity<MResult> deleteBrand(
            @PathVariable("brandId") String brandId) {
        MResult result = new MResult(MCode.V_1);
        try {
            brandApplication.delBrand(brandId);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("deleteBrand NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("deleteBrand Exception e:", e);
            result = new MResult(MCode.V_400, "删除品牌失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 查询品牌库列表
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
    public ResponseEntity<MPager> queryBrand(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "brandName", required = false) String brandName,
            @RequestParam(value = "condition", required = false) String condition,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = brandQueryApplication.queryBrandTotal(dealerId, brandName, condition, startTime,
                    endTime);
            if (total > 0) {
                List<BrandBean> brandBeans = brandQueryApplication.queryBrands(dealerId, brandName, condition, startTime,
                        endTime, pageNum, rows);
                if (null != brandBeans && brandBeans.size() > 0) {
                    List<BrandRepresentation> representations = new ArrayList<BrandRepresentation>();
                    for (BrandBean bean : brandBeans) {
                        representations.add(new BrandRepresentation(bean));
                    }
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询品牌列表失败", e);
            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 品牌详情
     *
     * @param brandId
     * @return
     */
    @RequestMapping(value = "/{brandId}", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryBrandDetail(@PathVariable("brandId") String brandId) {
        MResult result = new MResult(MCode.V_1);
        try {
            BrandBean bean = brandQueryApplication.queryBrand(brandId);
            if (null != bean) {
                result.setContent(new BrandDetailRepresentation(bean));
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询品牌详情失败", e);
            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/choice", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryBrandChoice(
            @RequestParam(value = "brandName", required = false) String brandName) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<BrandBean> beans = brandQueryApplication.queryBrandByName(brandName);
            if (null != beans && beans.size() > 0) {
                List<BrandChoiceRepresentation> representations = new ArrayList<>();
                for (BrandBean bean : beans) {
                    representations.add(new BrandChoiceRepresentation(bean));
                }
                result.setContent(representations);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询品牌失败", e);
            result = new MPager(MCode.V_400, "查询品牌失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

}
