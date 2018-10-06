package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "sys_role_menu")
public class SysRoleMenu extends BaseModel {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "menu_id")
    private Long menuId;

    /**
     * @return role_id
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return menu_id
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}