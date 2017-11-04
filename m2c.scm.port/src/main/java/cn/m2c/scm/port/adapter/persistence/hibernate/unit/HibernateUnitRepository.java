package cn.m2c.scm.port.adapter.persistence.hibernate.unit;


import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
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
	public Unit unitNameIsRepeat(String unitName) {
//		Unit unit = (Unit) this.session().createQuery(" select unit_id unitId,unit_name unitName,unit_status unitStatus from t_scm_unit where unit_status = 1 and unit_name =: unitName")
//				.setString("unitName", unitName).uniqueResult();
//				return unit;
				Unit unit = (Unit) this.session().createQuery(" FROM Unit WHERE unitStatus = 1 AND unitName =:unitName")
						.setString("unitName", unitName).uniqueResult();
				return unit;
	}

	/**
	 * 获取计量单位
	 */
	@Override
	public Unit getUnitByUnitId(String unitId) {
		StringBuffer sql = new StringBuffer("FROM Unit WHERE unitStatus = 1 AND unitId =:unitId");
		Unit unit = (Unit) this.session().createQuery(sql.toString()).setString("unitId", unitId).uniqueResult();
		return unit;
	}



}
