package cn.m2c.scm.port.adapter.restful.web.brand;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.brand.BrandApplication;
import cn.m2c.scm.application.brand.command.BrandCommand;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 品牌信息
 */
@RestController
@RequestMapping("/brand")
public class BrandAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(BrandAgent.class);

    @Autowired
    BrandApplication brandApplication;

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
    @RequestMapping(value = "", method = RequestMethod.POST)
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
                    threeAreaName, new Date(), null, 1);
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
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyBrand(
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
                    threeAreaName);
            brandApplication.modifyBrand(command);
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
}
