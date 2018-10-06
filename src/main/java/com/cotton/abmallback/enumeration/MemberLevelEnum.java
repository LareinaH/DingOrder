package com.cotton.abmallback.enumeration;

/**
 * MemberLevelEnum
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/14
 */
public enum MemberLevelEnum {

    /**
     * 小白
     */
    WHITE("小白"),

    /**
     * 代理人
     */
    AGENT("代言人"),

    /**
     * V1
     */
    V1("销售明星"),

    /**
     * V2
     */
    V2("销售经理"),

    /**
     * V3
     */
    V3("执行董事");

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    MemberLevelEnum(String displayName){

        this.displayName = displayName;
    }

    public static MemberLevelEnum getNextLevel(MemberLevelEnum levelEnum){
        switch (levelEnum){
            case WHITE:
                return MemberLevelEnum.AGENT;
            case AGENT:
                return MemberLevelEnum.V1;
            case V1:
                return MemberLevelEnum.V2;
            case V2:
                return MemberLevelEnum.V3;
            default:
                return null;
        }
    }
}
