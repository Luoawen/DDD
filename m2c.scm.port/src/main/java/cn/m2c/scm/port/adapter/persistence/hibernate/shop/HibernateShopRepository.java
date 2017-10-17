package cn.m2c.scm.port.adapter.persistence.hibernate.shop;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.shop.Shop;
import cn.m2c.scm.domain.model.shop.ShopRepository;

@Repository
public class HibernateShopRepository extends HibernateSupperRepository implements ShopRepository{

	@Override
	public Shop getShop(String dealerId) {
		Shop shop = (Shop) this.session().createQuery(" FROM Shop WHERE  dealerId = :dealerId")
				.setString("dealerId", dealerId).uniqueResult();
		return shop;
	}

	@Override
	public void save(Shop shop) {
		this.session().saveOrUpdate(shop);
	}

	@Override
	public Shop getShopByShopID(String shopId) {
		Shop shop = (Shop) this.session().createQuery(" FROM Shop WHERE  shopId = :shopId")
				.setString("shopId", shopId).uniqueResult();
		return shop;
	}

}
