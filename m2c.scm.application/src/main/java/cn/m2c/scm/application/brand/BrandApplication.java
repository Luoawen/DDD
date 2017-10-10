package cn.m2c.scm.application.brand;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.brand.command.BrandCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.brand.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 品牌
 */
@Service
public class BrandApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrandApplication.class);

    @Autowired
    BrandRepository brandRepository;

    /**
     * 添加品牌信息（商家管理平台，无需审批）
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addBrand(BrandCommand command) throws NegativeException {
        LOGGER.info("addBrand command >>{}", command);
        // 与当前品牌库中的不能重名
        if (brandRepository.brandNameIsRepeat(null, command.getBrandName())) {
            throw new NegativeException(MCode.V_301, "品牌名称已存在");
        }
        Brand brand = brandRepository.getBrandByBrandId(command.getBrandId());
        if (null == brand) {
            brand = new Brand(command.getBrandId(), command.getBrandName(), command.getBrandNameEn(), command.getBrandLogo(), command.getFirstAreaCode(),
                    command.getTwoAreaCode(), command.getThreeAreaCode(), command.getFirstAreaName(), command.getTwoAreaName(),
                    command.getThreeAreaName(), command.getApplyDate(), command.getDealerId(), command.getIsSysAdd());
            brandRepository.save(brand);
        }
    }

    /**
     * 修改品牌信息（商家管理平台，无需审批）
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyBrand(BrandCommand command) throws NegativeException {
        LOGGER.info("modifyBrand command >>{}", command);
        // 与当前品牌库中的不能重名
        if (brandRepository.brandNameIsRepeat(command.getBrandId(), command.getBrandName())) {
            throw new NegativeException(MCode.V_301, "品牌名称已存在");
        }
        Brand brand = brandRepository.getBrandByBrandId(command.getBrandId());
        if (null == brand) {
            throw new NegativeException(MCode.V_300, "品牌不存在");
        }
        brand.modify(command.getBrandName(), command.getBrandNameEn(), command.getBrandLogo(), command.getFirstAreaCode(),
                command.getTwoAreaCode(), command.getThreeAreaCode(), command.getFirstAreaName(), command.getTwoAreaName(),
                command.getThreeAreaName());
    }

    /**
     * 删除品牌信息
     *
     * @param brandId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void delBrand(String brandId) throws NegativeException {
        LOGGER.info("delBrand brandId >>{}", brandId);
        Brand brand = brandRepository.getBrandByBrandId(brandId);
        if (null == brand) {
            throw new NegativeException(MCode.V_300, "品牌不存在");
        }
        //todo 判断品牌是否被使用

        brand.delete();
    }
}
