package cn.m2c.scm.application.order;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.order.command.CancelOrderCmd;
import cn.m2c.scm.application.order.command.ConfirmSkuCmd;
import cn.m2c.scm.application.order.command.OrderAddCommand;
import cn.m2c.scm.application.order.command.OrderPayedCmd;
import cn.m2c.scm.application.order.command.PayOrderCmd;
import cn.m2c.scm.application.order.command.SendOrderSMSCommand;
import cn.m2c.scm.application.order.data.bean.CouponBean;
import cn.m2c.scm.application.order.data.bean.CouponUseBean;
import cn.m2c.scm.application.order.data.bean.FreightCalBean;
import cn.m2c.scm.application.order.data.bean.GoodsReqBean;
import cn.m2c.scm.application.order.data.bean.MarketBean;
import cn.m2c.scm.application.order.data.bean.MarketUseBean;
import cn.m2c.scm.application.order.data.bean.MediaResBean;
import cn.m2c.scm.application.order.data.bean.SkuMediaBean;
import cn.m2c.scm.application.order.data.representation.OrderMoney;
import cn.m2c.scm.application.order.query.OrderQueryApplication;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.application.postage.data.representation.PostageModelRuleRepresentation;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatform;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatformRepository;
import cn.m2c.scm.domain.model.order.AppOrdInfo;
import cn.m2c.scm.domain.model.order.CouponInfo;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.domain.model.order.OrderRepository;
import cn.m2c.scm.domain.model.order.SimpleCoupon;
import cn.m2c.scm.domain.model.order.SimpleMarketInfo;
import cn.m2c.scm.domain.model.order.SimpleMarketing;
import cn.m2c.scm.domain.model.special.GoodsSkuSpecial;
import cn.m2c.scm.domain.model.special.GoodsSpecialRepository;
import cn.m2c.scm.domain.service.order.OrderService;
import cn.m2c.scm.domain.util.GetDisconfDataGetter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/***
 * 订单应用服务类
 *
 * @author fanjc
 */
