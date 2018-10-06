package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.SysUserRole;
import com.cotton.abmallback.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysUserRole
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRole> implements SysUserRoleService {
}