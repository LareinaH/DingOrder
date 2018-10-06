package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "msg_message_template")
public class MsgMessageTemplate extends BaseModel {
    /**
     * 分享奖励,晋级奖励,高管薪酬
     */
    private String type;

    /**
     * 配置项
     */
    private String item;

    /**
     * 值
     */
    private String value;

    /**
     * 默认值
     */
    @Column(name = "default_value")
    private String defaultValue;

    /**
     * 获取分享奖励,晋级奖励,高管薪酬
     *
     * @return type - 分享奖励,晋级奖励,高管薪酬
     */
    public String getType() {
        return type;
    }

    /**
     * 设置分享奖励,晋级奖励,高管薪酬
     *
     * @param type 分享奖励,晋级奖励,高管薪酬
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取配置项
     *
     * @return item - 配置项
     */
    public String getItem() {
        return item;
    }

    /**
     * 设置配置项
     *
     * @param item 配置项
     */
    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    /**
     * 获取值
     *
     * @return value - 值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * 获取默认值
     *
     * @return default_value - 默认值
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * 设置默认值
     *
     * @param defaultValue 默认值
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue == null ? null : defaultValue.trim();
    }
}