package cn.m2c.scm.port.adapter.persistence.hibernate.dealer;


import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.dealer.Dealer;
import cn.m2c.scm.domain.model.dealer.DealerRepository;
import cn.m2c.scm.domain.util.DealerReportType;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class HibernateDealerRepository extends HibernateSupperRepository implements DealerRepository {

    public HibernateDealerRepository() {
        super();
    }

    @Override
    public Dealer getDealer(String dealerId) {
        Dealer dealer = (Dealer) this.session().createQuery(" FROM Dealer WHERE  dealerId = :dealerId")
                .setString("dealerId", dealerId).uniqueResult();
        return dealer;
    }

    @Override
    public void save(Dealer dealer) {
        this.session().saveOrUpdate(dealer);
    }

    /**
     * sellerId获取经销商
     */
    @Override
    public Dealer getDealerBySellerId(String sellerId) {
        Dealer dealer = (Dealer) this.session().createSQLQuery("FROM Dealer WHERE sellerId = :sellerId")
                .setString("sellerId", sellerId).uniqueResult();
        return dealer;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Dealer> getDealerByUserId(String userId) {
        return (List<Dealer>) this.session().createQuery(" FROM Dealer WHERE userId = :userId")
                .setString("userId", userId)
                .list();

    }

    @Override
    public void saveDealerDayReport(String dealerId, String type, Integer num, Long money, Integer day) throws NegativeException {
        Integer orderNum = 0;
        Long sellMoney = 0L;
        Integer orderRefundNum = 0;
        Long refundMoney = 0L;
        Integer goodsAddNum = 0;
        Integer goodsCommentNum = 0;

        // 不同类型处理
        if (DealerReportType.ORDER_PAY.equals(type)) {
            orderNum = num;
            sellMoney = money;
        } else if (DealerReportType.ORDER_REFUND.equals(type)) {
            orderRefundNum = num;
            refundMoney = money;
        } else if (DealerReportType.GOODS_ADD.equals(type)) {
            goodsAddNum = num;
        } else if (DealerReportType.GOODS_COMMENT.equals(type)) {
            goodsCommentNum = num;
        }

        Integer vId = getDealerDayReportVid(dealerId, day);
        if (null == vId) {//不存在则新增一条数据
            StringBuilder insertSql = new StringBuilder();
            insertSql.append("INSERT INTO t_scm_dealer_day_report (day,dealer_id,order_num,order_refund_num,goods_add_num,sell_money,refund_money,goods_comment_num) ");
            insertSql.append("VALUES (:day, :dealer_id, :order_num, :order_refund_num, :goods_add_num, :sell_money, :refund_money, :goods_comment_num) ");
            Query insert = this.session().createSQLQuery(insertSql.toString());
            insert.setParameter("day", day);
            insert.setParameter("dealer_id", dealerId);
            insert.setParameter("order_num", orderNum);
            insert.setParameter("order_refund_num", orderRefundNum);
            insert.setParameter("goods_add_num", goodsAddNum);
            insert.setParameter("sell_money", sellMoney);
            insert.setParameter("refund_money", refundMoney);
            insert.setParameter("goods_comment_num", goodsCommentNum);
            insert.executeUpdate();
        } else { // 已存在则更新
            StringBuilder updateSql = new StringBuilder();
            updateSql.append("UPDATE t_scm_dealer_day_report ");
            updateSql.append("SET order_num = order_num + :order_num, ");
            updateSql.append("order_refund_num = order_refund_num + :order_refund_num, ");
            updateSql.append("goods_add_num = goods_add_num + :goods_add_num, ");
            updateSql.append("sell_money = sell_money + :sell_money, ");
            updateSql.append("refund_money = refund_money + :refund_money, ");
            updateSql.append("goods_comment_num = goods_comment_num + :goods_comment_num, ");
            updateSql.append("concurrency_version = concurrency_version + 1 ");
            updateSql.append("WHERE 1=1 ");
            updateSql.append("AND day = :day ");
            updateSql.append("AND dealer_id = :dealer_id ");
            updateSql.append("AND concurrency_version = :vId ");
            Query update = this.session().createSQLQuery(updateSql.toString());
            update.setParameter("order_num", orderNum);
            update.setParameter("order_refund_num", orderRefundNum);
            update.setParameter("goods_add_num", goodsAddNum);
            update.setParameter("sell_money", sellMoney);
            update.setParameter("refund_money", refundMoney);
            update.setParameter("goods_comment_num", goodsCommentNum);
            update.setParameter("day", day);
            update.setParameter("dealer_id", dealerId);
            update.setParameter("vId", vId);
            int rows = update.executeUpdate();
            if (rows <= 0) {
                throw new NegativeException(MCode.V_400, "商家日报表数据更新失败");
            }
        }
    }

    public Integer getDealerDayReportVid(String dealerId, Integer day) {
        StringBuilder sql = new StringBuilder("select concurrency_version from t_scm_dealer_day_report where day =:day and dealer_id = :dealer_id");
        Query query = this.session().createSQLQuery(sql.toString());
        query.setParameter("day", day);
        query.setParameter("dealer_id", dealerId);
        Integer vId = null == query.uniqueResult() ? null : Integer.parseInt(String.valueOf(query.uniqueResult()));
        return vId;
    }


}
