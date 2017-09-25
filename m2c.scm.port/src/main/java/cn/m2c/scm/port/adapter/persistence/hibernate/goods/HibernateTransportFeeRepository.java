package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.templet.TransportFee;
import cn.m2c.goods.domain.templet.TransportFeeRepository;

/**
 * @ClassName: HibernateDealerRepository
 * @Description: 供应商持久化实现
 * @author yezp
 * @date 2017年6月30日 下午3:41:10
 *
 */
@Repository
public class HibernateTransportFeeRepository extends HibernateSupperRepository implements TransportFeeRepository {

	public HibernateTransportFeeRepository() {
		super();
	}

	@Override
	public void save(TransportFee transportFee) {
		this.session().saveOrUpdate(transportFee);
	}

	@Override
	public TransportFee getDetail(String transportFeeId) {
		TransportFee transportFee = (TransportFee) this.session().createQuery(" FROM TransportFee WHERE transportFeeId = :transportFeeId")
				.setString("transportFeeId", transportFeeId).uniqueResult();
		return transportFee;
	}



}
