package cn.m2c.scm.application.special.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.special.data.representation.GoodsSkuSpecialDetailAllBeanRepresentation;
import cn.m2c.scm.application.special.data.representation.GoodsSpecialDetailBeanRepresentation;
import cn.m2c.scm.application.utils.Utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    GoodsQueryApplication goodsQueryApplication;
    
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
        sql.append(" t_scm_goods_special WHERE 1 = 1 AND goods_id = ? AND status = 1");
        GoodsSpecialBean goodsSpecialBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsSpecialBean.class, goodsId);
        if (null != goodsSpecialBean) {
            goodsSpecialBean.setGoodsSpecialSkuBeans(queryGoodsSkuSpecialBySpecialId(goodsSpecialBean.getId()));
        }
        return goodsSpecialBean;
    }

    /**
     * 根据Id获取商品特惠信息
     *
     * @param specialId
     * @return
     */
    public GoodsSpecialBean queryGoodsSpecialBySpecialId(Integer specialId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" FROM ");
        sql.append(" t_scm_goods_special WHERE 1 = 1 AND id = ? AND status = 1");
        GoodsSpecialBean goodsSpecialBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsSpecialBean.class, specialId);
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
     *
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
                GoodsSpecialBean goodsSpecialBean = queryGoodsSpecialBySpecialId(bean.getSpecialId());
                if (null != goodsSpecialBean){
                    bean.setGoodsSpecialId(goodsSpecialBean.getSpecialId());
                }
                map.put(bean.getSkuId(), bean);
            }
            return map;
        }
        return null;
    }

    /**
     * 查询特惠价商品的总数
     *
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
        if (null != status) {
            sql.append(" AND status = ? ");
            params.add(status);
        }
        /*if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND start_time >= ? AND end_time <= ? ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
        }*/
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND ((start_time >= ? AND end_time <= ?) OR (start_time <= ? AND end_time >= ? ) OR (start_time <= ? AND end_time >= ? AND end_time <= ? ) OR (start_time >= ? AND start_time <= ? AND end_time >= ?)) ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(startTime + " 00:00:00 ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(endTime + " 23:59:59 ");
        }
        if (StringUtils.isNotEmpty(searchMessage)) {
            sql.append(" AND ( goods_name LIKE ? OR dealer_name Like ? ) ");
            params.add("%" + searchMessage + "%");
            params.add("%" + searchMessage + "%");
        }

        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
    }

    /**
     * 查询商品特惠价List(首页展示)
     *
     * @param status
     * @param startTime
     * @param endTime
     * @param searchMessage
     * @param pageNum
     * @param rows
     * @return
     */
    public List<GoodsSpecialBean> queryGoodsSpecialBeanList(Integer status, String startTime, String endTime,
                                                                    String searchMessage, Integer pageNum, Integer rows) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" From ");
        sql.append(" t_scm_goods_special WHERE 1 = 1 ");
        if (null != status) {
            sql.append(" AND status = ? ");
            params.add(status);
        }
        /*if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND start_time >= ? AND end_time <= ? ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
        }*/
        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND ((start_time >= ? AND end_time <= ?) OR (start_time <= ? AND end_time >= ? ) OR (start_time <= ? AND end_time >= ? AND end_time <= ? ) OR (start_time >= ? AND start_time <= ? AND end_time >= ?)) ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(startTime + " 00:00:00 ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(startTime + " 00:00:00 ");
            params.add(endTime + " 23:59:59 ");
            params.add(endTime + " 23:59:59 ");
        }
        if (StringUtils.isNotEmpty(searchMessage)) {
            sql.append(" AND ( goods_name LIKE ? OR dealer_name Like ? ) ");
            params.add("%" + searchMessage + "%");
            params.add("%" + searchMessage + "%");
        }
        sql.append(" ORDER BY create_time DESC , id DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        List<GoodsSpecialBean> goodsSpecialBeanLists = this.getSupportJdbcTemplate().queryForBeanList(sql.toString(), GoodsSpecialBean.class, params.toArray());
        if (goodsSpecialBeanLists != null && goodsSpecialBeanLists.size() >= 0) {
            for (GoodsSpecialBean goodsSpecialBean : goodsSpecialBeanLists) {
            	goodsSpecialBean.setGoodsSpecialSkuBeans(queryGoodsSkuSpecialBySpecialId(goodsSpecialBean.getId()));
            }
        }
        return goodsSpecialBeanLists;
    }

    /**
     * 根据specialId查询商品特惠价详情表述对象
     *
     * @param specialId
     * @return
     */
    public GoodsSpecialDetailBeanRepresentation queryGoodsSpecialDetailBeanRepresentationBySpecialId(String specialId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");
        sql.append(" * ");
        sql.append(" From ");
        sql.append(" t_scm_goods_special WHERE 1 = 1 AND special_id = ? ");
        GoodsSpecialBean goodsSpecialBean = this.getSupportJdbcTemplate().queryForBean(sql.toString(), GoodsSpecialBean.class, specialId);
        GoodsSpecialDetailBeanRepresentation goodsSpecialDetailBeanRepresentation = null;
        if (goodsSpecialBean != null) {
            goodsSpecialDetailBeanRepresentation = new GoodsSpecialDetailBeanRepresentation(goodsSpecialBean);
            goodsSpecialDetailBeanRepresentation.setGoodsSkuSpecials(queryGoodsSkuSpecialDetailAllBeanList(goodsSpecialDetailBeanRepresentation.getId()));
        }
        return goodsSpecialDetailBeanRepresentation;
    }

    /**
     * 查询特惠价sku详情
     * @param goodsId
     * @return
     */
    public List<GoodsSkuSpecialDetailAllBeanRepresentation> queryGoodsSkuSpecialDetailAllBeanList(Integer specialId){
    	//查出商品sku详情
    	List<GoodsSkuSpecialBean> goodsSkuSpecialBeanList = queryGoodsSkuSpecialBySpecialId(specialId);
		List<GoodsSkuSpecialDetailAllBeanRepresentation> list = new ArrayList<>();
    	if(null != goodsSkuSpecialBeanList && goodsSkuSpecialBeanList.size() > 0) {
    		for(GoodsSkuSpecialBean goodsSkuSpecialBean : goodsSkuSpecialBeanList) {
    			GoodsSkuSpecialDetailAllBeanRepresentation representation = new GoodsSkuSpecialDetailAllBeanRepresentation(goodsSkuSpecialBean);
    			GoodsSkuBean goodsSkuBean = goodsQueryApplication.queryGoodsSkuBeanBySkuId(representation.getSkuId());
    			//原供货价
    			representation.setGoodsSupplyPrice(Utils.moneyFormatCN(goodsSkuBean.getSupplyPrice()));
    			//原拍获价
    			representation.setGoodsSkuPrice(Utils.moneyFormatCN(goodsSkuBean.getPhotographPrice()));
    			list.add(representation);
    		}
    	}
    	return list;
    }
    
}
