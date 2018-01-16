package cn.m2c.scm.port.adapter.restful.admin;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.seller.SellerApplication;
import cn.m2c.scm.application.seller.query.SellerQuery;
import cn.m2c.scm.domain.NegativeException;

@Controller
@RequestMapping(value = "/admin/seller")
public class AdminSellerAgent {
	private static final Logger log = LoggerFactory.getLogger(AdminSellerAgent.class);
	
	@Autowired
	SellerApplication sellerApplication;
	
	@Autowired
	SellerQuery sellerQuery;


	/**
	 * 禁用业务员
	 * @param sellerId
	 * @return
	 */
	@RequestMapping(value = "/disable",method = RequestMethod.GET)
	public ResponseEntity<MResult> sellerDisable(@RequestParam(value = "sellerId",required = false) String sellerId){
		MResult result = new MResult(MCode.V_1);
		
		try {
			if (StringUtils.isEmpty(sellerId)) {
				throw new NegativeException(MCode.V_400,"业务员ID为空");
			}
			sellerApplication.sellerDisable(sellerId);
			result.setContent("禁用业务员成功!");
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			log.info("禁用业务员出错",e.getMessage());
			result = new MResult(MCode.V_400,"禁用业务员出错");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
		
		
	}
}
