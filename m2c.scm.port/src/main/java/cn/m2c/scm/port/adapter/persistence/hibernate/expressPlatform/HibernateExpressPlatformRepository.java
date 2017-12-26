package cn.m2c.scm.port.adapter.persistence.hibernate.expressPlatform;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatform;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatformRepository;




@Repository
public class HibernateExpressPlatformRepository extends HibernateSupperRepository implements ExpressPlatformRepository {

    public HibernateExpressPlatformRepository() {
        super();
    }

	@Override
	public ExpressPlatform getExpressPlatform(String com, String nu) {
		ExpressPlatform expressPlatform = (ExpressPlatform) this.session().createQuery(" FROM ExpressPlatform WHERE  com = :com AND nu = :nu")
	                .setString("com", com).setString("nu", nu).uniqueResult();
	        return expressPlatform;
	}

	@Override
	public void saveOrUpdate(ExpressPlatform ep) {
		this.session().saveOrUpdate(ep);
	}



}
