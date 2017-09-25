package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.seller.Seller;
import cn.m2c.goods.domain.seller.SellerRepository;

/**
 * @ClassName: HibernateDealerRepository
 * @Description: 供应商持久化实现
 * @author yezp
 * @date 2017年6月30日 下午3:41:10
 *
 */
@Repository
public class HibernateSellerRepository extends HibernateSupperRepository implements SellerRepository {

	public HibernateSellerRepository() {
		super();
	}

	@Override
	public Seller getSeller(String staffId) {
		Seller seller = (Seller) this.session().createQuery(" FROM Seller WHERE staffId = :staffId")
				.setString("staffId", staffId).uniqueResult();
		return seller;
	}

	@Override
	public void save(Seller seller) {
		this.session().saveOrUpdate(seller);
	}

	@Override
	public Seller getSysSeller(String sysuserId) {
		Seller seller = (Seller) this.session().createQuery(" FROM Seller WHERE userId = :userId")
				.setString("userId", sysuserId).uniqueResult();
		return seller;
	}





}