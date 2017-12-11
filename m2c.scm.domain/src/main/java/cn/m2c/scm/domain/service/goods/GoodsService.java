package cn.m2c.scm.domain.service.goods;

import java.util.List;
import java.util.Map;

/**
 * 商品
 */
public interface GoodsService {
    /**
     * 获取商品标签（满减、优惠券等）
     *
     * @param dealerId   商家ID
     * @param goodsId    商品ID
     * @param classifyId 商品分类ID
     * @return
     */
    List<Map> getGoodsTags(String dealerId, String goodsId, String classifyId);

    /**
     * 根据条码获取商品媒体资源信息
     *
     * @param barNo
     * @return
     */
    Map getMediaResourceInfo(String barNo);

    /**
     * 根据经纬度坐标查询商品ID
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    List<String> getGoodsIdByCoordinate(Double longitude, Double latitude);

    /**
     * 获取商品满减信息
     *
     * @param dealerId   商家ID
     * @param goodsId    商品ID
     * @param classifyId 商品分类ID
     * @return
     */
    List<Map> getGoodsFullCut(String dealerId, String goodsId, String classifyId);

    /**
     * 查询商品是否被用户收藏
     *
     * @param userId  用户id
     * @param goodsId 商品id
     */
    String getUserIsFavoriteGoods(String userId, String goodsId, String token);

    /**
     * 更新识别图状态
     * @param recognizedId
     * @param recognizedUrl
     * @param status 0 下架 1 上架
     * @return
     */
    boolean updateRecognizedImgStatus(String recognizedId, String recognizedUrl, Integer status);

    /**
     * 根据媒体资源id获取媒体信息
     *
     * @param mediaResourceId
     * @return
     */
    Map getMediaInfo(String mediaResourceId);

    /**
     * 根据userId获取用户信息
     * @param userId
     * @return
     */
    Map getUserInfoByUserId(String userId);

    /**
     * 获取购物车内有效商品总件数
     * @param userId
     * @return
     */
    Integer getCartGoodsTotal(String userId);
}