@Service
public class OrderApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplication.class);

    @Autowired
    OrderRepository orderRepository;//订单仓储

    @Autowired
    OrderService orderDomainService;// 领域服务

    @Autowired
    OrderQueryApplication queryApp; //订单查询

    @Autowired
    private GoodsApplication goodsApp;//商品业务

    @Autowired
    private GoodsQueryApplication gQueryApp;//商品

    @Autowired
    DealerQuery dealerQuery; //商家
    @Autowired
    PostageModelQueryApplication postApp; //运费
    @Autowired
    GoodsClassifyQueryApplication goodsClassQuery;//商品分类
    @Autowired
    GoodsSpecialRepository goodsSpecialRsp;//特惠价
    
    @Autowired
    ExpressPlatformRepository expressPlatformRepository;

    /**
     * 提交订单
     *
     * @param cmd
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public OrderResult submitOrder(OrderAddCommand cmd) throws NegativeException {
    	// 提交的商品数据
    	List<GoodsDto> gdes = cmd.getGoodses();
        List<Map<String, Object>> goodses = null;
        /**skuId与数量的键值对, 用于锁定库存*/
        Map<String, Integer> skus = new HashMap<String, Integer>();
        /**提交上来的商品位置 与广告位之间的关系*/
        Map<Integer, SkuMediaBean> mresIds = new HashMap<Integer, SkuMediaBean>();
        /**提交上来的商品sku 与广告位之间的关系*/
        Map<String, SkuMediaBean> mediaResIds = new HashMap<String, SkuMediaBean>();
        /**营销ID*/
        List<String> marketIds = new ArrayList<String>();
        /**需要查询特惠价的sku集合*/
        List<String> specialSkus = new ArrayList<String>();
        
        if (gdes == null || gdes.size() < 1) { // 此条件表示从购物车拿东东
            // 获取购物车数据
            goodses = orderDomainService.getShopCarGoods(cmd.getUserId());
            if (goodses == null)
                throw new NegativeException(MCode.V_1, "购物车中的商品为空！");
            int c = 0;
            for (Map<String, Object> it : goodses) {
                String sku = it.get("skuId").toString();
                Integer nm = (Integer) it.get("num");
                skus.put(sku, nm);
                String mResId = (String) it.get("mediaResId");
                if (!StringUtils.isEmpty(mResId)) {
                	SkuMediaBean skb = new SkuMediaBean(sku, mResId);
                	mresIds.put(c, skb);
                    mediaResIds.put(sku, skb);
                }
                //skuMedia.put(sku, mResId);
                String marketId = (String) it.get("marketId");
                if (!StringUtils.isEmpty(marketId) && !marketIds.contains(marketId))
                    marketIds.add(marketId);
                c ++;
            }
        } else { //此处表示为app上传的参数处理
            int sz = gdes.size();
            for (int i = 0; i < sz; i++) {
            	GoodsDto o = gdes.get(i);
                String sku = o.getSkuId();
                int pnum = o.getPurNum();
                Integer oNum = skus.get(sku);
                if (oNum == null) {
                	skus.put(sku, pnum);
                }
                else
                	skus.put(sku, oNum + pnum);
                String marketId = o.getMarketingId();
                //int level = o.getMarketLevel();

                String mResId = o.getMresId();
                if (!StringUtils.isEmpty(mResId)) {
                	SkuMediaBean skb = new SkuMediaBean(sku, mResId);
                	mresIds.put(i, skb);
                    mediaResIds.put(sku, skb);
                }
                //skuMedia.put(sku, mResId);
                if (!StringUtils.isEmpty(marketId) && !marketIds.contains(marketId))
                    marketIds.add(marketId);
                
                if (o.getIsSpecial() == 1)
                	specialSkus.add(sku);
            }
        }
        
        try {// 锁定库存
            goodsApp.outInventory(skus);
        } catch (NegativeException e) {//不存在或库存不够
            throw new NegativeException(MCode.V_100, e.getMessage());
        }

        long orderTime = System.currentTimeMillis();
        
        // 获取商品详情
        List<GoodsDto> goodDtls = gQueryApp.getGoodsDtl(skus.keySet());
        // 特惠价map key:skuid, specialprice
        Map<String, GoodsSkuSpecial> specialPriceMap = (Map<String, GoodsSkuSpecial>)goodsSpecialRsp.getEffectiveGoodsSkuSpecial(specialSkus);
        
        checkNotSatisfy(specialPriceMap, specialSkus);
        LOGGER.info("特惠价比较开始");
        //判断app传入的特惠价和商品获取的特惠价是否相同
        checkSpecialPriceChange(specialPriceMap,gdes);
        LOGGER.info("特惠价比较结束");
        // 获取分类及费率
        getClassifyRate(goodDtls, mediaResIds);
        //若有媒体信息则需要查询媒体信息
        Map<String, Object> resMap = null;
        if (mediaResIds != null) {
            Iterator<SkuMediaBean> it = mediaResIds.values().iterator();
            List<SkuMediaBean> lsMd = new ArrayList<>();
            while (it.hasNext()) {
                lsMd.add(it.next());
            }
            resMap = orderDomainService.getMediaBdByResIds(lsMd, orderTime);
            resetMediaMap(resMap, mediaResIds);
        }
        
        // 先把数据填充好,商品信息，特惠价，再处理其他事情
        fillData(gdes, goodDtls, specialPriceMap);
        // 拆单 设置商品数量即按商家来拆分
        Set<String> idsSet = new HashSet<String>();
        Map<String, List<GoodsDto>> dealerOrderMap = splitOrder(gdes, idsSet);
        // 获取运费模板，计算运费
        //calFreight(skuBeans, list, cmd.getAddr().getCityCode());
        calFreight(dealerOrderMap, cmd.getAddr().getCityCode(), skus);
        
        List<MarketBean> mks = orderDomainService.getMarketingsByIds(marketIds, cmd.getUserId(), MarketBean[].class);
        // 计算营销活动优惠
        OrderMarketCalc.calMarkets(mks, gdes);
        //校验营销活动分摊后金额为负数的情况
        checkMarkets(gdes);
        //计算优惠券
        //1.获取满足条件的优惠券id
        String couponId = getCouponId(gdes);
        CouponBean couponBean = null;
        //2.根据优惠券id查询营销接口获取优惠券详情
        if(!StringUtils.isEmpty(couponId)){
        	 couponBean = orderDomainService.getCouponById(couponId,cmd.getCouponUserId(),cmd.getUserId(),CouponBean.class);
        	LOGGER.info("获取到营销模块的优惠券的信息-----"+couponBean==null?"":couponBean.toString());
        	//3.计算优惠券优惠后最后的金额
        	//3.1首先将满足此优惠券的sku放入列表中<sku,GoodsDto>
        	if(couponBean!=null)
        		OrderCouponCalc.calCoupon(gdes,couponBean);
        	//校验优惠券优惠金额位负数的情况
        	checkCoupon(gdes);
        }
        // 获取结算方式
        Map<String, Integer> dealerCount = getDealerWay(idsSet);
        List<DealerOrder> dealerOrders = trueSplit(dealerOrderMap, cmd, dealerCount,
                resMap);

        long goodsAmounts = 0;
        long freight = 0;
        long plateDiscount = 0;
        long dealerDiscount = 0;
        long couponDiscount = 0;
        // 计算主订单费用
        for (DealerOrder d : dealerOrders) {
            freight += d.getOrderFreight();
            goodsAmounts += d.getGoodsAmount();
            plateDiscount += (d.getPlateformDiscount() == null ? 0 : d.getPlateformDiscount());
            dealerDiscount += d.getDealerDiscount();
            couponDiscount += (d.getCouponDiscount() == null ? 0 : d.getCouponDiscount());
        }
        //结算金额
        long orderAmount = goodsAmounts - plateDiscount - dealerDiscount - couponDiscount;
        List<MarketUseBean> useList = new ArrayList<>();
        List<CouponUseBean> useCouponList = new ArrayList<>();
        MainOrder order = new MainOrder(cmd.getOrderId(), cmd.getAddr(), goodsAmounts, freight
                , plateDiscount, dealerDiscount, cmd.getUserId(), cmd.getNoted(), dealerOrders
                , getUsedCoupon(cmd.getOrderId(), cmd.getCouponUserId(), couponBean, gdes, useCouponList)
                , getUsedMarket(cmd.getOrderId(), gdes, useList), cmd.getLatitude(), cmd.getLongitude()
                , couponDiscount);
        // 组织保存(重新设置计算好的价格)
        AppOrdInfo appInfo = cmd.getInfo().toAppInfo();
        order.add(skus, cmd.getFrom(),dealerOrders,appInfo==null?"":appInfo.getSn(),orderAmount);
        orderRepository.save(order);
        // 锁定营销 , orderNo, 营销ID, userId -----
        if (!orderDomainService.lockMarketIds(useList, cmd.getCouponUserId(),cmd.getOrderId(), cmd.getUserId(),orderAmount,orderTime,JSONObject.toJSONString(useCouponList))) {
            throw new NegativeException(MCode.V_300, "活动已被用完！");
        }
        
        try {
        	if (appInfo != null)
        		orderRepository.saveAppInfo(appInfo);
        }
        catch (Exception e) {
        	LOGGER.info("===fanjc==save appinfo error." + e.getMessage());
        }
        
        return new OrderResult(cmd.getOrderId(), goodsAmounts, freight, plateDiscount, dealerDiscount);
    }

    /**
     * 提交订单 for wechat mini app
     *
     * @param cmd
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public OrderResult submitOrderMini(OrderAddCommand cmd) throws NegativeException {
    	// 提交的商品数据
    	List<GoodsDto> gdes = cmd.getGoodses();
        /**skuId与数量的键值对, 用于锁定库存*/
        Map<String, Integer> skus = new HashMap<String, Integer>();
        /**提交上来的商品位置 与广告位之间的关系*/
        Map<Integer, SkuMediaBean> mresIds = new HashMap<Integer, SkuMediaBean>();
        /**提交上来的商品sku 与广告位之间的关系*/
        Map<String, SkuMediaBean> mediaResIds = new HashMap<String, SkuMediaBean>();
        /**营销ID*/
        List<String> marketIds = new ArrayList<String>();
        /**需要查询特惠价的sku集合*/
        List<String> specialSkus = new ArrayList<String>();
        
        //此处表示为app上传的参数处理
        int sz = gdes.size();
        for (int i = 0; i < sz; i++) {
        	GoodsDto o = gdes.get(i);
            String sku = o.getSkuId();
            int pnum = o.getPurNum();
            Integer oNum = skus.get(sku);
            if (oNum == null) {
            	skus.put(sku, pnum);
            }
            else
            	skus.put(sku, oNum + pnum);
            String marketId = o.getMarketingId();

            String mResId = o.getMresId();
            if (!StringUtils.isEmpty(mResId)) {
            	SkuMediaBean skb = new SkuMediaBean(sku, mResId);
            	mresIds.put(i, skb);
                mediaResIds.put(sku, skb);
            }
            if (!StringUtils.isEmpty(marketId) && !marketIds.contains(marketId))
                marketIds.add(marketId);
            
            if (o.getIsSpecial() == 1)
            	specialSkus.add(sku);
        }
        
        try {// 锁定库存
            goodsApp.outInventory(skus);
        } catch (NegativeException e) {//不存在或库存不够
            throw new NegativeException(MCode.V_100, e.getMessage());
        }

        long orderTime = System.currentTimeMillis();
        
        // 获取商品详情
        List<GoodsDto> goodDtls = gQueryApp.getGoodsDtl(skus.keySet());
        //Map<String, GoodsSkuSpecial> specialPriceMap = (Map<String, GoodsSkuSpecial>)goodsSpecialRsp.getEffectiveGoodsSkuSpecial(specialSkus);
        
        //checkNotSatisfy(specialPriceMap, specialSkus);
        //LOGGER.info("特惠价比较开始");
        //判断app传入的特惠价和商品获取的特惠价是否相同
        //checkSpecialPriceChange(specialPriceMap,gdes);
        //LOGGER.info("特惠价比较结束");
        // 获取分类及费率
        getClassifyRate(goodDtls, mediaResIds);
        //若有媒体信息则需要查询媒体信息
        Map<String, Object> resMap = null;
        if (mediaResIds != null) {
            Iterator<SkuMediaBean> it = mediaResIds.values().iterator();
            List<SkuMediaBean> lsMd = new ArrayList<>();
            while (it.hasNext()) {
                lsMd.add(it.next());
            }
            resMap = orderDomainService.getMediaBdByResIds(lsMd, orderTime);
            resetMediaMap(resMap, mediaResIds);
        }
        
        // 先把数据填充好,商品信息，特惠价，再处理其他事情
        //fillData(gdes, goodDtls, specialPriceMap);
        fillData(gdes, goodDtls, null);
        // 拆单 设置商品数量即按商家来拆分
        Set<String> idsSet = new HashSet<String>();
        Map<String, List<GoodsDto>> dealerOrderMap = splitOrder(gdes, idsSet);
        // 获取运费模板，计算运费
        //calFreight(skuBeans, list, cmd.getAddr().getCityCode());
        calFreight(dealerOrderMap, cmd.getAddr().getCityCode(), skus);
        
        //List<MarketBean> mks = orderDomainService.getMarketingsByIds(marketIds, cmd.getUserId(), MarketBean[].class);
        // 计算营销活动优惠
        //OrderMarketCalc.calMarkets(mks, gdes);
        //校验营销活动分摊后金额为负数的情况
        checkMarkets(gdes);
        //计算优惠券
        //1.获取满足条件的优惠券id
        String couponId = getCouponId(gdes);
        CouponBean couponBean = null;
        //2.根据优惠券id查询营销接口获取优惠券详情
        if(!StringUtils.isEmpty(couponId)){
        	 couponBean = orderDomainService.getCouponById(couponId,cmd.getCouponUserId(),cmd.getUserId(),CouponBean.class);
        	LOGGER.info("获取到营销模块的优惠券的信息-----"+couponBean==null?"":couponBean.toString());
        	//3.计算优惠券优惠后最后的金额
        	//3.1首先将满足此优惠券的sku放入列表中<sku,GoodsDto>
        	if(couponBean!=null)
        		OrderCouponCalc.calCoupon(gdes,couponBean);
        	//校验优惠券优惠金额位负数的情况
        	checkCoupon(gdes);
        }
        // 获取结算方式
        Map<String, Integer> dealerCount = getDealerWay(idsSet);
        List<DealerOrder> dealerOrders = trueSplit(dealerOrderMap, cmd, dealerCount,
                resMap);

        long goodsAmounts = 0;
        long freight = 0;
        long plateDiscount = 0;
        long dealerDiscount = 0;
        long couponDiscount = 0;
        // 计算主订单费用
        for (DealerOrder d : dealerOrders) {
            freight += d.getOrderFreight();
            goodsAmounts += d.getGoodsAmount();
            plateDiscount += (d.getPlateformDiscount() == null ? 0 : d.getPlateformDiscount());
            dealerDiscount += d.getDealerDiscount();
            couponDiscount += (d.getCouponDiscount() == null ? 0 : d.getCouponDiscount());
        }
        //结算金额
        long orderAmount = goodsAmounts - plateDiscount - dealerDiscount - couponDiscount;
        List<MarketUseBean> useList = new ArrayList<>();
        List<CouponUseBean> useCouponList = new ArrayList<>();
        MainOrder order = new MainOrder(cmd.getOrderId(), cmd.getAddr(), goodsAmounts, freight
                , plateDiscount, dealerDiscount, cmd.getUserId(), cmd.getNoted(), dealerOrders
                , getUsedCoupon(cmd.getOrderId(), cmd.getCouponUserId(), couponBean, gdes, useCouponList)
                , getUsedMarket(cmd.getOrderId(), gdes, useList), cmd.getLatitude(), cmd.getLongitude()
                , couponDiscount);
        // 组织保存(重新设置计算好的价格)
        AppOrdInfo appInfo = cmd.getInfo().toAppInfo();
        order.add(skus, cmd.getFrom(),dealerOrders,appInfo==null?"":appInfo.getSn(),orderAmount);
        orderRepository.save(order);
        // 锁定营销 , orderNo, 营销ID, userId -----
        if (!orderDomainService.lockMarketIds(useList, cmd.getCouponUserId(),cmd.getOrderId(), cmd.getUserId(),orderAmount,orderTime,JSONObject.toJSONString(useCouponList))) {
            throw new NegativeException(MCode.V_300, "活动已被用完！");
        }
        
        try {
        	if (appInfo != null)
        		orderRepository.saveAppInfo(appInfo);
        }
        catch (Exception e) {
        	LOGGER.info("===fanjc==save appinfo error." + e.getMessage());
        }
        
        return new OrderResult(cmd.getOrderId(), goodsAmounts, freight, plateDiscount, dealerDiscount);
    }

    /**
     * 数据组装，获取此订单里面所有的Sku
     * @param gdes
     * @return
     * @throws NegativeException 
     */
