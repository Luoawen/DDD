package cn.m2c.scm.application.comment.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.comment.query.data.bean.GoodsReplyCommentBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品评论查询
 */
@Service
public class GoodsCommentQueryApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCommentQueryApplication.class);
    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    /**
     * 1.拉取一条评论超过40个字的五星好评
     * 2.拉取最新的一条好评
     * 3.拉取一条非一星评论
     */
    public GoodsCommentBean queryGoodsDetailComment(String goodsId) {
        //1.拉取一条评论超过40个字的五星好评
        String oneSql = "select * from t_scm_goods_comment where goods_id = ? and comment_status = 1 and star_level = 5 and LENGTH(comment_content) > 40  order by created_date desc";
        List<GoodsCommentBean> goodsComments = this.getSupportJdbcTemplate().queryForBeanList(oneSql, GoodsCommentBean.class, goodsId);
        if (null != goodsComments && goodsComments.size() > 0) {
            return goodsComments.get(0);
        } else {
            //2.拉取最新的一条好评
            String twoSql = "select * from t_scm_goods_comment where goods_id = ? and comment_status = 1 and comment_level = 1 order by created_date desc";
            goodsComments = this.getSupportJdbcTemplate().queryForBeanList(twoSql, GoodsCommentBean.class, goodsId);
            if (null != goodsComments && goodsComments.size() > 0) {
                return goodsComments.get(0);
            } else {
                //3.拉取一条非一星评论
                String threeSql = "select * from t_scm_goods_comment where goods_id = ? and comment_status = 1 and star_level <> 1 order by created_date desc";
                goodsComments = this.getSupportJdbcTemplate().queryForBeanList(threeSql, GoodsCommentBean.class, goodsId);
                if (null != goodsComments && goodsComments.size() > 0) {
                    return goodsComments.get(0);
                } else {
                    String lastSql = "select * from t_scm_goods_comment where goods_id = ? and comment_status = 1 order by created_date desc";
                    goodsComments = this.getSupportJdbcTemplate().queryForBeanList(lastSql, GoodsCommentBean.class, goodsId);
                    if (null != goodsComments && goodsComments.size() > 0) {
                        return goodsComments.get(0);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 查询商品评论总数
     *
     * @param goodsId
     * @return
     */
    public Integer queryGoodsCommentTotal(String goodsId) {
        String sql = "select count(*) from t_scm_goods_comment where goods_id = ? and comment_status = 1";
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), new Object[]{goodsId}, Integer.class);
    }

    /**
     * 查询商品有图评论总数
     *
     * @param goodsId
     * @return
     */
    public Integer queryGoodsImageCommentTotal(String goodsId) {
        String sql = "select count(*) from t_scm_goods_comment where goods_id = ? and comment_status = 1 and image_status=2";
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), new Object[]{goodsId}, Integer.class);
    }

    /**
     * 查询商品好评总数
     *
     * @param goodsId
     * @return
     */
    public Integer queryGoodsHighCommentTotal(String goodsId) {
        String sql = "select count(*) from t_scm_goods_comment where goods_id = ? and comment_status = 1 and comment_level=1";
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), new Object[]{goodsId}, Integer.class);
    }

    /**
     * app查询评论
     *
     * @param goodsId
     * @return
     */
    public List<GoodsCommentBean> queryAppGoodComment(String goodsId, Integer type, Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_comment WHERE 1 = 1 AND goods_id = ? AND comment_status = 1  AND delayed_flag =1");
        params.add(goodsId);
        if (type == 1) {
            sql.append(" AND image_status=2");
        }
        sql.append(" order by created_date desc LIMIT ?,? ");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        List<GoodsCommentBean> goodsCommentBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsCommentBean.class, params.toArray());
        if (null != goodsCommentBeans && goodsCommentBeans.size() > 0) {
            for (GoodsCommentBean bean : goodsCommentBeans) {
                GoodsReplyCommentBean replyBean = queryGoodsReplyCommentById(bean.getId());
                if (null != replyBean) {
                    bean.setGoodsReplyCommentBean(replyBean);
                }
            }
        }
        return goodsCommentBeans;
    }

    /**
     * app查询评论总数
     *
     * @param goodsId
     * @return
     */
    public Integer queryAppGoodCommentTotal(String goodsId, Integer type) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(*) ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_comment WHERE 1 = 1 AND goods_id = ? AND comment_status = 1 AND delayed_flag =1");
        params.add(goodsId);
        if (type == 1) {
            sql.append(" AND image_status=2");
        }
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), new Object[]{goodsId}, Integer.class);
    }

    public GoodsReplyCommentBean queryGoodsReplyCommentById(Integer commentId) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_comment_reply WHERE 1 = 1 AND comment_id = ?");
        return this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsReplyCommentBean.class, commentId);
    }

    public List<GoodsCommentBean> searchGoodsComment(String dealerId, Integer replyStatus, Integer starLevel,
                                                     String startTime, String endTime, String condition, Integer imageStatus,
                                                     Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_comment WHERE 1 = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        if (null != replyStatus) {
            sql.append(" AND reply_status = ? ");
            params.add(replyStatus);
        }
        if (null != starLevel) {
            sql.append(" AND star_level = ? ");
            params.add(starLevel);
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ? ");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(condition)) {
            sql.append(" AND (goods_name LIKE ? OR order_id LIKE ? OR buyer_name LIKE ? OR buyer_phone_number LIKE ?)");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
        }
        if (null != imageStatus) {
            sql.append(" AND image_status = ? ");
            params.add(imageStatus);
        }
        sql.append(" order by created_date desc LIMIT ?,? ");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        List<GoodsCommentBean> goodsCommentBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsCommentBean.class, params.toArray());
        if (null != goodsCommentBeans && goodsCommentBeans.size() > 0) {
            for (GoodsCommentBean bean : goodsCommentBeans) {
                GoodsReplyCommentBean replyBean = queryGoodsReplyCommentById(bean.getId());
                if (null != replyBean) {
                    bean.setGoodsReplyCommentBean(replyBean);
                }
            }
        }
        return goodsCommentBeans;
    }

    public Integer searchGoodsCommentTotal(String dealerId, Integer replyStatus, Integer starLevel,
                                           String startTime, String endTime, String condition, Integer imageStatus) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" count(*) ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_comment WHERE 1 = 1");
        if (StringUtils.isNotEmpty(dealerId)) {
            sql.append(" AND dealer_id = ? ");
            params.add(dealerId);
        }
        if (null != replyStatus) {
            sql.append(" AND reply_status = ? ");
            params.add(replyStatus);
        }
        if (null != starLevel) {
            sql.append(" AND star_level = ? ");
            params.add(starLevel);
        }
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND created_date BETWEEN ? AND ? ");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(condition)) {
            sql.append(" AND (goods_name LIKE ? OR order_id LIKE ? OR buyer_name LIKE ? OR buyer_phone_number LIKE ?)");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
        }
        if (null != imageStatus) {
            sql.append(" AND image_status = ? ");
            params.add(imageStatus);
        }
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }
}
