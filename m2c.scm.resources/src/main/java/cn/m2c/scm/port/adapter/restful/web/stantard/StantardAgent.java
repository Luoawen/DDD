package cn.m2c.scm.port.adapter.restful.web.stantard;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.standstard.StandstardApplication;
import cn.m2c.scm.application.standstard.bean.StantardBean;
import cn.m2c.scm.application.standstard.command.StantardCommand;
import cn.m2c.scm.application.standstard.query.StantardQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.stantard.Stantard;

@Controller
@RequestMapping("/stantard")
public class StantardAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(StantardAgent.class);
	

	@Autowired
	StandstardApplication stantardApplication;
	
	@Autowired
	StantardQuery stantardQuery;
	
	/**
	 * 添加规格
	 * @param stantardName
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<MResult> addStandart(@RequestParam(value = "stantardName",required = false) String stantardName){
		MResult result = new MResult(MCode.V_1);
		try {
			String stantardId = IDGenerator.get(IDGenerator.SCM_STANTARD_PREFIX_TITLE);
			StantardCommand command = new StantardCommand(stantardId, stantardName);
			stantardApplication.addStantard(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("addStantard NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		}catch (Exception e) {
			LOGGER.error("addStantard Exception e:", e);
			result = new MResult(MCode.V_400, "添加规格失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 删除规格
	 * @param stantardName
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.DELETE)
	public ResponseEntity<MResult> delUnit(@RequestParam(value = "stantardId", required = false) String stantardId) {
		MResult result = new MResult(MCode.V_1);
		try {
			stantardApplication.delStantard(stantardId);
		} catch (NegativeException ne) {
			LOGGER.error("delStantard NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("deleteStantardException e:", e);
			result = new MResult(MCode.V_400, "删除规格失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 修改规格
	 * @param stantardId
	 * @param stantardName
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<MResult> updateUnit(
			@RequestParam(value = "stantardId", required = false) String stantardId,
			@RequestParam(value = "stantardName", required = false) String stantardName) {
		MResult result = new MResult(MCode.V_1);
		try {
			StantardCommand command = new StantardCommand(stantardId, stantardName);
			stantardApplication.modifyStantard(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException ne) {
			LOGGER.error("modifyStantard NegativeException e:", ne);
			result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("modifyStantard Exception e:", e);
			result = new MResult(MCode.V_400, "修改规格失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 获取stantardList
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<MPager> list(
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
			@RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows) {
		MPager result = new MPager(MCode.V_1);
		try {
			Integer total = stantardQuery.queryStantardTotal();
			List<StantardBean> stantardList = stantardQuery.getStantardList(pageNum, rows);
			result.setPager(total, pageNum, rows);
			result.setContent(stantardList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("规格列表出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result,HttpStatus.OK);

	}
	
	
	/**
	 * 获取stantard
	 * @param stantardId
	 * @return
	 */
	@RequestMapping(value = "/stantard", method = RequestMethod.GET)
	public ResponseEntity<MResult> getStantard(@RequestParam("stantardId") String stantardId) {
		MResult result = new MResult(MCode.V_1);
		try {
			StantardBean stantard = stantardQuery.getStantardByStantardId(stantardId);
			if (stantard != null) {
				result.setContent(stantard);
			}
			result.setStatus(MCode.V_200);
		}catch (Exception e) {
			LOGGER.error("获取规格失败" + e.getMessage(), e);
			result = new MResult(MCode.V_400,e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
}
