package cn.m2c.scm.port.adapter.restful.admin.config;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.config.ConfigApplication;
import cn.m2c.scm.application.config.command.ConfigCommand;
import cn.m2c.scm.application.config.data.bean.ConfigBean;
import cn.m2c.scm.application.config.data.representation.ConfigBeanRepresentation;
import cn.m2c.scm.application.config.query.ConfigQueryApplication;
import cn.m2c.scm.domain.NegativeException;

/**
 * 配置 key-value
 * (特惠价图片)
 */
@RestController
@RequestMapping("/admin/config")
public class AdminConfigAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminConfigAgent.class);
	
	@Autowired
	ConfigApplication configApplication;
	
	@Autowired
	ConfigQueryApplication configQueryApplication;
	
	@Autowired
    private HttpServletRequest request;
	
    /**
     * 保存配置
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<MResult> saveConfig(
	    	@RequestParam(value = "configKey",required = false) String configKey,
			@RequestParam(value = "configValue", required = false) String configValue,
			@RequestParam(value = "configDescribe", required = false) String configDescribe){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		ConfigCommand configCommand = new ConfigCommand(configKey, configValue, configDescribe);
    		configApplication.saveConfig(configCommand);
    		result.setStatus(MCode.V_200);
    	} catch (NegativeException ne) {
			LOGGER.error("saveConfig NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("saveConfig Exception e:", e);
			result = new MResult(MCode.V_400, "保存配置失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    
    /**
     * 修改配置
     * @return
     */
    @RequestMapping(value = "/{configKey}", method = RequestMethod.PUT)
    public ResponseEntity<MResult> modifyConfig(
    		@PathVariable(value = "configKey") String configKey,
    		@RequestParam(value = "configValue", required = false) String configValue,
			@RequestParam(value = "configDescribe", required = false) String configDescribe
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		ConfigCommand configCommand = new ConfigCommand(configKey, configValue, configDescribe);
    		String _attach = request.getHeader("attach");
    		configApplication.modifyConfig(configCommand, _attach);
    		result.setStatus(MCode.V_200);
    	} catch (NegativeException ne) {
			LOGGER.error("modifyConfig NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
		} catch (Exception e) {
			LOGGER.error("modifyConfig Exception e:", e);
			result = new MResult(MCode.V_400, "保存配置失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 查询配置(特惠价图片等)
     * @param configKey
     * @return
     */
    @RequestMapping(value = "/{configKey}", method = RequestMethod.GET)
    public ResponseEntity<MResult> getConfigByConfigKey(
    		@PathVariable(value = "configKey") String configKey
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		ConfigBean configBean = configQueryApplication.queryConfigBeanByConfigKey(configKey);
    		if(null != configBean) {
    			ConfigBeanRepresentation representation = new ConfigBeanRepresentation(configBean);
    			result.setContent(representation);
    		}
    		result.setStatus(MCode.V_200);
    	}catch (Exception e) {
            LOGGER.error("getConfigByConfigKey Exception e:", e);
            result = new MResult(MCode.V_400, "查询配置失败");
        }
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
}
