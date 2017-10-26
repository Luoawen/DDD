package cn.m2c.scm.application.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.order.data.bean.MarketBean;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.domain.NegativeException;

/***
 * 用于订单营销数据计算
 * @author fanjc
 * created date 2017年10月26日
 * copyrighted@m2c
 */
public class OrderMarketCalc {

	/***
	 * 计算校验营销活动
	 * @param mks
	 * @param goodsLs
	 */
	public static void calMarkets(List<MarketBean> mks, List<GoodsDto> goodsLs) throws NegativeException {
		if (mks == null)
			return;
		// 以marketId为key的商品
		Map<String, List<GoodsDto>> mapGoods = dividGoodsByMarketId(goodsLs);
		
		for(MarketBean mbean : mks) {
			calGoodsUseMarket(mbean, mapGoods.get(mbean.getFullCutId()));
		}
	}
	/***
	 * 按营销ID来分组商品
	 * @param goodsLs
	 * @return
	 */
	private static Map<String, List<GoodsDto>> dividGoodsByMarketId(List<GoodsDto> goodsLs) {
		Map<String, List<GoodsDto>> mapGoods = new HashMap<String, List<GoodsDto>>();
		for (GoodsDto a : goodsLs) {
			String mid = a.getMarketingId();
			if (!StringUtils.isEmpty(mid)) {
				List<GoodsDto> smap = mapGoods.get(mid);
				if (smap == null) {
					smap = new ArrayList<GoodsDto>();
					mapGoods.put(mid, smap);
				}
				smap.add(a);
			}
		}
		return mapGoods;
	}
	/***
	 * 计算某个营销规则
	 * @param bean 营销规则
	 * @param goodsLs 应用营销规则的商品
	 */
	private static void calGoodsUseMarket(MarketBean bean, List<GoodsDto> goodsLs) throws NegativeException{
		if (null == goodsLs || goodsLs.size() < 1)
			return;
		if (bean.getStatus() != 2) {
			// 取消应用market并设置优惠为0
			for (GoodsDto g : goodsLs) {
				g.setPlateformDiscount(0);
				g.setMarketingId(null);
			}
			return;
		}
		
		if (bean.getRemainNum() < 1) {
			// 抛出异常告知下不了单
			throw new NegativeException(MCode.V_300, "id为的" + bean.getFullCutId() + ",满足已用完!");
		}
		
		// 获取层级
		Integer level = goodsLs.get(0).getMarketLevel();
		
		isFullAndSet(bean, level, goodsLs);
	}
	/***
	 * 看是否满足，若满足则计算并设置值
	 * @param bean
	 * @param level 层级
	 * @param goodsLs
	 * @throws NegativeException
	 */
	private static void isFullAndSet(MarketBean bean, int level, List<GoodsDto> goodsLs) throws NegativeException {
		// 满减类型 1：减钱，2：打折，3：换购
		int t = bean.getFullCutType();
		//获取 层级对应的数量 (优惠金额，折扣，换购价等，与上面的类型对应)
		int as = bean.getLevelNum(level);
		// 获取门槛类型
		int threshold = bean.getThreshold();
		int type = bean.getThresholdType();
		double totalMoney = 0;
		int totalNum = 0;
		int changeMoney = 0;
		int changeNum = 0;
		for (GoodsDto d : goodsLs) {
			if (d.isChange() == 0) {
				totalNum += d.getPurNum();
				totalMoney += d.getDiscountPrice() * d.getPurNum();
			}
			else {
				// 判断下换购商品是否满足要求
				changeMoney += d.getChangePrice() * d.getPurNum();
				changeNum += d.getPurNum();
			}
		}
		
		if ((type == 1 && totalMoney >= threshold) || (type == 2 && totalNum >= threshold)) {
			if (t == 3) {
				
				if ((type == 1 && totalMoney < threshold * changeNum) || (type == 2 && totalNum < threshold * changeNum))
					throw new NegativeException(MCode.V_301, bean.getFullCutId());
				
				if(changeMoney < as * changeNum) { // 不满足换购条件
					throw new NegativeException(MCode.V_301, bean.getFullCutId());
				}
				
				for (GoodsDto d : goodsLs) {
					if (d.isChange() != 0) {
						d.setChangePrice(as);
						d.setPlateformDiscount((d.getDiscountPrice() - d.getChangePrice()) * d.getPurNum());
					}
				}
				
			}
			else // 满足其他条件需要做的计算
				calcItem(t, as, totalMoney, totalNum, changeMoney, goodsLs);
		}
		else { // 不满足则需要抛出异常
			throw new NegativeException(MCode.V_301, bean.getFullCutId());
		}
	}
	/***
	 * 
	 * @param fullType 满减类型 1：减钱，2：打折，3：换购
	 * @param fullNum 满减值
	 * @param totalMoney 总金额
	 * @param totalNum 总数量
	 * @param changeMoney 总换购价
	 * @param goodsLs 商品
	 */
	private static void calcItem(int fullType, int fullNum, double totalMoney, int totalNum, int changeMoney, List<GoodsDto> goodsLs) {
		for (GoodsDto d : goodsLs) {
			if (d.isChange() != 0) 
				continue;
			if (fullType == 1) {
				d.setPlateformDiscount((long)(fullNum * d.getDiscountPrice() * d.getPurNum()/totalMoney));
			}
			else if (fullType == 2) {
				d.setPlateformDiscount((long)(fullNum * d.getDiscountPrice() * d.getPurNum()/1000.0));
			}
		}
	}
}
