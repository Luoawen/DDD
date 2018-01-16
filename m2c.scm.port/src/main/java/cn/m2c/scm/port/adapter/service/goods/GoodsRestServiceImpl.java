package cn.m2c.scm.port.adapter.service.goods;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.service.goods.GoodsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@Service("goodsRestService")
public class GoodsRestServiceImpl implements GoodsService {
    private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRestServiceImpl.class);

    @Autowired
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return this.supportJdbcTemplate;
    }

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Map> getGoodsTags(String dealerId, String goodsId, String classifyId) {
        List<Map> resultList = new ArrayList<>();
        String url = M2C_HOST_URL + "/m2c.market/market/type/list?dealer_id={0}&goods_id={1}&classify_id={2}";
        try {
            String result = restTemplate.getForObject(url, String.class, dealerId, goodsId, classifyId);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject contentObject = json.getJSONObject("content");
                Iterator<Object> it = contentObject.getJSONArray("typeList").iterator();
                while (it.hasNext()) {
                    Map<String, String> jo = (Map<String, String>) it.next();
                    String labelName = jo.get("typeName");
                    String typeColor = jo.get("typeColor");
                    String typeColorTmd = jo.get("typeColorTmd");
                    String backgroundColor = jo.get("backgroundColor");
                    String backgroundColorTmd = jo.get("backgroundColorTmd");
                    Map mapTags = new HashMap<>();
                    mapTags.put("backcolor", backgroundColor);
                    mapTags.put("backcolorTmd", backgroundColorTmd);
                    mapTags.put("name", labelName);
                    mapTags.put("wordcolor", typeColor);
                    mapTags.put("wordcolorTmd", typeColorTmd);
                    resultList.add(mapTags);
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询商品营销活动标签失败");
            LOGGER.error("getGoodsTags failed.url=>" + url);
            LOGGER.error("getGoodsTags failed.param=>dealerId=" + dealerId + ",goodsId=" + goodsId + ",classifyId=" + classifyId);
        }
        return resultList;
    }

    @Override
    public List<Map> getGoodsFullCut(String userId, String dealerId, String goodsId, String classifyId) {
        String url = M2C_HOST_URL + "/m2c.market/domain/fullcut/list?dealer_id={0}&goods_id={1}&classify_id={2}&user_id={3}";
        try {
            String result = restTemplate.getForObject(url, String.class, dealerId, goodsId, classifyId, userId);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONArray contents = json.getJSONArray("content");
                if (null != contents) {
                    List<Map> resultList = new ArrayList<>();
                    Iterator<Object> contentJsons = contents.iterator();
                    while (contentJsons.hasNext()) {
                        Map resultMap = new HashMap<>();
                        List<Map> contentList = new ArrayList<>();
                        Object contentJson = contentJsons.next();
                        JSONObject contentObject = JSONObject.parseObject(JSONObject.toJSONString(contentJson));
                        Integer numPerOne = contentObject.getInteger("numPerOne");
                        Integer numPerDay = contentObject.getInteger("numPerDay");
                        resultMap.put("numPerOne", numPerOne);
                        resultMap.put("numPerDay", numPerDay);
                        resultMap.put("numLimit", new StringBuffer().append("每人优惠").append(numPerOne).append("次，每天仅可优惠").append(numPerDay).append("次").toString());

                        Iterator<Object> it = contentObject.getJSONArray("itemList").iterator();
                        while (it.hasNext()) {
                            Map jo = (Map) it.next();
                            String itemName = (String) jo.get("content");
                            Map map = new HashMap<>();
                            map.put("itemName", itemName);
                            contentList.add(map);
                        }
                        resultMap.put("itemNames", contentList);
                        Integer rangeType = contentObject.getInteger("rangeType");
                        resultMap.put("rangeType", rangeType);

                        resultMap.put("fullCutName", contentObject.getString("fullCutName"));
                        resultMap.put("fullCutType", contentObject.getInteger("fullCutType")); //满减形式，1：减钱，2：打折，3：换购

                        Iterator<Object> rangeIt = contentObject.getJSONArray("suitableRangeList").iterator();
                        List<String> idList = new ArrayList<>();
                        while (rangeIt.hasNext()) {
                            Map jo = (Map) rangeIt.next();
                            String id = (String) jo.get("id");
                            idList.add(id);
                        }
                        resultMap.put("ids", Utils.listToString(idList, ','));
                        resultList.add(resultMap);
                    }
                    return resultList;
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询商品满减信息失败");
            LOGGER.error("getGoodsFullCut failed.url=>" + url);
            LOGGER.error("getGoodsFullCut failed.param=>dealerId=" + dealerId + ",goodsId=" + goodsId + ",classifyId=" + classifyId + ",userId=" + userId);
        }
        return null;
    }

    @Override
    public Map getMediaResourceInfo(String barNo) {
        return null;
    }

    @Override
    public List<String> getGoodsIdByCoordinate(Double longitude, Double latitude) {
        return null;
    }

    @Override
    public String getUserIsFavoriteGoods(String userId, String goodsId, String token) {
        String url = M2C_HOST_URL + "/m2c.users/favorite/app/detail?token={0}&userId={1}&goodsId={2}";
        try {
            String result = restTemplate.getForObject(url, String.class, token, userId, goodsId);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject contents = json.getJSONObject("content");
                if (null != contents) {
                    String favoriteId = contents.getString("favoriteId");
                    return favoriteId;
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询用户是否收藏商品失败");
            LOGGER.error("getUserIsFavoriteGoods failed.url=>" + url);
            LOGGER.error("getUserIsFavoriteGoods failed.param=>goodsId=" + goodsId + ",userId=" + userId);
        }
        return null;
    }

    @Override
    public boolean updateRecognizedImgStatus(String recognizedId, String recognizedUrl, Integer status) {
        return false;
    }

    @Override
    public Map getMediaInfo(String mediaResourceId) {
        String url = M2C_HOST_URL + "/m2c.media/mres/detail/" + mediaResourceId + "/client";
        try {
            String result = restTemplate.getForObject(url, String.class);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject contentObject = json.getJSONObject("content");
                if (null != contentObject) {
                    String mediaId = contentObject.getString("mediaId");
                    String mediaName = contentObject.getString("mediaName");
                    String mresName = contentObject.getString("mresName");
                    Map<String, Object> mediaInfo = new HashMap<>();
                    mediaInfo.put("mediaId", mediaId);
                    mediaInfo.put("mediaName", mediaName);
                    mediaInfo.put("mresName", mresName);
                    return mediaInfo;
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询媒体信息失败");
            LOGGER.error("getMediaInfo failed.url=>" + url);
            LOGGER.error("getMediaInfo failed.param=>mediaResourceId=" + mediaResourceId);
        }
        return null;
    }

    @Override
    public Map getUserInfoByUserId(String userId) {
        String url = M2C_HOST_URL + "/m2c.users/user/detail?token={0}&userId={1}";
        try {
            String result = restTemplate.getForObject(url, String.class, "", userId);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject contentObject = json.getJSONObject("content");
                if (null != contentObject) {
                    String areaProvince = contentObject.getString("areaProvince");
                    String areaDistrict = contentObject.getString("areaDistrict");
                    String provinceCode = contentObject.getString("provinceCode");
                    String districtCode = contentObject.getString("districtCode");
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("areaProvince", areaProvince);
                    userInfo.put("areaDistrict", areaDistrict);
                    userInfo.put("provinceCode", provinceCode);
                    userInfo.put("districtCode", districtCode);
                    return userInfo;
                }
            }
        } catch (Exception e) {
            LOGGER.error("查询用户信息失败");
            LOGGER.error("getUserInfoByUserId failed.url=>" + url);
            LOGGER.error("getUserInfoByUserId failed.param=>userId=" + userId);
        }
        return null;
    }

    @Override
    public Integer getCartGoodsTotal(String userId) {
        String url = M2C_HOST_URL + "/m2c.users/cart/getTotalQuantity?userId=" + userId;
        try {
            String result = restTemplate.getForObject(url, String.class);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                return json.getInteger("content");
            }
        } catch (Exception e) {
            LOGGER.error("查询用户购物车中商品数量失败");
            LOGGER.error("getCartGoodsTotal failed.url=>" + url);
        }
        return 0;
    }

    @Override
    public List<Map> getGoodsCoupon(String userId, String dealerId, String goodsId, String classifyId) {
        String url = M2C_HOST_URL + "/m2c.market/domain/coupon/suitable/goods/list?dealer_id={0}&goods_id={1}&category_id={2}&user_id={3}";
        try {
            String result = restTemplate.getForObject(url, String.class, dealerId, goodsId, classifyId, userId);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONArray contents = json.getJSONArray("content");
                if (null != contents && contents.size() > 0) {
                    List<Map> resultList = new ArrayList<>();
                    Iterator<Object> contentIt = contents.iterator();
                    while (contentIt.hasNext()) {
                        Object contentJson = contentIt.next();
                        JSONObject contentObject = JSONObject.parseObject(JSONObject.toJSONString(contentJson));
                        // 优惠券ID
                        String couponId = contentObject.getString("couponId");
                        // 优惠券优惠形式：1 减钱 2 打折
                        Integer couponForm = contentObject.getInteger("couponForm");
                        // 优惠券类型：1：代金券，2：打折券，3：分享券
                        Integer couponType = contentObject.getInteger("couponType");
                        // 门槛文案
                        String thresholdContent = contentObject.getString("thresholdContent");
                        // 面值
                        String value = contentObject.getString("value");
                        // 优惠券名称
                        String couponName = contentObject.getString("couponName");
                        // 作用范围页面内容
                        String rangeContent = contentObject.getString("rangeContent");
                        // 有效期 ,返回格式:XXXX.XX.XX - XXXX.XX.XX
                        String expirationTime = contentObject.getString("expirationTime");
                        // 有效期开始时间
                        String expirationTimeStart = contentObject.getString("expirationTimeStart");
                        // 有效期截止时间
                        String expirationTimeEnd = contentObject.getString("expirationTimeEnd");
                        // 是否可领取 true：可以，false：不可以
                        Boolean ableToReceive = contentObject.getBoolean("ableToReceive");
                        // 优惠券总数
                        Integer totalCount = contentObject.getInteger("totalCount");
                        // 标签文案
                        String labelContent = contentObject.getString("labelContent");
                        // 生成者类型，1.平台，2.商家
                        Integer creatorType = contentObject.getInteger("creatorType");

                        Map tempMap = new HashMap<>();
                        tempMap.put("couponId", couponId);
                        tempMap.put("couponForm", couponForm);
                        tempMap.put("couponType", couponType);
                        tempMap.put("content", thresholdContent);
                        if (couponForm == 1) { // 减钱
                            tempMap.put("faceValue", Long.parseLong(value) / 10000);
                        } else { // 打折
                            tempMap.put("faceValue", value);
                        }
                        tempMap.put("couponName", couponName);
                        tempMap.put("rangeContent", rangeContent);
                        tempMap.put("expirationTime", expirationTime);
                        tempMap.put("expirationTimeStart", expirationTimeStart);
                        tempMap.put("expirationTimeEnd", expirationTimeEnd);
                        tempMap.put("ableToReceive", ableToReceive);
                        tempMap.put("totalCount", totalCount);
                        tempMap.put("labelContent", labelContent);
                        tempMap.put("creatorType", creatorType);
                        resultList.add(tempMap);
                    }
                    return resultList;
                }
            } else {
                LOGGER.error("查询商品优惠券信息失败");
                LOGGER.error("getGoodsCoupon failed.url=>" + url);
                LOGGER.error("getGoodsCoupon failed.error=>" + json.getString("errorMessage"));
                LOGGER.error("getGoodsCoupon failed.param=>dealerId=" + dealerId + ",goodsId=" + goodsId + ",category_id=" + classifyId + ",userId=" + userId);
            }
        } catch (Exception e) {
            LOGGER.error("查询商品优惠券信息异常");
            LOGGER.error("getGoodsCoupon exception.url=>" + url);
            LOGGER.error("getGoodsCoupon exception.error=>" + e.getMessage());
            LOGGER.error("getGoodsCoupon exception.param=>dealerId=" + dealerId + ",goodsId=" + goodsId + ",category_id=" + classifyId + ",userId=" + userId);
        }
        return null;
    }

    @Override
    public Map getCouponRange(String couponId) {
        String url = M2C_HOST_URL + "/m2c.market/domain/coupon/suitable/range/goods?coupon_id={0}";
        try {
            String result = restTemplate.getForObject(url, String.class, couponId);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject content = json.getJSONObject("content");
                if (null != content) {
                    /**
                     * 优惠券作用范围，0：全场，1：商家，2：商品，3：品类
                     * 备注：
                     * 当作用范围为全场时，dealerId、goodsId、categoryId为排除的对象；当作用范围不是全场时，三个id为优惠券实际作用的对象
                     */
                    Integer rangeType = content.getInteger("rangeType");
                    List dealerIdList = null;
                    List goodsIdsList = null;
                    List categoryList = null;
                    JSONArray dealerIds = content.getJSONArray("dealerList");
                    if (null != dealerIds && dealerIds.size() > 0) {
                        dealerIdList = dealerIds.toJavaList(String.class);
                    }
                    JSONArray goodsIds = content.getJSONArray("goodsList");
                    if (null != goodsIds && goodsIds.size() > 0) {
                        goodsIdsList = goodsIds.toJavaList(String.class);
                    }
                    JSONArray categoryIds = content.getJSONArray("categoryList");
                    if (null != categoryIds && categoryIds.size() > 0) {
                        categoryList = categoryIds.toJavaList(String.class);
                    }

                    Map resultMap = new HashMap<>();
                    resultMap.put("rangeType", rangeType);
                    resultMap.put("dealerIdList", dealerIdList);
                    resultMap.put("goodsIdsList", goodsIdsList);
                    resultMap.put("categoryList", categoryList);
                    return resultMap;
                }
            } else {
                LOGGER.error("查询商品优惠券适用范围信息失败");
                LOGGER.error("getCouponRange failed.url=>" + url);
                LOGGER.error("getCouponRange failed.error=>" + json.getString("errorMessage"));
                LOGGER.error("getCouponRange failed.param=>couponId=" + couponId);
            }
        } catch (Exception e) {
            LOGGER.error("查询商品优惠券适用范围信息异常");
            LOGGER.error("getCouponRange exception.url=>" + url);
            LOGGER.error("getCouponRange exception.error=>" + e.getMessage());
            LOGGER.error("getCouponRange exception.param=>couponId=" + couponId);
        }
        return null;
    }

    @Override
    public Map packetZoneGoods() {
        String url = M2C_HOST_URL + "/m2c.market/domain/packet/zone/goods";
        try {
            String result = restTemplate.getForObject(url, String.class);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject content = json.getJSONObject("content");
                if (null != content) {
                    Map resultMap = new HashMap<>();
                    String packetName = content.getString("packetName");
                    String packetId = content.getString("packetId");
                    String tip = content.getString("tip");
                    String bannerUrl = content.getString("bannerUrl");
                    String bgPicUrl = content.getString("bgPicUrl");
                    resultMap.put("packetName", packetName);
                    resultMap.put("packetId", packetId);
                    resultMap.put("tip", tip);
                    resultMap.put("bannerUrl", bannerUrl);
                    resultMap.put("bgPicUrl", bgPicUrl);

                    List<Map> zoneMapList = new ArrayList<>();
                    JSONArray zoneList = content.getJSONArray("zonelist");
                    Iterator<Object> zoneIt = zoneList.iterator();
                    while (zoneIt.hasNext()) {
                        Object zoneJson = zoneIt.next();
                        JSONObject zoneObject = JSONObject.parseObject(JSONObject.toJSONString(zoneJson));
                        JSONObject couponRange = zoneObject.getJSONObject("couponRange");
                        String zoneId = zoneObject.getString("zoneId");
                        String zoneName = zoneObject.getString("zoneName");
                        /**
                         * 优惠券作用范围，0：全场，1：商家，2：商品，3：品类
                         * 备注：
                         * 当作用范围为全场时，dealerId、goodsId、categoryId为排除的对象；当作用范围不是全场时，三个id为优惠券实际作用的对象
                         */
                        Integer rangeType = couponRange.getInteger("rangeType");
                        List dealerIdList = null;
                        List goodsIdsList = null;
                        List categoryList = null;
                        JSONArray dealerIds = couponRange.getJSONArray("dealerList");
                        if (null != dealerIds && dealerIds.size() > 0) {
                            dealerIdList = dealerIds.toJavaList(String.class);
                        }
                        JSONArray goodsIds = couponRange.getJSONArray("goodsList");
                        if (null != goodsIds && goodsIds.size() > 0) {
                            goodsIdsList = goodsIds.toJavaList(String.class);
                        }
                        JSONArray categoryIds = couponRange.getJSONArray("categoryList");
                        if (null != categoryIds && categoryIds.size() > 0) {
                            categoryList = categoryIds.toJavaList(String.class);
                        }

                        Map couponMap = new HashMap<>();
                        couponMap.put("rangeType", rangeType);
                        couponMap.put("dealerIdList", dealerIdList);
                        couponMap.put("goodsIdsList", goodsIdsList);
                        couponMap.put("categoryList", categoryList);

                        Map zoneMap = new HashMap<>();
                        zoneMap.put("zoneId", zoneId);
                        zoneMap.put("zoneName", zoneName);
                        zoneMap.put("couponRange", couponMap);

                        zoneMapList.add(zoneMap);
                    }
                    resultMap.put("zoneList", zoneMapList);
                    return resultMap;
                }
            } else {
                LOGGER.error("查询新人礼包专区信息失败");
                LOGGER.error("packetZoneGoods failed.url=>" + url);
                LOGGER.error("packetZoneGoods failed.error=>" + json.getString("errorMessage"));
            }
        } catch (Exception e) {
            LOGGER.error("查询新人礼包专区信息异常");
            LOGGER.error("packetZoneGoods exception.url=>" + url);
            LOGGER.error("packetZoneGoods exception.error=>" + e.getMessage());
        }
        return null;
    }
}
