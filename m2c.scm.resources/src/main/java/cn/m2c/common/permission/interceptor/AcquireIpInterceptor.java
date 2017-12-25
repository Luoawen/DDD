/**
 * Project Name:m2c.operate.resources
 * File Name:AcquireIpInterceptor.java
 * Package Name:cn.m2c.common.permission.interceptor
 * Date:2017年12月8日上午10:42:49
 * Copyright (c) 2017, marker@lcjh.com (李云龙) All Rights Reserved.
 *
 */

package cn.m2c.common.permission.interceptor;

import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.m2c.ddd.common.logger.model.Attach;
import cn.m2c.ddd.common.serializer.ObjectSerializer;

/**
 * ClassName:AcquireIpInterceptor <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2017年12月8日 上午10:42:49 <br/>
 * 
 * @author marker
 * @version
 * @since JDK 1.8
 * @see
 */
public class AcquireIpInterceptor extends OncePerRequestFilter
{

    private static Logger LOGGER = LoggerFactory
            .getLogger(AcquireIpInterceptor.class);

    private static final String ATTACH = "attach";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        ModifyParametersWrapper mParametersWrapper = new ModifyParametersWrapper(
                request);
        String attach = getAttach(request);
        if (StringUtils.isNotBlank(attach) && StringUtils.isNotEmpty(attach))
        {
            try
            {
                Attach _attach = ObjectSerializer.instance().deserialize(
                        attach, Attach.class);
                String ip = this.getIpAddress(request);
                if (StringUtils.isNotBlank(ip) && StringUtils.isNotEmpty(ip))
                {
                    _attach.setIp(ip);
                }
                attach = ObjectSerializer.instance().serialize(_attach);
                mParametersWrapper.putHeader("attach", attach);
            }
            catch (Exception e)
            {
                LOGGER.error(
                        "AcquireIpInterceptor Exception e:" + e.getMessage(), e);

            }
        }
        filterChain.doFilter(mParametersWrapper, response);
    }

    private String getAttach(HttpServletRequest request)
    {
        String attach = request.getHeader("attach");
        attach = attach != null ? attach : request.getParameter(ATTACH);
        return attach;
    }

    public String getIpAddress(HttpServletRequest request)
    {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Forwarded-For");
        if (LOGGER.isInfoEnabled())
        {
            LOGGER.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip={}"
                    + ip);
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("Proxy-Client-IP");
                if (LOGGER.isInfoEnabled())
                {
                    LOGGER.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip={}"
                            + ip);
                }
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("WL-Proxy-Client-IP");
                if (LOGGER.isInfoEnabled())
                {
                    LOGGER.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip={}"
                            + ip);
                }
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("HTTP_CLIENT_IP");
                if (LOGGER.isInfoEnabled())
                {
                    LOGGER.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip={}"
                            + ip);
                }
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
                if (LOGGER.isInfoEnabled())
                {
                    LOGGER.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip={}"
                            + ip);
                }
            }
            if (ip == null || ip.length() == 0
                    || "unknown".equalsIgnoreCase(ip))
            {
                ip = request.getRemoteAddr();
                if (LOGGER.isInfoEnabled())
                {
                    LOGGER.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip={}"
                            + ip);
                }
            }
        }
        else if (ip.length() > 15)
        {
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++)
            {
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp)))
                {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

    private class ModifyParametersWrapper extends HttpServletRequestWrapper
    {
        private final Map<String, String> customHeaders;

        ModifyParametersWrapper(HttpServletRequest request)
        {
            super(request);
            this.customHeaders = new HashMap<>();
        }

        void putHeader(String name, String value)
        {
            this.customHeaders.put(name, value);
        }

        public String getHeader(String name)
        {
            // check the custom headers first
            String headerValue = customHeaders.get(name);

            if (headerValue != null)
            {
                return headerValue;
            }
            // else return from into the original wrapped object
            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        public Enumeration<String> getHeaderNames()
        {
            // create a set of the custom header names
            Set<String> set = new HashSet<>(customHeaders.keySet());

            // now add the headers from the wrapped request object
            Enumeration<String> e = ((HttpServletRequest) getRequest())
                    .getHeaderNames();
            while (e.hasMoreElements())
            {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }
    }

}
