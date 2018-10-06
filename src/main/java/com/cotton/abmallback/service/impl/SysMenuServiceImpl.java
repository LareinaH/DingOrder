package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.SysMenu;
import com.cotton.abmallback.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SysMenuServiceImpl
 *
 * @author lareina_h
 * @version 1.0
 * @date
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements SysMenuService {
}