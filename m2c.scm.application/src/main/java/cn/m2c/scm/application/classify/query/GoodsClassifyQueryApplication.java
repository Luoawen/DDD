package cn.m2c.scm.application.classify.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;
import cn.m2c.scm.application.utils.Utils;
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

    private List<GoodsClassifyBean> recursionQueryGoodsUpClassify(String classifyId, List<GoodsClassifyBean> classifies) {
        GoodsClassifyBean bean = queryGoodsClassifiesById(classifyId);
        if (null != bean) {
            classifies.add(bean);
            if (!"-1".equals(bean.getParentClassifyId())) {
                return recursionQueryGoodsUpClassify(bean.getParentClassifyId(), classifies);
            }
        }
        return classifies;
    }

    public Map getClassifyMap(String classifyId) {
        List<GoodsClassifyBean> goodsClassifies = recursionQueryGoodsUpClassify(classifyId, new ArrayList<GoodsClassifyBean>());
        if (null != goodsClassifies && goodsClassifies.size() > 0) {
            List<String> ids = new ArrayList<>();
            List<String> names = new ArrayList<>();
            for (GoodsClassifyBean bean : goodsClassifies) {
                ids.add(bean.getClassifyId());
                names.add(bean.getClassifyName());
            }
            String goodsClassify = listJoinString(names);
            Collections.reverse(ids);
            Map map = new HashMap<>();
            map.put("name", goodsClassify);
            map.put("ids", ids);
            return map;
        }
        return null;
    }

    private String listJoinString(List<String> obj) {
        Collections.reverse(obj);
        String[] type = new String[obj.size()];
        String[] newGoodsClassify = (String[]) obj.toArray(type);
        String goodsClassify = String.join(",", newGoodsClassify);
        return goodsClassify;
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

    public Map queryServiceRateByClassifyIds(List<String> classifyIds) {
        Map map = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_classify WHERE 1 = 1");
        sql.append(" AND classify_id in (" + Utils.listParseString(classifyIds) + ")");
        List<GoodsClassifyBean> goodsClassifyBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsClassifyBean.class);
        if (null != goodsClassifyBeans && goodsClassifyBeans.size() > 0) {
            for (GoodsClassifyBean goodsClassifyBean : goodsClassifyBeans) {
                Float rate = null;
                if (null != goodsClassifyBean) {
                    rate = goodsClassifyBean.getServiceRate();
                    if (null == rate) {
                        if (!"-1".equals(goodsClassifyBean.getParentClassifyId())) {//不是一级分类
                            GoodsClassifyBean bean = queryGoodsClassifiesById(goodsClassifyBean.getParentClassifyId());
                            //找上级分类
                            rate = bean.getServiceRate();
                        }
                    }
                }
                map.put(goodsClassifyBean.getClassifyId(), rate);
            }
        }
        return map;
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
                if (null != bean) {
                    list.add(getFirstClassifyByClassifyId(bean.getParentClassifyId()));
                }
            }
        }
        return list;
    }

    public Map getFirstClassifyByIds(List<String> classifyIds) {
        Map map = new HashMap<>();
        for (String classifyId : classifyIds) {
            GoodsClassifyBean bean = queryGoodsClassifiesById(classifyId);
            if (null != bean && "-1".equals(bean.getParentClassifyId())) {
                map.put(classifyId, bean.getClassifyId());
            } else {
                if (null != bean) {
                    bean = getFirstClassifyByClassifyId(bean.getParentClassifyId());
                    map.put(classifyId, bean.getClassifyId());
                }
            }
        }
        return map;
    }
}
