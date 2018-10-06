package com.cotton.abmallback.enumeration;

/**
 * OrderReturnStatusEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/8
 */
public enum  OrderReturnStatusEnum {

    /**
     * 正常
     */
    NORMAL("正常"),
    REPLENISHMENT("补货");


    private String displayName;

    OrderReturnStatusEnum(String displayName){

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
