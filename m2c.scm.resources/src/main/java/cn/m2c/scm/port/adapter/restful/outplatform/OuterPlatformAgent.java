package cn.m2c.scm.port.adapter.restful.outplatform;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.scm.application.order.ExpressPlatformApplication;
import cn.m2c.scm.application.utils.expressUtil.JacksonHelper;
import cn.m2c.scm.application.utils.expressUtil.NoticeRequest;
import cn.m2c.scm.application.utils.expressUtil.NoticeResponse;
import cn.m2c.scm.application.utils.expressUtil.Result;

@RestController
@RequestMapping("/out-platform")
public class OuterPlatformAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(OuterPlatformAgent.class);
	
	@Autowired
	ExpressPlatformApplication expressPlatformApplication;
	 /**
     * 监听物流单号
     * @param 
     * @return
     */
    @RequestMapping(value = "/express", method = RequestMethod.POST)
    public void registExpress (
    		HttpServletRequest request,
			HttpServletResponse response
            ) throws ServletException, IOException{
    	LOGGER.info("-----物流回调开始");
    		NoticeResponse resp = new NoticeResponse();
    		resp.setResult(false);
    		resp.setReturnCode("500");
    		resp.setMessage("保存失败");
    		try {
    			String param = request.getParameter("param");
    			NoticeRequest nReq = JacksonHelper.fromJSON(param,
    					NoticeRequest.class);

    			Result res = nReq.getLastResult();
    			// 处理快递结果
    			if(res!=null){
    				expressPlatformApplication.addOrUpdate(res);
    			}
    			resp.setResult(true);
    			resp.setReturnCode("200");
    			LOGGER.info("-----物流回调开始");
    			response.getWriter().print(JacksonHelper.toJSON(resp)); //这里必须返回，否则认为失败，过30分钟又会重复推送。
    		}catch (Exception e) {
    		LOGGER.error("快递100回调失败", e);
    		response.getWriter().print(JacksonHelper.toJSON(resp));//保存失败，服务端等30分钟会重复推送。
        }
    }
}
