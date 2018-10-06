package com.cotton.abmallback.web.controller;

import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.web.PermissionContext;
import com.cotton.base.controller.BaseController;

/**
 * ABMallFrontBaseController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */
public class ABMallFrontBaseController extends BaseController {

    protected long getCurrentMemberId(){
        Member member = PermissionContext.getMember();
        if(null != member){
            return member.getId();
        }
        return 0L;
    }

    protected Member getCurrentMember(){
        return  PermissionContext.getMember();
    }
}
