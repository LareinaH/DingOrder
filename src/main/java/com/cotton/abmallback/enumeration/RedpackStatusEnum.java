package com.cotton.abmallback.enumeration;

/**
 * RedpackStatusEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/12
 */
public enum RedpackStatusEnum {

    /**
     *
     */
    WAIT_SEND("等待发放"),
    SEND("已经发放"),
    SEND_SCUUESS("发放成功"),
    WAIT_RETURN("等待退回"),
    RETURN("已经退回");


    private String displayName;

    RedpackStatusEnum(String displayName){

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
