package cn.m2c.scm.port.adapter.persistence.hibernate.goods;


import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.dealer.Dealer;
import cn.m2c.goods.domain.dealer.DealerRepository;

/**
 * @ClassName: HibernateDealerRepository
 * @Description: 供应商持久化实现
 * @author yezp
 * @date 2017年6月30日 下午3:41:10
 *
 */
@Repository
public class HibernateDealerRepository extends HibernateSupperRepository implements DealerRepository {

	public HibernateDealerRepository() {
		super();
	}

	@Override
	public Dealer getDealerDetail(String dealerId) {
		Dealer dealer = (Dealer) this.session().createQuery(" FROM Dealer WHERE  dealerId = :dealerId")
				.setString("dealerId", dealerId).uniqueResult();
		return dealer;
	}

	@Override
	public void save(Dealer dealer) {
		this.session().saveOrUpdate(dealer);
	}

	@Override
	public Dealer getDealerByMobile(String mobile) {
		Dealer dealer = (Dealer) this.session().createQuery(" FROM Dealer WHERE dealerStatus=1 and mobile = :mobile")
				.setString("mobile", mobile).uniqueResult();
		return dealer;
	}

	@Override
	public Dealer getDealerByUserId(String userId) {
		Dealer dealer = (Dealer) this.session().createQuery(" FROM Dealer WHERE dealerStatus=1 and userId = :userId")
				.setString("userId", userId).uniqueResult();
		return dealer;
	}





}
