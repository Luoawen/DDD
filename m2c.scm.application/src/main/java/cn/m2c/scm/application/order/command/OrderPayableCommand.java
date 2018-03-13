package cn.m2c.scm.application.order.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.NegativeException;
/***
 * 订单提交命令, 订单号，用户，收货地址必填
 * @author fanjc
 *
 */
public class OrderPayableCommand extends AssertionConcern implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String userId;
	
	private List<GoodsDto> goodses;
	
	private String cityCode;
	
	
	public OrderPayableCommand(String cityCode, String userId
			,String goodses
			) throws NegativeException {
		// 检验必传参数, 若不符合条件则直接抛出异常
		if (StringUtils.isEmpty(cityCode)) {
			throw new NegativeException(MCode.V_1, "地址区号不能为空(cityCode)");
		}
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID为空(userId)");
		}
		this.cityCode = cityCode;
		this.userId = userId;
		checkGoodses(goodses);
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
		
		JSONArray jsonArr = null;
		try {
			jsonArr = JSONObject.parseArray(ges);
		}
		catch (Exception e) {
			throw new NegativeException(MCode.V_1, "商品参数格式不正确！");
		}
		if (jsonArr.size() < 1) {
			throw new NegativeException(MCode.V_1, "请至少选择一个商品提交！");
		}
		
		int sz = jsonArr.size();
		for (int i=0; i<sz; i++) {
			
			JSONObject goods = jsonArr.getJSONObject(i);
			String tmp = goods.getString("goodsId");
			if (StringUtils.isEmpty(tmp)) {
				throw new NegativeException(MCode.V_1, "商品Id为空！");
			}
			String skuId = goods.getString("skuId");
			if (StringUtils.isEmpty(skuId)) {
				throw new NegativeException(MCode.V_1, "SKU Id为空！");
			}
			
			int sl = goods.getIntValue("purNum");
			if (sl < 1) {
				throw new NegativeException(MCode.V_1, "购买数量必须大于0！");
			}
			
			//解析app传入的特惠价
			String appSpecialPrice = "";
			Integer isSpecial = goods.getInteger("isSpecial");
			if(isSpecial != null && isSpecial==1){
				appSpecialPrice = goods.getString("appSpecialPrice");
				if (!StringUtils.isEmpty(appSpecialPrice)) {
					// 因后台已经变成了100表示1元
					appSpecialPrice = String.valueOf(Long.parseLong(appSpecialPrice) * 100);
				}
				
				String strPrice = goods.getString("strAppSpecialPrice");
				if (!StringUtils.isEmpty(strPrice)) {
					appSpecialPrice = String.valueOf(Utils.convertNeedMoney(strPrice));
				}
			}
			else
				isSpecial = 0;
			
			if (goodses == null) {
				goodses = new ArrayList<GoodsDto>();
			}
			GoodsDto dto = new GoodsDto();
			dto.setIndex(i+1); // 这个必须要设置为后面用, 不能为0， 因默认是0兼容之前的
			dto.setSkuId(skuId);
			dto.setGoodsId(tmp);
			dto.setPurNum(sl);
			dto.setIsSpecial(isSpecial);
			dto.setAppSpecialPrice(appSpecialPrice);
			
            goodses.add(dto);
		}
	}

	public String getUserId() {
		return userId;
	}

	public List<GoodsDto> getGoodses() {
		return goodses;
	}
	
	public String getCityCode() {
		return cityCode;
	}
}
