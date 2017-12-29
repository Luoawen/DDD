package cn.m2c.scm.application.unit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.ddd.common.logger.OperationLogManager;
import cn.m2c.scm.application.unit.command.UnitCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.unit.Unit;
import cn.m2c.scm.domain.model.unit.UnitRepository;

@Service
public class UnitApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnitApplication.class);

	@Autowired
	UnitRepository unitRepository;

	@Resource
    private OperationLogManager operationLogManager;
	/**
	 * 添加计量单位
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void addUnit(UnitCommand command) throws NegativeException {
		LOGGER.info("addUnit command >> {}", command);
		System.out.println(command);
		Unit unitByName = unitRepository.unitNameIsRepeat(command.getUnitName());
		if(unitByName != null) {
			throw new NegativeException(MCode.V_301, "计量单位已存在");
		}
		Unit unit = unitRepository.getUnitByUnitId(command.getUnitId());
		if (null == unit) {
			unit = new Unit(command.getUnitId(), command.getUnitName());
			unitRepository.saveUnit(unit);
		}
	}

	/**
	 * 删除计量单位
	 * 
	 * @param unitName
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void delUnit(String unitId,String _attach) throws NegativeException {
		LOGGER.info("delUnit unitName >>{}", unitId);
		Unit unit = unitRepository.getUnitByUnitId(unitId);
		if (unit.getUseNum() > 0) {
			throw new NegativeException(MCode.V_300, "计量单位被商品使用不能删除");
		}
		if (null == unit) {
			throw new NegativeException(MCode.V_300, "计量单位不存在");
		}
		if (StringUtils.isNotEmpty(_attach))
			operationLogManager.operationLog("删除计量单位", _attach, unit);
		
		unit.deleteUnit();
		unitRepository.saveUnit(unit);
	}

	/**
	 * 修改计量单位信息
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void modifyUnit(UnitCommand command,String _attach) throws NegativeException {
		LOGGER.info("modify unitName >>{}", command.getUnitName());
		Unit unit = unitRepository.getUnitByUnitId(command.getUnitId());
		if (null == unit) 
			throw new NegativeException(MCode.V_300, "计量单位不存在");
		Unit unitByName = unitRepository.unitNameIsRepeat(command.getUnitName());
		if (unitByName != null) {
			if(!command.getUnitId().equals(unitByName.getUnitId())) {
				throw new NegativeException(MCode.V_301, "计量单位已存在");
			}
		}
		if (StringUtils.isNotEmpty(_attach))
			operationLogManager.operationLog("修改计量单位", _attach, unit);
		System.out.println("-------计量单位："+command.getUnitId());
		System.out.println("---"+command.toString());
		unit.modify(command.getUnitId(), command.getUnitName(), command.getUnitStatus());
		unitRepository.saveUnit(unit);
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void beUsed(String unitId) {
		Unit unit = unitRepository.getUnitByUnitId(unitId);
		unit.used();
		unitRepository.saveUnit(unit);
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener(isListening = true)
	public void noBeUsed(String unitId) throws NegativeException {
		Unit unit = unitRepository.getUnitByUnitId(unitId);
		if (unit == null) {
			throw new NegativeException(MCode.V_300, "计量单位不存在");
		}
		if (unit.getUseNum() > 0) {
			unit.noUsed();
			unitRepository.saveUnit(unit);
		}
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener(isListening = true)
	public void updateUsed(String oldUnitId, String newUnitId) {
		Unit oldUnit = unitRepository.getUnitByUnitId(oldUnitId);
		Unit newUnit = unitRepository.getUnitByUnitId(newUnitId);
		if (oldUnit.getUseNum() > 0) {
			oldUnit.noUsed();
			unitRepository.saveUnit(oldUnit);
		}
		newUnit.used();
		unitRepository.saveUnit(newUnit);
	}

}
