package cn.m2c.scm.application.order.command;

import java.util.Date;

import cn.m2c.common.MCode;
import cn.m2c.common.StringUtil;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.ExpressInfo;
/***
 * 售后 用户发货 
 * @author fanjc
 * created date 2017年10月21日
 * copyrighted@m2c
 */
public class SaleAfterShipCmd extends AssertionConcern {

	private String userId;
	
	private String saleAfterNo;
	
	private String skuId;
	
	private String expressNo;
	
	private String expressCode;
	
	private String expressName;
	
	/**送货人姓名*/
	private String expressPerson;
	/**送货人电话*/
	private String expressPhone;
	/**配送方式 0:物流，1自有物流*/
	private int expressWay;
	
	public SaleAfterShipCmd(String userId, String saleAfterNo, String skuId, String expressNo
			, String expressCode, String expressName) throws NegativeException {
		
		if (StringUtil.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		if (StringUtil.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (StringUtil.isEmpty(expressNo)) {
			throw new NegativeException(MCode.V_1, "快递单号参数为空(dealerId)！");
		}
		
		if (StringUtil.isEmpty(expressCode)) {
			throw new NegativeException(MCode.V_1, "快递公司编码为空(expressCode)！");
		}
		
		if (StringUtil.isEmpty(skuId)) {
			throw new NegativeException(MCode.V_1, "售后商品sku参数为空(skuId)！");
		}
		
		if (StringUtil.isEmpty(expressName)) {
			throw new NegativeException(MCode.V_1, "快递公司名称主空(expressName)！");
		}
		
		this.userId = userId;
		this.skuId = skuId;
		this.saleAfterNo = saleAfterNo;
		this.expressNo = expressNo;
		this.expressCode = expressCode;
		this.expressName = expressName;
	}
	
	public SaleAfterShipCmd(String userId, String saleAfterNo, String skuId, String expressNo
			, String expressCode, String expressName, String expressPerson, String expressPhone
			, int expressWay) throws NegativeException {
		this(userId, saleAfterNo, skuId, expressNo, expressCode, expressName);
		
		this.expressPerson = expressPerson;
		this.expressPhone = expressPhone;
		this.expressWay = expressWay;
	}

	public String getUserId() {
		return userId;
	}

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public String getSkuId() {
		return skuId;
	}
	
	public ExpressInfo getExpressInfo() {
		return new ExpressInfo(expressNo, expressName, expressCode);
	}
	/***
	 * 获取商家发货信息
	 * @return
	 */
	public ExpressInfo getSdExpressInfo() {
		return new ExpressInfo(expressNo, expressName, expressPerson, expressCode,
				expressPhone, expressWay, "", new Date());
	}
}