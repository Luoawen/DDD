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
	public boolean stantardNameIsRepeat(String stantardName) {
		StringBuffer sql = new StringBuffer("select * from t_scm_stantard  where stantard_status = 1 AND stantard_name =:stantard_name");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(Stantard.class);
		query.setParameter("stantard_name", stantardName);
		List list = query.list();
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取规格
	 */
	@Override
	public Stantard getStantardByStantardId(String stantardId) {
		StringBuffer sql = new StringBuffer("select * from t_scm_stantard where stantard_status = 1 AND stantard_id =:stantard_id");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(Stantard.class);
		query.setParameter("stantard_id", stantardId);
		return (Stantard) query.uniqueResult();
	}

	@Override
	public void saveStantard(Stantard stantard) {
		this.session().saveOrUpdate(stantard);
		
	}

}
