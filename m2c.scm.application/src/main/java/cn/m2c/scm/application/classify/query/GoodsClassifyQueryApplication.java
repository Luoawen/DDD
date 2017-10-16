package cn.m2c.scm.application.classify.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品分类查询
 */
@Service
public class GoodsClassifyQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsClassifyQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    /**
     * 商品分类树形结构
     *
     * @return
     */
    public List<Map> recursionQueryGoodsClassifyTree(String parentClassifyId) {
        List<Map> resultList = new ArrayList<>();
        List<GoodsClassifyBean> subGoodsClassifyBeans = queryGoodsClassifiesByParentId(parentClassifyId);
        if (null != subGoodsClassifyBeans && subGoodsClassifyBeans.size() > 0) {
            for (GoodsClassifyBean bean : subGoodsClassifyBeans) {
                Map map = new HashMap<>();
                map.put("classifyId", bean.getClassifyId());
                map.put("classifyName", bean.getClassifyName());
                map.put("parentClassifyId", bean.getParentClassifyId());
                map.put("serviceRate", bean.getServiceRate());
                List<Map> subList = recursionQueryGoodsClassifyTree(bean.getClassifyId());
                if (null != subList && subList.size() > 0) {
                    map.put("subClassify", subList);
                }
                resultList.add(map);
            }
        }
        return resultList;
    }

    public List<GoodsClassifyBean> queryGoodsClassifiesByParentId(String parentId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_classify WHERE 1 = 1");
        sql.append(" AND parent_classify_id = ? AND status = 1");
        List<GoodsClassifyBean> goodsClassifyBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsClassifyBean.class, parentId);
        return goodsClassifyBeans;
    }

    public GoodsClassifyBean queryGoodsClassifiesById(String classifyId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_classify WHERE 1 = 1");
        sql.append(" AND classify_id = ? AND status = 1");
        GoodsClassifyBean goodsClassifyBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsClassifyBean.class, classifyId);
        return goodsClassifyBean;
    }

    public List<String> recursionQueryGoodsSubClassifyId(String parentClassifyId, List<String> resultList) {
        List<GoodsClassifyBean> subGoodsClassifyBeans = queryGoodsClassifiesByParentId(parentClassifyId);
        if (null != subGoodsClassifyBeans && subGoodsClassifyBeans.size() > 0) {
            for (GoodsClassifyBean bean : subGoodsClassifyBeans) {
                resultList.add(bean.getClassifyId());
                List<String> subList = recursionQueryGoodsSubClassifyId(bean.getClassifyId(), resultList);
                if (null != subList && subList.size() > 0) {
                    resultList.addAll(subList);
                }
            }
        }
        return resultList;
    }
}
