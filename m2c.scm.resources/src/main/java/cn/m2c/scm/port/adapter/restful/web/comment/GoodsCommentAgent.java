package cn.m2c.scm.port.adapter.restful.web.comment;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.comment.GoodsCommentApplication;
import cn.m2c.scm.application.comment.command.ReplyGoodsCommentCommand;
import cn.m2c.scm.application.comment.query.GoodsCommentQueryApplication;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.comment.query.data.representation.CommentRepresentation;
import cn.m2c.scm.domain.NegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品评论
 */
@RestController
@RequestMapping("/goods/comment")
public class GoodsCommentAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCommentAgent.class);

    @Autowired
    GoodsCommentQueryApplication goodsCommentQueryApplication;
    @Autowired
    GoodsCommentApplication goodsCommentApplication;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MPager> queryGoodsComment(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "replyStatus", required = false) Integer replyStatus,//回复状态 1未回复  2 已回复
            @RequestParam(value = "starLevel", required = false) Integer starLevel,//星级
            @RequestParam(value = "startTime", required = false) String startTime,//开始时间
            @RequestParam(value = "endTime", required = false) String endTime,//结束时间
            @RequestParam(value = "condition", required = false) String condition,//条件
            @RequestParam(value = "imageStatus", required = false) Integer imageStatus,//评论是否有图片，1:无图 2有图
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);

        try {
            Integer total = goodsCommentQueryApplication.searchGoodsCommentTotal(dealerId, replyStatus, starLevel,
                    startTime, endTime, condition, imageStatus);
            if (total > 0) {
                List<GoodsCommentBean> beans = goodsCommentQueryApplication.searchGoodsComment(dealerId, replyStatus, starLevel,
                        startTime, endTime, condition, imageStatus, pageNum, rows);
                if (null != beans && beans.size() > 0) {
                    List<CommentRepresentation> representations = new ArrayList<>();
                    for (GoodsCommentBean bean : beans) {
                        representations.add(new CommentRepresentation(bean));
                    }
                    result.setContent(representations);
                }
            }

            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsComment Exception e:", e);
            result = new MPager(MCode.V_400, "查询评论失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }

    /**
     * 回评
     *
     * @param commentId
     * @param replyContent
     * @return
     */
    @RequestMapping(value = "/reply", method = RequestMethod.PUT)
    public ResponseEntity<MResult> replyComment(
            @RequestParam(value = "commentId", required = false) String commentId,
            @RequestParam(value = "replyContent", required = false) String replyContent
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            ReplyGoodsCommentCommand command = new ReplyGoodsCommentCommand(commentId, replyContent);
            goodsCommentApplication.replyGoodsComment(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("replyComment NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("replyComment Exception e:", e);
            result = new MResult(MCode.V_400, "商品回评失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @RequestMapping(value = "/mng/{commentId}", method = RequestMethod.DELETE)
    @RequirePermissions(value ={"scm:goodsAppraise:delete"})
    public ResponseEntity<MResult> delComment(
            @PathVariable("commentId") String commentId
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            goodsCommentApplication.delGoodsComment(commentId);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("delComment NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("delComment Exception e:", e);
            result = new MResult(MCode.V_400, "删除商品评论失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }


    /**
     * 差评24h延时展示,超过24h更新状态
     *
     * @return
     */
    @RequestMapping(value = "/over/24h/bad/comment", method = RequestMethod.POST)
    public ResponseEntity<MResult> over24HBadCommentUpdateStatus() {
        MResult result = new MResult(MCode.V_1);
        try {
            goodsCommentApplication.over24HBadCommentUpdateStatus();
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("over24HBadCommentUpdateStatus NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("over24HBadCommentUpdateStatus Exception e:", e);
            result = new MResult(MCode.V_400, "更新差评延时状态失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
