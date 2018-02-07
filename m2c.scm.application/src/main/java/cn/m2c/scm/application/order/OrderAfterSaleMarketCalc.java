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
public class OrderAfterSaleMarketCalc {

    /***
     * 计算校验营销活动
     *
     * @param mks
     * @param goodsLs
     */
    public static void calMarkets(SimpleMarket marketInfo, List<SkuNumBean> goodsLs) throws NegativeException {
        if (marketInfo == null)
            return;
        // 以marketId为key的商品
        Map<String, List<SkuNumBean>> mapGoods = dividGoodsByMarketId(goodsLs);

       calGoodsUseMarket(marketInfo, mapGoods.get(marketInfo.getMarketingId()));
    }

    /***
     * 按营销ID来分组商品
     *
     * @param goodsLs
     * @return
     */
    private static Map<String, List<SkuNumBean>> dividGoodsByMarketId(List<SkuNumBean> goodsLs) {
        Map<String, List<SkuNumBean>> mapGoods = new HashMap<String, List<SkuNumBean>>();
        for (SkuNumBean a : goodsLs) {
            String mid = a.getMarketId();
            if (!StringUtils.isEmpty(mid)) {
                List<SkuNumBean> smap = mapGoods.get(mid);
                if (smap == null) {
                    smap = new ArrayList<SkuNumBean>();
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
    private static void calGoodsUseMarket(SimpleMarket bean, List<SkuNumBean> goodsLs) throws NegativeException {
        if (null == goodsLs || goodsLs.size() < 1)
            return;
        // 获取层级
//        Integer level = goodsLs.get(0).getMarketLevel();
        // 根据层级计算满减
    }

    

    /***
     * 重新设置每个skud的分摊
     *
     * @param marketInfo  满减活动
     * @param skuBeanLs 除去申请售后的商品sku
     * @param skuId      要退货或退款的skuId
     * @return 
     */
    public static void calcMarketReturnMoney(SimpleMarket marketInfo
            , List<SkuNumBean> skuBeanLs) {
    	List<SkuNumBean> marketSku = new ArrayList<SkuNumBean>();
        // 根据marketInfo 来计算
        if (marketInfo == null || skuBeanLs == null || skuBeanLs.size() < 1)
            return ;
        for (SkuNumBean bean : skuBeanLs) {
        	if(bean.getStatus()==0){
        		bean.setDiscountMoney(0);
        	}
			if(!StringUtils.isEmpty(bean.getMarketId())){
				marketSku.add(bean);
				marketInfo.setIsFull(false);
			}
		}
        //营销形式，1：减钱，2：打折，3：换购
        Integer a = marketInfo.getMarketType();
        //门槛类型：1：金额，2：件数
        Integer b = marketInfo.getThresholdType();
        //门槛
        long threshold = marketInfo.getThreshold();

        long total = 0;
        // 优惠金额或折扣
        Integer discount = marketInfo.getDiscount();
        for (SkuNumBean bean : marketSku) {
        	// sortNo == 0是为了兼容之前的数据
            if (b == 1 &&  bean.getIsChange() == 0) {
                total += bean.getGoodsAmount();
            } else if (b == 2  && bean.getIsChange() == 0) {
                total += bean.getNum();
            }
            else if ((b == 1 || b == 2) &&  bean.getIsChange() == 1 && a == 3) {
            	discount = (int)(bean.getGoodsAmount() - bean.getChangePrice() * bean.getNum());
            }            
        }
        
        if (discount == null)
            return ;

        if (total >= threshold) {// 若还满足, 需要计算满足的值
            for (SkuNumBean bean : marketSku) {

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
            for (SkuNumBean bean : marketSku) {
                switch (a) {
                    case 1:
                        bean.setDiscountMoney(0);
                        break;
                    case 2://打折就不用计算
                    	BigDecimal g = new BigDecimal(bean.getGoodsAmount());
                    	BigDecimal t = new BigDecimal(discount);
                    	t = t.divide(new BigDecimal(1000), 3, BigDecimal.ROUND_HALF_DOWN);
                    	bean.setDiscountMoney(0);
                        break;
                    case 3:
                        bean.setDiscountMoney(0);
                        break;
                }
            }
        }
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
