package cn.m2c.scm.application.postage.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.postage.data.bean.PostageModelRuleBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 运费模板查询
 */
@Service
public class PostageModelQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostageModelQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    public List<PostageModelBean> queryPostageModelsByDealerId(String dealerId) {
        String sql = "SELECT * FROM t_scm_postage_model WHERE 1 = 1 AND dealer_id = ? AND model_status = 1";
        List<PostageModelBean> postageModelBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), PostageModelBean.class,
                new Object[]{dealerId});
        if (null != postageModelBeans && postageModelBeans.size() > 0) {
            for (PostageModelBean bean : postageModelBeans) {
                bean.setPostageModelRuleBeans(queryPostageModelRuleByModelId(bean.getId()));
            }
        }
        return postageModelBeans;
    }

    public List<PostageModelRuleBean> queryPostageModelRuleByModelId(Integer modelId) {
        String sql = "SELECT * FROM t_scm_postage_model_rule  WHERE 1 = 1 AND model_id = ?";
        List<PostageModelRuleBean> postageModelRuleBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), PostageModelRuleBean.class,
                new Object[]{modelId});
        return postageModelRuleBeans;
    }
}
