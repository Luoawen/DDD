package cn.m2c.scm.port.adapter.restful.web.unit;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.unit.UnitApplication;
import cn.m2c.scm.application.unit.bean.UnitBean;
import cn.m2c.scm.application.unit.command.UnitCommand;
import cn.m2c.scm.application.unit.query.UnitQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.unit.Unit;
import cn.m2c.scm.port.adapter.restful.web.brand.BrandAgent;

@RestController
@RequestMapping("/unit")
public class UnitAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(BrandAgent.class);

	@Autowired
	UnitApplication unitApplication;

	@Autowired
	UnitQuery unitQuery;

	/**
	 * 添加计量单位
	 * 
	 * @param unitName
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<MResult> addUnit(@RequestParam(value = "unitName", required = false) String unitName) {
		MResult result = new MResult(MCode.V_1);
		try {
			String unitId = IDGenerator.get(IDGenerator.SCM_UNIT_PREFIX_TITLE);
			UnitCommand command = new UnitCommand(unitId,unitName);
			unitApplication.addUnit(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("addUnit NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("addUnit Exception e:", e);
			result = new MResult(MCode.V_400, "添加计量单位失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 删除计量单位
	 * 
	 * @param unitName
	 * @return
	 */
	@RequestMapping(value = "/{unitName}", method = RequestMethod.DELETE)
	public ResponseEntity<MResult> delUnit(@RequestParam(value = "unitName", required = false) String unitName) {
		MResult result = new MResult(MCode.V_1);
		try {
			unitApplication.delUnit(unitName);
		} catch (NegativeException ne) {
			LOGGER.error("deleteUnit NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("deleteUnitException e:", e);
			result = new MResult(MCode.V_400, "删除计量单位失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 更新计量单位
	 * 
	 * @param unitName
	 * @return
	 */
	@RequestMapping(value = "/{unitName}", method = RequestMethod.PUT)
	public ResponseEntity<MResult> updateUnit(
			@RequestParam(value = "unitId", required = false) String unitId,
			@RequestParam(value = "unitName", required = false) String unitName) {
		MResult result = new MResult(MCode.V_1);
		try {
			UnitCommand command = new UnitCommand(unitId,unitName);
			unitApplication.modifyUnit(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("modifyUnit NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("modifyUnit Exception e:", e);
			result = new MResult(MCode.V_400, "修改计量单位失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 查询计量单位
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<MPager> list(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
		MPager result = new MPager(MCode.V_1);
		try {
			Integer total = unitQuery.queryUnitTotal();
			List<UnitBean> unitList = unitQuery.getUnitList(pageNum, rows);
			result.setPager(total, pageNum, rows);
			result.setContent(unitList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("计量单位列表出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result,HttpStatus.OK);
	}
	
	/**
	 * 获取计量单位
	 * @param unitId
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public ResponseEntity<MResult> getUnit(@RequestParam(value = "unitId", required = false) String unitId){
		MResult result = new MResult(MCode.V_1);
		try {
			UnitBean unit = unitQuery.getUnitByUnitId(unitId);
			System.out.println("获取到的数据----------------"+unit);
			if (unit != null) {
				result.setContent(unit);
			}
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取计量单位出错"  + e.getMessage());
			result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result,HttpStatus.OK);
		
	}
	
}
