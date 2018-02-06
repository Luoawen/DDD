package cn.m2c.scm.application.order;

import cn.m2c.scm.application.order.data.bean.SimpleCoupon;
import cn.m2c.scm.application.order.data.bean.SimpleMarket;
import cn.m2c.scm.application.order.data.bean.SkuNumBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/***
 * 用于订单退款/退货退款营销数据计算
 *
 * @author fanjc
 *         created date 2017年10月26日
 *         copyrighted@m2c
 */
public class AfterOrderMarketCalc {

    /***
     * 计算售后需要退的某个商品的钱
     *
     * @param marketInfo
     * @param skuBeanLs
     * @param skuId      要退货或退款的skuId
     * @return 
     */
    public static void calcReturnMoney1(SimpleMarket marketInfo
            , List<SkuNumBean> skuBeanLs) {
        // 根据marketInfo 来计算
        if (marketInfo == null || skuBeanLs == null || skuBeanLs.size() < 1)
            return ;
        //营销形式，1：减钱，2：打折，3：换购
        Integer a = marketInfo.getMarketType();
        //门槛类型：1：金额，2：件数
        Integer b = marketInfo.getThresholdType();
        //门槛
        long threshold = marketInfo.getThreshold();

        long total = 0;
        // 优惠金额或折扣
        Integer discount = marketInfo.getDiscount();
        for (SkuNumBean bean : skuBeanLs) {
        	// sortNo == 0是为了兼容之前的数据
            if (b == 1 && bean.getIsChange() == 0) {
                total += bean.getGoodsAmount();
            } else if (b == 2 && bean.getIsChange() == 0) {
                total += bean.getNum();
            }
            else if ((b == 1 || b == 2) && bean.getIsChange() == 1 && a == 3) {
            	discount = (int)(bean.getGoodsAmount() - bean.getChangePrice() * bean.getNum());
            }            
        }
        
        if (discount == null)
            return ;

        if (total >= threshold) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : skuBeanLs) {

                if (total == 0)
            		total = 1;
                switch (a) {
                    case 1:// 减钱
                    	BigDecimal g = new BigDecimal(bean.getGoodsAmount()* discount);
                    	BigDecimal t = new BigDecimal(total);
                        bean.setDiscountMoney(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                    case 2://打折
                        BigDecimal m = new BigDecimal(bean.getGoodsAmount());
        				BigDecimal p = new BigDecimal(1000 - discount);
        				p = p.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
        				bean.setDiscountMoney(m.multiply(p).longValue());
                        break;
                    case 3:// 换购
                    	BigDecimal j = new BigDecimal(bean.getGoodsAmount()* discount);
                    	BigDecimal k = new BigDecimal(total);
                    	bean.setDiscountMoney(j.divide(k, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                }
            }

        } else { // 不满足
            marketInfo.setIsFull(false);
            for (SkuNumBean bean : skuBeanLs) {

                switch (a) {
                    case 1:
                        bean.setDiscountMoney(0);
                        break;
                    case 2://打折就不用计算
                    	//BigDecimal g = new BigDecimal(bean.getGoodsAmount());
                    	//BigDecimal t = new BigDecimal(discount);
                    	//t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
                    	//rtMoney += g.multiply(t.subtract(new BigDecimal(1)).abs()).longValue();
                    	bean.setDiscountMoney(0);
                        break;
                    case 3:
                        bean.setDiscountMoney(0);
                        break;
                }
            }
        }
        return ;
    }
    
    /***
     * 计算申请某个商品及数量售后 后 用户需要付的钱
     * @param marketInfo
     * @param skuBeanLs
     * @param skuId   要退货或退款的skuId
     * @return void
     */
    public static void calcReturnMoney2(SimpleMarket marketInfo
            , List<SkuNumBean> skuBeanLs, String skuId, int sortNo) {
        long rtMoney = 0;
        // 根据marketInfo 来计算
        if (marketInfo == null || skuBeanLs == null || skuBeanLs.size() < 1)
            return ;
        marketInfo.setIsFull(true);
        //营销形式，1：减钱，2：打折，3：换购
        Integer a = marketInfo.getMarketType();
        //门槛类型：1：金额，2：件数
        Integer b = marketInfo.getThresholdType();
        //门槛
        long threshold = marketInfo.getThreshold();

        long total = 0;
        // 优惠金额或折扣
        Integer discount = marketInfo.getDiscount();
        for (SkuNumBean bean : skuBeanLs) {
        	// sortNo == 0是为了兼容之前的数据
            boolean bFlag = (skuId.equals(bean.getSkuId()) && (sortNo == 0 || bean.getSortNo() == sortNo));
            if (b == 1 && !bFlag && bean.getIsChange() == 0) {
                total += bean.getGoodsAmount();
            } else if (b == 2 && !bFlag && bean.getIsChange() == 0) {
                total += bean.getNum();
            }
            else if ((b == 1 || b == 2) && !bFlag && bean.getIsChange() == 1 && a == 3) {
            	discount = (int)(bean.getGoodsAmount() - bean.getChangePrice() * bean.getNum());
            }            
        }
        
        if (discount == null) {
        	marketInfo.setIsFull(false);
            return ;
        }

        SkuNumBean tmp = null;
        if (total >= threshold) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : skuBeanLs) {

                boolean bFlag = (skuId.equals(bean.getSkuId()) && (sortNo == 0 || bean.getSortNo() == sortNo));
                if (bFlag) {
                    tmp = bean;//tmp售后的商品
                    continue;
                }
                if (total == 0)
            		total = 1;
                switch (a) {
                    case 1:// 减钱
                    	BigDecimal g = new BigDecimal(bean.getGoodsAmount()* discount);
                    	BigDecimal t = new BigDecimal(total);
                        bean.setDiscountMoney(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                    case 2://打折
                        BigDecimal m = new BigDecimal(bean.getGoodsAmount());
        				BigDecimal p = new BigDecimal(1000 - discount);
        				p = p.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
        				bean.setDiscountMoney(m.multiply(p).longValue());
                        break;
                    case 3:// 换购
                    	BigDecimal j = new BigDecimal(bean.getGoodsAmount()* discount);
                    	BigDecimal k = new BigDecimal(total);
                    	bean.setDiscountMoney(j.divide(k, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                }
            }

            if (a == 2) {
            	BigDecimal g = new BigDecimal(tmp.getGoodsAmount());
            	BigDecimal t = new BigDecimal(discount);
            	
            	BigDecimal s = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
            	BigDecimal m = new BigDecimal(1 - s.floatValue());
                //rtMoney = (long) (0.5 + tmp.getGoodsAmount() * (1 - discount / 1000.0));
            	rtMoney = g.multiply(m).longValue();
            	tmp.setDiscountMoney(rtMoney);
            }
            else if (a == 3 && tmp != null && tmp.getIsChange() == 1) {
            	rtMoney = tmp.getGoodsAmount() - (tmp.getChangePrice() * tmp.getNum());
            	marketInfo.setIsFull(false);
            }
        } else { // 不满足
            marketInfo.setIsFull(false);
            for (SkuNumBean bean : skuBeanLs) {

                boolean bFlag = (skuId.equals(bean.getSkuId()) && (sortNo == 0 || bean.getSortNo() == sortNo));
                if (bFlag) {
                    tmp = bean;
                    continue;
                }
                switch (a) {
                    case 1:
                        bean.setDiscountMoney(0);
                        break;
                    case 2://打折就不用计算
                    	BigDecimal g = new BigDecimal(bean.getGoodsAmount());
                    	BigDecimal t = new BigDecimal(discount);
                    	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
                    	rtMoney += g.multiply(t.subtract(new BigDecimal(1)).abs()).longValue();
                    	bean.setDiscountMoney(0);
                        break;
                    case 3:
                        bean.setDiscountMoney(0);
                        break;
                }
            }
            if (a == 2) {
            	BigDecimal g = new BigDecimal(tmp.getGoodsAmount());
            	BigDecimal t = new BigDecimal(discount);
            	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
            	rtMoney += g.multiply(t.subtract(new BigDecimal(1)).abs()).longValue();
            	tmp.setDiscountMoney(rtMoney);
            } else {
            	rtMoney = discount;
            	if (tmp != null)
            		tmp.setDiscountMoney(rtMoney);
            }
        }
        return ;
    }
    
    /**
	 * 计算售后优惠金额
	 * @param couponInfo
	 * @param totalSku
	 * @return
	 */
	public static void calcCouponReturnMoney1(SimpleCoupon couponInfo,
			List<SkuNumBean> totalSku) {
		List<SkuNumBean> couponSku = new ArrayList<SkuNumBean>();
		for (SkuNumBean couponbean : totalSku) {
			if (couponbean.getStatus() == 0) {
				couponbean.setDiscountMoney(0);
			}
			if(!StringUtils.isEmpty(couponbean.getCouponId())){
				couponSku.add(couponbean);
			}
		}
        // 根据couponInfo 来计算
        if (couponInfo == null || couponSku.size() < 1)
            return ;
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
            if (b == 1 && bean.getIsChange() == 0) {
                total += bean.getGoodsAmount()-bean.getDiscountMoney();
            } else if (b == 2 && bean.getIsChange() == 0) {
                total += bean.getNum();
            }
        }
        
        if (discount == null)
            return ;

        if (total >= threshold || b == 3) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : couponSku) {
                if (total == 0)
            		total = 1;
                switch (a) {
	                case 1:
	                	BigDecimal g = new BigDecimal((bean.getGoodsAmount()-bean.getDiscountMoney())* discount);
	                	BigDecimal t = new BigDecimal(total);
	                    bean.setCouponMoney(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
	                    break;
	                case 2://打折就不用计算
	                	BigDecimal m = new BigDecimal(bean.getGoodsAmount() - bean.getDiscountMoney());
	    				BigDecimal p = new BigDecimal(1000 - discount);
	    				p = p.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
	    				bean.setCouponMoney(m.multiply(p).longValue());
                        break;
                }
            }
        } else { // 不满足
        	couponInfo.setIsFull(false);
            for (SkuNumBean bean : couponSku) {

                switch (a) {
                    case 1:
                        bean.setCouponMoney(0);
                        break;
                    case 2://打折就不用计算
        				bean.setCouponMoney(0);
                        break;
                    case 3:
                        break;
                }
            }
        }
        return ;
    }
	
