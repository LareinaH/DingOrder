package com.cotton.abmallback.web;


import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.vo.admin.SysUserVo;

import java.util.HashMap;
import java.util.Map;

/**
 * PermissionContext 权限上下文信息，用以存储当前用户相关联的信息
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/25
 */
public class PermissionContext {

    private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();

    private static final String LOGIN_SESSION_ID_MEMBER = "member";

    private static final String LOGIN_SESSION_ID_USER = "user";

    public static void clearThreadVariable() {
        threadLocal.remove();
    }

    public static void setMember(Member member) {
        Map map = (Map) threadLocal.get();
        if (map == null) {
            map = new HashMap();
        }
        map.put(LOGIN_SESSION_ID_MEMBER, member);
        threadLocal.set(map);
    }

    public static Member getMember() {
        Map map = (Map) threadLocal.get();
        if (map != null) {
            return (Member) map.get(LOGIN_SESSION_ID_MEMBER);
        }
        return null;
    }

    public static void setSysUser(SysUserVo sysUser) {
        Map map = (Map) threadLocal.get();
        if (map == null) {
            map = new HashMap();
        }
        map.put(LOGIN_SESSION_ID_USER, sysUser);
        threadLocal.set(map);
    }

    public static SysUserVo getSysUser() {
        Map map = (Map) threadLocal.get();
        if (map != null) {
            return (SysUserVo) map.get(LOGIN_SESSION_ID_USER);
        }
        return null;
    }
}
