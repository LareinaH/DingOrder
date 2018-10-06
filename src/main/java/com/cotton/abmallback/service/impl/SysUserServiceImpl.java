package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.model.SysUser;
import com.cotton.abmallback.service.SysUserService;
import com.cotton.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

}
