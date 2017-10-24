package cn.m2c.scm.port.adapter.restful.web.comment;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品评论
 */
@RestController
@RequestMapping("/goods/comment")
public class GoodsCommentAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCommentAgent.class);

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<MPager> queryAppGoodsComment(
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
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
