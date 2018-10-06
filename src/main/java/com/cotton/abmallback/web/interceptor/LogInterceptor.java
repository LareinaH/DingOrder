package com.cotton.abmallback.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LogInterceptor
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/21
 */
public class LogInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        //打印当前请求
        this.logger.info("请求信息为:" + httpServletRequest.toString());

        return true;
    }
}
