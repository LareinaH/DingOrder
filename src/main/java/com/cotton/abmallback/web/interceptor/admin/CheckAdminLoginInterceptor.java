package com.cotton.abmallback.web.interceptor.admin;

/**
 * CheckLoginInterceptor
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/25
 */

import com.alibaba.fastjson.JSONObject;
import com.cotton.abmallback.model.vo.admin.SysUserVo;
import com.cotton.abmallback.service.SysUserService;
import com.cotton.abmallback.web.PermissionContext;
import com.cotton.base.common.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

public class CheckAdminLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private SysUserService sysUserService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {

        //清空context
        PermissionContext.clearThreadVariable();

        HttpSession session = httpServletRequest.getSession();

        SysUserVo sysUser = (SysUserVo)session.getAttribute("user");

        if (sysUser!= null) {
            PermissionContext.setSysUser(sysUser);
            return true;
        }

        setReLogin(httpServletRequest,httpServletResponse);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {

        //清理登录数据
        PermissionContext.clearThreadVariable();
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {

    }

    private void setReLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        RestResponse<Void> restResponse = RestResponse.getUnauthorizedFailedResponse();

        //转换成json
        String jsonString = JSONObject.toJSONString(restResponse);

        //写入response
        try {
            httpServletResponse.setContentType("application/json");

            //获取OutputStream输出流
            OutputStream outputStream = httpServletResponse.getOutputStream();

            byte[] dataByteArr = jsonString.getBytes("UTF-8");
            outputStream.write(dataByteArr);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}