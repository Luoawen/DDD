package cn.m2c.scm.domain.model.stantard;


public interface StantardRepository {
	/**
	 * 查询规格
	 * @param unitName
	 * @return
	 */
	public Stantard stantardNameIsRepeat(String stantardName);
	
	/**
	 * 
	 * @param unitName
	 * @return
	 */
	public Stantard getStantardByStantardId(String stantardId);
	
	/**
	 * 保存
	 * @param unit
	 */
	public void saveStantard(Stantard stantard);

}
