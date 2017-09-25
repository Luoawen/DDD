package cn.m2c.scm.domain.model.goods.classify;


public interface GoodsClassifyRepository {
	/**
	 * 根据id露出聚合
	 * @param goodsClassifyId
	 */
	public GoodsClassify getGoodsClassifyDetail(String goodsClassifyId);

	/**
	 * 保存商品分类
	 * @param goodsClassify
	 */
	public void save(GoodsClassify goodsClassify);
}
