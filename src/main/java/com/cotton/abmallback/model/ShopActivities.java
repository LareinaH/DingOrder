package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_activities")
public class ShopActivities extends BaseModel {
    /**
     * 活动名称
     */
    @Column(name = "activity_name")
    private String activityName;

    /**
     * 活动简介
     */
    @Column(name = "activity_brief")
    private String activityBrief;

    /**
     * 开始时间
     */
    @Column(name = "gmt_start")
    private Date gmtStart;

    /**
     * 结束时间
     */
    @Column(name = "gmt_end")
    private Date gmtEnd;

    @Column(name = "activity_status")
    private String activityStatus;

    /**
     * 活动详情
     */
    @Column(name = "activity_desc")
    private String activityDesc;

    /**
     * 获取活动名称
     *
     * @return activity_name - 活动名称
     */
    public String getActivityName() {
        return activityName;
    }

    /**
     * 设置活动名称
     *
     * @param activityName 活动名称
     */
    public void setActivityName(String activityName) {
        this.activityName = activityName == null ? null : activityName.trim();
    }

    /**
     * 获取活动简介
     *
     * @return activity_brief - 活动简介
     */
    public String getActivityBrief() {
        return activityBrief;
    }

    /**
     * 设置活动简介
     *
     * @param activityBrief 活动简介
     */
    public void setActivityBrief(String activityBrief) {
        this.activityBrief = activityBrief == null ? null : activityBrief.trim();
    }

    /**
     * 获取开始时间
     *
     * @return gmt_start - 开始时间
     */
    public Date getGmtStart() {
        return gmtStart;
    }

    /**
     * 设置开始时间
     *
     * @param gmtStart 开始时间
     */
    public void setGmtStart(Date gmtStart) {
        this.gmtStart = gmtStart;
    }

    /**
     * 获取结束时间
     *
     * @return gmt_end - 结束时间
     */
    public Date getGmtEnd() {
        return gmtEnd;
    }

    /**
     * 设置结束时间
     *
     * @param gmtEnd 结束时间
     */
    public void setGmtEnd(Date gmtEnd) {
        this.gmtEnd = gmtEnd;
    }

    /**
     * @return activity_status
     */
    public String getActivityStatus() {
        return activityStatus;
    }

    /**
     * @param activityStatus
     */
    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus == null ? null : activityStatus.trim();
    }

    /**
     * 获取活动详情
     *
     * @return activity_desc - 活动详情
     */
    public String getActivityDesc() {
        return activityDesc;
    }

    /**
     * 设置活动详情
     *
     * @param activityDesc 活动详情
     */
    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc == null ? null : activityDesc.trim();
    }
}