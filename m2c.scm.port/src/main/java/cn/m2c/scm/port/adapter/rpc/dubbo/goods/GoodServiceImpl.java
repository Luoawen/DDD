package cn.m2c.scm.port.adapter.rpc.dubbo.goods;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.m2c.scm.application.goods.goods.query.SpringJdbcDealerQuery;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcGoodsClassifyQuery;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcGoodsQuery;
import cn.m2c.scm.goods.interfaces.GoodService;

@Service
public class GoodServiceImpl implements GoodService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GoodServiceImpl.class);

	@Autowired
	SpringJdbcGoodsQuery query;
	
	@Autowired
	SpringJdbcGoodsClassifyQuery classifyQuery;
	
	
	@Autowired
	SpringJdbcDealerQuery dealerQuery;
	
	@Override
	public Map<String, Object> getPropertyInfo(String goodsId, String propertyId) {
		Map<String, Object> result = null;
		try {
			result = query.getPropertyInfo(goodsId, propertyId);
		} catch (Exception e) {
			LOGGER.error("调用服务查询出错",e);
		}
		return result;
	}

	@Override
	public Map<String, Object> checkGoodsStatus(String goodsId) {
		Map<String, Object> result = null;
		try {
			result = query.getGoodStatus(goodsId);
		} catch (Exception e) {
			LOGGER.error("检查商品是否存在出错",e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getSecondClassify() {
		List<Map<String, Object>> result = null;
		try {
			result = classifyQuery.getGoodSecondClassify();
		} catch (Exception e) {
			LOGGER.error("查询商品二级分类出错",e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getFavoriteGoods(String goodsId) {
		Map<String, Object> result = null;
		try {
			result = query.getFavoriteGoods(goodsId);
		} catch (Exception e) {
			LOGGER.error("查询收藏商品出错",e);
		}
		return result;
	}

	
	@Override
	public Map<String, Object> getGoodsInfo(String goodsId) {
		Map<String, Object> result = null;
		try {
			result = query.getGoodsInfo(goodsId);
			LOGGER.info("-------------商品信息返回数据"+result+"--------------");
		} catch (Exception e) {
			LOGGER.error("查商品详情出错",e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getNeedDeal() {
		Map<String, Object> result = null;
		try {
			result = query.unBindAd();
		} catch (Exception e) {
			LOGGER.error("查商品绑定媒体数出错",e);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getRecognizedPic() {
		List<Map<String, Object>> result = null;
		try {
			result = query.getRecognizedPic();
		} catch (Exception e) {
			LOGGER.error("查询识别图片id和url出错",e);
		}
		return result;
	}

	@Override
	public Map<String, Object> getDealerInfo(String dealerId) {
		Map<String, Object> result = null;
		try {
			result = dealerQuery.getDealer(dealerId);
		} catch (Exception e) {
			LOGGER.error("经销商信息出错",e);
		}
		return result;
	}

}
