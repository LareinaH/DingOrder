package com.cotton.abmallback.enumeration;

/**
 * AccountMoneyTypeEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/1
 */
public enum  AccountMoneyTypeEnum {

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
     * 活动奖励
     */
    ACTIVITY_AWARD("活动奖励");


    private String name;

    AccountMoneyTypeEnum(String name){

        this.name = name;
    }
}