//    private Map<String, GoodsDto> getCouponDto(List<GoodsDto> gdes) {
//    	Map<String, GoodsDto> resMap = new HashMap<String, GoodsDto>();
//    	for (GoodsDto goodsDto : gdes) {
//    		if(!StringUtils.isEmpty(goodsDto.getCouponId())){
//    			resMap.put(goodsDto.getSkuId(), goodsDto);
//    		}
//		}
//		return resMap;
//	}


	/**
	 * 过滤掉有媒体的商品信息
	 * @param dealerOrders
	 * @return
	 */
    private List<Map<String, String>> filterHasMediaGoods(List<DealerOrder> dealerOrders) {
    	List<Map<String, String>> resultList = new ArrayList<Map<String,String>>();
    	Map<String, String> perMedia = new HashMap<String, String>();
    	
		return null;
	}


	/**
     * 校验优惠券分摊后金额为负数的情况
     * @param gdes
   * @throws NegativeException 
     */
	  private void checkCoupon(List<GoodsDto> gdes) throws NegativeException {
			  for (GoodsDto goodsDto : gdes) {
				  if((goodsDto.getThePrice() * goodsDto.getPurNum() - goodsDto.getPlateformDiscount() -goodsDto.getCouponDiscount())<=0){
					  LOGGER.info("优惠券减的钱超过了商品原价");
					  throw new NegativeException(302, "下单失败");
					 }
			}
			
		}


