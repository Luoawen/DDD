package cn.m2c.scm.port.adapter.restful.mini.goods;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.config.data.bean.ConfigBean;
import cn.m2c.scm.application.config.query.ConfigQueryApplication;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.query.GoodsGuaranteeQueryApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.representation.mini.MiniGoodsDetailRepresentation;
import cn.m2c.scm.application.shop.query.ShopQuery;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.special.query.GoodsSpecialQueryApplication;
import cn.m2c.scm.application.unit.query.UnitQuery;
import cn.m2c.scm.domain.service.goods.GoodsService;

/**
 * 商品
 */
@RestController
@RequestMapping("/goods/mini")
public class MiniGoodsAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(MiniGoodsAgent.class);
	
	@Autowired
    GoodsQueryApplication goodsQueryApplication;
	
	@Autowired
    GoodsGuaranteeQueryApplication goodsGuaranteeQueryApplication;
	
	@Autowired
    UnitQuery unitQuery;
	
	@Autowired
    GoodsApplication goodsApplication;
	
	@Autowired
    ShopQuery shopQuery;
	
	@Autowired
    GoodsSpecialQueryApplication goodsSpecialQueryApplication;
	
	@Autowired
    ConfigQueryApplication configQueryApplication;
	
	@Resource(name = "goodsDubboService")
    GoodsService goodsDubboService;
	
	/**
     * 微信小程序拍照获取商品
     *
     * @param token
     * @param recognizedInfo
     * @param barNo
     * @param location
     * @param sn
     * @param os
     * @param appVersion
     * @param osVersion
     * @param triggerTime
     * @param userId
     * @param userName
     * @return
     */
    @RequestMapping(value = "/recognized", method = RequestMethod.GET)
    public ResponseEntity<MResult> miniRecognizedPic(
            @RequestParam(value = "token", required = false) String token,
            @RequestParam(value = "recognizedInfo", required = false) String recognizedInfo,
            @RequestParam(value = "barNo", required = false) String barNo,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "sn", required = false) String sn,
            @RequestParam(value = "os", required = false) String os,
            @RequestParam(value = "appVersion", required = false) String appVersion,
            @RequestParam(value = "osVersion", required = false) String osVersion,
            @RequestParam(value = "triggerTime", required = false) long triggerTime,
            @RequestParam(value = "userId", required = false, defaultValue = "") String userId,
            @RequestParam(value = "userName", required = false, defaultValue = "") String userName
    ) {
        MResult result = new MResult(MCode.V_1);
        Map mediaMap = goodsDubboService.getMediaResourceInfo(barNo);
        String mediaId = null == mediaMap ? "" : (String) mediaMap.get("mediaId");
        String mediaName = null == mediaMap ? "" : (String) mediaMap.get("mediaName");
        String mresId = null == mediaMap ? "" : (String) mediaMap.get("mresId");
        String mresName = null == mediaMap ? "" : (String) mediaMap.get("mresName");


        try {
            List<GoodsBean> goodsBeans = goodsQueryApplication.recognizedGoods(recognizedInfo, location);
            if (null != goodsBeans && goodsBeans.size() > 0) {
                List<MiniGoodsDetailRepresentation> representations = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeans) {
                    List<GoodsGuaranteeBean> goodsGuarantee = goodsGuaranteeQueryApplication.queryGoodsGuaranteeByIds(JsonUtils.toList(goodsBean.getGoodsGuarantee(), String.class));
                    String goodsUnitName = unitQuery.getUnitNameByUnitId(goodsBean.getGoodsUnitId());

                    // 商家客服电话
                    String phone = shopQuery.getDealerShopCustmerTel(goodsBean.getDealerId());
                    
                    MiniGoodsDetailRepresentation representation = new MiniGoodsDetailRepresentation(goodsBean,
                            goodsGuarantee, goodsUnitName, mresId, phone);
                    
                    representations.add(representation);
                    
                    /*
                    //商品总评论数
                    Integer commentTotal = goodsCommentQueryApplication.queryGoodsCommentTotal(goodsBean.getGoodsId());
                    GoodsCommentBean goodsCommentBean = null;
                    if (commentTotal > 0) {
                        goodsCommentBean = goodsCommentQueryApplication.queryGoodsDetailComment(goodsBean.getGoodsId());
                    }
				    //满减
                    List<Map> fullCut = goodsRestService.getGoodsFullCut(userId, goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                    // 优惠券
                    List<Map> coupons = goodsRestService.getGoodsCoupon(userId, goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());
                    //标签
                    List<Map> goodsTags = goodsRestService.getGoodsTags(goodsBean.getDealerId(), goodsBean.getGoodsId(), goodsBean.getGoodsClassifyId());

                    //查询商品被收藏id 
                    String favoriteId = null;
                    if (StringUtils.isNotEmpty(userId)) {
                        favoriteId = goodsRestService.getUserIsFavoriteGoods(userId, goodsBean.getGoodsId(), "");
                    }
                    
                    // 拍获进来，特惠价/优惠券
                    GoodsSpecialBean goodsSpecialBean = null;
                    //Map photographGetCoupon = null;
                    if (null != mediaMap && mediaMap.size() > 0) {
                        //商品有无媒体信息,有媒体信息则返回特惠价
                        goodsSpecialBean = goodsSpecialQueryApplication.queryGoodsSpecialByGoodsId(goodsBean.getGoodsId());
                        if (null != goodsSpecialBean) {
                            // 特惠价角标
                            ConfigBean configBean = configQueryApplication.queryConfigBeanByConfigKey("SCM_GOODS_SPECIAL_IMAGE");
                            if (null != configBean) {
                                goodsSpecialBean.setSpecialIcon(configBean.getConfigValue());
                            }
                        } else {
                            // 拍照领券
                            //photographGetCoupon = goodsRestService.photographGetCoupon(userId, mresId);
                        }
                    }
                    
                    MiniGoodsDetailRepresentation representation = new MiniGoodsDetailRepresentation(goodsBean,
                            goodsGuarantee, goodsUnitName, mresId, commentTotal, goodsCommentBean, fullCut, coupons, goodsTags, favoriteId, phone, goodsSpecialBean, photographGetCoupon);
                    */
                }
                result.setContent(representations);

                // 埋点
                goodsApplication.goodsAppCapturedMD(sn, os, appVersion,
                        osVersion, triggerTime, userId, userName,
                        goodsBeans.get(0).getGoodsId(), goodsBeans.get(0).getGoodsName(), mediaId, mediaName,
                        mresId, mresName);

            }
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("miniRecognizedPic Exception e:", e);
            result = new MResult(MCode.V_400, "拍照获取商品失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 小程序查询商品图文详情
     *
     * @param writer
     * @param goodsId
     */
    /*@RequestMapping(value = "/desc", method = RequestMethod.GET)
    public void miniGoodsDesc(Writer writer,
                             @RequestParam(value = "goodsId", required = true) String goodsId) {
        try {
            GoodsBean goodsBean = goodsQueryApplication.appGoodsDetailByGoodsId(goodsId);
            StringBuffer sb = new StringBuffer();
            sb.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" /><style>img{max-width:100%;border:0; margin : 0;padding :0 ;vertical-align:top;}</style>");
            sb.append(goodsBean.getGoodsDesc());
            writer.write(sb.toString());
            writer.close();
        } catch (IllegalArgumentException e) {
            LOGGER.error("miniGoodsDesc Exception e:", e);
        } catch (Exception e) {
            LOGGER.error("miniGoodsDesc Exception e:", e);
        }
    }*/
}
