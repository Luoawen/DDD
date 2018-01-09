package cn.m2c.scm.port.adapter.service.order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.m2c.common.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.http.protocol.HttpRequestExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.m2c.common.MCode;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.data.bean.MediaResBean;
import cn.m2c.scm.application.utils.EXPRESSMD5;
import cn.m2c.scm.application.utils.HttpRequest;
import cn.m2c.scm.application.utils.expressUtil.JacksonHelper;
import cn.m2c.scm.application.utils.expressUtil.TaskRequest;
import cn.m2c.scm.application.utils.expressUtil.TaskResponse;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.order.OrderService;

/***
 * 订单领域服务实现类
 *
 * @author fanjc
 *         created date 2017年10月16日
 *         copyrighted@m2c
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    /***/
    private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();
    private static final String KUAIDI_100_URL = DisconfDataGetter.getByFileItem("constants.properties", "express.url").toString().trim();
    private static final String KUAIDI_100_KEY = DisconfDataGetter.getByFileItem("constants.properties", "express.key").toString().trim();
    private static final String KUAIDI_100_CUSTOMER = DisconfDataGetter.getByFileItem("constants.properties", "express.customer").toString().trim();
    private static final String KUAIDI_100_ORDERURL = DisconfDataGetter.getByFileItem("constants.properties", "express.orderurl").toString().trim();
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Map<String, Object> judgeStock(Map<String, Integer> skus) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
	public <T> boolean lockMarketIds(List<T> marketIds,String couponUserId, String orderNo, String userId,long orderAmount,long orderTime,String useCouponList) throws NegativeException{
		// TODO Auto-generated method stub
		if ((null == marketIds || marketIds.size() < 1) && StringUtils.isEmpty(couponUserId) ) {
			return true;
		}
		System.out.println("1:"+JSONObject.toJSONString(marketIds));
		System.out.println("1:"+couponUserId);
		System.out.println("1:"+orderNo);
		System.out.println("1:"+userId);
		System.out.println("1:"+orderAmount);
		System.out.println("1:"+orderTime);
		System.out.println("1:"+useCouponList);
		String url = M2C_HOST_URL + "/m2c.market/domain/fullcut/use?goods_list={0}&order_id={1}&user_id={2}&coupon_goods_list={3}&order_amount={4}&order_time={5}&coupon_user_id={6}";
		String rtResult = restTemplate.postForObject(url, null, String.class, JSONObject.toJSONString(marketIds), orderNo, userId ,useCouponList,orderAmount,orderTime,couponUserId);
		JSONObject json = JSONObject.parseObject(rtResult);
        if (json.getInteger("status") != 200) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Object> getMarketings(Map<String, Object> goodses) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getShopCarGoods(String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    /***
     * 获取营销规则列表
     *
     * @param goodsId, typeId, dealerId
     * @return
     */
    @Override
    public Map<String, Object> getMarketingsByGoods(Map<String, Object> skus) {
        return null;
    }

    /***
     * 获取商品的供货价
     *
     * @param goods 商品ID, 营销Id
     * @return
     */
    @Override
    public Map<String, Object> getSupplyPriceByIds(List<Map<String, String>> goods) {
        return null;
    }

    @Override
    public void lockCoupons(List<String> couponsIds) {

    }

    @Override
    public <T> Map<String, Object> getMediaBdByResIds(List<T> resIds, long time) {
        // TODO Auto-generated method stub
        if (null == resIds || resIds.size() < 1) {
            return null;
        }

        String url = M2C_HOST_URL + "/m2c.media/order/ad?skuListStr={0}&orderDateTime={1}";
        //String url = "http://api.m2c2017test.com/m2c.media/order/ad?skuListStr={0}&orderDateTime={1}";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String rtResult = restTemplate.getForObject(url, String.class, JSONObject.toJSONString(resIds),
                formatter.format(new Date(time)));
        formatter = null;
        JSONObject json = JSONObject.parseObject(rtResult);

        Map<String, Object> result = null;
        if (json.getInteger("status") == 200) {
            result = new HashMap<String, Object>();
            JSONObject contentArr = json.getJSONObject("content");
            //int sz = contentArr.size();
            Gson gson = new Gson();
            //for (int i=0; i< sz; i++) {
            //	JSONObject obj = contentArr.getJSONObject(i);
            //MediaResBean m = gson.fromJson(obj.toJSONString(), MediaResBean.class);
            //result.put(m.getMresId(), m);
            Type type = new TypeToken<HashMap<String, MediaResBean>>() {
            }.getType();

            //HashMap<String, MediaResBean> mp = JSON.parseObject(obj.toJSONString(), type, Feature.IgnoreNotMatch, Feature.DisableCircularReferenceDetect);
            HashMap<String, MediaResBean> mp = gson.fromJson(contentArr.toJSONString(), type);
            result.putAll(mp);
            //}
        }
        
        /*if (json.getInteger("status") == 200) {
            result = new HashMap<String, Object>();
        	Map<String, Object> contentArr = JsonUtils.toMap4Obj(json.getJSONObject("content").toJSONString());
        	Iterator<String> it = contentArr.keySet().iterator();
            while (it.hasNext()) {
            	String key = it.next();
            	JSONObject beanStr = (JSONObject)contentArr.get(key);
            	result.put(key, JsonUtils.toBean(beanStr.toJSONString(), MediaResBean.class));
            }
        }*/
        return result;
    }

    @Override
    public void unlockCoupons(List<String> couponsIds, String userId) {
        // TODO Auto-generated method stub

    }

    /***
     * 获取营销活动
     */
    @Override
    public <T> List<T> getMarketingsByIds(List<String> marketingIds, String userId, Class<T[]> clss) {
        // TODO Auto-generated method stub

        if (null == marketingIds || marketingIds.size() < 1) {
            return null;
        }

        String url = M2C_HOST_URL + "/m2c.market/domain/fullcut/user/{0}/multi?full_cut_ids={1}";
        StringBuilder mks = new StringBuilder(200);
        for (int i = 0; i < marketingIds.size(); i++) {
            if (i > 0)
                mks.append(",");
            mks.append(marketingIds.get(i));
        }

        String rtResult = restTemplate.getForObject(url, String.class, userId, mks.toString());
        JSONObject json = JSONObject.parseObject(rtResult);

        List<T> result = null;
        if (json.getInteger("status") == 200) {
            String content = json.getString("content");
            Gson gson = new Gson();
            //result = gson.fromJson(content, new TypeToken<List<T>>() {}.getType());
            result = Arrays.asList(gson.fromJson(content, clss));
        }
        return result;
    }

    /**
     * 获取第三方物流信息(old)
     *
     * @throws Exception
     */
    @Override
    public String getExpressInfo(String com, String nu) throws Exception {
        //原来的逻辑
        String param = "{\"com\":\"" + com + "\",\"num\":\"" + nu + "\"}";
        String key = KUAIDI_100_KEY;
        String customer = KUAIDI_100_CUSTOMER;
        String sign = EXPRESSMD5.encode(param + key + customer);
        HashMap<String, String> params = dealData(param, sign, customer);
        String resp = "";
        try {
            resp = new HttpRequest().postData(KUAIDI_100_URL, params, "utf-8").toString();
        } catch (Exception e) {
            throw e;
        }
        return resp;

    }

    /**
     * 封装参数map
     *
     * @param param
     * @param sign
     * @param customer
     * @return
     */
    private HashMap<String, String> dealData(String param, String sign,
                                             String customer) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("param", param);
        map.put("sign", sign);
        map.put("customer", customer);
        return map;
    }

    /**
     * 根据用户id获取用户的手机号调用用户中心
     */
    @Override
    public String getUserMobileByUserId(String userId) throws NegativeException {
        String mobile = "";
        try {
            String url = M2C_HOST_URL + "/m2c.users/user/detail?token=123132&userId={0}";
            String rtResult = restTemplate.getForObject(url, String.class, userId);
            JSONObject json = JSONObject.parseObject(rtResult);
            if (json.getInteger("status") == 200) {
                JSONObject contentObject = json.getJSONObject("content");
                if (!StringUtils.isEmpty(contentObject)) {
                    mobile = (String) contentObject.get("mobile");
                }
            }
        } catch (Exception e) {
            throw new NegativeException(400, "根据用户id查询用户手机号失败");
        }
        return mobile;
    }

    /**
     * 调用第三方发送短信接口
     */
    @Override
    public void sendOrderSMS(String userMobile, String shopName)
            throws NegativeException {
        String url = M2C_HOST_URL + "/m2c.support/sms/sendsmsByTemplate";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("codeType", "6");
        params.put("mobile", userMobile);
        params.put("content", shopName);
        params.put("templateType", "2");
        String resp = "";
        try {
            resp = new HttpRequest().postData(url, params, "utf-8").toString();
            JSONObject json = JSONObject.parseObject(resp);
            if (json.getInteger("status") != 200) {
                throw new NegativeException(401, "发送失败");
            }
        } catch (Exception e) {
            LOGGER.error("Exception----->>>", e);
            throw new NegativeException(400, "发送短信出问题");
        }
        System.out.println("返回数据" + resp);
    }

    /**
     * 调用三方物流监听
     */
    @Override
    public void registExpress(String com, String nu) throws NegativeException {
        try {
            TaskRequest req = new TaskRequest();
            req.setCompany(com);
            req.setFrom("");
            req.setTo("");
            req.setNumber(nu);
            req.getParameters().put("callbackurl", M2C_HOST_URL + "/m2c.scm/out-platform/express");
            req.setKey(KUAIDI_100_KEY);

            HashMap<String, String> p = new HashMap<String, String>();
            p.put("schema", "json");
            p.put("param", JacksonHelper.toJSON(req));
            String ret = HttpRequest.postData(KUAIDI_100_ORDERURL, p, "UTF-8");
            TaskResponse resp = JacksonHelper.fromJSON(ret, TaskResponse.class);
            if (!resp.getResult()) {
                if (resp.getReturnCode().equals("701"))
                    throw new NegativeException(701, "拒绝订阅的快递公司");
                if (resp.getReturnCode().equals("700"))
                    throw new NegativeException(700, "订阅方的订阅数据存在错误（如不支持的快递公司、单号为空、单号超长等）或错误的回调地址");
                if (resp.getReturnCode().equals("702"))
                    throw new NegativeException(702, "别不到该单号对应的快递公司");
                if (resp.getReturnCode().equals("600"))
                    throw new NegativeException(600, "您不是合法的订阅者（即授权Key出错）");
                if (resp.getReturnCode().equals("601"))
                    throw new NegativeException(601, "KEY已过期");
                if (resp.getReturnCode().equals("500"))
                    throw new NegativeException(500, "服务器错误（即快递100的服务器出理间隙或临时性异常，有时如果因为不按规范提交请求，比如快递公司参数写错等，也会报此错误）");
            }
        } catch (Exception e) {
            throw new NegativeException(MCode.V_400, "内部错误");
        }
    }

    /**
	 * 获取优惠券信息
	 */
	@Override
	public <T> T getCouponById(String couponId, String couponUserId,
			String userId,Class<T> cla)
			throws NegativeException {
		String url = M2C_HOST_URL + "/m2c.market/domain/coupon/query/detail/content?userId={0}&couponId={1}&couponUserId={2}";
		String rtResult = restTemplate.getForObject(url, String.class ,userId,couponId,couponUserId);
		JSONObject json = JSONObject.parseObject(rtResult);
		T result = null;
		if (json.getInteger("status") == 200) {
			String content = json.getString("content");
			Gson gson = new Gson();
			//result = gson.fromJson(content, new TypeToken<List<T>>() {}.getType());
//	        	result = Arrays.asList(gson.fromJson(content, cla));
            result = gson.fromJson(content, cla);
        } else {
            throw new NegativeException(150, "获取营销模块优惠券出错");
        }
        return result;
    }

    @Override
    public String getUserIdByUserName(String userName) {
        String url = M2C_HOST_URL + "/m2c.users/user/detailByUserName?userName={0}";
        try {
            String result = restTemplate.getForObject(url, String.class, userName);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONObject content = json.getJSONObject("content");
                if (null != content) {
                    return content.getString("userId");
                }
            } else {
                LOGGER.error("根据用户名查询用户id信息失败");
                LOGGER.error("getUserIdByUserName failed.url=>" + url);
                LOGGER.error("getUserIdByUserName failed.error=>" + json.getString("errorMessage"));
                LOGGER.error("getUserIdByUserName failed.param=>userName=" + userName);
            }
        } catch (Exception e) {
            LOGGER.error("根据用户名查询用户id信息异常");
            LOGGER.error("getUserIdByUserName exception.url=>" + url);
            LOGGER.error("getUserIdByUserName exception.error=>" + e.getMessage());
            LOGGER.error("getUserIdByUserName exception.param=>userName=" + userName);
        }
        return null;
    }

    @Override
    public String getMediaName(String mediaId) {
        List<String> mediaIds = new ArrayList<String>();
        mediaIds.add(mediaId);
        String url = M2C_HOST_URL + "/m2c.media/media/info/client?mediaIds="+JsonUtils.toStr(mediaIds);
        try {
            String result = restTemplate.getForObject(url, String.class);
            JSONObject json = JSONObject.parseObject(result);
            if (json.getInteger("status") == 200) {
                JSONArray contents = json.getJSONArray("content");
                if (null != contents && contents.size() > 0) {
                    JSONObject contentObject = JSONObject.parseObject(JSONObject.toJSONString(contents.get(0)));
                    return contentObject.getString("mediaName");
                }
            } else {
                LOGGER.error("根据媒体id查询媒体信息失败");
                LOGGER.error("getMediaName failed.url=>" + url);
                LOGGER.error("getMediaName failed.error=>" + json.getString("errorMessage"));
                LOGGER.error("getMediaName failed.param=>mediaId=" + mediaId);
            }
        } catch (Exception e) {
            LOGGER.error("根据媒体id查询媒体信息异常");
            LOGGER.error("getMediaName exception.url=>" + url);
            LOGGER.error("getMediaName exception.error=>" + e.getMessage());
            LOGGER.error("getMediaName exception.param=>mediaId=" + mediaId);
        }
        return null;
    }
}
