package cn.m2c.scm.port.adapter.restful.web.dealer;

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
import cn.m2c.scm.application.dealer.DealerApplication;
import cn.m2c.scm.application.dealer.command.DealerAddOrUpdateCommand;
import cn.m2c.scm.domain.IDGenerator;

@RestController
@RequestMapping("/dealer/sys")
public class DealerAgent {
	private final static Logger log = LoggerFactory.getLogger(DealerAgent.class);
	
	@Autowired
	DealerApplication application;
	
	@RequestMapping(value="",method = RequestMethod.POST)
	public ResponseEntity<MResult> add(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="userId",required=true)String userId,
			@RequestParam(value="dealerName",required=true)String dealerName,
			@RequestParam(value="dealerClassify",required=true)String dealerClassify,
			@RequestParam(value="cooperationMode",required=true)Integer cooperationMode,
			@RequestParam(value="startSignDate",required=true)String startSignDate,
			@RequestParam(value="endSignDate",required=true)String endSignDate,
			@RequestParam(value="dealerProvince",required=true)String dealerProvince,
			@RequestParam(value="dealerCity",required=true)String dealerCity,
			@RequestParam(value="dealerarea",required=true)String dealerarea,
			@RequestParam(value="dealerPcode",required=true)String dealerPcode,
			@RequestParam(value="dealerCcode",required=true)String dealerCcode,
			@RequestParam(value="dealerAcode",required=true)String dealerAcode,
			@RequestParam(value="dealerDetailAddress",required=false,defaultValue="")String dealerDetailAddress,
			@RequestParam(value="countMode",required=true)Integer countMode,
			@RequestParam(value="deposit",required=false,defaultValue="0")Long deposit,
			@RequestParam(value="isPayDeposit",required=true,defaultValue="0")Integer isPayDeposit,
			@RequestParam(value="managerName",required=true)String managerName,
			@RequestParam(value="managerPhone",required=true)String managerPhone,
			@RequestParam(value="managerqq",required=false)String managerqq,
			@RequestParam(value="managerWechat",required=false)String managerWechat,
			@RequestParam(value="managerEmail",required=false)String managerEmail,
			@RequestParam(value="managerDepartment",required=false)String managerDepartment,
			@RequestParam(value="sellerId",required=true)String sellerId){
			MResult result = new MResult(MCode.V_1);
			try {
				String dealerId = IDGenerator.get(IDGenerator.DEALER_PREFIX_TITLE);
				DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,userId, dealerName, dealerClassify, cooperationMode, startSignDate, endSignDate, dealerProvince, dealerCity, dealerarea, dealerPcode, dealerCcode, dealerAcode, dealerDetailAddress, countMode, deposit, isPayDeposit, managerName, managerPhone, managerqq, managerWechat, managerEmail, managerDepartment, sellerId);
				application.addDealer(command);
				result.setStatus(MCode.V_200);
			} catch (Exception e) {
				log.error("添加经销商出错" + e.getMessage(), e);
	            result = new MResult(MCode.V_400, "服务器开小差了");
			}
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
}
