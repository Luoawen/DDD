package cn.m2c.scm.domain.model.stantard;

import cn.m2c.scm.domain.model.unit.Unit;

public interface StantardRepository {
	/**
	 * 查询规格
	 * @param unitName
	 * @return
	 */
	public boolean stantardNameIsRepeat(String stantardName);
	
	/**
	 * 
	 * @param unitName
	 * @return
	 */
	public Stantard getUnitByStantardName(String stantardName);
	
	/**
	 * 保存
	 * @param unit
	 */
	public void saveStantard(Stantard stantard);

}
