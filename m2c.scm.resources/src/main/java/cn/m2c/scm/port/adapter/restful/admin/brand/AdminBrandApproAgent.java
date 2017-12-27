package cn.m2c.scm.port.adapter.restful.admin.brand;

import java.util.ArrayList;
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
import cn.m2c.scm.application.brand.BrandApproveApplication;
import cn.m2c.scm.application.brand.command.BrandApproveAgreeCommand;
import cn.m2c.scm.application.brand.command.BrandApproveRejectCommand;
import cn.m2c.scm.domain.NegativeException;


@RestController
@RequestMapping("/admin/brand/approve")
public class AdminBrandApproAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminBrandApproAgent.class);

	@Autowired
	BrandApproveApplication brandApproveApplication;

	@Autowired
	private  HttpServletRequest request;
	
	/**
	 * 批量审核同意
	 * 
	 * @param approveIds
	 * @param brandIds
	 * @return
	 */
	@RequirePermissions(value ={"scm:brandApprove:batchagree"})
	@RequestMapping(value = "/batchagree", method = RequestMethod.POST)
	public ResponseEntity<MResult> brandApproveBatchAgree(
			@RequestParam(value = "approveIds", required = false) List<String> approveIds,
			@RequestParam(value = "brandIds", required = false) List<String> brandIds) {
		MResult result = new MResult(MCode.V_1);
		try {
			String _attach= request.getHeader("attach");
			brandApproveApplication.batchAgreeBrandApprove(approveIds, _attach);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("批量同意品牌审核:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("批量同意品牌审核:", e);
			result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	
	 /**
     * 批量审核拒绝
     * @param approveIds
     * @param brandIds
     * @return
     */
	@RequirePermissions(value ={"scm:brandApprove:batchreject"})
    @RequestMapping(value = "/batchreject",method = RequestMethod.POST)
    public ResponseEntity<MResult> brandApproveBatchReject(
    		@RequestParam(value = "approveIds",required = false) List<String> approveIds,
    		@RequestParam(value = "rejectReason",required = false) String rejectReason
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
			String _attach= request.getHeader("attach");
			brandApproveApplication.batchRejectBrandApprove(approveIds, _attach,rejectReason);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("批量拒绝品牌审核:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
            LOGGER.error("批量拒绝品牌审核:", e);
            result = new MResult(MCode.V_400, "服务器开小差了");
        }
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
