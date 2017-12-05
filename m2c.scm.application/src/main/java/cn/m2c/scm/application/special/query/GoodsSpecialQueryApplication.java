package cn.m2c.scm.application.special.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialDetailBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialListBean;
import cn.m2c.scm.application.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特惠价
 */
@Service
public class GoodsSpecialQueryApplication {
    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }

    /**
     * 根据商品Id获取商品特惠信息
     *
     * @param goodsId
     * @return
     */
    public GoodsSpecialBean queryGoodsSpecialByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_special WHERE 1 = 1 AND status = 1");
        GoodsSpecialBean goodsSpecialBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsSpecialBean.class);
        if (null != goodsSpecialBean) {
            goodsSpecialBean.setGoodsSpecialSkuBeans(queryGoodsSkuSpecialBySpecialId(goodsSpecialBean.getId()));
        }
        return goodsSpecialBean;
    }

    public List<GoodsSkuSpecialBean> queryGoodsSkuSpecialBySpecialId(Integer specialId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_sku_special WHERE 1 = 1 AND special_id = ?");
        return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSkuSpecialBean.class, specialId);
    }

    /**
     * 获取sku有效的特惠价
     * @param skuIds
     * @return
     */
    public Map getEffectiveGoodsSkuSpecial(List<String> skuIds) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" s.* ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_special g,t_scm_goods_sku_special s WHERE g.id = s.special_id AND 1 = 1");
        sql.append(" AND g.status = 1 ");
        sql.append(" AND s.sku_id in (" + Utils.listParseString(skuIds) + ")");
        List<GoodsSkuSpecialBean> goodsSkuSpecialBeans = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSkuSpecialBean.class);
        if (null != goodsSkuSpecialBeans && goodsSkuSpecialBeans.size() > 0) {
            Map map = new HashMap<>();
            for (GoodsSkuSpecialBean bean : goodsSkuSpecialBeans) {
                map.put(bean.getSkuId(), bean.getSpecialPrice());
            }
            return map;
        }
        return null;
    }
    /** 
     * 查询特惠价商品的总数
     * @param status
     * @param startTime
     * @param endTime
     * @param searchMessage
     * @return
     */
	public Integer queryGoodsSpecialCount(Integer status, String startTime, String endTime, String searchMessage) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT ");
		sql.append(" count(*) ");
		sql.append(" From ");
		sql.append(" t_scm_goods_special WHERE 1 = 1 ");
		if(null != status) {
			sql.append(" AND status = ? ");
			params.add(status);
		}
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND start_time >= ? AND end_time <= ? ");
            params.add(startTime+ " 00:00:00 ");
            params.add(endTime+ " 23:59:59 ");
		}
		if(StringUtils.isNotEmpty(searchMessage)) {
			sql.append(" AND ( goods_name LIKE ? OR dealer_name Like ? ) ");
			params.add("%"+searchMessage+"%");
			params.add("%"+searchMessage+"%");
		}
		
		return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
	}

	/**
	 * 查询商品特惠价List
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @param searchMessage
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<GoodsSpecialListBean> queryGoodsSpecialBeanList(Integer status, String startTime, String endTime,
			String searchMessage, Integer pageNum, Integer rows) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT ");
		sql.append(" * ");
		sql.append(" From ");
		sql.append(" t_scm_goods_special WHERE 1 = 1 ");
		if(null != status) {
			sql.append(" AND status = ? ");
			params.add(status);
		}
		if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND start_time >= ? AND end_time <= ? ");
            params.add(startTime+ " 00:00:00 ");
            params.add(endTime+ " 23:59:59 ");
		}
		if(StringUtils.isNotEmpty(searchMessage)) {
			sql.append(" AND ( goods_name LIKE ? OR dealer_name Like ? ) ");
			params.add("%"+searchMessage+"%");
			params.add("%"+searchMessage+"%");
		}
		sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        List<GoodsSpecialListBean> goodsSpecialBeanList = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(),GoodsSpecialListBean.class,params.toArray());
        if(goodsSpecialBeanList != null && goodsSpecialBeanList.size() >= 0) {
        	for(GoodsSpecialListBean goodsSpecialBean : goodsSpecialBeanList ) {
        		goodsSpecialBean.setSpecialPriceMin(querySpecialPriceMin(goodsSpecialBean.getId()));
        	}
        }
		return goodsSpecialBeanList;
	}
	
	/**
	 * 根据specialId查询商品最小特惠价
	 * @param specialId
	 * @return
	 */
	public Long querySpecialPriceMin(Integer specialId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" MIN( special_price ) ");
		sql.append(" FROM ");
		sql.append(" t_scm_goods_sku_special ");
		sql.append(" WHERE special_id = ? ");
		return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Long.class , specialId);
	}
	
	/**
	 * 根据specialId查询GoodsSkuSpecialBean
	 * @param sPecialId
	 * @return
	 */
	public List<GoodsSkuSpecialBean> queryGoodsSkuSpecialBeanBySpecialId(Integer specialId){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" * ");
		sql.append(" From ");
		sql.append(" t_scm_goods_sku_special WHERE 1 = 1 AND special_id = ?");
		return this.getSupportJdbcTemplate().queryForBeanList(sql.toString(),GoodsSkuSpecialBean.class,specialId);
	}

	/**
	 * 根据specialId查询GoodsSpecialBean
	 * @param specialId
	 * @return
	 */
	public GoodsSpecialDetailBean queryGoodsSkuSpecialBeanBySpecialId(String specialId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" * ");
		sql.append(" From ");
		sql.append(" t_scm_goods_special WHERE 1 = 1 AND special_id = ?");
		GoodsSpecialDetailBean goodsSpecialBean =  this.getSupportJdbcTemplate().queryForBean(sql.toString(),GoodsSpecialDetailBean.class,specialId);
		if(goodsSpecialBean != null) {
			goodsSpecialBean.setGoodsSkuSpecials(queryGoodsSkuSpecialBeanBySpecialId(goodsSpecialBean.getId()));
		}
		return goodsSpecialBean;
	}
}
