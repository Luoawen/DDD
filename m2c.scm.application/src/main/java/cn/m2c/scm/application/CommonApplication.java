package cn.m2c.scm.application;

import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.DomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公共
 */
@Service
@Transactional
public class CommonApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonApplication.class);

    DomainService domainService;

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public String generateGoodsSku() {
        return domainService.generateGoodsSku();
    }
}
