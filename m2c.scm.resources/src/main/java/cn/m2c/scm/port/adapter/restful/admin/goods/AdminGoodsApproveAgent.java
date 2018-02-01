package cn.m2c.scm.port.adapter.restful.admin.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.goods.GoodsApproveApplication;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectBatchCommand;
import cn.m2c.scm.application.goods.query.GoodsHistoryQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsHistoryBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsHistoryRepresentation;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品审核
 *
 * @author ps
 */
@RestController
@RequestMapping("admin/goods/approve")
public class AdminGoodsApproveAgent {
    private final static Logger LOGGER = LoggerFactory.getLogger(AdminGoodsApproveAgent.class);

    @Autowired
    GoodsApproveApplication goodsApproveApplication;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    GoodsHistoryQueryApplication goodsHistoryQueryApplication;

    /**
     * 商品批量审核同意
     *
     * @param goodsIds
     * @return
     */
    @RequirePermissions(value = {"scm:goodsCheck:agreebatch"})
    @RequestMapping(value = "/agreebatch", method = RequestMethod.POST)
    public ResponseEntity<MResult> agreeGoodsApproveBatch(
            @RequestParam(value = "goodsIds", required = false) List goodsIds) {
        MResult result = new MResult(MCode.V_1);
        try {
            String _attach = request.getHeader("attach");
            goodsApproveApplication.agreeGoodsApproveBatch(goodsIds, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("agreeGoodsApproveBatch NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("agreeGoodsApproveBatch Exception e:", e);
            result = new MResult(MCode.V_400, "批量同意商品审核失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 批量拒绝商品审核
     */
    @RequirePermissions(value = {"scm:goodsCheck:rejectbatch"})
    @RequestMapping(value = "/rejectbatch", method = RequestMethod.POST)
    public ResponseEntity<MResult> rejectGoodsApproveBatch(
            @RequestParam(value = "goodsIds", required = false) List goodsIds,
            @RequestParam(value = "rejectReason", required = false) String rejectReason
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsApproveRejectBatchCommand command = new GoodsApproveRejectBatchCommand(goodsIds, rejectReason);
            String _attach = request.getHeader("attach");
            goodsApproveApplication.rejectGoodsApproveBatch(command, _attach);
            result.setStatus(MCode.V_200);
        } catch (NegativeException ne) {
            LOGGER.error("rejectGoodsApprove NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
        } catch (Exception e) {
            LOGGER.error("rejectGoodsApprove Exception e:", e);
            result = new MResult(MCode.V_400, "批量拒绝商品审核失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    @RequestMapping(value = {"/history"}, method = RequestMethod.GET)
    public ResponseEntity<MPager> queryGoodsHistory(
            @RequestParam(value = "goodsId", required = false) String goodsId,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = goodsHistoryQueryApplication.queryGoodsHistoryTotal(goodsId, true);
            if (total > 0) {
                List<GoodsHistoryBean> historyBeanList = goodsHistoryQueryApplication.queryGoodsHistory(goodsId, pageNum, rows, true);
                if (null != historyBeanList && historyBeanList.size() > 0) {
                    List<GoodsHistoryRepresentation> representations = new ArrayList<>();
                    for (GoodsHistoryBean history : historyBeanList) {
                        GoodsHistoryRepresentation representation = new GoodsHistoryRepresentation(history);
                        representations.add(representation);
                    }
                    result.setContent(representations);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsHistory Exception e:", e);
            result = new MPager(MCode.V_400, "查询商品审核历史变更记录失败");
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
