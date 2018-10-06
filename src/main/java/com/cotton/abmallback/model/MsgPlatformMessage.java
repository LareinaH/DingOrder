package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "msg_platform_message")
public class MsgPlatformMessage extends BaseModel {
    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息简介
     */
    private String brief;

    /**
     * 通知广告图
     */
    @Column(name = "ads_url")
    private String adsUrl;

    /**
     * 发布时间
     */
    @Column(name = "gmt_publish")
    private Date gmtPublish;

    /**
     * 通知级别
     */
    private String level;

    /**
     * 消息状态:待发布,发布,取消
     */
    @Column(name = "message_status")
    private String messageStatus;

    /**
     * 消息内容
     */
    @Column(name = "message_detail")
    private String messageDetail;

    /**
     * 获取消息标题
     *
     * @return title - 消息标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置消息标题
     *
     * @param title 消息标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取消息简介
     *
     * @return brief - 消息简介
     */
    public String getBrief() {
        return brief;
    }

    /**
     * 设置消息简介
     *
     * @param brief 消息简介
     */
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    /**
     * 获取通知广告图
     *
     * @return ads_url - 通知广告图
     */
    public String getAdsUrl() {
        return adsUrl;
    }

    /**
     * 设置通知广告图
     *
     * @param adsUrl 通知广告图
     */
    public void setAdsUrl(String adsUrl) {
        this.adsUrl = adsUrl == null ? null : adsUrl.trim();
    }

    /**
     * 获取发布时间
     *
     * @return gmt_publish - 发布时间
     */
    public Date getGmtPublish() {
        return gmtPublish;
    }

    /**
     * 设置发布时间
     *
     * @param gmtPublish 发布时间
     */
    public void setGmtPublish(Date gmtPublish) {
        this.gmtPublish = gmtPublish;
    }

    /**
     * 获取通知级别
     *
     * @return level - 通知级别
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置通知级别
     *
     * @param level 通知级别
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * 获取消息状态:待发布,发布,取消
     *
     * @return message_status - 消息状态:待发布,发布,取消
     */
    public String getMessageStatus() {
        return messageStatus;
    }

    /**
     * 设置消息状态:待发布,发布,取消
     *
     * @param messageStatus 消息状态:待发布,发布,取消
     */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus == null ? null : messageStatus.trim();
    }

    /**
     * 获取消息内容
     *
     * @return message_detail - 消息内容
     */
    public String getMessageDetail() {
        return messageDetail;
    }

    /**
     * 设置消息内容
     *
     * @param messageDetail 消息内容
     */
    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail == null ? null : messageDetail.trim();
    }
}