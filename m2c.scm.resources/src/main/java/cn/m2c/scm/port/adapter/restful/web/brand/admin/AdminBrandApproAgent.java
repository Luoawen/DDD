package cn.m2c.scm.port.adapter.restful.web.brand.admin;

import java.util.ArrayList;
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
import cn.m2c.scm.application.brand.BrandApproveApplication;
import cn.m2c.scm.application.brand.command.BrandApproveAgreeCommand;
import cn.m2c.scm.application.brand.command.BrandApproveRejectCommand;
import cn.m2c.scm.domain.NegativeException;


@RestController
@RequestMapping("/brand/approve/admin")
public class AdminBrandApproAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminBrandApproAgent.class);

	@Autowired
	BrandApproveApplication brandApproveApplication;

	/**
	 * 批量审核同意
	 * 
	 * @param approveIds
	 * @param brandIds
	 * @return
	 */
	@RequestMapping(value = "/web/batchagree", method = RequestMethod.POST)
	public ResponseEntity<MResult> brandApproveBatchAgree(
			@RequestParam(value = "approveIds", required = false) List<String> approveIds,
			@RequestParam(value = "brandIds", required = false) List<String> brandIds) {
		MResult result = new MResult(MCode.V_1);
		try {
			List<BrandApproveAgreeCommand> commands = new ArrayList<BrandApproveAgreeCommand>();
			for (int i = 0; i < approveIds.size(); ++i) {
				BrandApproveAgreeCommand command = new BrandApproveAgreeCommand(brandIds.get(i), approveIds.get(i));
				commands.add(command);
			}
			brandApproveApplication.batchAgreeBrandApprove(commands);
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
    @RequestMapping(value = "/web/batchreject",method = RequestMethod.POST)
    public ResponseEntity<MResult> brandApproveBatchReject(
    		@RequestParam(value = "approveIds",required = false) List<String> approveIds,
    		@RequestParam(value = "rejectReason",required = false) String rejectReason
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		List<BrandApproveRejectCommand> commands = new ArrayList<BrandApproveRejectCommand>();
			for(int i = 0;i < approveIds.size(); ++i) {
				BrandApproveRejectCommand command = new BrandApproveRejectCommand(approveIds.get(i), rejectReason);
				commands.add(command);
			}
			brandApproveApplication.batchRejectBrandApprove(commands);
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
