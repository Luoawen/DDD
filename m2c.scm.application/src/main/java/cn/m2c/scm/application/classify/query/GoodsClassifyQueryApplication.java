package cn.m2c.scm.application.classify.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
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
                recursionQueryGoodsSubClassifyId(bean.getClassifyId(), resultList);
            }
        }
        return resultList;
    }

    private List<String> recursionQueryGoodsUpClassifyName(String classifyId, List<String> classifyNames) {
        GoodsClassifyBean bean = queryGoodsClassifiesById(classifyId);
        if (null != bean) {
            classifyNames.add(bean.getClassifyName());
            if (!"-1".equals(bean.getParentClassifyId())) {
                return recursionQueryGoodsUpClassifyName(bean.getParentClassifyId(), classifyNames);
            }
        }
        return classifyNames;
    }

    public String getClassifyNames(String classifyId) {
        List<String> goodsClassifies = recursionQueryGoodsUpClassifyName(classifyId, new ArrayList<>());
        if (null != goodsClassifies && goodsClassifies.size() > 0) {
            Collections.reverse(goodsClassifies);
            String[] type = new String[goodsClassifies.size()];
            String[] newGoodsClassify = (String[]) goodsClassifies.toArray(type);
            String goodsClassify = String.join(",", newGoodsClassify);
            return goodsClassify;
        }
        return null;
    }

    public List<GoodsClassifyBean> queryGoodsClassifyRandom(Integer number) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_classify where status= 1 order by rand() limit 0,?");
        List<GoodsClassifyBean> goodsClassifyBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsClassifyBean.class, number);
        return goodsClassifyBeans;
    }

    public List<GoodsClassifyBean> queryGoodsClassifiesByLevel(Integer level) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_classify WHERE 1 = 1");
        sql.append(" AND level = ? AND status = 1");
        List<GoodsClassifyBean> goodsClassifyBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsClassifyBean.class, level);
        return goodsClassifyBeans;
    }

    public Float queryServiceRateByClassifyId(String classifyId) {
        GoodsClassifyBean bean = queryGoodsClassifiesById(classifyId);
        if (null != bean) {
            Float rate = bean.getServiceRate();
            if (null == rate) {
                if (!"-1".equals(bean.getParentClassifyId())) {//不是一级分类
                    //找上级分类
                    return queryServiceRateByClassifyId(bean.getParentClassifyId());
                }
            } else {
                return rate;
            }
        }
        return null;
    }

    public List<GoodsClassifyBean> queryGoodsClassifiesByName(String classifyName) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_classify WHERE 1 = 1");
        sql.append(" AND classify_name LIKE ? AND status = 1");
        params.add("%" + classifyName + "%");
        List<GoodsClassifyBean> goodsClassifyBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsClassifyBean.class, params.toArray());
        return goodsClassifyBeans;
    }

    public List<String> getGoodsSubClassifyIdByName(String classifyName) {
        List<String> subList = new ArrayList<>();
        List<GoodsClassifyBean> list = queryGoodsClassifiesByName(classifyName);
        if (null != list && list.size() > 0) {
            for (GoodsClassifyBean bean : list) {
                List<String> goodsClassifyIds = recursionQueryGoodsSubClassifyId(bean.getClassifyId(), new ArrayList<String>());
                goodsClassifyIds.add(bean.getClassifyId());
                subList.addAll(goodsClassifyIds);
            }
        }
        return subList;
    }

    private GoodsClassifyBean getFirstClassifyByClassifyId(String classifyId) {
        GoodsClassifyBean bean = queryGoodsClassifiesById(classifyId);
        if (null != bean && "-1".equals(bean.getParentClassifyId())) {
            return bean;
        } else {
            return getFirstClassifyByClassifyId(bean.getParentClassifyId());
        }
    }

    public List<GoodsClassifyBean> getFirstClassifyByClassifyIds(List<String> classifyIds) {
        List<GoodsClassifyBean> list = new ArrayList<>();
        for (String classifyId : classifyIds) {
            GoodsClassifyBean bean = queryGoodsClassifiesById(classifyId);
            if (null != bean && "-1".equals(bean.getParentClassifyId())) {
                list.add(bean);
            } else {
                list.add(getFirstClassifyByClassifyId(bean.getParentClassifyId()));
            }
        }
        return list;
    }
}
