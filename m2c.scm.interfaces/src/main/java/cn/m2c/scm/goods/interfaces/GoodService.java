package cn.m2c.scm.goods.interfaces;

import java.util.List;
import java.util.Map;

public interface GoodService {
	
	/**
	 * 订单领域需要的数据
	 * @param goodsId
	 * @param propertyId
	 * @return
	 */
	public Map<String,Object> getPropertyInfo(String goodsId,String propertyId);
	
	/**
	 * 检查商品是否存在和商品现在的状态
	 * @param goodsId
	 * @return
	 */
	public Map<String,Object> checkGoodsStatus(String goodsId);
	
	/**
	 * 查询商品分类的名称以及商品数量
	 * @return
	 */
	public List<Map<String,Object>> getSecondClassify();
	
	/**
	 * 根据商品id查询爱好商品
	 * @param goodsId
	 * @return
	 */
	public Map<String,Object> getFavoriteGoods(String goodsId);
	
	
	/**
	 * 获得商品信息
	 */
	public Map<String,Object> getGoodsInfo(String goodsId);
	
	
	
	/**
	 * 获得商品待绑定广告位
	 */
	public Map<String,Object> getNeedDeal();
	
	
	/**
	 * 获取识别图片的id和url
	 * @return
	 */
	public List<Map<String , Object>> getRecognizedPic();
	
	/**
	 * 根据经销商id查询经销商信息
	 */
	public Map<String,Object> getDealerInfo(String dealerId);
	
	
	
}
