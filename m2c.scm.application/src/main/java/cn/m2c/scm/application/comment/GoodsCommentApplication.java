package cn.m2c.scm.application.comment;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.ddd.common.logger.OperationLogManager;
import cn.m2c.scm.application.comment.command.AddGoodsCommentCommand;
import cn.m2c.scm.application.comment.command.ReplyGoodsCommentCommand;
import cn.m2c.scm.application.order.DealerOrderApplication;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.comment.GoodsComment;
import cn.m2c.scm.domain.model.comment.GoodsCommentRepository;
import cn.m2c.scm.domain.model.goods.Goods;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import cn.m2c.scm.domain.service.order.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品评价
 */
@Service
@Transactional
public class GoodsCommentApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCommentApplication.class);

    @Autowired
    GoodsCommentRepository goodsCommentRepository;
    @Autowired
    private DealerOrderApplication orderApp;

    @Resource
    private OperationLogManager operationLogManager;
    @Autowired
    OrderService orderService;
    @Autowired
    GoodsRepository goodsRepository;

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
                    command.getSkuId(), command.getSkuName(), command.getGoodsNum(), command.getGoodsName(),
                    command.getDealerId(), command.getDealerName(), command.getBuyerId(), command.getBuyerName(),
                    command.getBuyerPhoneNumber(), command.getBuyerIcon(), command.getCommentContent(), command.getCommentImages(),
                    command.getStarLevel());
            goodsCommentRepository.save(goodsComment);

            // 更新订单状态
            orderApp.commentSku(command.getOrderId(), command.getSkuId(), 1);
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void replyGoodsComment(ReplyGoodsCommentCommand command, String _attach) throws NegativeException {
        LOGGER.info("replyGoodsComment command >>{}", command);
        // 查询评论信息
        GoodsComment goodsComment = goodsCommentRepository.queryGoodsCommentById(command.getCommentId());
        if (null == goodsComment) {
            throw new NegativeException(MCode.V_300, "评论信息不存在");
        }
        if (StringUtils.isNotEmpty(_attach))
            operationLogManager.operationLog("商品评价回评", _attach, goodsComment, new String[]{"goodsComment"}, null);
        goodsComment.replyComment(command.getReplyContent());

        // 商品回评推送消息
        Map extraMap = new HashMap<>();
        Goods goods = goodsRepository.queryGoodsByGoodsId(goodsComment.goodsId());
        extraMap.put("goodsImage", null != goods.goodsMainImages() ? goods.goodsMainImages().get(0) : null);
        extraMap.put("goodsId", goodsComment.goodsId());
        extraMap.put("goodsName", goodsComment.goodsName());
        extraMap.put("commentContent", goodsComment.commentContent());
        extraMap.put("replyContent", command.getReplyContent());
        extraMap.put("optType", 4);
        orderService.msgPush(2, goodsComment.buyerId(), JsonUtils.toStr(extraMap), goodsComment.dealerId());
    }


    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void delGoodsComment(String commentId, String _attach) throws NegativeException {
        LOGGER.info("delGoodsComment commentId >>{}", commentId);
        // 查询评论信息
        GoodsComment goodsComment = goodsCommentRepository.queryGoodsCommentById(commentId);
        if (null == goodsComment) {
            throw new NegativeException(MCode.V_300, "评论信息不存在");
        }
        if (StringUtils.isNotEmpty(_attach))
            operationLogManager.operationLog("删除评论", _attach, goodsComment, new String[]{"goodsComment"}, null);
        goodsComment.remove();

        // 更新订单状态
        orderApp.commentSku(goodsComment.orderId(), goodsComment.skuId(), 0);
    }

    /**
     * 差评24h延时展示,超过24h更新状态
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void over24HBadCommentUpdateStatus() throws NegativeException {
        List<GoodsComment> goodsComments = goodsCommentRepository.queryOver24HBadComment();
        if (null != goodsComments && goodsComments.size() > 0) {
            for (GoodsComment goodsComment : goodsComments) {
                goodsComment.over24HBadCommentStatus();
            }
        }
    }
}
