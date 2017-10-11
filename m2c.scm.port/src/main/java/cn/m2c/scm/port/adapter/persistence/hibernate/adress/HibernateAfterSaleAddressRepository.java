package cn.m2c.scm.port.adapter.persistence.hibernate.adress;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.address.AfterSaleAddress;
import cn.m2c.scm.domain.model.address.AfterSaleAddressRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 售后地址
 */
@Repository
public class HibernateAfterSaleAddressRepository extends HibernateSupperRepository implements AfterSaleAddressRepository {
    @Override
    public AfterSaleAddress getAfterSaleAddressByAddressId(String addressId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_after_sale_address where address_id =:address_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(AfterSaleAddress.class);
        query.setParameter("address_id", addressId);
        return (AfterSaleAddress) query.uniqueResult();
    }

    @Override
    public void save(AfterSaleAddress afterSaleAddress) {
        this.session().save(afterSaleAddress);
    }
}
