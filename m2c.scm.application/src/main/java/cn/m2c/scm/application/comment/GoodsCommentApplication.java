package cn.m2c.scm.application.comment;

import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.comment.command.AddGoodsCommentCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.comment.GoodsComment;
import cn.m2c.scm.domain.model.comment.GoodsCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品评价
 */
@Service
@Transactional
public class GoodsCommentApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCommentApplication.class);

    @Autowired
    GoodsCommentRepository goodsCommentRepository;

    /**
     * 增加评论
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void addGoodsComment(AddGoodsCommentCommand command) throws NegativeException {
        LOGGER.info("addGoodsComment command >>{}", command);
        // 查询评论信息
        GoodsComment goodsComment = goodsCommentRepository.queryGoodsCommentById(command.getCommentId());
        if (null == goodsComment) {
            goodsComment = new GoodsComment(command.getCommentId(), command.getOrderId(), command.getGoodsId(),
                    command.getSkuId(), command.getSkuName(), command.getGoodsName(),
                    command.getDealerId(), command.getDealerName(), command.getBuyerId(), command.getBuyerName(),
                    command.getBuyerPhoneNumber(), command.getBuyerIcon(), command.getCommentContent(), command.getCommentImages(),
                    command.getStarLevel());
            goodsCommentRepository.save(goodsComment);
        }
    }
}
