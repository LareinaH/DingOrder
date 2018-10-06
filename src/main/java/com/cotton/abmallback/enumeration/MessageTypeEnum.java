package com.cotton.abmallback.enumeration;

/**
 * MessageTypeEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/1
 */
public enum MessageTypeEnum {

    /**
     * 晋级奖励
     */
    PROMOTION_AWARD("晋级奖励"),
    /**
     * 分享奖励
     */
    SHARE_AWARD("分享奖励"),

    /**
     * 复购奖励
     */
    REPURCHASE_AWARD("复购奖励"),

    /**
     * 高管奖励
     */
    EXECUTIVE_AWARD("高管奖励"),

    /**
     * 平台通知
     */
    SYSTEM_NOTICE("平台通知"),

    /**
     * 活动奖励
     */
    ACTIVITY_AWARD("活动奖励");


    private String displayName;

    MessageTypeEnum(String displayName){

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
