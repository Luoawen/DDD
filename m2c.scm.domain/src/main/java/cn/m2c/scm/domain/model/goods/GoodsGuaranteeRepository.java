package cn.m2c.scm.domain.model.goods;

import java.util.List;

public interface GoodsGuaranteeRepository {
	
	/**
	 * 根据商品保障id查询商品保障
	 * */
	public GoodsGuarantee queryGoodsGuaranteeByIdAndDealerId(String guaranteeId, String dealerId);

	/**
	 * 查询商品保障名是否重复
	 * */
	public boolean goodsGuaranteeNameIsRepeat(String guaranteeName, String dealerId, String guaranteeId);

	/**
	 * 保存商品保障
	 * */
	public void save(GoodsGuarantee goodsGuarantee);

	/**
	 * 删除商品保障
	 * */
	public void remove(GoodsGuarantee goodsGuarantee);

	/**
	 * 查询商家商品保障
	 * */
	public List<GoodsGuarantee> queryGoodsGuaranteeByDealerId(String dealerId);

	/**
	 * 根据保障id查询商品保障
	 * @param guaranteeId
	 * @return
	 */
	public GoodsGuarantee queryGoodsGuaranteeById(String guaranteeId);
	
}
