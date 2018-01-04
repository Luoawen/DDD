package cn.m2c.scm.application.order;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.order.data.bean.MarketBean;
import cn.m2c.scm.application.order.data.bean.MarketLevelBean;
import cn.m2c.scm.application.order.data.bean.MarketRangeSuit;
import cn.m2c.scm.application.order.data.bean.MarketSku;
import cn.m2c.scm.application.order.data.bean.SimpleMarket;
import cn.m2c.scm.application.order.data.bean.SkuNumBean;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 用于订单营销数据计算
 *
 * @author fanjc
 *         created date 2017年10月26日
 *         copyrighted@m2c
 */
public class OrderMarketCalc {

    /***
     * 计算校验营销活动
     *
     * @param mks
     * @param goodsLs
     */
    public static void calMarkets(List<MarketBean> mks, List<GoodsDto> goodsLs) throws NegativeException {
        if (mks == null)
            return;
        // 以marketId为key的商品
        Map<String, List<GoodsDto>> mapGoods = dividGoodsByMarketId(goodsLs);

        for (MarketBean mbean : mks) {
            calGoodsUseMarket(mbean, mapGoods.get(mbean.getFullCutId()));
        }
    }

    /***
     * 按营销ID来分组商品
     *
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
     *
     * @param bean    营销规则
     * @param goodsLs 应用营销规则的商品
     */
    private static void calGoodsUseMarket(MarketBean bean, List<GoodsDto> goodsLs) throws NegativeException {
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

        if (bean.getRemainNum() < 1 && bean.getTotalNum() > 0) {
            // 抛出异常告知下不了单
            throw new NegativeException(MCode.V_300, "id为的" + bean.getFullCutId() + ",平台活动次数已用完!");
        }

        if (bean.getHasUsedNum() < 1) {
            // 抛出异常告知下不了单
            throw new NegativeException(MCode.V_303, "您的活动次数已用完！");
        }

        // 获取层级
        Integer level = goodsLs.get(0).getMarketLevel();
        // 根据层级计算满减
        isFullAndSet(bean, level, goodsLs);
    }

    /***
     * 看是否满足，若满足则计算并设置值
     *
     * @param bean
     * @param level   层级
     * @param goodsLs
     * @throws NegativeException
     */
    private static void isFullAndSet(MarketBean bean, int level, List<GoodsDto> goodsLs) throws NegativeException {
        // 满减类型 1：减钱，2：打折，3：换购
        int cutType = bean.getFullCutType();
        //获取 层级对应的数量 (优惠金额，折扣，换购价等，与上面的类型对应)
        int as = bean.getLevelNum(level);
        // 获取门槛
        long threshold = bean.getThreshold();
        // 获取门槛类型
        int type = bean.getThresholdType();
        double totalMoney = 0;
        int totalNum = 0;
        long changeMoney = 0;
        int changeNum = 0;
        for (GoodsDto d : goodsLs) {
            if (d.isChange() == 0) {
                // 增加判断数量要求, 若不满足则需要按原价执行
                if (judgeOk(bean, d)) {
                    totalNum += d.getPurNum();
                    totalMoney += d.getThePrice() * d.getPurNum();
                }
            } else {
                // 判断换购下商品是否满足要求
                //changeMoney += d.getChangePrice() * d.getPurNum();
            	if (judgeChange(bean, d)) {
            		d.setChangePrice(as);
	            	changeMoney += (d.getThePrice() - as) * d.getPurNum();
	                changeNum += d.getPurNum();
            	}
            }
            d.setMarketType(bean.getFullCutType());
        }
        
        if ((type == 1 && totalMoney >= threshold) || (type == 2 && totalNum >= threshold)) {
            if (cutType == 3) {
            	calcChangeOld(type, totalMoney, threshold, changeNum, totalNum, goodsLs, as, bean);
            	//calcChange(type, totalMoney, threshold, changeNum, totalNum, goodsLs, as, bean, changeMoney);
            } else {// 满足其他条件需要做的计算
                calcItem(cutType, as, totalMoney, totalNum, changeMoney, goodsLs,
                        type, threshold, bean.getFullCutName(), bean.getCostList());
            }
        } else { // 不满足则需要抛出异常
            throw new NegativeException(MCode.V_301, bean.getFullCutId());
        }
    }
    /***
     * 计算换购
     * @param type 门槛类型
     * @param totalMoney 分母
     * @param threshold 门槛
     * @param changeNum 换购数量
     * @param totalNum 总数量
     * @param goodsLs 数据
     * @param as 层级对应的数量或金额
     * @param bean 营销实体
     */
    private static void calcChangeOld(int type, double totalMoney, long threshold, int changeNum, int totalNum, 
    		List<GoodsDto> goodsLs, int as, MarketBean bean) throws NegativeException {
    	
    	if ((type == 1 && totalMoney < threshold * changeNum) || (type == 2 && totalNum < threshold * changeNum))
            throw new NegativeException(MCode.V_301, bean.getFullCutId());
        //changeMoney  换货总共优惠的金额   as换货价  changeNum换货数量
        int last = goodsLs.size() - 1;
        if (goodsLs.get(last).isChange() == 1)
        	last = last - 1;
    	//long subSum = 0;//前多少个的处理
        for (GoodsDto d : goodsLs) {
            if (d.isChange() == 1) {
                d.setChangePrice(as);
                //d.setPlateformDiscount((d.getThePrice() - d.getChangePrice()) * d.getPurNum());
                d.setPlateformDiscount((d.getThePrice() - as) * d.getPurNum());                        
            }
            /*else {
            	BigDecimal g = new BigDecimal(changeMoney * d.getThePrice() * d.getPurNum());
            	BigDecimal t1 = new BigDecimal(totalMoney);
            	t1 = g.divide(t1, 3, BigDecimal.ROUND_HALF_DOWN);
            	
            	//long a = (long) (0.5 + changeMoney * d.getThePrice() * d.getPurNum() / totalMoney);
            	long a = t1.longValue();
            	if (last > 0 && d == goodsLs.get(last)) {
            		d.setPlateformDiscount(changeMoney - subSum);
            	}
            	else {
            		d.setPlateformDiscount(a);
            		subSum += a;
            	}                     	
            }*/
            d.setMarketType(bean.getFullCutType());
        	d.setThreshold(threshold);
        	d.setThresholdType(type);
            d.setSharePercent(bean.getCostList());
        }
    }
    
