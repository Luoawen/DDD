package cn.m2c.scm.port.adapter.service.goods;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.service.goods.GoodsService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;

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
        return resultList;
    }

    @Override
    public List<Map> getGoodsFullCut(String userId, String dealerId, String goodsId, String classifyId) {
        String url = M2C_HOST_URL + "/m2c.market/fullcut/domain/list?dealer_id={0}&goods_id={1}&classify_id={2}&user_id={3}";
        String result = restTemplate.getForObject(url, String.class, dealerId, goodsId, classifyId,userId);
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
        String result = restTemplate.getForObject(url, String.class, token, userId, goodsId);
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("status") == 200) {
            JSONObject contents = json.getJSONObject("content");
            if (null != contents) {
                String favoriteId = contents.getString("favoriteId");
                return favoriteId;
            }
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
        return null;
    }

    @Override
    public Map getUserInfoByUserId(String userId) {
        String url = M2C_HOST_URL + "/m2c.users/user/detail?token={0}&userId={1}";
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
        return null;
    }

    @Override
    public Integer getCartGoodsTotal(String userId) {
        String url = M2C_HOST_URL + "/m2c.users/cart/getTotalQuantity?userId=" + userId;
        String result = restTemplate.getForObject(url, String.class);
        JSONObject json = JSONObject.parseObject(result);
        if (json.getInteger("status") == 200) {
            return json.getInteger("content");
        }
        return 0;
    }
}
