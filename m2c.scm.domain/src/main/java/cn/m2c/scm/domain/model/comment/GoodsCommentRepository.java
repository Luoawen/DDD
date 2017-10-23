package cn.m2c.scm.domain.model.comment;

/**
 * 商品评论
 */
public interface GoodsCommentRepository {
    GoodsComment queryGoodsCommentById(String commentId);

    void save(GoodsComment goodsComment);
}