    /***
     * 计算换购 新
     * @param type 门槛类型
     * @param totalMoney 分母
     * @param threshold 门槛
     * @param changeNum 换购数量
     * @param totalNum 总数量
     * @param goodsLs 数据
     * @param as 层级对应的数量或金额
     * @param bean 营销实体
     */
    private static void calcChange(int type, double totalMoney, long threshold, int changeNum, int totalNum, 
    		List<GoodsDto> goodsLs, int as, MarketBean bean, long changeMoney) throws NegativeException {
    	
    	if ((type == 1 && totalMoney < threshold * changeNum) || (type == 2 && totalNum < threshold * changeNum))
            throw new NegativeException(MCode.V_301, bean.getFullCutId());
        //changeMoney  换货总共优惠的金额   as换货价  changeNum换货数量
        int last = goodsLs.size() - 1;
        if (goodsLs.get(last).isChange() == 1)
        	last = last - 1;
    	long subSum = 0;//前多少个的处理
        for (GoodsDto d : goodsLs) {
            if (d.isChange() == 1) {
                d.setChangePrice(as);
                //d.setPlateformDiscount((d.getThePrice() - d.getChangePrice()) * d.getPurNum());
                //d.setPlateformDiscount((d.getThePrice() - as) * d.getPurNum());                        
            }
            else {
            	BigDecimal g = new BigDecimal(changeMoney * d.getThePrice() * d.getPurNum());
            	BigDecimal t1 = new BigDecimal(totalMoney);
            	// 所占比重
            	t1 = g.divide(t1, 3, BigDecimal.ROUND_HALF_DOWN);
            	
            	long a = t1.longValue();
            	if (last > 0 && d == goodsLs.get(last)) {
            		d.setPlateformDiscount(changeMoney - subSum);
            	}
            	else {
            		d.setPlateformDiscount(a);
            		subSum += a;
            	}                     	
            }
            d.setMarketType(bean.getFullCutType());
        	d.setThreshold(threshold);
        	d.setThresholdType(type);
            d.setSharePercent(bean.getCostList());
        }
    }

