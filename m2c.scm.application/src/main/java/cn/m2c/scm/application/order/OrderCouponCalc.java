package cn.m2c.scm.application.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m2c.scm.application.order.data.bean.CouponBean;
import cn.m2c.scm.application.order.query.dto.GoodsDto;

/**
 * 用于计算优惠卷的类
 * @author yezp
 *
 */
public class OrderCouponCalc {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCouponCalc.class);
	/**
	 * 计算优惠券的入口
	 * @param gdes
	 * @param couponBean
	 */
	public static void calCoupon(List<GoodsDto> gdes, CouponBean couponBean) {
		if(couponBean!=null) {
			//平台
			if(couponBean.getCreatorType()==1){
				calPlateformCoupon(gdes,couponBean);
			}else if(couponBean.getCreatorType()==2){//商家
				calDealerCoupon(gdes,couponBean);
			}
		}
	}

	/**
	 * 计算平台优惠券
	 * @param gdes
	 * @param couponBean
	 */
	private static void calPlateformCoupon(List<GoodsDto> gdes,
			CouponBean couponBean) {
		List<GoodsDto> couponsGoods = new ArrayList<GoodsDto>();
		//查询所有优惠券满足的商品sku
		for (GoodsDto goodsDto : gdes) {
			if(!StringUtils.isEmpty(goodsDto.getCouponId())){
				couponsGoods.add(goodsDto);
			}
		}
		if(couponBean.getThresholdType()==1){
			//金额门槛
			calThresholdByMoney(couponsGoods,couponBean);
		}else if(couponBean.getThresholdType()==2){
			//件数
			calThresholdByCout(couponsGoods,couponBean);
		}else{
			//无门槛
			calWithoutThreshold(couponsGoods,couponBean);
		}
	}

	
	/**
	 * 计算商家优惠券
	 * @param gdes
	 * @param couponBean
	 */
	private static void calDealerCoupon(List<GoodsDto> gdes,
			CouponBean couponBean) {
		List<GoodsDto> dealerGoodsDto = new ArrayList<GoodsDto>();//满足使用此商家优惠券的
		for (GoodsDto goodsDto : gdes) {
			if(goodsDto.getDealerId().equals(couponBean.getDealerId())){
				dealerGoodsDto.add(goodsDto);
			}
		}
		//判断门槛类型
		if(couponBean.getThresholdType()==1){
			//金额门槛
			calThresholdByMoney(dealerGoodsDto,couponBean);
		}else if(couponBean.getThresholdType()==2){
			//件数门槛
			calThresholdByCout(dealerGoodsDto,couponBean);
		}else{
			//无门槛
			calWithoutThreshold(dealerGoodsDto,couponBean);
		}
		
	}

	/**
	 * 平台计算无门槛
	 * @param couponsGoods 满足此优惠券的商品信息
	 * @param couponBean 
	 */
	
	private static void calWithoutThreshold(List<GoodsDto> couponsGoods, CouponBean couponBean) {
		Long sum = 0L;//去掉营销活动后优惠券的总金额
		for (GoodsDto goodsDto : couponsGoods) {
			 sum +=  (goodsDto.getThePrice() * goodsDto.getPurNum() - goodsDto.getPlateformDiscount());
		}
		countCouponDiscount(couponsGoods,couponBean,sum);
	}

	/**
	 * 平台计算件数门槛
	 * @param couponsGoods
	 * @param couponBean 
	 */
	private static void calThresholdByCout(List<GoodsDto> couponsGoods, CouponBean couponBean) {
		Integer countNum = 0;//件数
		Long sum = 0L;//去掉营销活动后优惠券的总金额
		for (GoodsDto goodsDto : couponsGoods) {
			countNum +=  goodsDto.getPurNum();
			sum +=  (goodsDto.getThePrice() * goodsDto.getPurNum() - goodsDto.getPlateformDiscount());
		}
		if(countNum>=couponBean.getThreshold()){
			countCouponDiscount(couponsGoods,couponBean,sum);
		}
	}

	/**
	 * 平台计算金额门槛
	 * @param couponsGoods
	 * @param couponBean 
	 */
	private static void calThresholdByMoney(List<GoodsDto> couponsGoods, CouponBean couponBean) {
		Long sum = 0L;//去掉营销活动后优惠券的总金额
		for (GoodsDto goodsDto : couponsGoods) {
			 sum +=  (goodsDto.getThePrice() * goodsDto.getPurNum() - goodsDto.getPlateformDiscount());
		}
		if(sum>=couponBean.getThreshold()){//满足门槛
			countCouponDiscount(couponsGoods,couponBean,sum);
			
		}
	}

	/**
	 * 减钱打折的最后计算每个商品的优惠金额
	 * @param couponsGoods
	 * @param couponBean
	 * @param sum
	 */
	private static void countCouponDiscount(List<GoodsDto> couponsGoods,
			CouponBean couponBean, Long sum) {
		// TODO Auto-generated method stub
		for (GoodsDto dto : couponsGoods) {
			if(couponBean.getCouponForm()==1){//减钱
				LOGGER.info("平台计算金额门槛打折信息是"+couponBean.getDiscount());
				BigDecimal g = new BigDecimal((dto.getThePrice() * dto.getPurNum()  - dto.getPlateformDiscount()) * couponBean.getDiscountNum());
				BigDecimal t1 = new BigDecimal(sum);
				t1 = g.divide(t1, 3, BigDecimal.ROUND_HALF_DOWN);
				dto.setCouponDiscount(t1.longValue());
			}else if(couponBean.getCouponForm()==2) {//打折
				long discontNum  =  couponBean.getDiscountNum();//将打折信息转为  折扣（8.5） * 100
				LOGGER.info("平台计算金额门槛打折信息是"+discontNum);
				BigDecimal g = new BigDecimal(dto.getThePrice() * dto.getPurNum() - dto.getPlateformDiscount());
				BigDecimal t = new BigDecimal(1000 - discontNum);
				t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
				long couponDiscount = g.multiply(t).longValue();
				dto.setCouponDiscount(couponDiscount);
			}
		}
	}

}
