package cn.m2c.common.permission.interceptor;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.m2c.common.RedisUtil;
import cn.m2c.ddd.common.auth.JwtClientSideSubject;
import cn.m2c.ddd.common.auth.JwtSubject;
import cn.m2c.ddd.common.port.adapter.util.GlobalConstants;
import cn.m2c.ddd.common.port.adapter.util.JwtUtil;
import cn.m2c.ddd.common.port.adapter.util.RSAUtil;
import cn.m2c.ddd.common.serializer.ObjectSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 权限校验拦截器
 */
public class AuthorizationInterceptor implements HandlerInterceptor
{
    private static Logger logger = LoggerFactory
            .getLogger(AuthorizationInterceptor.class);

    private static final String AUTHORIZATION = "X-Authorization";

    /**
     * token失效
     */
    private static Integer TOKEN_EXPIRATION_EXCEPTION = 800004;

    /**
     * session失效
     */
    private static Integer SESSION_EXPIRATION_EXCEPTION = 800003;

    /**
     * token非法
     */
    private static Integer TOKEN_ILEGAL = 800002;

    /**
     * Token未找到
     */
    private static Integer TOKEN_NOT_FOUND = 800001;

    /**
     * Token校验异常
     */
    private static Integer TOKEN_VERIFY_EXCEPTION = 800000;

    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
    {
        String authorization = getAuthorization(request);

        logger.info(
                "AuthorizationInterceptor.preHandle url is:{},Authorization:{}",
                request.getRequestURI(), authorization);

        if (StringUtils.isBlank(authorization)
                || StringUtils.isEmpty(authorization))
        {
            writeResult(response, TOKEN_NOT_FOUND, "AccessToken不存在！");
            return false;
        }

        try
        {
            return validateJwtToken(authorization, request, response);

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

    private boolean validateJwtToken(String authorization,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {

        try
        {
            String json = "";
            Claims claims = JwtUtil.parseJWT(authorization,
                    RSAUtil.getPublicKey(GlobalConstants.JWT_TOKEN_PUBLIC_KEY));
            json = claims.getSubject();
            Date exprirarionDate = claims.getExpiration();
            Date now = new Date();
            if (now.after(exprirarionDate))
            {
                writeResult(response, TOKEN_EXPIRATION_EXCEPTION,"Session会话已过期！");
                return false;
            }

            if (StringUtils.isBlank(json) && StringUtils.isEmpty(json))
            {
                writeResult(response, TOKEN_EXPIRATION_EXCEPTION, "认证信息被未认证！");
                return false;
            }

            JSONObject user = JSON.parseObject(json);
            String userId = (String) user.get("userId");

            String key = GlobalConstants.USER_CLIENT_SIDE_LOGIN_SESSION_KEY
                    .replace("{USER_ID}", userId).replace(
                            "{KEY}", DigestUtils.md5Hex(json));

            String auth = RedisUtil.get(key);
            if (StringUtils.isBlank(auth) || StringUtils.isEmpty(auth))
            {
                writeResult(response, SESSION_EXPIRATION_EXCEPTION, "session失效！");
            }
            
            JwtClientSideSubject jwtSubject = ObjectSerializer.instance()
                    .deserialize(auth, JwtClientSideSubject.class);
            
            if (jwtSubject == null)
            {
                writeResult(response, SESSION_EXPIRATION_EXCEPTION,
                        "session失效！");
                return false;
            }

            if (!jwtSubject.getUserId().equals(userId))
            {
                writeResult(response, TOKEN_ILEGAL, "认证Token禁止窜用！");
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

    private String getAuthorization(HttpServletRequest request)
    {
        String authorization = request.getHeader("X-Authorization");
        authorization = authorization != null ? authorization : request
                .getParameter(AUTHORIZATION);
        return authorization;
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
