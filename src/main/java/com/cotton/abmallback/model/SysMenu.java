package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "sys_menu")
public class SysMenu extends BaseModel {
    /**
     * 菜单显示名
     */
    @Column(name = "display_name")
    private String displayName;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 获取菜单显示名
     *
     * @return display_name - 菜单显示名
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 设置菜单显示名
     *
     * @param displayName 菜单显示名
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    /**
     * @return parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}