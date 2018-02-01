package cn.m2c.scm.port.adapter.restful.app.comment;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.comment.GoodsCommentApplication;
import cn.m2c.scm.application.comment.command.AddGoodsCommentCommand;
import cn.m2c.scm.application.comment.query.GoodsCommentQueryApplication;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.comment.query.data.representation.app.AppCommentRepresentation;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.representation.GoodsSkuInfoRepresentation;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品评论
 */
@RestController
@RequestMapping("/goods/comment/app")
public class AppGoodsCommentAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppGoodsCommentAgent.class);


    @Autowired
    GoodsQueryApplication goodsQueryApplication;
    @Autowired
    GoodsCommentApplication goodsCommentApplication;
    @Autowired
    GoodsCommentQueryApplication goodsCommentQueryApplication;

    /**
     * 发布评价
     *
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<MResult> addGoodsComment(
            @RequestParam(value = "orderId", required = false) String orderId,
            @RequestParam(value = "skuId", required = false) String skuId,
            @RequestParam(value = "goodsNum", required = false) Integer goodsNum,
            @RequestParam(value = "buyerId", required = false) String buyerId,
            @RequestParam(value = "buyerName", required = false) String buyerName,
            @RequestParam(value = "buyerPhoneNumber", required = false) String buyerPhoneNumber,
            @RequestParam(value = "buyerIcon", required = false) String buyerIcon,
            @RequestParam(value = "starLevel", required = false) Integer starLevel,
            @RequestParam(value = "commentContent", required = false) String commentContent,
            @RequestParam(value = "commentImages", required = false) String commentImages
            ,@RequestParam(value = "sortNo", required = false, defaultValue="0") int sortNo
            ,@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId
    ) {
        MResult result = new MResult(MCode.V_1);

        // 查询商品信息
        GoodsSkuInfoRepresentation info = goodsQueryApplication.queryGoodsBySkuId(skuId);
        if (null == info) {
            result = new MResult(MCode.V_300, "商品信息不存在");
            return new ResponseEntity<MResult>(result, HttpStatus.OK);
        }
        String id = IDGenerator.get(IDGenerator.SCM_GOODS_COMMENT_PREFIX_TITLE);
        AddGoodsCommentCommand command = new AddGoodsCommentCommand(id, orderId, skuId, info.getSkuName(), goodsNum, buyerId, buyerName,
                buyerPhoneNumber, buyerIcon, commentContent, commentImages,
                info.getGoodsId(), info.getGoodsName(), info.getDealerId(), info.getDealerName(), starLevel, sortNo, dealerOrderId);
        try {
            goodsCommentApplication.addGoodsComment(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addGoodsComment NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addGoodsComment Exception e:", e);
            result = new MResult(MCode.V_400, "添加评论失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseEntity<MPager> queryAppGoodsComment(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "type", required = false) Integer type,//0:全部，1：有图
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        Map resultMap = new HashMap<>();
        try {
            Integer total = goodsCommentQueryApplication.queryAppGoodCommentTotal(goodsId, type);
            if (total > 0) {
                List<GoodsCommentBean> beans = goodsCommentQueryApplication.queryAppGoodComment(goodsId, type, pageNum, rows);
                if (null != beans && beans.size() > 0) {
                    List<AppCommentRepresentation> representations = new ArrayList<>();
                    for (GoodsCommentBean bean : beans) {
                        representations.add(new AppCommentRepresentation(bean));
                    }
                    resultMap.put("goodsComments", representations);
                }
            }

            // 好评数
            Integer highCommentTotal = goodsCommentQueryApplication.queryGoodsHighCommentTotal(goodsId);
            // 总评数
            Integer commentTotal = goodsCommentQueryApplication.queryGoodsCommentTotal(goodsId);
            // 有图评论数
            Integer imageCommentTotal = goodsCommentQueryApplication.queryGoodsImageCommentTotal(goodsId);
            // 好评度
            Float highCommentRate = 0 == commentTotal ? 0 : highCommentTotal / Float.parseFloat(commentTotal.toString());
            Integer highCommentRateInt = new Double(Double.parseDouble(new DecimalFormat("#.00").format(highCommentRate)) * 100).intValue();
            resultMap.put("commentTotal", commentTotal);
            resultMap.put("imageCommentTotal", imageCommentTotal);
            resultMap.put("highCommentRate", highCommentRateInt);
            result.setContent(resultMap);
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryAppGoodsComment Exception e:", e);
            result = new MPager(MCode.V_400, "查询评论失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
