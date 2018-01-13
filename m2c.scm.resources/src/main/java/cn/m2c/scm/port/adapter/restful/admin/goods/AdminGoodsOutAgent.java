package cn.m2c.scm.port.adapter.restful.admin.goods;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsDetailMultipleRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsRandomRepresentation;
import cn.m2c.scm.application.order.data.bean.CouponDealerBean;
import cn.m2c.scm.application.order.data.representation.CouponDealerBeanRepresentation;
import cn.m2c.scm.domain.service.goods.GoodsService;

/**
 * 商品对运营后台提供的接口
 */
@RestController
@RequestMapping("/goods-out")
public class AdminGoodsOutAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminGoodsOutAgent.class);
	
	@Autowired
	GoodsQueryApplication goodsQueryApplication;
	@Resource(name = "goodsRestService")
	GoodsService goodsService;
	
	@RequestMapping(value = "/pioneerten", method = RequestMethod.GET)
	public ResponseEntity<MResult> queryGoodsPioneerTenByGoodsName(
		@RequestParam(value = "goodsName", required = false) String goodsName
		){
		MResult result = new MResult(MCode.V_1);
		try {
			List<GoodsBean> goodsBeans = goodsQueryApplication.queryGoodsPioneerTenByGoodsName(goodsName);
			if (null != goodsBeans && goodsBeans.size() > 0) {
				List<GoodsRandomRepresentation> representations = new ArrayList<>();
				for (GoodsBean goodsBean : goodsBeans) {
					representations.add(new GoodsRandomRepresentation(goodsBean));
				}
				result.setContent(representations);
			}
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("queryGoodsPioneerTenByGoodsName Exception e:", e);
			result = new MResult(MCode.V_400, "查询最早十个商品失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 根据优惠券id查询适用商家/商品
	 * @param couponId  优惠券id
	 * @param pageNum   第几页
	 * @param rows      每页多少行
	 * @return
	 */
	@RequestMapping(value = "/couponapply", method = RequestMethod.GET)
	public ResponseEntity<MPager> queryCouponApplyGoodsOrDealer(
		@RequestParam(value = "couponId", required = false) String couponId, 
		@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
		@RequestParam(value = "rows", required = false, defaultValue = "30") Integer rows
		){
		MPager result = new MPager(MCode.V_1);
		try {
			Map couponMap = null;
			if(StringUtils.isNotEmpty(couponId)) { //调用营销接口查询优惠券信息
				couponMap = goodsService.getCouponRange(couponId);
			}
			Integer total = 0;
			if(null != couponMap) {
				Integer couponRangeType = (Integer) couponMap.get("rangeType");
				//根据优惠券适用类型查询商品或商家总数
				total = goodsQueryApplication.queryCouponApplyGoodsOrDealerTotal(couponMap, couponRangeType);
				if(total > 0) {
					if(couponRangeType == 1) {//查商家
						List<CouponDealerBean> couponDealerBeans = goodsQueryApplication.queryCouponApplyDealer(couponMap, pageNum, rows);
						if(null != couponDealerBeans && couponDealerBeans.size() > 0) {
							List<CouponDealerBeanRepresentation> representations = new ArrayList<>();
							for(CouponDealerBean couponDealerBean : couponDealerBeans) {
								representations.add(new CouponDealerBeanRepresentation(couponDealerBean));
							}
							result.setContent(representations);
						}
					}
					if(couponRangeType == 0 || couponRangeType == 2 || couponRangeType == 3) {//查商品
						List<GoodsBean> goodsBeans = goodsQueryApplication.appSearchGoods(null, null, null, null, null, null, null, couponMap, pageNum, rows);
						if(null != goodsBeans && goodsBeans.size() > 0) {
							List<GoodsDetailMultipleRepresentation> representations = new ArrayList<>();
							for(GoodsBean goodsBean : goodsBeans) {
								representations.add(new GoodsDetailMultipleRepresentation(goodsBean));
							}
							result.setContent(representations);
						}
					}
				}
			}
			result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("queryCouponApplyGoodsOrDealer Exception e:", e);
			result = new MPager(MCode.V_400, "查询优惠券适用商品或商家失败");
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
}
