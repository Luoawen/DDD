package cn.m2c.scm.application.address.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.address.data.bean.AfterSaleAddressBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 运费模板查询
 */
@Service
public class AfterSaleAddressQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(AfterSaleAddressQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public AfterSaleAddressBean queryAfterSaleAddressByDealerId(String dealerId) {
        String sql = "SELECT * FROM t_scm_after_sale_address WHERE 1 = 1 AND dealer_id = ?";
        AfterSaleAddressBean afterSaleAddressBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), AfterSaleAddressBean.class,
                new Object[]{dealerId});
        return afterSaleAddressBean;
    }
}
