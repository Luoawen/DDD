package cn.m2c.scm.domain.model.comment;

import java.util.List;

/**
 * 商品评论
 */
public interface GoodsCommentRepository {
    GoodsComment queryGoodsCommentById(String commentId);

    void save(GoodsComment goodsComment);

    List<GoodsComment> queryOver24HBadComment();
}
