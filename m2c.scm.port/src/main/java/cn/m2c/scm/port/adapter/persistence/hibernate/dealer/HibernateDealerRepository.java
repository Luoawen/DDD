package cn.m2c.scm.port.adapter.persistence.hibernate.dealer;


import java.util.List;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.dealer.Dealer;
import cn.m2c.scm.domain.model.dealer.DealerRepository;


@Repository
public class HibernateDealerRepository extends HibernateSupperRepository implements DealerRepository{

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
		return (List<Dealer>)this.session().createQuery(" FROM Dealer WHERE userId = :userId")
                .setString("userId", userId)
                .list();
		
	}


}
