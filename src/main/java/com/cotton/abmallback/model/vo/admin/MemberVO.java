package com.cotton.abmallback.model.vo.admin;

import com.cotton.abmallback.model.Member;

/**
 * MemberVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/25
 */
public class MemberVO extends Member {

    /**
     * 引荐人昵称
     */
    private String referrerName;

    /**
     * 有效订单数目
     */
    private Long ordersCount = 0L;

    /**
     * 团队中white数目
     */
    private Long teamWhiteCount = 0L;

    /**
     * 团队中Agent数目
     */
    private Long teamAgentCount = 0L;

    /**
     * 团队中V1数目
     */
    private Long teamV1Count = 0L;

    /**
     * 团队中V2数目
     */
    private Long teamV2Count = 0L;

    /**
     * 团队中V3数目
     */
    private Long teamV3Count = 0L;


    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public Long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(Long ordersCount) {
        this.ordersCount = ordersCount;
    }

    public Long getTeamWhiteCount() {
        return teamWhiteCount;
    }

    public void setTeamWhiteCount(Long teamWhiteCount) {
        this.teamWhiteCount = teamWhiteCount;
    }

    public Long getTeamAgentCount() {
        return teamAgentCount;
    }

    public void setTeamAgentCount(Long teamAgentCount) {
        this.teamAgentCount = teamAgentCount;
    }

    public Long getTeamV1Count() {
        return teamV1Count;
    }

    public void setTeamV1Count(Long teamV1Count) {
        this.teamV1Count = teamV1Count;
    }

    public Long getTeamV2Count() {
        return teamV2Count;
    }

    public void setTeamV2Count(Long teamV2Count) {
        this.teamV2Count = teamV2Count;
    }

    public Long getTeamV3Count() {
        return teamV3Count;
    }

    public void setTeamV3Count(Long teamV3Count) {
        this.teamV3Count = teamV3Count;
    }
}
