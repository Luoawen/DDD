package cn.m2c.scm.port.adapter.restful.admin.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.command.GoodsRecognizedAddCommand;
import cn.m2c.scm.application.goods.command.GoodsRecognizedDelCommand;
import cn.m2c.scm.application.goods.command.GoodsRecognizedModifyCommand;
import cn.m2c.scm.application.goods.query.GoodsHistoryQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsHistoryBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsHistoryRepresentation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品
 */
@RestController
@RequestMapping("/")
public class AdminGoodsAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminGoodsAgent.class);

    @Autowired
    GoodsApplication goodsApplication;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    GoodsHistoryQueryApplication goodsHistoryQueryApplication;

    /**
     * 商品批量上架
     */
    @RequirePermissions(value = {"scm:goodsStorage:upShelfBatch"})
    @RequestMapping(value = {"web/goods/up/shelfbatch", "admin/goods/up/shelfbatch"}, method = RequestMethod.PUT)
    public ResponseEntity<MResult> upShelfGoodsBatch(
            @RequestParam("goodsIds") List goodsIds
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApplication.upShelfGoodsBatch(goodsIds, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("upShelfGoodsBatch NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("upShelfGoodsBatch Exception e:", e);
            result = new MResult(MCode.V_400, "商品批量上架失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 商品批量下架
     *
     * @param goodsIds
     * @return
     */
    @RequirePermissions(value = {"scm:goodsStorage:offShelfBatch"})
    @RequestMapping(value = {"web/goods/off/shelfbatch", "admin/goods/off/shelfbatch"}, method = RequestMethod.PUT)
    public ResponseEntity<MResult> offShelfGoodsBatch(
            @RequestParam("goodsIds") List goodsIds
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApplication.offShelfGoodsBatch(goodsIds, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("offShelfGoodsBatch NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("offShelfGoodsBatch Exception e:", e);
            result = new MResult(MCode.V_400, "商品批量下架失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 修改商品识别图
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/admin/goods/recognized/{goodsId}", method = RequestMethod.PUT)
    //@RequirePermissions(value = {"scm:goodsStorage:modifyRecognized"})
    public ResponseEntity<MResult> modifyRecognized(
            @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "recognizedNo", required = false) String recognizedNo,
            @RequestParam(value = "recognizedId", required = false) String recognizedId,
            @RequestParam(value = "recognizedUrl", required = false) String recognizedUrl
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsRecognizedModifyCommand command = new GoodsRecognizedModifyCommand(goodsId, recognizedNo, recognizedId, recognizedUrl);
            String _attach = request.getHeader("attach");
            goodsApplication.modifyRecognized(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("modifyRecognized NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("modifyRecognized Exception e:", e);
            result = new MResult(MCode.V_400, "修改商品识别图失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 增加商品识别图
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/admin/goods/recognized/{goodsId}", method = RequestMethod.POST)
    //@RequirePermissions(value = {"scm:goodsStorage:addRecognized"})
    public ResponseEntity<MResult> addRecognized(
            @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "recognizedId", required = false) String recognizedId,
            @RequestParam(value = "recognizedUrl", required = false) String recognizedUrl
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsRecognizedAddCommand command = new GoodsRecognizedAddCommand(goodsId, recognizedId, recognizedUrl);
            goodsApplication.addRecognized(command);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("addRecognized NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("addRecognized Exception e:", e);
            result = new MResult(MCode.V_400, "增加商品识别图失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 删除商品识别图
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/admin/goods/recognized/{goodsId}", method = RequestMethod.DELETE)
    //@RequirePermissions(value = {"scm:goodsStorage:delRecognized"})
    public ResponseEntity<MResult> delRecognized(
            @PathVariable("goodsId") String goodsId,
            @RequestParam(value = "recognizedNo", required = false) String recognizedNo,
            @RequestParam(value = "recognizedId", required = false) String recognizedId,
            @RequestParam(value = "recognizedUrl", required = false) String recognizedUrl
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsRecognizedDelCommand command = new GoodsRecognizedDelCommand(goodsId, recognizedNo, recognizedId, recognizedUrl);
            String _attach = request.getHeader("attach");
            goodsApplication.delRecognized(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("delRecognized NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("delRecognized Exception e:", e);
            result = new MResult(MCode.V_400, "删除商品识别图失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }


    @RequestMapping(value = {"/admin/goods/history"}, method = RequestMethod.GET)
    public ResponseEntity<MPager> queryGoodsHistory(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        Integer total = goodsHistoryQueryApplication.queryGoodsHistoryTotal(goodsId);
        if (total > 0) {
            List<GoodsHistoryBean> historyBeanList = goodsHistoryQueryApplication.queryGoodsHistory(goodsId, pageNum, rows);
            if (null != historyBeanList && historyBeanList.size() > 0) {
                for (GoodsHistoryBean history : historyBeanList) {
                    GoodsHistoryRepresentation representation = new GoodsHistoryRepresentation(history);
                    
                }
            }
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
