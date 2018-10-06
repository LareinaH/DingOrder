package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "content_study")
public class ContentStudy extends BaseModel {
    /**
     * 教程标题
     */
    private String title;

    /**
     * 教程文字简介
     */
    private String brief;

    /**
     * 教程显示图
     */
    @Column(name = "ads_url")
    private String adsUrl;

    /**
     * 教程状态:待发布,发布,结束发布
     */
    @Column(name = "message_status")
    private String messageStatus;

    /**
     * 教程内容
     */
    @Column(name = "message_detail")
    private String messageDetail;

    /**
     * 获取教程标题
     *
     * @return title - 教程标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置教程标题
     *
     * @param title 教程标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取教程文字简介
     *
     * @return brief - 教程文字简介
     */
    public String getBrief() {
        return brief;
    }

    /**
     * 设置教程文字简介
     *
     * @param brief 教程文字简介
     */
    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    /**
     * 获取教程显示图
     *
     * @return ads_url - 教程显示图
     */
    public String getAdsUrl() {
        return adsUrl;
    }

    /**
     * 设置教程显示图
     *
     * @param adsUrl 教程显示图
     */
    public void setAdsUrl(String adsUrl) {
        this.adsUrl = adsUrl == null ? null : adsUrl.trim();
    }

    /**
     * 获取教程状态:待发布,发布,结束发布
     *
     * @return message_status - 教程状态:待发布,发布,结束发布
     */
    public String getMessageStatus() {
        return messageStatus;
    }

    /**
     * 设置教程状态:待发布,发布,结束发布
     *
     * @param messageStatus 教程状态:待发布,发布,结束发布
     */
    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus == null ? null : messageStatus.trim();
    }

    /**
     * 获取教程内容
     *
     * @return message_detail - 教程内容
     */
    public String getMessageDetail() {
        return messageDetail;
    }

    /**
     * 设置教程内容
     *
     * @param messageDetail 教程内容
     */
    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail == null ? null : messageDetail.trim();
    }
}