package cn.m2c.scm.application.order.token;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.support.interfaces.dubbo.TokenService;
import cn.m2c.support.interfaces.dubbo.command.AppAccessTokenCommand;
import cn.m2c.support.interfaces.dubbo.command.SystemAccessTokenCommand;

@Service
@Transactional
public class TokenApplication {
	private static final Logger logger = LoggerFactory.getLogger(TokenApplication.class);
	
	@Autowired
	private TokenService tokenService;
	
	public Map<String,Object> loginUser(String token){
		Map<String,Object> map = null;
		try{
			AppAccessTokenCommand appAccessTokenCommand = tokenService.getAppAccessToken(token);
			logger.info("tokenApplication.loginUser appAccessTokenCommand >> " + appAccessTokenCommand);
			if(appAccessTokenCommand != null){
				map = new HashMap<String,Object>();
				map.put("userId", appAccessTokenCommand.getUserId());
				map.put("userName", appAccessTokenCommand.getUsername());
			}
		}catch(Exception e){
			logger.error("tokenApplication.loginUser appAccessTokenCommand error",e);
		}
		if(map != null){
			return map;
		}else{
			try{
				SystemAccessTokenCommand systemAccessTokenCommand = tokenService.getSystemAccessToken(token);
				logger.info("tokenApplication.loginUser systemAccessTokenCommand >> " + systemAccessTokenCommand);
				if(systemAccessTokenCommand != null){
					map = new HashMap<String,Object>();
					map.put("userId", systemAccessTokenCommand.getUserId());
					map.put("userName", systemAccessTokenCommand.getUserName());
				}
			}catch(Exception e){
				logger.error("tokenApplication.loginUser systemAccessTokenCommand error",e);
			}
		}
		return map;
		
	}
	
	public String getUserId(String token){
		try{
			Map<String,Object> map = this.loginUser(token);
			if(map == null){
				return "";
			}
			return map.get("userId")==null?"":(String)map.get("userId");
		}catch(Exception e){
			logger.error("tokenApplication.loginUser getUserId error");
		}
		return "";
	}
	
	public String getUserName(String token){
		try{
			Map<String,Object> map = this.loginUser(token);
			if(map == null){
				return "";
			}
			return map.get("userName")==null?"":(String)map.get("userName");
		}catch(Exception e){
			logger.error("tokenApplication.loginUser getUserName error");
		}
		return "";
	}

}
