package cn.m2c.common.permission.interceptor;

import cn.m2c.ddd.common.port.adapter.util.OpenApiUtil;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * domain访问权限校验 拦截器
 * @author fanjc
 * created date 2018年3月20日
 * copyrighted@m2c
 */
public class DomainAccessInterceptor implements HandlerInterceptor
{
    private static Logger logger = LoggerFactory
            .getLogger(DomainAccessInterceptor.class);

    private static final String SIGN = "sign";

    private static final String INNER_API_TOKEN = "innerApiToken";

    /**
     * 请求非法
     */
    private static Integer REQ_ILEGAL = 80000;

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
    {
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        try {
	        Map<String, String> requestMap = getParameterMap(request);
			if (OpenApiUtil.verifyInnerApi(requestMap)) {
				return true;
			}
        }
        catch(Exception e) {
        	logger.error(" verifyInnerApi Exception e:" + e.getMessage(), e);
        	writeResult(response, REQ_ILEGAL, "请求非法，请修改!");
            return false;
        }
        writeResult(response, REQ_ILEGAL, "请求非法!");
        return false;
        
    }

    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception
    {

    }

    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception
    {

    }    

    private void writeResult(HttpServletResponse response, Integer status,
            String errorMessage) throws IOException
    {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("status", status);
        result.put("errorMessage", errorMessage);
        writer.write(JSONObject.toJSONString(result));
        writer.flush();
        writer.close();
    }
    
    /***
	 * 将请求参数转化为map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getParameterMap(HttpServletRequest request) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, String[]> tempMap = request.getParameterMap();
		Set<String> keys = tempMap.keySet();
		for (String key : keys) {
			if (!SIGN.equals(key) && !INNER_API_TOKEN.equals(key)) {
				continue;
			}
			byte source[] = request.getParameter(key).getBytes("iso8859-1");
			String modelname = new String(source, "UTF-8");
			resultMap.put(key, modelname);
		}
		return resultMap;
	}  
}
