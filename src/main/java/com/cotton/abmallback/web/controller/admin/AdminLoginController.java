package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.SysRole;
import com.cotton.abmallback.model.SysUser;
import com.cotton.abmallback.model.vo.admin.SysUserVo;
import com.cotton.abmallback.service.SysRoleService;
import com.cotton.abmallback.service.SysUserService;
import com.cotton.base.common.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * AdminLoginController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/29
 */
@Controller
@RequestMapping("admin/un")
public class AdminLoginController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    public AdminLoginController(SysUserService sysUserService, SysRoleService sysRoleService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public RestResponse<SysUserVo> login(String username, String password, HttpServletResponse httpResponse, HttpSession session) {


        if (StringUtils.isBlank(username)) {
            return RestResponse.getFailedResponse(300, "用户名不能为空");
        }

        if (StringUtils.isBlank(password)) {
            return RestResponse.getFailedResponse(300, "密码不能为空");
        }

        SysUser model = new SysUser();
        model.setUsername(username);
        model.setIsDeleted(false);
        SysUser sysUser = sysUserService.selectOne(model);

        if (null == sysUser) {

            return RestResponse.getFailedResponse(300, "用户名或密码不正确");
        }

        if (!password.equals(sysUser.getPassword())) {
            return RestResponse.getFailedResponse(300, "用户名或密码不正确");
        }

        List<SysRole> sysRoles = sysRoleService.getRolesBySysUserId(sysUser.getId());

        SysUserVo sysUserVo = new SysUserVo(sysUser,sysRoles);

        session.setAttribute("user", sysUserVo);

        return RestResponse.getSuccesseResponse(sysUserVo);

    }

    @ResponseBody
    @RequestMapping(value = "/logout")
    public RestResponse<Void> logout(HttpSession session) {
        session.removeAttribute("user");
        return RestResponse.getSuccesseResponse();

    }



}
