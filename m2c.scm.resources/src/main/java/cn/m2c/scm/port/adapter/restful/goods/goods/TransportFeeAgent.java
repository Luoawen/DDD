package cn.m2c.scm.port.adapter.restful.goods.goods;

import java.util.List;
import java.util.Map;

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
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.common.StringUtil;
import cn.m2c.goods.domain.IDGenerator;
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.TransportFeeApplication;
import cn.m2c.scm.application.goods.goods.command.TransportFeeAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcTransportFeeQuery;

/**
 * 
 * 运费模板管理
 * @author ps
 *
 */
@RestController
@RequestMapping("/transportFee")
public class TransportFeeAgent {
	@Autowired
	TransportFeeApplication transportFeeApplication;
	@Autowired
	SpringJdbcTransportFeeQuery query;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TransportFeeAgent.class);
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<MResult> addtransportFee(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="modelName",required=true)String modelName,
			@RequestParam(value="fee",required=true) Long fee
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		String transportFeeId = IDGenerator.get(IDGenerator.GOODS_TRANSPORT_FEE_PREFIX_TITLE);
			try {
				TransportFeeAddOrUpdateCommand command = new TransportFeeAddOrUpdateCommand(transportFeeId,modelName,fee,dealerId);
				transportFeeApplication.addTransportFee(command);
				result.setStatus(MCode.V_200);
			}catch (NegativeException e) {
				LOGGER.error("transportFee add Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("transportFee add Exception e:", e);
				result = new MPager(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("transportFee add Exception e:", e);
				result = new MPager(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<MResult> updatetransportFee(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="transportFeeId",required=true)String transportFeeId,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="modelName",required=true)String modelName,
			@RequestParam(value="fee",required=true)Long fee){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			TransportFeeAddOrUpdateCommand command = new TransportFeeAddOrUpdateCommand(transportFeeId,modelName,fee,dealerId);
				transportFeeApplication.updatetransportFee(command);
				result.setStatus(MCode.V_200);
			}catch (NegativeException e) {
				LOGGER.error("transportFee update Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("transportFee update Exception e:", e);
				result = new MPager(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("transportFee update Exception e:", e);
				result = new MPager(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public ResponseEntity<MResult> deltransportFee(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="transportFeeId",required=true)String transportFeeId
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			transportFeeApplication.deltransportFee(transportFeeId);
			result.setStatus(MCode.V_200);
		}catch (NegativeException e) {
			LOGGER.error("transportFee del Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("transportFee del Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("transportFee del Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ResponseEntity<MPager> listtransportFee(@RequestParam(value="token",required=true)String token,
			@RequestParam(value = "dealerId" ,required = false) String dealerId,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MPager>(result, HttpStatus.OK);
		}
		try {
			List<Map<String, Object>> transportFeeList = query.getlist(dealerId,rows,pageNum);
			Integer totalCount = query.getTransportFeeCount();
			result.setPager(totalCount, pageNum, rows);
			result.setContent(transportFeeList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("transport list Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("transport list Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
}
