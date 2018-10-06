package com.cotton.abmallback.enumeration;

/**
 * MessageTypeEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/1
 */
public enum PlatformMessageStatusEnum {

    /**
     * 待发布
     */
    WAIT_PUBLISH("待发布"),
    /**
     * 已发布
     */
    PUBLISHED("已发布"),
    /**
     * 取消
     */
    CANCEL("取消");

    private String displayName;

    PlatformMessageStatusEnum(String displayName){

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
