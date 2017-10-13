package cn.m2c.scm.domain.model.unit;


public interface UnitRepository {

	/**
	 * 查询计量单位
	 * @param unitName
	 * @return
	 */
	public boolean unitNameIsRepeat(String unitName);
	
	/**
	 * 
	 * @param unitName
	 * @return
	 */
	public Unit getUnitByUnitName(String unitName);
	
	/**
	 * 保存
	 * @param unit
	 */
	public void saveUnit(Unit unit);
}
