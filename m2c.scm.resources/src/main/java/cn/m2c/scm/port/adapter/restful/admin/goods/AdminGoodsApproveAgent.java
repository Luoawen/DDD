package cn.m2c.scm.port.adapter.restful.admin.goods;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.GoodsApproveApplication;
import cn.m2c.scm.application.goods.command.GoodsApproveRejectBatchCommand;
import cn.m2c.scm.domain.NegativeException;

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
	
	/**
     * 商品批量审核同意,未鉴权
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/agreebatch", method = RequestMethod.POST)
    public ResponseEntity<MResult> agreeGoodsApproveBatch(
            @RequestParam(value = "goodsIds", required = false) List goodsIds
    ) {
    	MResult result = new MResult(MCode.V_1);
        try {
            goodsApproveApplication.agreeGoodsApproveBatch(goodsIds);
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
     * 批量拒绝商品审核,未鉴权
     */
    @RequestMapping(value = "/rejectbatch", method = RequestMethod.POST)
    public ResponseEntity<MResult> rejectGoodsApproveBatch(
            @RequestParam(value = "goodsIds", required = false) List goodsIds,
            @RequestParam(value = "rejectReason", required = false) String rejectReason
    ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	GoodsApproveRejectBatchCommand command = new GoodsApproveRejectBatchCommand(goodsIds, rejectReason);
        	goodsApproveApplication.rejectGoodsApproveBatch(command);
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
}
