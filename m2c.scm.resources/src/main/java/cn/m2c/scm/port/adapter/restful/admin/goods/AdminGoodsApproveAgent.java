package cn.m2c.scm.port.adapter.restful.admin.goods;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import cn.m2c.ddd.common.auth.RequirePermissions;
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
	
	@Autowired
	private  HttpServletRequest request;
	
	/**
     * 商品批量审核同意,未鉴权
     * @param goodsId
     * @return
     */
	@RequirePermissions(value ={"scm:goodsCheck:agreebatch"})
    @RequestMapping(value = "/agreebatch", method = RequestMethod.POST)
    public ResponseEntity<MResult> agreeGoodsApproveBatch(
            @RequestParam(value = "goodsIds", required = false) List goodsIds
    ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	String _attach= request.getHeader("attach");
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
     * 批量拒绝商品审核,未鉴权
     */
	@RequirePermissions(value ={"scm:goodsCheck:rejectbatch"})
    @RequestMapping(value = "/rejectbatch", method = RequestMethod.POST)
    public ResponseEntity<MResult> rejectGoodsApproveBatch(
            @RequestParam(value = "goodsIds", required = false) List goodsIds,
            @RequestParam(value = "rejectReason", required = false) String rejectReason
    ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	GoodsApproveRejectBatchCommand command = new GoodsApproveRejectBatchCommand(goodsIds, rejectReason);
        	String _attach= request.getHeader("attach");
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
}
