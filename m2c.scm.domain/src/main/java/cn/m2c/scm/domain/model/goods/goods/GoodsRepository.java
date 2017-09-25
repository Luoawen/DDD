package cn.m2c.scm.domain.model.goods.goods;

import java.util.List;


public interface GoodsRepository {
	/**
	 * 根据id露出聚合
	 * @param sellerId
	 */
	public Goods getGoodsDetail(String goodsId);

	/**
	 * 保存业务员操作
	 * @param dseller
	 */
	public void save(Goods goods);

	/**
	 * 根据经销商id获取商品列表
	 * @param dealerId
	 * @return
	 */
	public List<Goods> getGoodsList(String dealerId);
}
