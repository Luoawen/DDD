package cn.m2c.scm.port.adapter.service.goods;

import cn.m2c.media.interfaces.dubbo.MediaService;
import cn.m2c.scm.domain.service.goods.GoodsService;
import cn.m2c.support.interfaces.dubbo.RecognizeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@Service("goodsDubboService")
public class GoodsServiceDubboImpl implements GoodsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsServiceDubboImpl.class);

    @Autowired
    MediaService mediaService;
    @Autowired
    RecognizeService recognizeService;

    @Override
    public List<Map> getGoodsTags(String dealerId, String goodsId, String classifyId) {
        return null;
    }

    @Override
    public Map getMediaResourceInfo(String barNo) {
        if (StringUtils.isNotEmpty(barNo)) {
            try {
                Map<String, Object> mediaData = mediaData = mediaService.getMres(null, barNo);
                String mresId = mediaData.get("mresId") == null ? "" : (String) mediaData.get("mresId");
                String mresName = mediaData.get("mresName") == null ? "" : (String) mediaData.get("mresName");
                String mediaId = mediaData.get("mediaId") == null ? "" : (String) mediaData.get("mediaId");
                String mediaName = mediaData.get("mediaName") == null ? "" : (String) mediaData.get("mediaName");
                Map map = new HashMap<>();
                map.put("mresId", mresId);
                map.put("mresName", mresName);
                map.put("mediaId", mediaId);
                map.put("mediaName", mediaName);
                return map;
            } catch (Exception e) {
                LOGGER.error("调用媒体中心，获取媒体信息失败", e);
            }
        }
        return null;
    }

    @Override
    public List<String> getGoodsIdByCoordinate(Double longitude, Double latitude) {
        try {
            List<Map<String, Object>> goodsIdList = mediaService.getGoodsList(longitude, latitude);
            if (null != goodsIdList && goodsIdList.size() > 0) {
                List<String> goodsIds = new ArrayList<>();
                for (Map map : goodsIdList) {
                    String goodsId = (String) map.get("goodsId");
                    goodsIds.add(goodsId);
                }
                return goodsIds;
            }
        } catch (Exception e) {
            LOGGER.error("调用媒体中心,根据经纬度获取商品ID失败", e);
        }
        return null;
    }

    @Override
    public List<Map> getGoodsFullCut(String dealerId, String goodsId, String classifyId) {
        return null;
    }

    @Override
    public String getUserIsFavoriteGoods(String userId, String goodsId, String token) {
        return null;
    }

    @Override
    public boolean updateRecognizedImgStatus(String recognizedId, String recognizedUrl, Integer status) {
        return recognizeService.updateRecoImgStatus(recognizedId, recognizedUrl, status);
    }

    @Override
    public Map getMediaInfo(String mediaResourceId) {
        return null;
    }

    @Override
    public Map getUserInfoByUserId(String userId) {
        return null;
    }
}
