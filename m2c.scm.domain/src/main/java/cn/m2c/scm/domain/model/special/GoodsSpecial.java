package cn.m2c.scm.domain.model.special;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.util.GetMapValueUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品特惠价
 */
public class GoodsSpecial extends ConcurrencySafeEntity {
    private String specialId;
    private String goodsId;
    private String goodsName;
    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    private Integer skuFlag;
    private String dealerId;
    private String dealerName;
    private Date startTime;
    private Date endTime;
    private String congratulations;
    private String activityDescription;
    private Integer status; //状态（0未生效，1已生效，2已失效）
    private Date createTime;
    private Date lastUpdatedDate;
    private List<GoodsSkuSpecial> goodsSkuSpecials;

    public GoodsSpecial() {
        super();
    }

    public GoodsSpecial(String specialId, String goodsId, String goodsName, Integer skuFlag,
                        String dealerId, String dealerName, String startTime, String endTime,
                        String congratulations, String activityDescription, String goodsSkuSpecials) throws ParseException, NegativeException {
        this.specialId = specialId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.skuFlag = skuFlag;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startTime = df.parse(startTime + " 00:00:00");
        this.endTime = df.parse(endTime + " 23:59:59");
        this.congratulations = congratulations;
        this.activityDescription = activityDescription;
        this.status = 1;
        if (this.startTime.getTime() > new Date().getTime()) {
            this.status = 0;
        }
        this.createTime = new Date();
        this.goodsSkuSpecials = getGoodsSkuSpecials(goodsSkuSpecials);
    }

    private List<GoodsSkuSpecial> getGoodsSkuSpecials(String goodsSkuSpecials) throws NegativeException {
        List<Map> list = JsonUtils.toList(goodsSkuSpecials, Map.class);
        if (null != list && list.size() > 0) {
            List<GoodsSkuSpecial> goodsSpecials = new ArrayList<>();
            for (Map map : list) {
                String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                String skuName = GetMapValueUtils.getStringFromMapKey(map, "skuName");
                Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
                Long specialPrice = GetMapValueUtils.getLongFromMapKey(map, "specialPrice");
                GoodsSkuSpecial goodsSkuSpecial = new GoodsSkuSpecial(this, skuId, skuName, supplyPrice, specialPrice);
                goodsSpecials.add(goodsSkuSpecial);
            }
            return goodsSpecials;
        } else {
            throw new NegativeException(MCode.V_1, "商品优惠价格信息为空");
        }
    }

    public void modifyGoodsSpecial(String startTime, String endTime,
                                   String congratulations, String activityDescription, String goodsSkuSpecials) throws ParseException, NegativeException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startTime = df.parse(startTime + " 00:00:00");
        this.endTime = df.parse(endTime + " 23:59:59");
        this.congratulations = congratulations;
        this.activityDescription = activityDescription;
        for (GoodsSkuSpecial goodsSkuSpecial : this.goodsSkuSpecials) {
            Map map = getGoodsSkuSpecial(goodsSkuSpecial.skuId(), goodsSkuSpecials);
            if (null == map) {
                throw new NegativeException(MCode.V_1, "商品优惠价格信息为空");
            }
            Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
            Long specialPrice = GetMapValueUtils.getLongFromMapKey(map, "specialPrice");
            goodsSkuSpecial.modifyGoodsSkuSpecialPrice(supplyPrice, specialPrice);
        }
        this.lastUpdatedDate = new Date();
    }

    private Map getGoodsSkuSpecial(String skuId, String goodsSkuSpecials) throws NegativeException {
        List<Map> list = JsonUtils.toList(goodsSkuSpecials, Map.class);
        if (null != list && list.size() > 0) {
            for (Map map : list) {
                String skuIdParam = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                if (skuId.equals(skuIdParam)) {
                    return map;
                }
            }
        }
        return null;
    }

    public GoodsSkuSpecial getOptimalGoodsSkuSpecial() {
        //排序
        Collections.sort(this.goodsSkuSpecials, new Comparator<GoodsSkuSpecial>() {
            public int compare(GoodsSkuSpecial skuSpecial1, GoodsSkuSpecial skuSpecial2) {
                if (skuSpecial2.specialPrice().compareTo(skuSpecial1.specialPrice()) == 0) {
                    return skuSpecial1.supplyPrice().compareTo(skuSpecial2.supplyPrice());
                } else {
                    return skuSpecial2.specialPrice().compareTo(skuSpecial1.specialPrice());
                }
            }
        });
        return this.goodsSkuSpecials.get(0);
    }

    public void modifyGoodsSkuSpecial(List<Map> addSkuList) {
        GoodsSkuSpecial skuSpecial = getOptimalGoodsSkuSpecial();
        for (Map map : addSkuList) {
            String skuId = (String) map.get("skuId");
            String skuName = null != map.get("skuName") ? (String) map.get("skuName") : null;
            GoodsSkuSpecial goodsSkuSpecial = new GoodsSkuSpecial(this, skuId, skuName, skuSpecial.supplyPrice(), skuSpecial.specialPrice());
            this.goodsSkuSpecials.add(goodsSkuSpecial);
        }
    }

    public void startSpecial() {
        this.status = 1;
        this.lastUpdatedDate = new Date();
    }

    public void endSpecial() {
        this.status = 2;
        this.lastUpdatedDate = new Date();
    }

    public void modifyGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void modifyDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
