package cn.m2c.scm.application.goods.query;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.RedisUtil;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品查询
 */
@Service
public class GoodsQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsQueryApplication.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;

    /**
     * 搜索
     * 商家平台搜索框：可按照商品名称、商品编码（商家自己的内部编码）、条形码、品牌查找，都是模糊搜索
     * 商家管理平台搜索框：可按照商品名称、平台sku（平台自己生成的内部唯一编码）、商家编码（商家自己的内部编码）、条形码、商家名称、品牌查找，都是模糊搜索
     *
     * @param dealerId
     * @param goodsClassifyId
     * @param goodsStatus
     * @param condition
     * @param startTime
     * @param endTime
     * @param pageNum
     * @param rows
     * @return
     */
    public List<GoodsBean> searchGoodsByCondition(String dealerId, String goodsClassifyId, Integer goodsStatus,
                                                  String condition, String startTime, String endTime,
                                                  Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" goods_id ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        // 根据商品分类id,找到所有下级分类ID
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND goods_classify_id in (" + Utils.listParseString(goodsClassifyIds) + ") ");
        }
        if (null != goodsStatus) {
            sql.append(" AND goods_status = ? ");
            params.add(goodsStatus);
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {//商家平台
                sql.append(" AND (goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR goods_brand_name LIKE ?)");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            } else {//商家管理平台
                sql.append(" AND (sku_id LIKE ? OR goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR dealer_name LIKE ? OR goods_brand_name LIKE ? )");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND goods_created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" AND del_status= 1 group by goods_id ORDER BY goods_created_date DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);

        List<GoodsBean> goodsBeanList = new ArrayList<>();
        List<Integer> ids = supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), params.toArray(), Integer.class);
        if (null != ids && ids.size() > 0) {
            for (Integer id : ids) {
                goodsBeanList.add(queryGoodsById(id));
            }
        }
        return goodsBeanList;
    }

    public Integer searchGoodsByConditionTotal(String dealerId, String goodsClassifyId, Integer goodsStatus,
                                               String condition, String startTime, String endTime) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(distinct goods_id) ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        // 根据商品分类id,找到所有下级分类ID
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND goods_classify_id in (" + Utils.listParseString(goodsClassifyIds) + ") ");
        }
        if (null != goodsStatus) {
            sql.append(" AND goods_status = ? ");
            params.add(goodsStatus);
        }
        if (StringUtils.isNotEmpty(condition)) {
            if (StringUtils.isNotEmpty(dealerId)) {//商家平台
                sql.append(" AND (goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR goods_brand_name LIKE ?)");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            } else {//商家管理平台
                sql.append(" AND (sku_id LIKE ? OR goods_name LIKE ? OR goods_bar_code LIKE ? OR goods_code LIKE ? OR dealer_name LIKE ? OR goods_brand_name LIKE ? )");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
                params.add("%" + condition + "%");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND goods_created_date BETWEEN ? AND ?");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" AND del_status= 1");

        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public List<GoodsSkuBean> queryGoodsSKUsByGoodsId(Integer goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1 AND goods_id = ?");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSkuBean.class, goodsId);
    }

    public GoodsBean queryGoodsById(Integer id) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods WHERE 1 = 1 AND id = ?");
        GoodsBean goodsBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsBean.class, id);
        if (null != goodsBean) {
            goodsBean.setGoodsSkuBeans(queryGoodsSKUsByGoodsId(id));
        }
        return goodsBean;
    }

    public List<GoodsBean> goodsChoice(String goodsClassifyId, String condition,
                                       Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" goods_id ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1");
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND goods_classify_id in (" + Utils.listParseString(goodsClassifyIds) + ") ");
        }
        if (StringUtils.isNotEmpty(condition)) {
            sql.append(" AND goods_name like ? ");
            params.add("%" + condition + "%");
        }
        sql.append(" AND del_status= 1 group by goods_id ORDER BY goods_created_date desc,photograph_price desc ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);

        List<GoodsBean> goodsBeanList = new ArrayList<>();
        List<Integer> ids = supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), params.toArray(), Integer.class);
        if (null != ids && ids.size() > 0) {
            for (Integer id : ids) {
                goodsBeanList.add(queryGoodsById(id));
            }
        }
        return goodsBeanList;
    }

    public Integer goodsChoiceTotal(String goodsClassifyId, String condition) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(distinct goods_id) ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku WHERE 1 = 1");
        if (StringUtils.isNotEmpty(goodsClassifyId)) {
            List<String> goodsClassifyIds = goodsClassifyQueryApplication.recursionQueryGoodsSubClassifyId(goodsClassifyId, new ArrayList<String>());
            goodsClassifyIds.add(goodsClassifyId);
            sql.append(" AND goods_classify_id in (" + Utils.listParseString(goodsClassifyIds) + ") ");
        }
        if (StringUtils.isNotEmpty(condition)) {
            sql.append(" AND goods_name like ? ");
            params.add("%" + condition + "%");
        }
        sql.append(" AND del_status= 1");
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    public GoodsBean queryGoodsByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods WHERE 1 = 1 AND goods_id = ?");
        GoodsBean goodsBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsBean.class, goodsId);
        if (null != goodsBean) {
            goodsBean.setGoodsSkuBeans(queryGoodsSKUsByGoodsId(goodsBean.getId()));
        }
        return goodsBean;
    }

    public List<GoodsBean> queryGoodsByGoodsIds(List<String> goodsIds) {
        List<GoodsBean> goodsBeans = new ArrayList<>();
        for (String goodsId : goodsIds) {
            GoodsBean goodsBean = queryGoodsByGoodsId(goodsId);
            goodsBeans.add(goodsBean);
        }
        return goodsBeans;
    }

    public List<GoodsBean> queryGoodsRandom(Integer number) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods where del_status= 1 order by rand() limit 0,?");
        List<GoodsBean> goodsBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsBean.class, number);
        return goodsBeans;
    }

    public List<String> queryGoodsKeyWordRandom(Integer number) {
        List<String> keyWords = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods where del_status= 1 and goods_key_word is not null order by rand() limit 0,?");
        List<GoodsBean> goodsBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsBean.class, number);
        List<String> tempList = new ArrayList<>();
        if (null != goodsBeans && goodsBeans.size() > 0) {
            for (GoodsBean goodsBean : goodsBeans) {
                String keyWord = goodsBean.getGoodsKeyWord();
                List<String> tempKeyWord = JsonUtils.toList(keyWord, String.class);
                tempList.addAll(tempKeyWord);
            }
            if (tempList.size() > number) {
                keyWords = tempList.subList(0, number);
            } else {
                keyWords = tempList;
            }
        }
        return keyWords;
    }

    public List<GoodsBean> queryGoodsGuessCache(Integer positionType) {
        //猜你喜欢位置：1:首页(共32条，分页，每页8条，分4页)，2:购物车页面（共12条，不需分页），3:商品详情页（共12条，不需分页）
        Integer number = 32;
        if (positionType != 1) {
            number = 12;
        }

        List<GoodsBean> goodsBeans = new ArrayList<>();
        String key = "scm.goods.guess." + positionType;
        String guess = redisUtil.getString(key); //从缓存中取数据
        if (StringUtils.isNotEmpty(guess)) { // 缓存不为空
            List<GoodsBean> guessInfoList = JsonUtils.toList(guess, GoodsBean.class);
            if (guessInfoList.size() < number) { //随机取剩余部分
                List<String> goodsIds = new ArrayList<>();
                for (GoodsBean goodsBean : guessInfoList) {
                    goodsIds.add(goodsBean.getGoodsId());
                }
                guessInfoList.addAll(queryGoodsGuess((number - guessInfoList.size()), positionType, goodsIds));
                redisUtil.setString(key, 24 * 3600, JsonUtils.toStr(guessInfoList));
            }
            goodsBeans = guessInfoList;
        } else {
            goodsBeans = queryGoodsGuess(number, positionType, null);
        }
        return goodsBeans;
    }

    private List<GoodsBean> queryGoodsGuess(Integer number, Integer positionType, List<String> goodsIds) {
        String key = "scm.goods.guess." + positionType;
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods where del_status= 1 ");
        if (null != goodsIds && goodsIds.size() > 0) {
            sql.append(" and goods_id not in (" + Utils.listParseString(goodsIds) + ")");
        }
        sql.append(" order by rand() limit 0,?");
        List<GoodsBean> goodsBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsBean.class, number);
        if (null == goodsIds) {
            if (null != goodsBeans && goodsBeans.size() > 0) {
                redisUtil.setString(key, 24 * 3600, JsonUtils.toStr(goodsBeans));
            }
        }
        return goodsBeans;
    }

    /**
     * list 分页
     *
     * @param pageNum
     * @param rows
     * @param goodsBeans
     * @return
     */
    public List<GoodsBean> getPagedList(Integer pageNum, Integer rows, List<GoodsBean> goodsBeans) {
        List<GoodsBean> goodsBeanPage = new ArrayList<GoodsBean>();
        int currIdx = (pageNum > 1 ? (pageNum - 1) * rows : 0);
        for (int i = 0; i < rows && i < goodsBeans.size() - currIdx; i++) {
            GoodsBean memberArticleBean = goodsBeans.get(currIdx + i);
            goodsBeanPage.add(memberArticleBean);
        }
        return goodsBeanPage;
    }
}
