package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.SysRoleMenu;
import com.cotton.abmallback.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysRoleMenu
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu> implements SysRoleMenuService {
}