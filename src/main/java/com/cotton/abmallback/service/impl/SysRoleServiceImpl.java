package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.model.SysUserRole;
import com.cotton.abmallback.service.SysUserRoleService;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.SysRole;
import com.cotton.abmallback.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.MalformedObjectNameException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    private final SysUserRoleService sysUserRoleService;

    public SysRoleServiceImpl(SysUserRoleService sysUserRoleService) {
        this.sysUserRoleService = sysUserRoleService;
    }


    @Override
    public  List<SysRole> getRolesBySysUserId(long userId) {

        List<SysRole> sysRoles = new ArrayList<>();

        SysUserRole model = new SysUserRole();
        model.setUserId(userId);
        model.setIsDeleted(false);

        List<SysUserRole> sysUserRoleList = sysUserRoleService.queryList(model);

        if (sysUserRoleList.size() > 0) {

            for(SysUserRole sysUserRole : sysUserRoleList) {
                SysRole sysRole = getById(sysUserRole.getRoleId());

                if(null != sysRole){
                    sysRoles.add(sysRole);
                }
            }
        }
        return sysRoles;
    }
}