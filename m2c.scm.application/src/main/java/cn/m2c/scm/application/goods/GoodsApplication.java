package cn.m2c.scm.application.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.goods.command.GoodsCommand;
import cn.m2c.scm.application.goods.command.GoodsRecognizedModifyCommand;
import cn.m2c.scm.application.goods.command.MDViewGoodsCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.Goods;
import cn.m2c.scm.domain.model.goods.GoodsApproveRepository;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import cn.m2c.scm.domain.model.goods.GoodsSku;
import cn.m2c.scm.domain.model.goods.GoodsSkuRepository;
import cn.m2c.scm.domain.model.goods.event.GoodsAppCapturedMDEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsAppSearchMDEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsAppViewMDEvent;
import cn.m2c.scm.domain.service.goods.GoodsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@Service
@Transactional
public class GoodsApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsApplication.class);
    @Autowired
    GoodsRepository goodsRepository;
    @Autowired
    GoodsSkuRepository goodsSkuRepository;
    @Autowired
    GoodsApproveRepository goodsApproveRepository;
    @Resource(name = "goodsDubboService")
    GoodsService goodsDubboService;
    @Resource(name = "goodsRestService")
    GoodsService goodsRestService;

    /**
     * 商品审核同意,保存商品
     *
     * @param command
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void saveGoods(GoodsCommand command) throws NegativeException {
        LOGGER.info("saveGoods command >>{}", command);
        Goods goods = goodsRepository.queryGoodsById(command.getGoodsId());
        if (null == goods) {//增加商品
            goods = new Goods(command.getGoodsId(), command.getDealerId(), command.getDealerName(), command.getGoodsName(), command.getGoodsSubTitle(),
                    command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsBrandName(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                    command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(), command.getGoodsGuarantee(),
                    command.getGoodsMainImages(), command.getGoodsDesc(), command.getGoodsShelves(), command.getGoodsSpecifications(), command.getGoodsSKUs(), command.getSkuFlag());
        } else {//修改商品审核：修改商品的拍获价，供货价，规格
            goods.modifyApproveGoodsSku(command.getGoodsSKUs());
        }
        goodsRepository.save(goods);
    }

    /**
     * 修改商品
     *
     * @param command
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void modifyGoods(GoodsCommand command) throws NegativeException {
        LOGGER.info("modifyGoods command >>{}", command);
        Goods goods = goodsRepository.queryGoodsById(command.getGoodsId());
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        if (goodsRepository.goodsNameIsRepeat(command.getGoodsId(), command.getDealerId(), command.getGoodsName()) ||
                goodsApproveRepository.goodsNameIsRepeat(command.getGoodsId(), command.getDealerId(), command.getGoodsName())) {
            throw new NegativeException(MCode.V_300, "商品名称已存在");
        }

        // 判断商品编码是否存在
        Map<String, String> codesMap = command.getCodeMap();
        if (null != codesMap && codesMap.size() > 0) {
            for (Map.Entry<String, String> entry : codesMap.entrySet()) {
                String skuId = entry.getKey();
                String goodsCode = entry.getValue();
                if (goodsSkuRepository.goodsCodeIsRepeat(command.getDealerId(), skuId, goodsCode)) {
                    throw new NegativeException(MCode.V_300, "商品编码已存在");
                }
            }
        }

        goods.modifyGoods(command.getGoodsName(), command.getGoodsSubTitle(),
                command.getGoodsClassifyId(), command.getGoodsBrandId(), command.getGoodsBrandName(), command.getGoodsUnitId(), command.getGoodsMinQuantity(),
                command.getGoodsPostageId(), command.getGoodsBarCode(), command.getGoodsKeyWord(), command.getGoodsGuarantee(),
                command.getGoodsMainImages(), command.getGoodsDesc(), command.getGoodsSpecifications(), command.getGoodsSKUs());
    }

    /**
     * 删除商品
     *
     * @param goodsId
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void deleteGoods(String goodsId) throws NegativeException {
        LOGGER.info("deleteGoods goodsId >>{}", goodsId);
        Goods goods = goodsRepository.queryGoodsById(goodsId);
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        goods.remove();
    }

    /**
     * 商品上架
     *
     * @param goodsId
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void upShelfGoods(String goodsId) throws NegativeException {
        LOGGER.info("upShelfGoods goodsId >>{}", goodsId);
        Goods goods = goodsRepository.queryGoodsById(goodsId);
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        goods.upShelf();
        if (StringUtils.isNotEmpty(goods.recognizedId())) {
            boolean result = goodsDubboService.updateRecognizedImgStatus(goods.recognizedId(), goods.recognizedUrl(), 1);
            if (!result) {
                LOGGER.error("商品上架，更新识别图片状态失败");
            }
        }
    }

    /**
     * 商品下架
     *
     * @param goodsId
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void offShelfGoods(String goodsId) throws NegativeException {
        LOGGER.info("offShelfGoods goodsId >>{}", goodsId);
        Goods goods = goodsRepository.queryGoodsById(goodsId);
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        goods.offShelf();
        if (StringUtils.isNotEmpty(goods.recognizedId())) {
            boolean result = goodsDubboService.updateRecognizedImgStatus(goods.recognizedId(), goods.recognizedUrl(), 0);
            if (!result) {
                LOGGER.error("商品下架，更新识别图片状态失败");
            }
        }
    }

    /**
     * 修改商品识别图
     *
     * @param command
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyRecognized(GoodsRecognizedModifyCommand command) throws NegativeException {
        LOGGER.info("modifyRecognized command >>{}", command);
        Goods goods = goodsRepository.queryGoodsById(command.getGoodsId());
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        goods.modifyRecognized(command.getRecognizedId(), command.getRecognizedUrl());
        Integer status = goods.goodsStatus() == 1 ? 0 : 1;
        if (StringUtils.isEmpty(command.getRecognizedId())) {
            status = 0;
        }
        boolean result = goodsDubboService.updateRecognizedImgStatus(goods.recognizedId(), goods.recognizedUrl(), status);
        if (!result) {
            LOGGER.error("商品添加广告图，更新识别图片状态失败");
        }
    }

    /**
     * 扣库存
     *
     * @param map
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void outInventory(Map<String, Integer> map) throws NegativeException {
        List<String> skuIds = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String skuId = entry.getKey();
            Integer num = entry.getValue();
            GoodsSku goodsSku = goodsSkuRepository.queryGoodsSkuById(skuId);
            if (null == goodsSku) {//信息不存在
                skuIds.add(skuId);
                continue;
            }
            if (goodsSku.availableNum() < num) {//库存不足
                skuIds.add(skuId);
                continue;
            }
        }
        if (null != skuIds && skuIds.size() > 0) {
            throw new NegativeException(MCode.V_301, JsonUtils.toStr(skuIds));//301:库存不足
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String skuId = entry.getKey();
            Integer num = entry.getValue();
            GoodsSku goodsSku = goodsSkuRepository.queryGoodsSkuById(skuId);
            // 版本号
            Integer concurrencyVersion = goodsSku.concurrencyVersion();
            int result = goodsSkuRepository.outInventory(skuId, num, concurrencyVersion);
            if (result <= 0) {
                throw new NegativeException(MCode.V_400, skuId);//400:扣库存失败
            }
            Goods goods = goodsRepository.queryGoodsById(goodsSku.goods().getId());
            goods.soldOut();//如果售完则修改状态
        }
    }

    /**
     * app搜索埋点
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void goodsAppSearchMD(String sn, String userId, String searchFrom, String keyWord) throws NegativeException {
        DomainEventPublisher
                .instance()
                .publish(new GoodsAppSearchMDEvent(sn, userId, searchFrom, keyWord));
    }


    /**
     * app拍获埋点
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void goodsAppCapturedMD(String sn, String os, String appVersion,
                                   String osVersion, Long triggerTime, String userId, String userName,
                                   String goodsId, String goodsName, String mediaId, String mediaName,
                                   String mresId, String mresName) throws NegativeException {
        DomainEventPublisher
                .instance()
                .publish(new GoodsAppCapturedMDEvent(sn, os, appVersion,
                        osVersion, triggerTime, userId, userName,
                        goodsId, goodsName, mediaId, mediaName,
                        mresId, mresName));
    }


    /**
     * 修改商品品牌名称
     *
     * @param brandId
     * @param brandName
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsBrandName(String brandId, String brandName) throws NegativeException {
        LOGGER.info("modifyGoodsBrandName brandId >>{}", brandId);
        List<Goods> goodsList = goodsRepository.queryGoodsByBrandId(brandId);
        if (null != goodsList) {
            for (Goods goods : goodsList) {
                goods.modifyBrandName(brandName);
            }
        }
    }

    /**
     * 修改商品供应商名称
     *
     * @param dealerId
     * @param dealerName
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void modifyGoodsDealerName(String dealerId, String dealerName) throws NegativeException {
        LOGGER.info("modifyGoodsDealerName dealerId >>{}", dealerId);
        List<Goods> goodsList = goodsRepository.queryGoodsByDealerId(dealerId);
        if (null != goodsList) {
            for (Goods goods : goodsList) {
                goods.modifyDealerName(dealerName);
            }
        }
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void GoodsSkuUpdateByOrderPayed(Map<String, Object> map) {
        LOGGER.info("goodsApplication GoodsSkuUpdateByOrderPayed start...");
        LOGGER.info("GoodsSkuUpdateByOrderPayed param=>" + JsonUtils.toStr(map));
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String skuId = entry.getKey();
                Integer num = Integer.parseInt(String.valueOf(entry.getValue()));
                GoodsSku goodsSku = goodsSkuRepository.queryGoodsSkuById(skuId);
                if (null != goodsSku) {
                    goodsSku.orderPayed(num);
                }
            }
        }
        LOGGER.info("goodsApplication GoodsSkuUpdateByOrderPayed end...");
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void GoodsSkuUpdateByOrderCancel(Map<String, Object> map) {
        LOGGER.info("goodsApplication GoodsSkuUpdateByOrderCancel start...");
        LOGGER.info("GoodsSkuUpdateByOrderCancel param=>" + JsonUtils.toStr(map));
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String skuId = entry.getKey();
                Integer num = Integer.parseInt(String.valueOf(entry.getValue()));
                GoodsSku goodsSku = goodsSkuRepository.queryGoodsSkuById(skuId);
                if (null != goodsSku) {
                    goodsSku.orderCancel(num);
                }
            }
        }
        LOGGER.info("goodsApplication GoodsSkuUpdateByOrderCancel end...");
    }

    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void GoodsSkuUpdateByOrderReturnGoods(Map<String, Object> map) {
        if (null != map && map.size() > 0) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String skuId = entry.getKey();
                Integer num = Integer.parseInt(String.valueOf(entry.getValue()));
                GoodsSku goodsSku = goodsSkuRepository.queryGoodsSkuById(skuId);
                if (null != goodsSku) {
                    goodsSku.orderReturnGoods(num);
                }
            }
        }
    }

    /**
     * 修改商品投放状态
     *
     * @param goodsId
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    public void LaunchGoods(String goodsId) throws NegativeException {
        LOGGER.info("LaunchGoods goodsId >>{}", goodsId);
        Goods goods = goodsRepository.queryGoodsById(goodsId);
        if (null != goods) {
            goods.launchGoods();
        }
    }

    /**
     * app搜索埋点
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void mdViewGoods(MDViewGoodsCommand command) throws NegativeException {
        String areaProvince = "";
        String areaDistrict = "";
        String provinceCode = "";
        String districtCode = "";
        if (StringUtils.isNotEmpty(command.getUserId())) {
            Map userInfo = goodsRestService.getUserInfoByUserId(command.getUserId());
            if (null != userInfo) {
                areaProvince = null != userInfo.get("areaProvince") ? (String) userInfo.get("areaProvince") : "";
                areaDistrict = null != userInfo.get("areaDistrict") ? (String) userInfo.get("areaDistrict") : "";
                provinceCode = null != userInfo.get("provinceCode") ? (String) userInfo.get("provinceCode") : "";
                districtCode = null != userInfo.get("districtCode") ? (String) userInfo.get("districtCode") : "";
            }
        }
        String mediaId = "";
        String mediaName = "";
        String mresName = "";
        if (StringUtils.isNotEmpty(command.getMresId())) {
            Map mediaInfo = goodsRestService.getMediaInfo(command.getMresId());
            if (null != mediaInfo) {
                mediaId = (String) mediaInfo.get("mediaId");
                mediaName = (String) mediaInfo.get("mediaName");
                mresName = (String) mediaInfo.get("mresName");
            }
        }
        GoodsAppViewMDEvent goodsViewEvent = new GoodsAppViewMDEvent(command.getSn(), command.getOs(), command.getAppVersion(),
                command.getOsVersion(), command.getTriggerTime(), command.getLastTime(), command.getUserId(), command.getUserName(),
                areaProvince, areaDistrict, provinceCode, districtCode,
                command.getGoodsId(), command.getGoodsName(), mediaId, mediaName, command.getMresId(),
                mresName);
        DomainEventPublisher.instance().publish(goodsViewEvent);
    }

    /**
     * 修改商品主图
     *
     * @param goodsId
     * @throws NegativeException
     */
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
    @EventListener(isListening = true)
    public void modifyGoodsMainImages(String goodsId, List<String> images) throws NegativeException {
        LOGGER.info("modifyGoodsMainImages goodsId >>{}", goodsId);
        Goods goods = goodsRepository.queryGoodsById(goodsId);
        if (null == goods) {
            throw new NegativeException(MCode.V_300, "商品不存在");
        }
        goods.modifyGoodsMainImages(images);
    }
}
