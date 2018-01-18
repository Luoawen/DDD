package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.GoodsHistory;
import cn.m2c.scm.domain.model.goods.GoodsHistoryRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品审核变更历史记录
 */
@Repository
public class HibernateGoodsHistoryRepository extends HibernateSupperRepository implements GoodsHistoryRepository {
    @Override
    public void save(GoodsHistory goodsHistory) {
        this.session().save(goodsHistory);
    }
}