/**
   * 校验营销活动分摊后金额为负数的情况
   * @param gdes
 * @throws NegativeException 
   */
	private void checkMarkets(List<GoodsDto> gdes) throws NegativeException {
		for (GoodsDto goodsDto : gdes) {
			if((goodsDto.getThePrice() * goodsDto.getPurNum() - goodsDto.getPlateformDiscount())<=0){
				 LOGGER.info("满减活动减的钱超过了商品原价");
				 throw new NegativeException(302, "下单失败");
			}
		}
		
	}


	/**
     * 获取订单中使用的优惠券信息
     * @param gdes
     * @return
     */
    private String getCouponId(List<GoodsDto> gdes) {
    	String couponId = "";
    	for (GoodsDto goodsDto : gdes) {
    		if(!StringUtils.isEmpty(goodsDto.getCouponId())){
    			couponId = goodsDto.getCouponId();
    			LOGGER.info("订单中使用的优惠券id是"+couponId);
    			return couponId;
    		}
		}
    	LOGGER.info("订单中所有商品没有传入使用的优惠券");
		return couponId;
	}


	/**
     * 判断从商品那边获取的特惠价和app传入的特惠价是否相等
     * @param specialPriceMap
     * @param gdes
     * @throws NegativeException
     */
    private void checkSpecialPriceChange(Map<String, GoodsSkuSpecial> specialPriceMap, List<GoodsDto> gdes) throws NegativeException {
        LOGGER.info("开始计算app传入特惠价和商品的sku的特惠价比较");
        if (null == specialPriceMap)
        	return;
        LOGGER.info("---"+specialPriceMap.keySet());
        Iterator<String> it = specialPriceMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String specialPrice = (specialPriceMap.get(key) == null ? String.valueOf(0):String.valueOf(specialPriceMap.get(key).specialPrice()));
            if(!StringUtils.isEmpty(specialPrice)){
            	LOGGER.info("商品那边的特惠价"+specialPrice);
                for(GoodsDto d : gdes){
                    if(!StringUtils.isEmpty(d.getAppSpecialPrice()) && d.getSkuId().equals(key)){
                    	LOGGER.info("app那边的特惠价"+d.getAppSpecialPrice());
                        if(!d.getAppSpecialPrice().equals(specialPrice)){
                            throw new NegativeException(MCode.V_101, "特惠价变更"+key);
                        }
                    }
                }
            }
        }        
    }

    /***
     * 订单拆分
     *
     * @param ls
     * @param ids
     * @return
     */
    private Map<String, List<GoodsDto>> splitOrder(List<GoodsDto> ls, Set<String> ids) {
        Map<String, List<GoodsDto>> rs = new HashMap<String, List<GoodsDto>>();
        List<GoodsDto> dtos = null;
        for (GoodsDto bean : ls) {
            dtos = rs.get(bean.getDealerId());
            ids.add(bean.getDealerId());
            if (dtos == null) {
                dtos = new ArrayList<GoodsDto>();
                rs.put(bean.getDealerId(), dtos);
            }
            dtos.add(bean);
        }
        return rs;
    }
    /***
     * 获取应用的营销信息
     * @param orderNo
     * @param beans
     * @return
     */
    /*List<SimpleMarketing> getUsedMarket(String orderNo, Map<String, GoodsReqBean> beans) {
        List<SimpleMarketing> result = null;
		
		Iterator<String> keys = beans.keySet().iterator();
		while(keys.hasNext()) {
			SimpleMarketInfo info = beans.get(keys.next()).toMarket();
			if (info != null) {
				if (result == null)
					result = new ArrayList<SimpleMarketing>();
				result.add(new SimpleMarketing(orderNo, info));
			}
		}
		
		return result;
	}*/

    /***
     * 获取应用的营销信息
     *
     * @param orderNo
     * @param beans
     * @return
     */
    private List<SimpleMarketing> getUsedMarket(String orderNo, List<GoodsDto> beans, List<MarketUseBean> useList) {
        List<SimpleMarketing> result = null;

        List<String> mids = new ArrayList<>();
        for (GoodsDto b : beans) {
            SimpleMarketInfo info = b.toMarketInfo();
            String mid = b.getMarketingId();
            if (mid != null) {
                useList.add(new MarketUseBean(b.getGoodsId(), mid, b.getSkuId(), b.getPurNum()));
            }
            if (info != null) {
                if (mids.contains(mid)) {
                    info = null;
                    continue;
                }
                mids.add(mid);
                if (result == null)
                    result = new ArrayList<SimpleMarketing>();
                result.add(new SimpleMarketing(orderNo, info));
            }
        }
        mids = null;
        return result;
    }

    /**
     * 获取应用的优惠券信息
     * @param orderId
     * @param couponUserId
     * @param couponBean 
     * @param gdes
     * @param useCouponList
     * @return
     */
    private List<SimpleCoupon> getUsedCoupon(String orderId,
			String couponUserId, CouponBean couponBean, List<GoodsDto> gdes,
			List<CouponUseBean> useCouponList) {
    	List<SimpleCoupon> result = new ArrayList<SimpleCoupon>();
    	for (GoodsDto b : gdes) {
    		if(!StringUtils.isEmpty(b.getCouponId())){
    			CouponInfo info = b.toCouponInfo(couponUserId,couponBean);
    			useCouponList.add(new CouponUseBean(b.getGoodsId(), b.getCouponId(), b.getSkuId(), b.getPurNum()));
    			if(useCouponList.size()>0){
    				result.add(new SimpleCoupon(orderId, info));
    				break;
    			}
    		}
    	}
    	LOGGER.info("use conponList"+result.toString());
		return result;
	}
    /***
     * 真实拆分订单到商家
     *
     * @param map
     * @param cmd
     * @param dc     商家结算方式
     * @param medias 媒体信息
     * @return
     */
    private List<DealerOrder> trueSplit(Map<String, List<GoodsDto>> map, OrderAddCommand cmd
            , Map<String, Integer> dc, Map<String, Object> medias) {
        List<DealerOrder> rs = new ArrayList<DealerOrder>();

        Iterator<String> it = map.keySet().iterator();
        int c = 0;
        while (it.hasNext()) {
            String dealerId = it.next();
            List<GoodsDto> dtos = map.get(dealerId);

            List<DealerOrderDtl> dtls = new ArrayList<DealerOrderDtl>();
            int freight = 0;
            long goodsAmount = 0;
            long plateDiscount = 0;
            long dealerDiscount = 0;
            long couponDiscount = 0;
            int termOfPayment = 0;
            if (null != dc && null != dc.get(dealerId)) {
                termOfPayment = dc.get(dealerId);
            }
            String dealerOrderId = cmd.getOrderId() + c;
            
            for (GoodsDto bean : dtos) {
                float num = bean.getPurNum();
                freight += bean.getFreight();
                goodsAmount += (int) (num * bean.getThePrice());
                plateDiscount += bean.getPlateformDiscount();
                couponDiscount += bean.getCouponDiscount();
                String resId = bean.getMresId();
                MediaResBean mb = null;
                if (!StringUtils.isEmpty(resId))
                	mb = medias != null ? (MediaResBean) medias.get(bean.getSkuId() + resId) : null;

                if (mb == null) {
                    dtls.add(new DealerOrderDtl(cmd.getOrderId(), dealerOrderId, cmd.getAddr(),
                            cmd.getInvoice(), null, null,
                            bean.toGoodsInfo(), 0, cmd.getNoted(), bean.toMarketInfo(), bean.getIndex()));
                } else {
                    mb.setMresId(resId);
                    dtls.add(new DealerOrderDtl(cmd.getOrderId(), dealerOrderId, cmd.getAddr(),
                            cmd.getInvoice(), null, mb.toMediaInfo(),
                            bean.toGoodsInfo(), 0, cmd.getNoted(), bean.toMarketInfo(), bean.getIndex()));
                }
            }
            rs.add(new DealerOrder(cmd.getOrderId(), dealerOrderId, dealerId, goodsAmount, freight,
                    plateDiscount, dealerDiscount, cmd.getNoted(), termOfPayment
                    , cmd.getAddr(), cmd.getInvoice(), dtls, couponDiscount));
            c++;
        }
        return rs;
    }

    /***
     * 取消订单(只能取消未支付的)
     *
     * @param cmd
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void cancelOrder(CancelOrderCmd cmd) throws NegativeException {

        MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
        if (order == null) {
        	throw new NegativeException(MCode.V_1, "无此订单！");
        }
        // 检查是否可取消,若不可取消抛出异常。
        if (order.cancel()) {
            // 可能是逻辑删除或是改成取消状态(子订单也要改)
            orderRepository.updateMainOrder(order);
            // 若订单中有优惠券则需要解锁
            orderDomainService.unlockCoupons(queryApp.getCouponsByOrderId(cmd.getOrderId()), "");
        } else {
            throw new NegativeException(MCode.V_1, "订单处于不可取消状态！");
        }
    }
    /***
     * 删除订单，只是设置一个标志位
     * @param cmd
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener
    public void delOrder(CancelOrderCmd cmd) throws NegativeException {

    	MainOrder order = orderRepository.getOrderById(cmd.getOrderId(), cmd.getUserId());
    	if (StringUtils.isEmpty(cmd.getDealerOrderId())) {
	        //MainOrder order = orderRepository.getOrderById(cmd.getOrderId(), cmd.getUserId());
	        // 检查是否可删除,若不可删除抛出异常。
	        if (order == null) {
	        	throw new NegativeException(MCode.V_1, "无此订单！");
	        }
	        
	        if (order.del()) {
	            orderRepository.updateMainOrder(order);
	        } else {
	            throw new NegativeException(MCode.V_1, "订单处于不可删除状态！");
	        }
    	}
    	else {
    		if (order.del()) {
    			orderRepository.updateMainOrder(order);
    		}
    		else {
	    		DealerOrder d = orderRepository.getDealerOrderById(cmd.getOrderId(), cmd.getUserId(), cmd.getDealerOrderId());
	    		if (d.del()) {
		            orderRepository.updateDealerOrder(d);
		        } else {
		            throw new NegativeException(MCode.V_1, "订单处于不可删除状态！");
		        }
    		}
    	}
    }

    /***
     * 计算运费
     * @param skus
     * @param ls
     * @param cityCode
     */
    private void calFreight(Map<String, GoodsReqBean> skus, List<GoodsDto> ls, String cityCode) throws NegativeException {
        LOGGER.info("==fanjc==计算运费.");
        Iterator<String> it = skus.keySet().iterator();
        List<String> skuIds = new ArrayList<String>();
        while (it.hasNext()) {
            skuIds.add(it.next());
        }

        Map<String, PostageModelRuleRepresentation> postMap = postApp.getGoodsPostageRule(skuIds, cityCode);

        for (GoodsDto bean : ls) {
            String skuId = bean.getSkuId();
            GoodsReqBean gdb = skus.get(skuId);
            bean.setPurNum(gdb.getPurNum());
            bean.setMarketingId(gdb.getMarketId());
            bean.setMarketLevel(gdb.getLevel());
            bean.setIsChange(gdb.getIsChange());
            //bean.setThreshold(gdb.get);
            calFrt(bean, postMap.get(skuId));
            // bean.setFreight(1000);
        }
    }
    
    /***
     * 计算运费 按商家单来计算
     * @param map 拆分后的商家订货单
     * @param cityCode 城市code
     * @param skus 带营销相关的东东
     */
    private void calFreight(Map<String, List<GoodsDto>> map, String cityCode, Map<String, Integer> skus) throws NegativeException {
        LOGGER.info("==fanjc==计算运费.");
        
        Iterator<String> it = map.keySet().iterator();
        
        while(it.hasNext()) {
        	List<GoodsDto> ls = map.get(it.next());
	        List<String> goodsIds = new ArrayList<String>();
	        
	        int sumNum = 0;
	        float sumWeight = 0;
	        Map<String, FreightCalBean> calMap = new HashMap<String, FreightCalBean>();// 按商品ID分
	        FreightCalBean fBean = null;
	        for (GoodsDto bean : ls) {
	        	String id = bean.getGoodsId();
	        	if (!goodsIds.contains(id)) { // 商品id合并
	        		goodsIds.add(id);
	        		fBean = new FreightCalBean();
	        		fBean.setBean(bean);
	        		calMap.put(id, fBean);
	        		sumNum = 0;
	        		sumWeight = 0;
	        	}
	        	
	        	//String skuId = bean.getSkuId();
	        	//Integer nm = skus.get(skuId);
	            //bean.setPurNum(nm);
	        	
	            /*bean.setMarketingId(gdb.getMarketId());
	            bean.setMarketLevel(gdb.getLevel());
	            bean.setIsChange(gdb.getIsChange());*/
	            
	            sumNum += bean.getPurNum();
	            sumWeight += bean.getPurNum() * bean.getWeight();
	            fBean.setNums(sumNum);
	            fBean.setWeight(sumWeight);
	        }
	
	        Map<String, PostageModelRuleRepresentation> postMap = postApp.getGoodsPostageRuleByGoodsId(goodsIds, cityCode);
	        
	        Iterator<String> goodsIt = calMap.keySet().iterator();
	        while(goodsIt.hasNext()) {
	        	String goodsId = goodsIt.next();
	            calFrt(calMap.get(goodsId), postMap.get(goodsId));
	        }
        }
    }

    /***
     * 计算单类物品运费
     * @param b
     * @param pb
     * @return
     */
    private void calFrt(FreightCalBean b, PostageModelRuleRepresentation pb) {
        if (pb == null) {
            b.getBean().setFreight(0);
            return;
        }

        if (pb.getChargeType() == 1) {//0:按重量,1:按件数
            long ft = pb.getFirstPostage();
            long ct = pb.getContinuedPostage();
            int fpt = pb.getFirstPiece();
            int cpt = pb.getContinuedPiece();
            int ss = b.getNums() - fpt; // 续件
            if (ss > 0) {
            	if (cpt == 0) {
            		b.getBean().setFreight(ft);
            	}
            	else 
            		b.getBean().setFreight(ft + (ss / cpt + (ss % cpt > 0 ? 1 : 0)) * ct);
            } else
            	b.getBean().setFreight(ft);
        } else {
            long ft = pb.getFirstPostage();
            long ct = pb.getContinuedPostage();
            float fpt = pb.getFirstWeight();
            float cpt = pb.getContinuedWeight();
            float ss = b.getWeight() - fpt; // 续件
            if (ss > 0) {
            	if (cpt == 0) {
            		b.getBean().setFreight(ft);
            	}
            	else {
	                int t = (int) (ss / cpt); //倍数
	                b.getBean().setFreight(ft + (t + (ss > (t * cpt) ? 1 : 0)) * ct);
            	}
            } else
            	b.getBean().setFreight(ft);
        }
    }
    /***
     * 计算单个物品运费
     *
     * @param b
     * @param pb
     * @return
     */
    private void calFrt(GoodsDto b, PostageModelRuleRepresentation pb) {
        if (pb == null) {
            b.setFreight(0);
            return;
        }

        if (pb.getChargeType() == 1) {//0:按重量,1:按件数
            long ft = pb.getFirstPostage();
            long ct = pb.getContinuedPostage();
            int fpt = pb.getFirstPiece();
            int cpt = pb.getContinuedPiece();
            int ss = b.getPurNum() - fpt; // 续件
            if (ss > 0) {
                b.setFreight(ft + (ss / cpt + (ss % cpt > 0 ? 1 : 0)) * ct);
            } else
                b.setFreight(ft);
        } else {
            long ft = pb.getFirstPostage();
            long ct = pb.getContinuedPostage();
            float fpt = pb.getFirstWeight();
            float cpt = pb.getContinuedWeight();
            float ss = b.getPurNum() * b.getWeight() - fpt; // 续件
            if (ss > 0) {
                int t = (int) (ss / cpt); //倍数
                b.setFreight(ft + (t + (ss > (t * cpt) ? 1 : 0)) * ct);
            } else
                b.setFreight(ft);
        }
    }

    /***
     * 获取商家结算方式
     *
     * @param ids
     * @return 商家对应的支付方式
     * @throws NegativeException
     */
    private Map<String, Integer> getDealerWay(Set<String> ids) throws NegativeException {
        Iterator<String> it = ids.iterator();
        StringBuilder dealerIds = new StringBuilder();
        int c = 0;
        while (it.hasNext()) {
            if (c > 0)
                dealerIds.append(",").append(it.next());
            else
                dealerIds.append(it.next());
            c++;
        }
        Map<String, Integer> rs = null;
        List<DealerBean> beans = dealerQuery.getDealers(dealerIds.toString());
        if (beans == null || beans.size() < 1)
            return rs;
        rs = new HashMap<String, Integer>();
        for (DealerBean b : beans) {
            rs.put(b.getDealerId(), b.getCountMode());
        }
        return rs;
    }

    /**
     * 订单支付
     * 不用
     * @param cmd
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public Object payOrder(PayOrderCmd cmd) {
        MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
        // 获取订单所用营销策略
        //List<String> mks = order.getMkIds();
        // 获取营销策略详情看是否有变化
        //orderDomainService.getMarketingsByIds(mks);
        // 若有变化则需要重新计算金额并更新

        // 请求发起支付
        order.getActual();
        // 调用restful接口;
        return null;
    }

    /***
     * 确认收货(只能取消未支付的)
     *
     * @param cmd
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void confirmSku(ConfirmSkuCmd cmd) throws NegativeException {

    	if (cmd.getSortNo() == 0) {
	    	if(orderRepository.checkSku(cmd.getSkuId(),cmd.getDealerOrderId())) {
	        	throw new NegativeException(MCode.V_1,"请升级客户端！");
	        }
    	}
    	
        DealerOrderDtl dtl = orderRepository.getDealerOrderDtlBySku(cmd.getDealerOrderId(), cmd.getSkuId(), cmd.getSortNo());
        if (dtl == null) {
        	throw new NegativeException(MCode.V_1, "无此商品！");
        }
        
        // 检查是否可确认收货
        boolean flag = dtl.confirmRev(cmd.getUserId());
        // 可能是逻辑删除或是改成取消状态(子订单也要改)
        DealerOrder order = orderRepository.getDealerOrderByNo(cmd.getDealerOrderId());
        if (order.checkAllRev(cmd.getSkuId(), dtl)) { // 同一个运单号一起确认收货
            if (!order.confirmRev())
                throw new NegativeException(MCode.V_1, "确认收货出错！");
            // 检查主订单下的所有商家订单是不是已经全部确认收货了
        }
        orderRepository.updateDealerOrder(order);
              
    }

    /***
     * 获取订单支付金额，用于支付时调用
     *
     * @param orderNo
     * @return
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public OrderMoney getOrderMoney(String orderNo, String userId) throws NegativeException {

        if (StringUtils.isEmpty(orderNo)) {
            throw new NegativeException(MCode.V_1, "订单号参数为空！");
        }

        if (StringUtils.isEmpty(userId)) {
            throw new NegativeException(MCode.V_1, "用户ID参数为空！");
        }

        OrderMoney result = new OrderMoney();
        MainOrder order = orderRepository.getOrderById(orderNo);
        // 判断是否符合营销规则，不符合需要重新计算，保存
        // 计算营销活动优惠  2017-10-31 已经锁定的优惠不需要再计算一次
		/*List<GoodsDto> goods = orderRepository.getOrderGoodsForCal(orderNo, GoodsDto.class);
		
		List<MarketBean> mks = orderDomainService.getMarketingsByIds(order.getMkIds(), userId, MarketBean.class);
		try {
			OrderMarketCalc.calMarkets(mks, goods);
		}
		catch (NegativeException e) {
			// 按原价计算
		}	*/
        // 设置计算后的金额 2017-10-31
        // setCalAmount(order, goods);
        order.calOrderMoney();
        result.setAmountOfMoney(order.getActual());
        result.setOrderNo(orderNo);
        orderRepository.updateMainOrder(order);
        return result;
    }

    /***
     * 订单支付成功
     *
     * @param cmd
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void orderPayed(OrderPayedCmd cmd) {
        MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
        if (order.paySuccess(cmd.getPayNo(), cmd.getPayWay(), cmd.getPayTime(), cmd.getUserId()))
            orderRepository.updateMainOrder(order);
        return;
    }

    /**
     * 设置计算后的金额,已不用
     */
    private void setCalAmount(MainOrder order, List<GoodsDto> goods) {
        for (GoodsDto g : goods) {
            order.setSkuMoney(g.getSkuId(), g.getPlateformDiscount(), g.getMarketingId());
        }
    }

    /***
     * 获取商品分类费率并设置,同时需要设置查询媒体时的父分类
     * 用于媒体
     * @param goodses
     */
    private void getClassifyRate(List<GoodsDto> goodses, Map<String, SkuMediaBean> resMap) {
        if (null == goodses || goodses.size() < 1)
            return;
        List<String> clsIds = new ArrayList<String>();
        List<String> clsIdMedia = new ArrayList<String>();
        for (GoodsDto d : goodses) {
            String s = d.getGoodsTypeId();
            if (!clsIds.contains(s)) {
                clsIds.add(s);
            }

            if (resMap != null) {
                SkuMediaBean skb = resMap.get(d.getSkuId());
                if (skb != null) {
                    clsIdMedia.add(s);
                    skb.setGoodsId(d.getGoodsId());
                    skb.setGoodsTypeCode(s);
                }
            }
        }

        Map<String, Float> map = (Map<String, Float>) goodsClassQuery.queryServiceRateByClassifyIds(clsIds);

        for (GoodsDto d : goodses) {
            String s = d.getGoodsTypeId();
            Float rate = map.get(s);
            if (rate != null)
                d.setRate(rate);
        }

        if (resMap != null && clsIdMedia.size() > 0) { // 获取父级分类
            Map<String, String> clsMap = (Map<String, String>) goodsClassQuery.getFirstClassifyByIds(clsIdMedia);
            if (clsMap == null)
                return;
            Iterator<SkuMediaBean> it = resMap.values().iterator();
            while (it.hasNext()) {
                SkuMediaBean skb = it.next();
                String cid = skb.getGoodsTypeCode();

                String pId = clsMap.get(cid);
                if (pId != null) {
                    skb.setGoodsTypeCode(pId);
                }
            }
        }
    }

    /***
     * 取消所有24小时还未支付的订单
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void cancelAllNotPayed(String userId) {
    	try {
    		int hour = 24;
    		try {
    			String val = GetDisconfDataGetter.getDisconfProperty("order.waitPay");
    			hour = Integer.parseInt(val);
    			if (hour < 1) {
    				hour = 1;
    			}
    		}
    		catch (Exception e) {    			
    		}
	    	List<MainOrder> mainOrders = orderRepository.getNotPayedOrders(hour);
	    	
	    	if (mainOrders == null || mainOrders.size() < 1)
	    		throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "没有满足条件的商家订单.");
	    	
	    	for (MainOrder m : mainOrders) {
	    		jobCancelOrder(m, userId);
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		LOGGER.error(e.getMessage());
    	}
    	return ;
    }
    
    /***
     * 执行取消操作
     * @param m
     * @param userId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class},propagation= Propagation.REQUIRES_NEW)
    private void jobCancelOrder(MainOrder m, String userId) {
    	m.jobCancel(userId);
		orderRepository.save(m);
    }
    /**
     * 查询所有的可以结束的订单，并更新其状态到可结算状态
     * @param userId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void updateAllOrderStatus(String userId) {
    	//int hour = 1;
		/*try {
			String val = GetDisconfDataGetter.getDisconfProperty("order.waitPay");
			hour = Integer.parseInt(val);
			if (hour < 1) {
				hour = 1;
			}
		}
		catch (Exception e) {    			
		}*/
    	
    	// 查询所有的可以结束的订单，并更新其状态到可结算状态
    	orderRepository.getSpecifiedOrderStatus();
    }
    /***
     * 查询可以结束的订单，并使之结束（需要判断是交易关闭还是交易完成）
     * @param userId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void judgeOrderSellAfter(String userId) {
    	List<String> orderIds = orderRepository.getMayCompleteOrderIds();
    	if (orderIds == null || orderIds.size() < 1) {
    		return;
    	}
    	//orderIds.add("20171123141428US");
    	
    	for (String a : orderIds) {
    		jobCompleteOrder(a, userId);
    	}    	
    }
    /***
     * 子订单完成或售后单完成，需要完成的订单
     * @param orderId
     * @param userId
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class},propagation= Propagation.REQUIRES_NEW)
    private void jobCompleteOrder(String orderId, String userId) {
    	boolean f = orderRepository.judgeOrderHasAfterSale(orderId);    
    	MainOrder m = orderRepository.getOrderById(orderId);
    	m.dealComplete(f);
    	orderRepository.save(m);
    }
    /***
     * 填充数据，属性及优惠价
     * @param userData 用户提交的商品数据
     * @param goodses
     * @param specialPrice
     */
    private void fillData(List<GoodsDto> userData, List<GoodsDto> goodses, Map<String, GoodsSkuSpecial> specialPrice) {
    	for (GoodsDto t : userData) {
    		
    		String sku = t.getSkuId();
    		
    		GoodsDto src = getGoodsBySkuId(sku, goodses);
    		if (src != null)
    			t.copyField(src);
    		if (t.getIsSpecial() == 1 && specialPrice != null && specialPrice.get(sku) != null) {
    			t.setSpecialPrice(specialPrice.get(sku).specialPrice());
    			Long sPrice = specialPrice.get(sku).supplyPrice();
    			if (sPrice != null)
    				t.setSupplyPrice(sPrice);
    		}
    		else // 若没有特惠价则不执行特惠价
    			t.setIsSpecial(0);
    	}
    }
    /***
     * 从给定的列表中找出sku相同的一个对象
     * @param skuId
     * @param goodses
     * @return
     */
    private GoodsDto getGoodsBySkuId(String skuId, List<GoodsDto> goodses) {
    	
    	if (StringUtils.isEmpty(skuId))
    		return null;
    	
    	for (GoodsDto g : goodses) {
    		if (skuId.equals(g.getSkuId()))
    			return g;
    	}
    	return null;
    }
    /***
     * 重新设置媒体使之符合需要的数据结构
     * @param resMap
     * @param mediaResIds
     */
    private void resetMediaMap(Map<String, Object> resMap, Map<String, SkuMediaBean> mediaResIds) {
    	if (resMap == null)
    		return;
    	Iterator<String> it = resMap.keySet().iterator();
    	List<String> keys = new ArrayList<String>();
    	
    	Map<String, Object> tmpMap = new HashMap<String, Object>();
    	while(it.hasNext()) {
    		String key = it.next();
    		Object obj = resMap.get(key);
    		keys.add(key);
    		SkuMediaBean skb = mediaResIds.get(key);
    		if (skb != null) {
    			tmpMap.put(key + skb.getMresId(), obj);
    		}
    	}
    	resMap.putAll(tmpMap);
    	for (String k : keys) {
    		resMap.remove(k);
    	}
    }
    /***
     * 检测看是否有不满足的特惠价商品
     */
    private void checkNotSatisfy(Map<String, GoodsSkuSpecial> specialPriceMap, List<String> skus) throws NegativeException {
    	if (null == skus || skus.size() < 1)
    		return;
    	
    	if (null == specialPriceMap) {
    		int sz = skus.size();
        	/*StringBuilder sb = new StringBuilder();
        	for(int i = 0 ; i < sz; i++) {
        		if (i>0)
        			sb.append(",");
        		sb.append(skus.get(i));
        	}*/
        	
        	if (sz > 0) {
        		throw new NegativeException(MCode.V_105, JSONObject.toJSONString(skus));
        	}
        	return;
    	}
    	
    	Iterator<String> it = specialPriceMap.keySet().iterator();
    	while (it.hasNext()) {
    		String key = it.next();
    		if (skus.contains(key)) {
    			skus.remove(key);
    		}
    	}
    	int sz = skus.size();
    	/*StringBuilder sb = new StringBuilder();
    	for(int i = 0 ; i < sz; i++) {
    		if (i>0)
    			sb.append(",");
    		sb.append(skus.get(i));
    	}*/
    	
    	if (sz > 0) {
    		throw new NegativeException(MCode.V_105, JSONObject.toJSONString(skus));
    	}
    }


    /**
     * 发送发货短信的application
     * @param cmd
     * @throws NegativeException 
     */
	public void sendOrderSMS(SendOrderSMSCommand cmd) throws NegativeException {
    	//首先根据用户id获取用户中心手机号
    	String userMobile  = orderDomainService.getUserMobileByUserId(cmd.getUserId());//下单人手机号
    	if(!StringUtils.isEmpty(userMobile)){
    		//然后根据发送短信接口发送短信（调用support中心的功能）
    		orderDomainService.sendOrderSMS(userMobile,cmd.getShopName());
    	}
	}


	/**
	 * 注册物流监听
	 * @param com
	 * @param nu
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void registExpress(String com, String nu,Integer shipType) throws NegativeException {
		ExpressPlatform ep = new ExpressPlatform();
		ep.save(com, nu, shipType);
		expressPlatformRepository.saveOrUpdate(ep);
		orderDomainService.registExpress(com,nu);
	}
	
	
}
