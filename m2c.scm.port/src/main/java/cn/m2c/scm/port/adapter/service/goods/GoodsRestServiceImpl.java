package cn.m2c.scm.port.adapter.service.goods;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.service.goods.GoodsService;
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
    public Map getMediaResourceInfo(String barNo) {
        return null;
    }

    @Override
    public List<String> getGoodsIdByCoordinate(Double longitude, Double latitude) {
        return null;
    }
}
