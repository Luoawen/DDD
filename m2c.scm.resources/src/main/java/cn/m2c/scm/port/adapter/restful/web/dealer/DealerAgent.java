package cn.m2c.scm.port.adapter.restful.web.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.dealer.DealerApplication;
import cn.m2c.scm.application.dealer.command.DealerAddOrUpdateCommand;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.data.representation.DealerDetailRepresentation;
import cn.m2c.scm.application.dealer.data.representation.DealerNameListRepresentation;
import cn.m2c.scm.application.dealer.data.representation.DealerRepresentation;
import cn.m2c.scm.application.dealer.data.representation.DealerShopRepresentation;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.dealerclassify.query.DealerClassifyQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;

@RestController
@RequestMapping("/dealer/sys")
public class DealerAgent {
	private final static Logger log = LoggerFactory.getLogger(DealerAgent.class);
	
	@Autowired
	DealerApplication application;
	
	@Autowired
	DealerQuery dealerQuery;
	
	@Autowired
	DealerClassifyQuery dealerClassifyQuery;
	/**
	 * 新增经销商
	 * @param userId
	 * @param dealerName
	 * @param dealerClassify
	 * @param cooperationMode
	 * @param startSignDate
	 * @param endSignDate
	 * @param dealerProvince
	 * @param dealerCity
	 * @param dealerarea
	 * @param dealerPcode
	 * @param dealerCcode
	 * @param dealerAcode
	 * @param dealerDetailAddress
	 * @param countMode
	 * @param deposit
	 * @param isPayDeposit
	 * @param managerName
	 * @param managerPhone
	 * @param managerqq
	 * @param managerWechat
	 * @param managerEmail
	 * @param managerDepartment
	 * @param sellerId
	 * @return
	 */
	@RequestMapping(value="",method = RequestMethod.POST)
	public ResponseEntity<MResult> add(
			@RequestParam(value="userId",required=true)String userId,
			@RequestParam(value="userName",required=true)String userName,
			@RequestParam(value="userPhone",required=true)String userPhone,
			@RequestParam(value="dealerName",required=true)String dealerName,
			@RequestParam(value="dealerClassify",required=true)String dealerClassify,
			@RequestParam(value="cooperationMode",required=true)Integer cooperationMode,
			@RequestParam(value="startSignDate",required=true)String startSignDate,
			@RequestParam(value="endSignDate",required=true)String endSignDate,
			@RequestParam(value="dealerProvince",defaultValue="")String dealerProvince,
			@RequestParam(value="dealerCity",defaultValue="")String dealerCity,
			@RequestParam(value="dealerArea",defaultValue="")String dealerArea,
			@RequestParam(value="dealerPcode",defaultValue="")String dealerPcode,
			@RequestParam(value="dealerCcode",defaultValue="")String dealerCcode,
			@RequestParam(value="dealerAcode",defaultValue="")String dealerAcode,
			@RequestParam(value="dealerDetailAddress",required=false,defaultValue="")String dealerDetailAddress,
			@RequestParam(value="countMode",required=false)Integer countMode,
			@RequestParam(value="deposit",required=false,defaultValue="0")Long deposit,
			@RequestParam(value="isPayDeposit",required=true,defaultValue="0")Integer isPayDeposit,
			@RequestParam(value="managerName",required=false)String managerName,
			@RequestParam(value="managerPhone",required=false)String managerPhone,
			@RequestParam(value="managerqq",required=false)String managerqq,
			@RequestParam(value="managerWechat",required=false)String managerWechat,
			@RequestParam(value="managerEmail",required=false)String managerEmail,
			@RequestParam(value="managerDepartment",required=false)String managerDepartment,
			@RequestParam(value="sellerId",required=true)String sellerId,
			@RequestParam(value="sellerName",required=true)String sellerName,
			@RequestParam(value="sellerPhone",required=true)String sellerPhone){
			MResult result = new MResult(MCode.V_1);
			try {
				String dealerId = IDGenerator.get(IDGenerator.DEALER_PREFIX_TITLE);
				DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,userId,userName,userPhone, dealerName, dealerClassify, cooperationMode, startSignDate, endSignDate, dealerProvince, dealerCity, dealerArea, dealerPcode, dealerCcode, dealerAcode, dealerDetailAddress, countMode, deposit, isPayDeposit, managerName, managerPhone, managerqq, managerWechat, managerEmail, managerDepartment, sellerId,sellerName,sellerPhone);
				application.addDealer(command);
				result.setStatus(MCode.V_200);
			}catch (IllegalArgumentException e) {
				log.error("添加经销商出错", e);
				result = new MResult(MCode.V_1, e.getMessage());
			}catch (NegativeException ne) {
				log.error("添加经销商出错", ne);
				result = new MResult(ne.getStatus(), ne.getMessage());
			}catch (Exception e) {
				log.error("添加经销商出错" + e.getMessage(), e);
	            result = new MResult(MCode.V_400, "服务器开小差了");
			}
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	/**
	 * 修改经销商
	 * @param dealerId
	 * @param userId
	 * @param dealerName
	 * @param dealerClassify
	 * @param cooperationMode
	 * @param startSignDate
	 * @param endSignDate
	 * @param dealerProvince
	 * @param dealerCity
	 * @param dealerarea
	 * @param dealerPcode
	 * @param dealerCcode
	 * @param dealerAcode
	 * @param dealerDetailAddress
	 * @param countMode
	 * @param deposit
	 * @param isPayDeposit
	 * @param managerName
	 * @param managerPhone
	 * @param managerqq
	 * @param managerWechat
	 * @param managerEmail
	 * @param managerDepartment
	 * @param sellerId
	 * @return
	 */
	@RequestMapping(value="",method = RequestMethod.PUT)
	public ResponseEntity<MResult> update(
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="userId",required=true)String userId,
			@RequestParam(value="userName",required=true)String userName,
			@RequestParam(value="userPhone",required=true)String userPhone,
			@RequestParam(value="dealerName",required=true)String dealerName,
			@RequestParam(value="dealerClassify",required=true)String dealerClassify,
			@RequestParam(value="cooperationMode",required=true)Integer cooperationMode,
			@RequestParam(value="startSignDate",required=true)String startSignDate,
			@RequestParam(value="endSignDate",required=true)String endSignDate,
			@RequestParam(value="dealerProvince",defaultValue="")String dealerProvince,
			@RequestParam(value="dealerCity",defaultValue="")String dealerCity,
			@RequestParam(value="dealerArea",defaultValue="")String dealerArea,
			@RequestParam(value="dealerPcode",defaultValue="")String dealerPcode,
			@RequestParam(value="dealerCcode",defaultValue="")String dealerCcode,
			@RequestParam(value="dealerAcode",defaultValue="")String dealerAcode,
			@RequestParam(value="dealerDetailAddress",required=false,defaultValue="")String dealerDetailAddress,
			@RequestParam(value="countMode",required=true)Integer countMode,
			@RequestParam(value="deposit",required=false,defaultValue="0")Long deposit,
			@RequestParam(value="isPayDeposit",required=true)Integer isPayDeposit,
			@RequestParam(value="managerName",required=false)String managerName,
			@RequestParam(value="managerPhone",required=false)String managerPhone,
			@RequestParam(value="managerqq",required=false)String managerqq,
			@RequestParam(value="managerWechat",required=false)String managerWechat,
			@RequestParam(value="managerEmail",required=false)String managerEmail,
			@RequestParam(value="managerDepartment",required=false)String managerDepartment,
			@RequestParam(value="sellerId",required=true)String sellerId,
			@RequestParam(value="sellerName",required=true)String sellerName,
			@RequestParam(value="sellerPhone",required=true)String sellerPhone){
			MResult result = new MResult(MCode.V_1);
			try {
				DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,userId,userName,userPhone, dealerName, dealerClassify, cooperationMode, startSignDate, endSignDate, dealerProvince, dealerCity, dealerArea, dealerPcode, dealerCcode, dealerAcode, dealerDetailAddress, countMode, deposit, isPayDeposit, managerName, managerPhone, managerqq, managerWechat, managerEmail, managerDepartment, sellerId,sellerName,sellerPhone);
				application.updateDealer(command);
				result.setStatus(MCode.V_200);
			}catch (IllegalArgumentException e) {
				log.error("修改经销商出错", e);
				result = new MResult(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				log.error("修改经销商出错" + e.getMessage(), e);
	            result = new MResult(MCode.V_400, "服务器开小差了");
			}
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
		@RequestMapping(value="/list",method = RequestMethod.GET)
		public ResponseEntity<MPager> list(@RequestParam(value="dealerClassify",required=false)String dealerClassify,
				@RequestParam(value="cooperationMode",required=false)Integer cooperationMode,
				@RequestParam(value="countMode",required=false)Integer countMode,
				@RequestParam(value="isPayDeposit",required=false)Integer isPayDeposit,
				@RequestParam(value="filter",required=false)String filter,
				@RequestParam(value="startTime",required=false)String startTime,
				@RequestParam(value="endTime",required=false)String endTime,
				@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
		        @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows
				){
			MPager result = new MPager(MCode.V_1);
			try {
				List<DealerBean> dealerList = dealerQuery.getDealerList(dealerClassify,cooperationMode,countMode,isPayDeposit,filter,startTime,endTime,pageNum,rows);
				Integer count = dealerQuery.getDealerCount(dealerClassify,cooperationMode,countMode,isPayDeposit,filter,startTime,endTime,pageNum,rows);
				if(dealerList!=null && dealerList.size()>0){
					List<DealerRepresentation> list = new ArrayList<DealerRepresentation>();
					for (DealerBean model : dealerList) {
						list.add(new DealerRepresentation(model));
					}
					result.setContent(list);
				}
				result.setPager(count, pageNum, rows);
				result.setStatus(MCode.V_200);
			} catch (Exception e) {
				log.error("修改经销商出错" + e.getMessage(), e);
	            result =  new MPager(MCode.V_400, "服务器开小差了");
			}
			return new ResponseEntity<MPager>(result, HttpStatus.OK);
		}
		
	
		 
		 /**
		  * 查询供应商列表
		  */
		 @RequestMapping(value = "/dealers", method = RequestMethod.GET)
		    public ResponseEntity<MResult> queryDealerList(
		            @RequestParam(value = "dealerIds", required = true) String dealerIds
		            ) {
			 MResult result = new MResult(MCode.V_1);
		        try {
		        	List<DealerShopRepresentation> list = new ArrayList<DealerShopRepresentation>();
		        	List<DealerBean> dealers =  dealerQuery.getDealers(dealerIds);
		        	for (DealerBean model : dealers) {
		        		list.add(new DealerShopRepresentation(model));
					}
		        	result.setContent(list);
		            result.setStatus(MCode.V_200);
		        } catch (Exception e) {
		        	log.error("经销商列表出错", e);
		            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
		        }
		        return new ResponseEntity<MResult>(result, HttpStatus.OK);
		    }
		 
		 
		 /**
		  * 
		  * @param dealerId
		  * @return
		  */
		 @RequestMapping(value = "/{dealerId}", method = RequestMethod.GET)
		    public ResponseEntity<MResult> queryDealer(
		            @PathVariable(value = "dealerId", required = true) String dealerId
		            ) {
			 MResult result = new MResult(MCode.V_1);
		        try {
		        	DealerBean dealer =  dealerQuery.getDealer(dealerId);
		        	DealerDetailRepresentation representation = new DealerDetailRepresentation(dealer);
		        	result.setContent(representation);
		            result.setStatus(MCode.V_200);
		        } catch (Exception e) {
		        	log.error("经销商列表出错", e);
		            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
		        }
		        return new ResponseEntity<MResult>(result, HttpStatus.OK);
		    }
		 
		 /**
		  * 根据经销商名称获取经销商
		  * @param dealerName
		  * @return
		  */
		 @RequestMapping(value = "/getDealerName", method = RequestMethod.GET)
		    public ResponseEntity<MResult> getDealerName(
		            @RequestParam(value = "dealerName", required = true) String dealerName
		            ) {
			 MResult result = new MResult(MCode.V_1);
			 List<DealerNameListRepresentation> list = new ArrayList<DealerNameListRepresentation>();
		        try {
		        	List<DealerBean> dealerList =  dealerQuery.getDealerByName(dealerName);
		        	if(dealerList!=null && dealerList.size()>0){
		        		for (DealerBean model : dealerList) {
		        			list.add(new DealerNameListRepresentation(model));
						}
		        	}
		        	result.setContent(list);
		            result.setStatus(MCode.V_200);
		        } catch (Exception e) {
		        	log.error("经销商名称列表出错", e);
		            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
		        }
		        return new ResponseEntity<MResult>(result, HttpStatus.OK);
		    }
		 
}
