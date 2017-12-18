package cn.m2c.scm.domain.model.goods;

public interface GoodsGuaranteeRepository {
	
	/**
	 * 根据商品保障id查询商品保障
	 * */
	public GoodsGuarantee queryGoodsGuaranteeById(String guaranteeId);

	/**
	 * 查询商品保障名是否重复
	 * */
	public boolean goodsGuaranteeNameIsRepeat(String guaranteeName, String dealerId, String guaranteeId);

	/**
	 * 保存商品保障
	 * */
	public void save(GoodsGuarantee goodsGuarantee);
	
}
