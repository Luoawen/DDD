package cn.m2c.scm.domain.service.order;

import java.util.List;
import java.util.Map;

import cn.m2c.scm.domain.NegativeException;



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
	public <T> boolean lockMarketIds(List<T> marketIds,String couponUserId, String orderNo, String userId,long orderAmount,long orderTime,String useCouponList) throws NegativeException;
	/***
	 * 解锁优惠券
	 * @param couponsIds
	 * @param userId
	 * @return
	 */
	public void unlockCoupons(List<String> couponsIds, String userId) throws NegativeException;
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
	
	/***
	 * 获取第三方物流信息
	 * @param resIds
	 * @return
	 */
	public String getExpressInfo(String com,String nu) throws Exception;
	
	/**
	 * 根据用户id获取用户手机号
	 * @param userId
	 * @return
	 */
	public String getUserMobileByUserId(String userId) throws NegativeException;
	
	/**
	 * 调用第三方发送短信接口
	 * @param userMobile
	 * @param shopName
	 * @throws NegativeException
	 */
	public void sendOrderSMS(String userMobile, String shopName) throws NegativeException;
	
	/**
	 * 三方物流监听
	 * @param com
	 * @param nu
	 * @throws NegativeException
	 */
	public void registExpress(String com, String nu) throws NegativeException;
	/**
	 * 获取优惠券信息
	 * @param couponId
	 * @param cla 
	 * @return
	 * @throws NegativeException
	 */
	public <T> T getCouponById(String couponId,String couponUserId,String userId, Class<T> cla) throws NegativeException;
	/**
	 * 根据用户名查询用户id
	 * @param userName
	 * @return
     */
	public String getUserIdByUserName(String userName);

	String getMediaName(String mediaId);
	
	Map getUserMobileOrUserName(String userMessage);
	/**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    public Map getUserInfoByUserId(String userId) throws NegativeException;
	
	 /**
     * 消息推送
     *
     * @param msgType  推送类型 1:物流助手 2:通知消息
     * @param userId   被推送者ID
     * @param extra    业务参数
     * @param senderId 发送者ID
     */
    public void msgPush(Integer msgType, String userId, String extra, String senderId);
}
