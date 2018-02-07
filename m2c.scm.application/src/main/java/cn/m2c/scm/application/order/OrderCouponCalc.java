package cn.m2c.scm.application.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m2c.scm.application.order.data.bean.CouponBean;
import cn.m2c.scm.application.order.data.bean.SimpleCoupon;
import cn.m2c.scm.application.order.data.bean.SkuNumBean;
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

	/**
	 * 计算售后优惠金额
	 * @param couponInfo
	 * @param totalSku
	 * @param skuId
	 * @param _sortNo
	 * @return
	 */
	public static long calcReturnMoney(SimpleCoupon couponInfo,
			List<SkuNumBean> totalSku, String skuId, int _sortNo) {
		List<SkuNumBean> couponSku = new ArrayList<SkuNumBean>();
		for (SkuNumBean couponbean : totalSku) {
			if (couponbean.getStatus() == 0) {
				couponbean.setDiscountMoney(0);
			}
			if(!StringUtils.isEmpty(couponbean.getCouponId())){
				couponSku.add(couponbean);
			}
		}
        long rtMoney = 0;
        // 根据marketInfo 来计算
        if (couponInfo == null || couponSku.size() < 1)
            return rtMoney;
        //营销形式，1：减钱，2：打折
        Integer a = couponInfo.getCouponForm();
        //门槛类型：1：金额，2：件数 3 无门槛
        Integer b = couponInfo.getThresholdType();
        //门槛
        long threshold = couponInfo.getThreshold();

        long total = 0;
        // 优惠金额或折扣
        Integer discount = couponInfo.getDiscount();
        for (SkuNumBean bean : couponSku) {
        	// sortNo == 0是为了兼容之前的数据
            //boolean bFlag = (skuId.equals(bean.getSkuId()));
            boolean bFlag = (skuId.equals(bean.getSkuId()) && (_sortNo == 0 || bean.getSortNo() == _sortNo));
            if (b == 1 && !bFlag && bean.getIsChange() == 0) {
                total += bean.getGoodsAmount()-bean.getDiscountMoney();
            } else if (b == 2 && !bFlag && bean.getIsChange() == 0) {
                total += bean.getNum();
            }
        }
        
        if (discount == null)
            return rtMoney;

        SkuNumBean tmp = null;
        if (total >= threshold || b == 3) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : couponSku) {

                //boolean bFlag = (skuId.equals(bean.getSkuId()));
                boolean bFlag = (skuId.equals(bean.getSkuId()) && (_sortNo == 0 || bean.getSortNo() == _sortNo));
                if (bFlag) {
                    tmp = bean;//tmp售后的商品
                    continue;
                }
                if (total == 0)
            		total = 1;
                switch (a) {
                    case 1:// 减钱
//                    	BigDecimal g = new BigDecimal((bean.getGoodsAmount()-bean.getDiscountMoney())* discount);
//                    	BigDecimal t = new BigDecimal(total);
//                        bean.setDiscountMoney(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                    case 2://打折
//                        BigDecimal m = new BigDecimal(bean.getGoodsAmount());
//        				BigDecimal p = new BigDecimal(1000 - discount);
//        				p = p.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
//        				bean.setDiscountMoney(m.multiply(p).longValue());
                        break;
                }
            }

            if (a == 2) {
            	long finalAmount = tmp.getGoodsAmount()-tmp.getDiscountMoney();
            	BigDecimal g = new BigDecimal(finalAmount);
            	if (total < 1 && (b == 1 && finalAmount < threshold)) // 只剩最后一个了，看下是不是是不是不满足
            		discount = 1000;
            	BigDecimal t = new BigDecimal(discount);
            	
            	BigDecimal s = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
            	BigDecimal m = new BigDecimal(1 - s.floatValue());
                //rtMoney = (long) (0.5 + tmp.getGoodsAmount() * (1 - discount / 1000.0));
            	rtMoney = g.multiply(m).longValue();
            }
        } else { // 不满足
        	couponInfo.setIsFull(false);
            for (SkuNumBean bean : couponSku) {

            	boolean bFlag = (skuId.equals(bean.getSkuId()) && (_sortNo == 0 || bean.getSortNo() == _sortNo));
                if (bFlag) {
                    tmp = bean;
                    continue;
                }
                switch (a) {
                    case 1:
//                        bean.setDiscountMoney(0);
                        break;
                    case 2://打折就不用计算
                    	BigDecimal g = new BigDecimal(bean.getGoodsAmount()-bean.getDiscountMoney());
                    	BigDecimal t = new BigDecimal(discount);
                    	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
                    	rtMoney += g.multiply(t.subtract(new BigDecimal(1)).abs()).longValue();
                        //rtMoney += (bean.getGoodsAmount() * (1 - discount / 1000.0));
                        break;
                    case 3:
                        break;
                }
            }
            if (a == 2) {
            	//BigDecimal g = new BigDecimal(tmp.getGoodsAmount()-tmp.getDiscountMoney());
            	long finalAmount = tmp.getGoodsAmount()-tmp.getDiscountMoney();
            	BigDecimal g = new BigDecimal(finalAmount);
            	if (total < 1 && (b == 1 && finalAmount < threshold)) // 只剩最后一个了，看下是不是是不是不满足
            		discount = 1000; // 1000 表示不满足也表示打0折
            	
            	BigDecimal t = new BigDecimal(discount);
            	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
            	rtMoney += g.multiply(t.subtract(new BigDecimal(1)).abs()).longValue();
                // rtMoney += (tmp.getGoodsAmount() * (1 - discount / 1000.0));
            } else {
            	if (tmp != null) {
	            	long finalAmount = tmp.getGoodsAmount()-tmp.getDiscountMoney();
	            	if (total < 1 && (b == 1 && finalAmount < threshold))
	            		discount = 0; // 0  表示少退0元
            	}
            	rtMoney = discount;
            }
        }
        return rtMoney;
    }

	/**
	 * 计算优惠券的分摊金额（退商品后）
	 * @param couponInfo
	 * @param unReturnGoods
	 */
	public static void calcReturnMoney(SimpleCoupon couponInfo,
			List<SkuNumBean> unReturnGoods) {

        long rtMoney = 0;
        // 根据marketInfo 来计算
        if (couponInfo == null)
            return ;
        
        List<SkuNumBean> couponSku = new ArrayList<SkuNumBean>();
		for (SkuNumBean couponbean : unReturnGoods) {
			if (couponbean.getStatus() == 0) {
				couponbean.setDiscountMoney(0);
				couponInfo.setIsFull(false);
			}
			if(!StringUtils.isEmpty(couponbean.getCouponId())){
				couponSku.add(couponbean);
			}
		}
        //营销形式，1：减钱，2：打折
        Integer a = couponInfo.getCouponForm();
        //门槛类型：1：金额，2：件数 3 无门槛
        Integer b = couponInfo.getThresholdType();
        //门槛
        long threshold = couponInfo.getThreshold();

        long total = 0;
        // 优惠金额或折扣
        Integer discount = couponInfo.getDiscount();
        for (SkuNumBean bean : couponSku) {
        	// sortNo == 0是为了兼容之前的数据
            //boolean bFlag = (skuId.equals(bean.getSkuId()));
            if (b == 1 &&  bean.getIsChange() == 0) {
                total += bean.getGoodsAmount()-bean.getDiscountMoney();
            } else if (b == 2 &&  bean.getIsChange() == 0) {
                total += bean.getNum();
            }
        }
        
        if (discount == null)
            return ;

        SkuNumBean tmp = null;
        if (total >= threshold || b == 3) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : couponSku) {

                //boolean bFlag = (skuId.equals(bean.getSkuId()));
                if (total == 0)
            		total = 1;
                switch (a) {
                    case 1:// 减钱
                    	BigDecimal g = new BigDecimal((bean.getGoodsAmount()-bean.getDiscountMoney())* discount);
                    	BigDecimal t = new BigDecimal(total);
                        bean.setCouponDiscount(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                    case 2://打折
                        BigDecimal m = new BigDecimal(bean.getGoodsAmount()-bean.getDiscountMoney());
        				BigDecimal p = new BigDecimal(1000 - discount);
        				p = p.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
        				bean.setCouponDiscount(m.multiply(p).longValue());
                        break;
                }
            }

        } else { // 不满足
        	couponInfo.setIsFull(false);
            for (SkuNumBean bean : couponSku) {

                switch (a) {
                    case 1:
                        bean.setCouponDiscount(0);
                        break;
                    case 2://打折就不用计算
                    	BigDecimal g = new BigDecimal(bean.getGoodsAmount()-bean.getDiscountMoney());
                    	BigDecimal t = new BigDecimal(discount);
                    	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
                    	bean.setCouponDiscount(0);
                        break;
                }
            }
		
        }
	}
}
