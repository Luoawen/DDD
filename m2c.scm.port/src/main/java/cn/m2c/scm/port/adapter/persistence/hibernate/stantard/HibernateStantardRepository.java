package cn.m2c.scm.port.adapter.persistence.hibernate.stantard;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.stantard.StantardRepository;
import cn.m2c.scm.domain.model.stantard.Stantard;
import cn.m2c.scm.domain.model.unit.Unit;

@Repository
public class HibernateStantardRepository extends HibernateSupperRepository implements StantardRepository {

	
	/**
	 * 获取规格是否重复
	 * @param stantardName
	 * @return
	 */
	@Override
	public Stantard stantardNameIsRepeat(String stantardName) {
		return (Stantard) this.session().createQuery(" FROM Stantard WHERE stantardStatus = 1 AND stantardName =:stantardName")
		.setString("stantardName", stantardName).uniqueResult();
		
	}

	/**
	 * 获取规格
	 */
	@Override
	public Stantard getStantardByStantardId(String stantardId) {
		return (Stantard) this.session().createQuery(" FROM Stantard WHERE stantardStatus = 1 AND stantardId =:stantardId")
				.setString("stantardId", stantardId).uniqueResult();
	}

	@Override
	public void saveStantard(Stantard stantard) {
		this.session().saveOrUpdate(stantard);
		
	}

}