    /***
     * 计算是否真的满足
     *
     * @param fullType      满减类型 1：减钱，2：打折，3：换购
     * @param fullNum       满减值
     * @param totalMoney    总金额
     * @param totalNum      总数量
     * @param changeMoney   总换购价
     * @param goodsLs       商品
     * @param thresholdType
     * @param threshold
     * @param marketName
     * @param sharePercent
     */
    private static void calcItem(int fullType, int fullNum, double totalMoney, int totalNum, long changeMoney, List<GoodsDto> goodsLs
            , int thresholdType, long threshold, String marketName, String sharePercent) {
    	int last = goodsLs.size() - 1;
    	long subSum = 0;//前多少个的处理
        for (GoodsDto d : goodsLs) {
            if (d.isChange() != 0)
                continue;
            d.setThreshold(threshold);
            d.setThresholdType(thresholdType);
            d.setMarketName(marketName);
            d.setSharePercent(sharePercent);
            d.setDiscount(fullNum);// 若是折扣就已经乘了100了，所以需要除1000
            if (fullType == 1) {
            	BigDecimal g = new BigDecimal(fullNum * d.getThePrice() * d.getPurNum());
            	BigDecimal t = new BigDecimal(totalMoney);
            	t = g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN);
            	
            	//long a = (long) (0.5 + fullNum * d.getThePrice() * d.getPurNum() / totalMoney);
            	long a = t.longValue();
            	if (d == goodsLs.get(last)) {
            		d.setPlateformDiscount(fullNum - subSum);
            	}
            	else {
            		d.setPlateformDiscount(a);
            		subSum += a;
            	}
            } else if (fullType == 2) { // 除以1000是因为前面已经乘以100了，因存的是8表示8折优惠
            	BigDecimal g = new BigDecimal(d.getThePrice() * d.getPurNum());
            	BigDecimal t = new BigDecimal(1000 - fullNum);
            	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
            	long rtMoney = g.multiply(t).longValue();
                //d.setPlateformDiscount((long) (0.5 + d.getThePrice() * d.getPurNum() * (1000 - fullNum)/ 1000.0));
            	d.setPlateformDiscount(rtMoney);
            }
        }
    }

    /***
     * 判断是否满足条件
     *
     * @param bean
     * @param d
     * @return true满足
     */
    private static boolean judgeOk(MarketBean bean, GoodsDto d) {
        if (bean.getRangeType() != 2)
            return true;

        List<MarketRangeSuit> suits = bean.getSuitableRangeList();
        if (suits == null) {
            d.setMarketingId(null);
            return false;
        }
        for (MarketRangeSuit s : suits) {
            if (s.getSkuFlag() == 0)
                return true;
            if (d.getGoodsId().equals(s.getId()) && isSkuOk(d, s.getSkuList())) {
                return true;
            }
        }
        return false;
    }
    
    /***
     * 判断换购商品是否满足条件
     *
     * @param bean
     * @param d
     * @return true满足
     */
    private static boolean judgeChange(MarketBean bean, GoodsDto d) {
        if (bean.getRangeType() != 2)
            return true;

        List<MarketLevelBean> suits = bean.getItemList();
        if (suits == null) {
            d.setMarketingId(null);
            return false;
        }
        for (MarketLevelBean s : suits) {
            if (s.getGoodsIds() == null)
                return true;
            if (s.getGoodsIds().indexOf(d.getGoodsId()) != -1) {
                return true;
            }
        }
        return false;
    }

    /***
     * 检查指定的SKU是否可以
     *
     * @param d
     * @param skus
     * @return
     */
    private static boolean isSkuOk(GoodsDto d, List<MarketSku> skus) {

        for (MarketSku s : skus) {
            if (d.getSkuId().equals(s.getSkuId()) && s.getSkuRemainNum() >= d.getPurNum()) {
                return true;
            }
        }

        return false;
    }

    /***
     * 计算售后需要退的某个商品的钱
     *
     * @param marketInfo
     * @param skuBeanLs
     * @param skuId      要退货或退款的skuId
     * @return 返回本商品优惠的金额
     */
    public static long calcReturnMoney(SimpleMarket marketInfo
            , List<SkuNumBean> skuBeanLs, String skuId, int sortNo) {
        long rtMoney = 0;
        // 根据marketInfo 来计算
        if (marketInfo == null || skuBeanLs == null || skuBeanLs.size() < 1)
            return rtMoney;
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
        
        if (discount == null)
            return rtMoney;

        SkuNumBean tmp = null;
        if (total >= threshold) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : skuBeanLs) {

                boolean bFlag = (skuId.equals(bean.getSkuId()) && (sortNo == 0 || bean.getSortNo() == sortNo));
                if (bFlag) {
                    tmp = bean;
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
                    case 2://打折就不用计算
                        // bean.setDiscountMoney((long)(bean.getGoodsAmount() * discount / 1000.0));
                        break;
                    case 3:// 换购
                    	/*g = new BigDecimal(bean.getGoodsAmount()* discount);
                    	t = new BigDecimal(total);
                        //bean.setDiscountMoney((long) (bean.getGoodsAmount() * discount / (total + 0.0)));
                    	bean.setDiscountMoney(g.divide(t, 3, BigDecimal.ROUND_HALF_DOWN).longValue());*/
                    	bean.setDiscountMoney(0);
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
                        //rtMoney += (bean.getGoodsAmount() * (1 - discount / 1000.0));
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
                // rtMoney += (tmp.getGoodsAmount() * (1 - discount / 1000.0));
            } else
                rtMoney = discount;
        }
        return rtMoney;
    }
    
    public static void main(String[] args) {
    	BigDecimal g = new BigDecimal(22500);
    	BigDecimal t = new BigDecimal(1000 - 925);
    	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_UP);
    	long rtMoney = g.multiply(t).longValue();
    	System.out.println(rtMoney);
    	
    	g = new BigDecimal(22500);
    	t = new BigDecimal(925);
    	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
    	rtMoney = g.multiply(t).longValue();
    	System.out.println(rtMoney);
    	//System.out.println(22500 * (1000 - 925)/ 1000.0);
    }
}
