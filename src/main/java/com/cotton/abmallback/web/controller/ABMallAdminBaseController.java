package com.cotton.abmallback.web.controller;

import com.cotton.abmallback.model.vo.admin.SysUserVo;
import com.cotton.abmallback.web.PermissionContext;
import com.cotton.base.common.RestResponse;
import com.cotton.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ABMallFrontBaseController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

@Controller
@RequestMapping("/admin")
public class ABMallAdminBaseController extends BaseController {

    @ResponseBody
    @RequestMapping(value = "/getLoginUser")
    public RestResponse<SysUserVo> getLoginUser() {

        return RestResponse.getSuccesseResponse(getCurrentUser());
    }



    protected SysUserVo getCurrentUser(){
        return  PermissionContext.getSysUser();
    }


}
