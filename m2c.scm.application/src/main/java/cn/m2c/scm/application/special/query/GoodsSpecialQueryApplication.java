package cn.m2c.scm.application.special.query;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
