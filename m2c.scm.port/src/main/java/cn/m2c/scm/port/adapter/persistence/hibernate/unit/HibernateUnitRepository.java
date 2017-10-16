package cn.m2c.scm.port.adapter.persistence.hibernate.unit;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.application.unit.bean.UnitBean;
import cn.m2c.scm.domain.model.unit.Unit;
import cn.m2c.scm.domain.model.unit.UnitRepository;

/**
 * 计量单位信息
 * @author lqwen
 *
 */
@Repository
public class HibernateUnitRepository extends HibernateSupperRepository implements UnitRepository{

	@Override
	public void saveUnit(Unit unit) {
		this.session().saveOrUpdate(unit);
	}

	
	/**
	 * 判断计量单位是否重复
	 */
	@Override
	public boolean unitNameIsRepeat(String unitName) {
		StringBuffer sql = new StringBuffer("select u.unit_name from t_scm_unit u where unit_status = 1 AND unit_name =:unit_name");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(Unit.class);
		query.setParameter("unit_name", unitName);
		List list = query.list();
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取计量单位
	 */
	@Override
	public Unit getUnitByUnitName(String unitName) {
		StringBuffer sql = new StringBuffer("select u.unit_name from t_scm_unit u where unit_status = 1 AND unit_name =:unit_name");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(Unit.class);
		query.setParameter("unit_name", unitName);
		return (Unit) query.uniqueResult();
	}


	/**
	 * 通过unitId获取计量单位
	 */
	@Override
	public UnitBean getUnitByUnitId(String unitId) {
		StringBuffer sql = new StringBuffer("select * from t_scm_unit where unit_status = 1 AND unit_id = :unit_id");
		Query query = this.session().createSQLQuery(sql.toString()).addEntity(Unit.class);
		query.setParameter("unit_id", unitId);
		return (UnitBean) query.uniqueResult();
	}

}
