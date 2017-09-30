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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
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
	public ResponseEntity<MResult> add(
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
	
	/**
	 * 查询店铺详情
	 * @param dealerName
	 * @param dealerClassify
	 * @param dealerId
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	 @RequestMapping(value = "/shop", method = RequestMethod.GET)
	    public ResponseEntity<MPager> dealerShopList(
	            @RequestParam(value = "dealerName", required = false) String dealerName,
	            @RequestParam(value = "dealerClassify", required = false) String dealerClassify,
	            @RequestParam(value = "dealerId", required = false) String dealerId,
	            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
	            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
	        MPager result = new MPager(MCode.V_1);
	        try {
	            List<Map<String, Object>> dealerList = new ArrayList<Map<String,Object>>();
	            Map<String, Object> map = new HashMap<String, Object>();
	            map.put("dealerId", "JXS4485CE8E3EE849F4BAAD4A1A290CD95A");
	            map.put("shopName", "青青草原旗舰店");
	            map.put("dealerName", "轻轻的我来了供应商");
	            map.put("shopIcon", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
	            map.put("onSaleGoods", 100);
	            Map<String, Object> map1 = new HashMap<String, Object>();
	            map1.put("dealerId", "JXS2B8734CCD4B8477983CDA97C68D6AF8C");
	            map1.put("shopName", "北大青鸟旗舰店");
	            map1.put("shopIcon", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
	            map1.put("onSaleGoods", 100);

	            dealerList.add(map);
	            dealerList.add(map1);
	            result.setContent(dealerList);
	            result.setPager(2, pageNum, rows);
	            result.setStatus(MCode.V_200);
	        } catch (Exception e) {
	        	log.error("店铺列表查询出错", e);
	            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
	        }
	        return new ResponseEntity<MPager>(result, HttpStatus.OK);
	    }
	 
	 
	 /**
		 * 查询店铺列表
		 * @param dealerName
		 * @param dealerClassify
		 * @param dealerId
		 * @param pageNum
		 * @param rows
		 * @return
		 */
		 @RequestMapping(value = "/detail", method = RequestMethod.GET)
		    public ResponseEntity<MResult> dealerShopDetail(
		            @RequestParam(value = "dealerId", required = false) String dealerId) {
			 MResult result = new MResult(MCode.V_1);
		        try {
		            Map<String, Object> map = new HashMap<String, Object>();
		            map.put("dealerId", "JXS4485CE8E3EE849F4BAAD4A1A290CD95A");
		            map.put("shopName", "青青草原旗舰店");
		            map.put("dealerName", "轻轻的我来了供应商");
		            map.put("shopIcon", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
		            map.put("onSaleGoods", 100);
		            result.setContent(map);
		            result.setStatus(MCode.V_200);
		        } catch (Exception e) {
		        	log.error("店铺详情出错", e);
		            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
		        }
		        return new ResponseEntity<MResult>(result, HttpStatus.OK);
		    }
}
