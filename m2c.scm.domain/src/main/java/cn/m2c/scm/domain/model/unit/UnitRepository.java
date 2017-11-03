package cn.m2c.scm.domain.model.unit;


public interface UnitRepository {

	/**
	 * 查询计量单位
	 * @param unitName
	 * @return
	 */
	public Unit unitNameIsRepeat(String unitName);
	
	/**
	 * 
	 * @param unitName
	 * @return
	 */
	public Unit getUnitByUnitId(String unitId);
	
	/**
	 * 保存
	 * @param unit
	 */
	public void saveUnit(Unit unit);
	
}
