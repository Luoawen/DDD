package cn.m2c.scm.port.adapter.restful.domain.goods;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.representation.GoodsDetailMultipleRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsForNormalRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.GoodsRandomRepresentation;
import cn.m2c.scm.application.goods.query.data.representation.domain.GoodsDetailRepresentation;
import cn.m2c.scm.application.order.data.bean.CouponDealerBean;
import cn.m2c.scm.application.order.data.representation.CouponDealerBeanRepresentation;
import cn.m2c.scm.domain.service.goods.GoodsService;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 商品接口
 */
@RestController
@RequestMapping("/domain/goods")
public class GoodsDomainAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsDomainAgent.class);

    @Autowired
    GoodsQueryApplication goodsQueryApplication;
    @Resource(name = "goodsRestService")
	GoodsService goodsService;
    
    /**
     * 给营销工具提供接口查询商品信息
     *
     * @param goodsIds
     * @return
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryNormalGoodsByGoodsIds(
            @RequestParam(value = "goodsIds", required = false) List goodsIds) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<GoodsBean> goodsBeans = goodsQueryApplication.queryGoodsByGoodsIds(goodsIds);
            if (null != goodsBeans && goodsBeans.size() > 0) {
                List<GoodsForNormalRepresentation> resultRepresentation = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeans) {
                    resultRepresentation.add(new GoodsForNormalRepresentation(goodsBean));
                }
                result.setContent(resultRepresentation);
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryNormalGoodsByGoodsIds Exception e:", e);
            result = new MPager(MCode.V_400, "查询在售商品列表失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 给营销工具提供接口查询商品信息
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> queryGoodsByGoodsId(
            @RequestParam(value = "goodsId", required = false) String goodsId) {
        MResult result = new MResult(MCode.V_1);
        try {
            GoodsBean goodsBean = goodsQueryApplication.queryGoodsByGoodsId(goodsId);
            if (null != goodsBean) {
                result.setContent(new GoodsDetailRepresentation(goodsBean));
            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("queryGoodsByGoodsId Exception e:", e);
            result = new MPager(MCode.V_400, "查询商品信息失败");
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
	@RequestMapping(value = "/coupon/apply", method = RequestMethod.GET)
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
					if(couponRangeType == 0 || couponRangeType == 2 || couponRangeType == 3 || couponRangeType == 4) {//查商品
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
	
	/**
	 * 给营销工具提供接口根据商品名称/ids查询所有商品
	 * @param goodsName  商品名称
	 * @param goodsIds   商品ids
	 * @return
	 */
	@RequestMapping(value = "/name/ids", method = RequestMethod.GET)
	public ResponseEntity<MResult> queryGoodsMessageByGoodsName(
			 @RequestParam(value = "goodsName", required = false) String goodsName
			,@RequestParam(value = "goodsIds", required = false) List goodsIds 
			){
		MResult result = new MResult(MCode.V_1);
		try {
			List<GoodsBean> list = goodsQueryApplication.queryGoodsMessageByGoodsNameOrGoodsIds(goodsName, goodsIds);
			if(null != list && list.size() > 0) {
				List<GoodsRandomRepresentation> representations = new ArrayList<>();
				for(GoodsBean bean : list) {
					representations.add(new GoodsRandomRepresentation(bean));
				}
				result.setContent(representations);
			}
			result.setStatus(MCode.V_200);
		}catch(Exception e) {
			LOGGER.error("queryGoodsMessageByGoodsName Exception e:", e);
			result = new MPager(MCode.V_400, "查询商品信息失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
}
