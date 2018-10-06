package com.cotton.abmallback.enumeration;

/**
 * ShopActivityStatusEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/8
 */
public enum ShopActivityStatusEnum {

    CONFIRMED("已保存"),
    NOT_BEGIN("未开始"),
    PROCESSING("进行中"),
    FINISH("已经结束");


    private String displayName;

    ShopActivityStatusEnum(String displayName){

        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
