package cn.m2c.scm.port.adapter.service;

import cn.m2c.common.RandomUtils;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.service.DomainService;
import cn.m2c.scm.domain.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return this.supportJdbcTemplate;
    }

    @Override
    public List<Map> getGoodsTags(String goodsId) {
        return null;
    }

    public String generateGoodsSku() {
        String time = DateUtils.getDateStr(DateUtils.TYPE_0);
        String random = RandomUtils.toNums(6);
        String sku = new StringBuffer().append(time).append(random).toString();
        saveGenerateNo(sku, 2);
        return sku;
    }


    @Override
    public void saveGenerateNo(String no, Integer type) {
        String sql = "INSERT INTO `t_scm_generate_no` (`unique_no`, `type`) VALUES (?, ?)";
        this.getSupportJdbcTemplate().jdbcTemplate().update(sql, new Object[]{no, type});
    }
}
