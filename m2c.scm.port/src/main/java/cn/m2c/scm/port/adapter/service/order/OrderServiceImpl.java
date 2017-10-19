package cn.m2c.scm.port.adapter.service.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.m2c.scm.domain.service.order.OrderService;
/***
 * 订单领域服务实现类
 * @author fanjc
 * created date 2017年10月16日
 * copyrighted@m2c
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Override
	public Map<String, Object> judgeStock(Map<String, Integer> skus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lockStock(Map<String, Float> skus) {
		// TODO Auto-generated method stub
		// 抛出异常表示锁定不成功.
	}

	@Override
	public Map<String, Object> getMarketings(Map<String, Object> goodses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getShopCarGoods(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * 获取营销规则列表
	 * @param goodsId, typeId, dealerId
	 * @return
	 */
	@Override
	public Map<String, Object> getMarketingsByGoods(Map<String, Object> skus) {
		return null;
	}
	
	/***
	 * 获取营销规则列表通过营销ID
	 * @param marketingIds
	 * @return
	 */
	@Override
	public Map<String, Object> getMarketingsByIds(List<String> marketingIds) {
		return null;
	}
	
	/***
	 * 获取商品的供货价
	 * @param goods 商品ID, 营销Id
	 * @return
	 */
	@Override
	public Map<String, Object> getSupplyPriceByIds(List<Map<String, String>> goods) {
		return null;
	}
	
	@Override
	public void lockCoupons(List<String> couponsIds) {
		
	}

	@Override
	public Map<String, Object> getMediaBdByResIds(List<String> resIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unlockCoupons(List<String> couponsIds, String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockStock(Map<String, Float> skus) {
		// TODO Auto-generated method stub
		
	}
}
