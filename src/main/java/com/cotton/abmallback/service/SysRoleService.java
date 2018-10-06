package com.cotton.abmallback.service;

import com.cotton.base.service.BaseService;
import com.cotton.abmallback.model.SysRole;

import java.util.List;

/**
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

public interface SysRoleService extends BaseService<SysRole> {

    List<SysRole> getRolesBySysUserId(long userId);
}
