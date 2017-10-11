package cn.m2c.scm.application.address;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.address.command.AfterSaleAddressCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.address.AfterSaleAddress;
import cn.m2c.scm.domain.model.address.AfterSaleAddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 售后地址
 */
@Service
@Transactional
public class AfterSaleAddressApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AfterSaleAddressApplication.class);

    @Autowired
    AfterSaleAddressRepository afterSaleAddressRepository;

    /**
     * 添加售后地址
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void addAfterSaleAddress(AfterSaleAddressCommand command) {
        LOGGER.info("addAfterSaleAddress command >>{}", command);
        AfterSaleAddress afterSaleAddress = afterSaleAddressRepository.getAfterSaleAddressByAddressId(command.getAddressId());
        if (null == afterSaleAddress) {
            afterSaleAddress = new AfterSaleAddress(command.getAddressId(), command.getDealerId(), command.getProCode(), command.getCityCode(), command.getAreaCode(),
                    command.getProName(), command.getCityName(), command.getAreaName(), command.getAddress(), command.getContactName(), command.getContactNumber());
            afterSaleAddressRepository.save(afterSaleAddress);
        }
    }

    /**
     * 修改售后地址
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyAfterSaleAddress(AfterSaleAddressCommand command) throws NegativeException {
        LOGGER.info("modifyAfterSaleAddress command >>{}", command);
        AfterSaleAddress afterSaleAddress = afterSaleAddressRepository.getAfterSaleAddressByAddressId(command.getAddressId());
        if (null == afterSaleAddress) {
            throw new NegativeException(MCode.V_300, "售后地址不存在");
        }
        afterSaleAddress.modifyAfterSaleAddress(command.getProCode(), command.getCityCode(), command.getAreaCode(),
                command.getProName(), command.getCityName(), command.getAreaName(), command.getAddress(), command.getContactName(), command.getContactNumber());
    }
}
