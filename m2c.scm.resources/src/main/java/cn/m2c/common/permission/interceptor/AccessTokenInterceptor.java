package cn.m2c.common.permission.interceptor;

import cn.m2c.common.RedisUtil;
import cn.m2c.ddd.common.auth.JwtSubject;
import cn.m2c.ddd.common.port.adapter.util.GlobalConstants;
import cn.m2c.ddd.common.port.adapter.util.JwtUtil;
import cn.m2c.ddd.common.port.adapter.util.RSAUtil;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限校验拦截器
 */
public class AccessTokenInterceptor implements HandlerInterceptor
{
    private static Logger logger = LoggerFactory
            .getLogger(AccessTokenInterceptor.class);

    private static final String ACCESS_TOKEN = "access_token";

    private static final String TOKEN = "token";

    /**
     * token失效
     */
    private static Integer TOKEN_EXPIRATION_EXCEPTION = 900004;

    /**
     * session失效
     */
    private static Integer SESSION_EXPIRATION_EXCEPTION = 900003;

    /**
     * token非法
     */
    private static Integer TOKEN_ILEGAL = 900002;

    /**
     * Token未找到
     */
    private static Integer TOKEN_NOT_FOUND = 900001;

    /**
     * Token校验异常
     */
    private static Integer TOKEN_VERIFY_EXCEPTION = 900000;

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
    {
        String accessToken = getAccessToken(request);

        logger.info(
                "AccessTokenInterceptor.preHandle url is:{},accessToken:{}",
                request.getRequestURI(), accessToken);

        if (StringUtils.isBlank(accessToken)
                || StringUtils.isEmpty(accessToken))
        {
            writeResult(response, TOKEN_NOT_FOUND, "AccessToken不存在！");
            return false;
        }

        try
        {
            return validateJwtToken(accessToken, request, response);

        }
        catch (Exception e)
        {
            logger.error(
                    "validateJwtToken preHandle Exception e:" + e.getMessage(),
                    e);
            writeResult(response, TOKEN_VERIFY_EXCEPTION, "Token校验异常不存在！");
            return false;

        }
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

    private boolean validateJwtToken(String accessToken,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {

        try
        {
            String json = "";
            Claims claims = JwtUtil.parseJWT(accessToken,
                    RSAUtil.getPublicKey(GlobalConstants.JWT_TOKEN_PUBLIC_KEY));
            json = claims.getSubject();
            Date exprirarionDate = claims.getExpiration();
            Date now = new Date();
            if (now.after(exprirarionDate))
            {
                writeResult(response, TOKEN_EXPIRATION_EXCEPTION,
                        "ACCESS_TOKEN:已过期！");
                return false;
            }

            if (StringUtils.isBlank(json) && StringUtils.isEmpty(json))
            {
                writeResult(response, TOKEN_EXPIRATION_EXCEPTION, "认证信息被未认证！");
                return false;
            }

            String key = GlobalConstants.USER_LOGIN_SESSION_KEY.replace(
                    "{key}", DigestUtils.md5Hex(json));

            String auth = RedisUtil.get(key);
            if (StringUtils.isBlank(auth) || StringUtils.isEmpty(auth))
            {
                writeResult(response, SESSION_EXPIRATION_EXCEPTION,
                        "session失效！");
            }
            JwtSubject jwtSubject = ObjectSerializer.instance().deserialize(
                    auth, JwtSubject.class);
            if (jwtSubject == null)
            {
                writeResult(response, SESSION_EXPIRATION_EXCEPTION,
                        "session失效！");
                return false;
            }

            JSONObject user = JSON.parseObject(json);
            String userId = (String) user.get("userId");

            if (!jwtSubject.getUserId().equals(userId))
            {
                writeResult(response, TOKEN_ILEGAL, "ACCESS_TOKEN:非法窜用！");
                return false;
            }
            return true;
        }
        catch (Exception e)
        {

            logger.error("validateJwtToken Exception e:" + e.getMessage(), e);
            writeResult(response, TOKEN_VERIFY_EXCEPTION, "ACCESS_TOKEN校验异常！");
            return false;
        }
    }

    private String getAccessToken(HttpServletRequest request)
    {
        String accessToken = request.getHeader("access_token");
        accessToken = accessToken != null ? accessToken : request
                .getParameter(TOKEN);
        return accessToken;
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
}
