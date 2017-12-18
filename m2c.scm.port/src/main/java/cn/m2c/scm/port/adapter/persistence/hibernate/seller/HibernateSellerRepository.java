package cn.m2c.scm.port.adapter.persistence.hibernate.seller;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.seller.Seller;
import cn.m2c.scm.domain.model.seller.SellerRepository;
@Repository
public class HibernateSellerRepository extends HibernateSupperRepository implements SellerRepository{

	@Override
	public Seller getSeller(String filter) {
		Seller seller = (Seller) this.session().createQuery(" FROM Seller WHERE  sellerId = :sellerId OR sellerPhone = :sellerPhone")
				.setString("sellerId", filter).setString("sellerPhone", filter).uniqueResult();
		System.out.println("-------------执行到获取业务员");
		return seller;
	}

	@Override
	public void save(Seller seller) {
		// TODO Auto-generated method stub
		this.session().saveOrUpdate(seller);
	}

}
