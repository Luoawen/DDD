package cn.m2c.scm.domain.service.order;

import java.util.List;
import java.util.Map;

/***
 * 订单领域服务
 * @author fanjc
 * created date 2017年10月16日
 * copyrighted@m2c
 */
public interface OrderService {
	/***
	 * 判断商品sku库存
	 * @param skus key:skuId, val:num
	 * @return 缺少库存的商品及数量
	 */
	public Map<String, Object> judgeStock(Map<String, Integer> skus);
	/***
	 * 锁定营销活动
	 * @param <T>
	 * @param marketIds
	 * @param orderNo
	 * @param userId
	 * @return 成功与否
	 */
	public <T> boolean lockMarketIds(List<T> marketIds, String orderNo, String userId);
	/***
	 * 解锁优惠券
	 * @param couponsIds
	 * @param userId
	 * @return
	 */
	public void unlockCoupons(List<String> couponsIds, String userId);
	/***
	 * 锁定优惠券
	 * @param couponsIds
	 * @return
	 */
	public void lockCoupons(List<String> couponsIds);
	/***
	 * 获取与商品或商家相关的所有的营策略
	 * @param goodses(goodsId, dealerId)
	 * @return
	 */
	public Map<String, Object> getMarketings(Map<String, Object> goodses);
	
	/***
	 * 获取用户购物车下单的商品数据
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getShopCarGoods(String userId);
	/***
	 * 获取营销规则列表
	 * @param goodsId, typeId, dealerId
	 * @return
	 */
	public Map<String, Object> getMarketingsByGoods(Map<String, Object> skus);
	
	/***
	 * 获取营销规则列表通过营销ID
	 * @param <T>
	 * @param marketingIds
	 * @param userId
	 * @return
	 */
	public <T> List<T> getMarketingsByIds(List<String> marketingIds, String userId, Class<T[]> clss);
	
	/***
	 * 获取商品的供货价
	 * @param goods 商品ID, 营销Id
	 * @return
	 */
	public Map<String, Object> getSupplyPriceByIds(List<Map<String, String>> goods);
	
	/***
	 * 获取媒体相关信息
	 * @param resIds
	 * @return
	 */
	public <T> Map<String, Object> getMediaBdByResIds(List<T> resIds, long time);
	
}
