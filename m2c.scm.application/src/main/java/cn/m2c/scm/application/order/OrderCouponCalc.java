package cn.m2c.scm.application.order;

import java.util.List;

import cn.m2c.scm.application.order.data.bean.CouponBean;
import cn.m2c.scm.application.order.query.dto.GoodsDto;

/**
 * 用于计算优惠卷的类
 * @author yezp
 *
 */
public class OrderCouponCalc {

	/**
	 * 计算优惠券的入口
	 * @param gdes
	 * @param couponBean
	 */
	public static void calCoupon(List<GoodsDto> gdes, CouponBean couponBean) {
		if(couponBean!=null) {
			//平台
			if(couponBean.getCreatorType()==1){
				calDealerCoupon(gdes,couponBean);
			}else if(couponBean.getCreatorType()==2){
				calPlateformCoupon(gdes,couponBean);
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
		
	}

	/**
	 * 计算商家优惠券
	 * @param gdes
	 * @param couponBean
	 */
	private static void calDealerCoupon(List<GoodsDto> gdes,
			CouponBean couponBean) {
		
	}

	
	
}
