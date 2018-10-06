package com.cotton.abmallback.enumeration;

/**
 *  PlatformMessageLevelEnum
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/1
 */
public enum PlatformMessageLevelEnum {

    /**
     * 重要紧急
     */
    IMPORTANT_EMERGENCY("重要紧急"),
    /**
     * 重要非紧急
     */
    IMPORTANT_NOT_EMERGENCY("重要非紧急"),
    /**
     * 非重要紧急
     */
    NOT_IMPORTANT_EMERGENCY("非重要紧急"),
    /**
     * 非紧急非重要
     */
    NOT_IMPORTANT_NOT_EMERGENCY("非紧急非重要");


    private String displayName;

    PlatformMessageLevelEnum(String displayName){

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
