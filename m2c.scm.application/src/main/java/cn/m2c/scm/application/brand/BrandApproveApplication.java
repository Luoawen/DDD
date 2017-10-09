package cn.m2c.scm.application.brand;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.brand.command.BrandCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.brand.BrandApprove;
import cn.m2c.scm.domain.model.brand.BrandApproveRepository;
import cn.m2c.scm.domain.model.brand.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 品牌审批
 */
@Service
public class BrandApproveApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandApproveApplication.class);

    @Autowired
    BrandApproveRepository brandApproveRepository;
    @Autowired
    BrandRepository brandRepository;

    /**
     * 添加品牌信息（商家平台，需审批）
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addBrandApprove(BrandCommand command) throws NegativeException {
        LOGGER.info("addBrandApprove command >>{}", command);
        // 与当前品牌库中的不能重名
        List<Brand> brands = brandRepository.getBrandByBrandName(command.getBrandName());
        if (null != brands && brands.size() > 0) {
            throw new NegativeException(MCode.V_301, "品牌名称已存在");
        }
        BrandApprove brandApprove = brandApproveRepository.getBrandApproveByApproveId(command.getBrandApproveId());
        if (null == brandApprove) {
            brandApprove = new BrandApprove(command.getBrandApproveId(), command.getBrandName(), command.getBrandNameEn(), command.getBrandLogo(), command.getFirstAreaCode(),
                    command.getTwoAreaCode(), command.getThreeAreaCode(), command.getFirstAreaName(), command.getTwoAreaName(),
                    command.getThreeAreaName(), command.getDealerId());
            brandApproveRepository.save(brandApprove);
        }
    }

    /**
     * 同意添加品牌
     *
     * @param approveId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void agreeAddBrandApprove(String approveId) throws NegativeException {
        LOGGER.info("agreeAddBrandApprove approveId >>{}", approveId);
        BrandApprove brandApprove = brandApproveRepository.getBrandApproveByApproveId(approveId);
        if (null == brandApprove) {
            throw new NegativeException(MCode.V_300, "待审批品牌不存在");
        }
        brandApprove.agreeAddBrand();
        brandApproveRepository.remove(brandApprove);
    }
}
