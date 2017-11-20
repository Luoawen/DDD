package cn.m2c.scm.port.adapter.restful.web.seller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.dealer.data.export.SellerExportModel;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.goods.query.data.export.GoodsServiceRateModel;
import cn.m2c.scm.application.goods.query.data.export.GoodsSupplyPriceModel;
import cn.m2c.scm.application.seller.SellerApplication;
import cn.m2c.scm.application.seller.command.SellerCommand;
import cn.m2c.scm.application.seller.data.bean.SellerBean;
import cn.m2c.scm.application.seller.query.SellerQuery;
import cn.m2c.scm.application.utils.ExcelUtil;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;

@Controller
@RequestMapping("/seller/sys")
public class SellerAgent {
	private final static Logger log = LoggerFactory.getLogger(SellerAgent.class);

	@Autowired
	SellerApplication sellerApplication;

	@Autowired
	SellerQuery sellerQuery;

	/**
	 * 添加业务员
	 * 
	 * @param sellerName
	 * @param sellerPhone
	 * @param sellerSex
	 * @param sellerNo
	 * @param sellerPass
	 * @param sellerConfirmPass
	 * @param sellerProvince
	 * @param sellerCity
	 * @param sellerArea
	 * @param sellerPcode
	 * @param sellerCcode
	 * @param sellerAcode
	 * @param sellerqq
	 * @param sellerWechat
	 * @param sellerRemark
	 * @return
	 */
	@RequestMapping(value = "/mng", method = RequestMethod.POST)
	@RequirePermissions(value ={"scm:seller:add"})
	public ResponseEntity<MResult> add(@RequestParam(value = "sellerName", required = false) String sellerName,
			@RequestParam(value = "sellerPhone", required = false) String sellerPhone,
			@RequestParam(value = "sellerSex", required = false) Integer sellerSex,
			@RequestParam(value = "sellerNo", required = false) String sellerNo,
			@RequestParam(value = "sellerPass", required = false,defaultValue="") String sellerPass,
			@RequestParam(value = "sellerConfirmPass", required = false,defaultValue="") String sellerConfirmPass,
			@RequestParam(value = "sellerProvince", required = false) String sellerProvince,
			@RequestParam(value = "sellerCity", required = false) String sellerCity,
			@RequestParam(value = "sellerArea", required = false) String sellerArea,
			@RequestParam(value = "sellerPcode", required = false) String sellerPcode,
			@RequestParam(value = "sellerCcode", required = false) String sellerCcode,
			@RequestParam(value = "sellerAcode", required = false) String sellerAcode,
			@RequestParam(value = "sellerqq", required = false) String sellerqq,
			@RequestParam(value = "sellerWechat", required = false) String sellerWechat,
			@RequestParam(value = "sellerRemark", required = false) String sellerRemark) {
		MResult result = new MResult(MCode.V_1);
		try {
			String sellerId = IDGenerator.get(IDGenerator.SALE_PREFIX_TITLE);
			if("".equals(sellerPass) || "".equals(sellerConfirmPass)){
				result.setErrorMessage("请输入业务员密码");
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			SellerCommand command = new SellerCommand(sellerId, sellerName, sellerPhone, sellerSex, sellerNo,
					sellerPass, sellerConfirmPass, sellerProvince, sellerCity, sellerArea, sellerPcode, sellerCcode,
					sellerAcode, sellerqq, sellerWechat, sellerRemark);
			sellerApplication.addSeller(command);
			result.setStatus(MCode.V_200);
		}catch (IllegalArgumentException e) {
			log.error("添加业务员出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (NegativeException ne) {
			result = new MResult(MCode.V_1, ne.getMessage());
		} catch (Exception e) {
			log.error("添加业务员出错" + e.getMessage(), e);
			result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);

	}

	/**
	 * 修改业务员
	 * 
	 * @param sellerId
	 * @param sellerName
	 * @param sellerPhone
	 * @param sellerSex
	 * @param sellerNo
	 * @param sellerPass
	 * @param sellerConfirmPass
	 * @param sellerProvince
	 * @param sellerCity
	 * @param sellerArea
	 * @param sellerPcode
	 * @param sellerCcode
	 * @param sellerAcode
	 * @param sellerqq
	 * @param sellerWechat
	 * @param sellerRemark
	 * @return
	 */
	@RequestMapping(value = "/mng", method = RequestMethod.PUT)
	@RequirePermissions(value ={"scm:seller:add"})
	public ResponseEntity<MResult> update(@RequestParam(value = "sellerId", required = true) String sellerId,
			@RequestParam(value = "sellerName", required = true) String sellerName,
			@RequestParam(value = "sellerPhone", required = true) String sellerPhone,
			@RequestParam(value = "sellerSex", required = true) Integer sellerSex,
			@RequestParam(value = "sellerNo", required = false) String sellerNo,
			@RequestParam(value = "sellerPass", required = true,defaultValue="") String sellerPass,
			@RequestParam(value = "sellerConfirmPass", required = true,defaultValue="") String sellerConfirmPass,
			@RequestParam(value = "sellerProvince", required = true) String sellerProvince,
			@RequestParam(value = "sellerCity", required = true) String sellerCity,
			@RequestParam(value = "sellerArea", required = true) String sellerArea,
			@RequestParam(value = "sellerPcode", required = true) String sellerPcode,
			@RequestParam(value = "sellerCcode", required = true) String sellerCcode,
			@RequestParam(value = "sellerAcode", required = true) String sellerAcode,
			@RequestParam(value = "sellerqq", required = false) String sellerqq,
			@RequestParam(value = "sellerWechat", required = false) String sellerWechat,
			@RequestParam(value = "sellerRemark", required = false) String sellerRemark) {
		MResult result = new MResult(MCode.V_1);
		System.out.println("--------------------请求到update方法");
		try {
			SellerCommand command = new SellerCommand(sellerId, sellerName, sellerPhone, sellerSex, sellerNo,
					sellerPass, sellerConfirmPass, sellerProvince, sellerCity, sellerArea, sellerPcode, sellerCcode,
					sellerAcode, sellerqq, sellerWechat, sellerRemark);
			sellerApplication.update(command);
			result.setStatus(MCode.V_200);
		}catch (IllegalArgumentException e) {
			log.error("修改业务员出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (NegativeException ne) {
			result = new MResult(MCode.V_1, ne.getMessage());
		}catch (Exception e) {
			log.error("修改业务员出错" + e.getMessage(), e);
			result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<MPager> list(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "rows", required = false) Integer rows) {
		System.out.println("----------请求到List方法");
		MPager result = new MPager(MCode.V_1);
		try {
			// List<DealerBean> dealerList =
			// dealerQuery.getDealerList(dealerClassify,cooperationMode,countMode,isPayDeposit,dealerName,dealerId,userPhone,sellerPhone,startTime,endTime,pageNum,rows);
			// Integer count =
			// dealerQuery.getDealerCount(dealerClassify,cooperationMode,countMode,isPayDeposit,dealerName,dealerId,userPhone,sellerPhone,startTime,endTime,pageNum,rows);
			List<SellerBean> sellerList = sellerQuery.getSellerList(filter, startTime, endTime, pageNum, rows);
			Integer count = sellerQuery.getCount(filter, startTime, endTime);
			if (pageNum != null && rows != null) {
				result.setPager(count, pageNum, rows);
			}
			result.setContent(sellerList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			log.error("业务员列表出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);

	}

	/**
	 * 业务员详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{sellerId}", method = RequestMethod.GET)
	public ResponseEntity<MPager> getSellerDetail(@PathVariable(name = "sellerId", required = true) String sellerId) {
		MPager result = new MPager(MCode.V_1);
		try {
			SellerBean seller = sellerQuery.getSeller(sellerId);
			result.setContent(seller);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			log.error("业务员列表出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);

	}
	
	/**
	 * 业务员详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exportSeller", method = RequestMethod.GET)
	public ResponseEntity<MPager> getSellerExport(
			HttpServletResponse response,
			@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "startTime", required = false) String startTime,
			@RequestParam(value = "endTime", required = false) String endTime) {
		MPager result = new MPager(MCode.V_1);
		try {
			List<SellerBean> sellerList = sellerQuery.getSellerExportList(filter,startTime,endTime);
			List<SellerExportModel> excelList = new ArrayList<SellerExportModel>();  
			for (SellerBean model : sellerList) {
				excelList.add(new SellerExportModel(model));
              }
			String fileName = "业务员.xls";
            ExcelUtil.writeExcel(response, fileName, excelList, SellerExportModel.class);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			log.error("业务员列表出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);

	}
}
