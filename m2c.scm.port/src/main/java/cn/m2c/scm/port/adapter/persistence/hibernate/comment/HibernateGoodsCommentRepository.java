package cn.m2c.scm.port.adapter.persistence.hibernate.comment;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.comment.GoodsComment;
import cn.m2c.scm.domain.model.comment.GoodsCommentRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品评论
 */
@Repository
public class HibernateGoodsCommentRepository extends HibernateSupperRepository implements GoodsCommentRepository {
    @Override
    public GoodsComment queryGoodsCommentById(String commentId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_comment where comment_id =:comment_id and comment_status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsComment.class);
        query.setParameter("comment_id", commentId);
        return (GoodsComment) query.uniqueResult();
    }

    @Override
    public void save(GoodsComment goodsComment) {
        this.session().save(goodsComment);
    }

    @Override
    public List<GoodsComment> queryOver24HBadComment() {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_comment t where t.delayed_flag=2");
        sql.append(" and UNIX_TIMESTAMP(t.created_date) < UNIX_TIMESTAMP(NOW()) - 3600 * 24");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsComment.class);
        return query.list();
    }
}
