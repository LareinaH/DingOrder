package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "sys_role")
public class SysRole extends BaseModel {
    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色显示名
     */
    @Column(name = "display_name")
    private String displayName;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取角色显示名
     *
     * @return display_name - 角色显示名
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * 设置角色显示名
     *
     * @param displayName 角色显示名
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