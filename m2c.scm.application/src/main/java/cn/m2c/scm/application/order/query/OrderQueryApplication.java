package cn.m2c.scm.application.order.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.m2c.common.StringUtil;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.representation.DealerOrderBean;
import cn.m2c.scm.application.order.data.representation.OptLogBean;
import cn.m2c.scm.application.order.data.representation.OrderBean;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.domain.NegativeException;

/**
 * 订单查询
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
@Service
public class OrderQueryApplication {
	/**调试打日志用*/
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryApplication.class);
	
	@Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }
    /***
     * 商家管理平台，获取订单列表
     * @return
     */
    public List<OrderBean> getOrderList() {
    	return null;
    }
    
    /***
     * 商家平台，获取订单列表
     * @return
     */
    public List<DealerOrderBean> getDealerOrderList(String dealerId) {
    	return null;
    }
    
    /***
     * 获取商家订单操作日志列表
     * @return
     */
    public List<OptLogBean> getDealerOrderOptLog(String dealerId) {
    	return null;
    }
    
    /***
     * 获取商品列表
     * @return
     */
    public List<GoodsDto> getGoodsDtl(Set<String> skuIds) throws NegativeException {
    	if (skuIds == null || skuIds.size() < 1)
    		return null;
    	try {
	    	StringBuilder sql = new StringBuilder(512);
	    	sql.append("select a.goods_id as goodsId")
	    	.append(", a.goods_name as goodsName")
	    	.append(", a.goods_sub_title as goodsTitle")
	    	.append(", a.goods_classify_id as goodsTypeId")
	    	.append(", c.unit_name as goodsUnit")
	    	.append(", b.sku_id as skuId")
	    	.append(", b.sku_name as skuName")
	    	.append(", a.goods_main_images as goodsIcon")
	    	.append(", d.service_rate as rate")
	    	.append(", d.classify_name as goodsType")
	    	.append(", b.weight, b.dealer_id as dealerId")
	    	.append(", b.supply_price as supplyPrice")
	    	.append(", b.market_price as price")
	    	.append(", b.photograph_price as discountPrice")
	    	.append(" from t_scm_goods_sku b, t_scm_goods a")
	    	.append(" left outer join t_scm_unit c on a.goods_unit_id=c.unit_id")
	    	.append(" left outer join t_scm_goods_classify d on a.goods_classify_id=d.classify_id")
	    	.append(" where a.id=b.goods_id ")
	    	.append(" and b.sku_id in(");
	    	int sz = skuIds.size();
	    	for (int i=0; i< sz; i++) {
	    		if (i > 0)
	    			sql.append(",?");
	    		else
	    			sql.append("?");
	    	}
	    	sql.append(")");
	    	Object[] args = new Object[skuIds.size()];
	    	return supportJdbcTemplate.queryForBeanList(sql.toString(), GoodsDto.class, skuIds.toArray(args));
    	}
    	catch (Exception e) {
    		LOGGER.error("===fanjc==订单获取商品详情出错",e);
			throw new NegativeException(500, "获取商品详情列表出错");
    	}
    }
    /***
     * 根据订单号获取订单下的优惠券
     * @param orderId
     * @return
     */
    public List<String> getCouponsByOrderId(String orderId) throws NegativeException {
    	if (StringUtil.isEmpty(orderId))
    		return null;
    	List<String> rs = null;
    	try {
    		rs = supportJdbcTemplate.queryForBeanList("select coupon_id from t_scm_order_coupon_used where order_id=? and _status=1 ", String.class, orderId);
    	}
    	catch (Exception e) {
    		LOGGER.error("===fanjc==获取订单下的优惠券出错",e);
			throw new NegativeException(500, "获取订单下的优惠券出错");
    	}
    	return rs;
    }
    
    /***
     * 根据订单号获取订单下的商品ID及数量
     * @param orderId
     * @return
     */
    public Map<String, Float> getSkusByOrderId(String orderId) throws NegativeException {
    	if (StringUtil.isEmpty(orderId))
    		return null;
    	Map<String, Float> rs = null;
    	try {
    		supportJdbcTemplate.queryForBeanList("select sku_id, sell_num from t_scm_order_detail where order_id=?", HashMap.class, orderId);
    	}
    	catch (Exception e) {
    		LOGGER.error("===fanjc==获取订单下的优惠券出错",e);
			throw new NegativeException(500, "获取订单下的优惠券出错");
    	}
    	return rs;
    }
}
