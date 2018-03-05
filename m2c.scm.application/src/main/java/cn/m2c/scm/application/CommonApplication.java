package cn.m2c.scm.application;

import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.DomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 公共应用类
 */
@Service
@Transactional
public class CommonApplication {
    //private static final Logger LOGGER = LoggerFactory.getLogger(CommonApplication.class);

    @Autowired
    DomainService domainService;

    /***
     * 生成商品SKU号（库中唯一）并返回
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public String generateGoodsSku() {
        return domainService.generateGoodsSku();
    }
    /***
     * 生成订单号（库中唯一）并返回
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public String generateOrderNo() {
        return domainService.generateOrderNo();
    }
}
