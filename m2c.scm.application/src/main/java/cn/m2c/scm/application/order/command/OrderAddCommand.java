package cn.m2c.scm.application.order.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.InvoiceInfo;
import cn.m2c.scm.domain.model.order.ReceiveAddr;
/***
 * 订单提交命令, 订单号，用户，收货地址必填
 * @author fanjc
 *
 */
public class OrderAddCommand extends AssertionConcern implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String orderId;
	
	private String userId;
	
	private String noted;
	
	private JSONArray goodses;
	
	private JSONArray coupons;
	
	private InvoiceInfo invoice;
	/**收货地址*/
	private ReceiveAddr addr;
	
	private Double latitude;
	
	private Double longitude;
	
	public Double getLatitude() {
		return latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	
	private Integer from;
	
	public Integer getFrom() {
		return from;
	}
	
	public OrderAddCommand(String orderId, String userId, String noted
			,String goodses, String invoice, String addr, String coupons
			, Double latitude, Double longitude, Integer from) throws NegativeException {
		// 检验必传参数, 若不符合条件则直接抛出异常
		if (StringUtils.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_1, "订单号为空(orderId)");
		}
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID为空(userId)");
		}
		
		this.userId = userId;
		this.orderId = orderId;
		this.noted = noted;
		this.from = from;
		checkGoodses(goodses);
		
		if (!StringUtils.isEmpty(coupons)) {
			try {
				this.coupons = JSONObject.parseArray(coupons);
			}
			catch (Exception e) {
				throw new NegativeException(MCode.V_1, "优惠券参数格式不正确！");
			}
		}
		this.latitude = latitude;
		this.longitude = longitude;
		
		Gson gson = new Gson();
		
		checkInvoice(invoice, gson);
		
		checkAddr(addr, gson);
	}
	/**
	 * 检查商品参数
	 * @param ges
	 * @throws NegativeException
	 */
	private void checkInvoice(String ges, Gson gson) throws NegativeException {
		if (StringUtils.isEmpty(ges)) {
			return;
		}
		JSONObject jsonObj;
		try {
			jsonObj = JSONObject.parseObject(ges);
		}
		catch (Exception e) {
			throw new NegativeException(MCode.V_1, "发票参数格式不正确！");
		}
		checkInvoice(jsonObj, gson);
	}
	
	/**
	 * 检查商品参数
	 * @param ges
	 * @throws NegativeException
	 */
	private void checkAddr(String ges, Gson gson) throws NegativeException {
		if (StringUtils.isEmpty(ges)) {
			return;
		}
		JSONObject addr;
		try {
			addr = JSONObject.parseObject(ges);
		}
		catch (Exception e) {
			throw new NegativeException(MCode.V_1, "地址参数格式不正确！");
		}
		
		String tmp = addr.getString("province");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货地址省为空！");
		}
		
		tmp = addr.getString("provinceCode");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货地址省编码为空！");
		}
		
		tmp = addr.getString("city");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货地址市为空！");
		}
		
		tmp = addr.getString("cityCode");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货地址省为空！");
		}
		
		tmp = addr.getString("area");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货地址区或城镇为空！");
		}
		
		tmp = addr.getString("street");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货详细地址为空！");
		}
		
		tmp = addr.getString("revPerson");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货联系人为空！");
		}
		
		tmp = addr.getString("phone");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "收货联系人电话为空！");
		}
		
		//this.addr = JSONObject.parseObject(ges, ReceiveAddr.class);
		this.addr = gson.fromJson(ges, ReceiveAddr.class);
	}
	/**
	 * 检查商品参数
	 * @param ges
	 * @throws NegativeException
	 */
	private void checkGoodses(String ges) throws NegativeException {
		if (StringUtils.isEmpty(ges)) {
			return;
		}
		try {
			goodses = JSONObject.parseArray(ges);
		}
		catch (Exception e) {
			throw new NegativeException(MCode.V_1, "商品参数格式不正确！");
		}
		if (this.goodses.size() < 1) {
			throw new NegativeException(MCode.V_1, "请至少选择一个商品提交！");
		}
		
		int sz = goodses.size();
		for (int i=0; i<sz; i++) {
			JSONObject goods = goodses.getJSONObject(i);
			String tmp = goods.getString("goodsId");
			if (StringUtils.isEmpty(tmp)) {
				throw new NegativeException(MCode.V_1, "商品Id为空！");
			}
			tmp = goods.getString("skuId");
			if (StringUtils.isEmpty(tmp)) {
				throw new NegativeException(MCode.V_1, "SKU Id为空！");
			}
			
			int sl = goods.getIntValue("purNum");
			if (sl < 1) {
				throw new NegativeException(MCode.V_1, "购买数量必须大于0！");
			}
			
			/*tmp = goods.getString("unit");
			if (StringUtils.isEmpty(tmp)) {
				throw new NegativeException(MCode.V_1, "商品计量单位为空！");
			}*/
		}
	}
	/***
	 * 检查发票
	 * @throws NegativeException 
	 */
	private void checkInvoice(JSONObject invoice1, Gson gson) throws NegativeException {
		if (invoice1 == null)
			return;
		String tmp = invoice1.getString("header");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "发票抬头为空！");
		}
		/*tmp = invoice1.getString("name");
		if (StringUtils.isEmpty(tmp)) {
			throw new NegativeException(MCode.V_1, "开票的单位或名称为空！");
		}*/
		//this.invoice = JSONObject.parseObject(invoice1.toJSONString(), InvoiceInfo.class);
		this.invoice = gson.fromJson(invoice1.toJSONString(), InvoiceInfo.class);
	}

	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}

	public String getNoted() {
		return noted;
	}

	public JSONArray getGoodses() {
		return goodses;
	}

	public JSONArray getCoupons() {
		return coupons;
	}

	public InvoiceInfo getInvoice() {
		return invoice;
	}

	public ReceiveAddr getAddr() {
		return addr;
	}
	
}
