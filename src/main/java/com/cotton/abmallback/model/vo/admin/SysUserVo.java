package com.cotton.abmallback.model.vo.admin;

import com.cotton.abmallback.model.SysRole;
import com.cotton.abmallback.model.SysUser;

import java.util.ArrayList;
import java.util.List;

/**
 * SysUserVo
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/30
 */
public class SysUserVo {

    public SysUserVo(SysUser sysUser, List<SysRole>sysRoleList){

        this.id = sysUser.getId();
        this.username = sysUser.getUsername();

        if(sysRoleList != null){
            List<String> stringList = new ArrayList<>();

            for(SysRole sysRole:sysRoleList){
                stringList.add(sysRole.getName());
            }
            this.currentAuthority = stringList;
        }

    }

    private Long id;

    private String username;

    private List<String> currentAuthority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getCurrentAuthority() {
        return currentAuthority;
    }

    public void setCurrentAuthority(List<String> currentAuthority) {
        this.currentAuthority = currentAuthority;
    }
}