    /**
	 * 计算售后优惠金额
	 * @param couponInfo
	 * @param totalSku
	 * @param skuId
	 * @param _sortNo
	 * @return
	 */
	public static void calcCouponReturnMoney2(SimpleCoupon couponInfo,
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
        // 根据couponInfo 来计算
        if (couponInfo == null || couponSku.size() < 1) {
        	couponInfo.setIsFull(false);
        	return ;
        }
        couponInfo.setIsFull(true);
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
        
        if (discount == null) {
        	couponInfo.setIsFull(false);
        	return ;
        }

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
                    	BigDecimal g = new BigDecimal((bean.getGoodsAmount()-bean.getDiscountMoney())* discount);
                    	BigDecimal t = new BigDecimal(total);
                        bean.setCouponMoney(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());
                        break;
                    case 2://打折
                        BigDecimal m = new BigDecimal(bean.getGoodsAmount() - bean.getDiscountMoney());
        				BigDecimal p = new BigDecimal(1000 - discount);
        				p = p.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
        				bean.setCouponMoney(m.multiply(p).longValue());
                        break;
                }
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
                        bean.setCouponMoney(0);
                        break;
                    case 2://打折就不用计算
                    	bean.setCouponMoney(0);
                        break;
                    case 3:
                    	bean.setCouponMoney(0);
                        break;
                }
            }
        }
        return ;
    }
}
