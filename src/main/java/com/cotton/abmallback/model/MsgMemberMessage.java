package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "msg_member_message")
public class MsgMemberMessage extends BaseModel {
    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息分类:分享奖励,晋级奖励,高管薪酬,平台通知
     */
    private String type;

    /**
     * 会员id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 关联的平台消息详情id
     */
    @Column(name = "system_message_id")
    private Long systemMessageId;

    /**
     * 升级到的等级
     */
    @Column(name = "promotion_level")
    private String promotionLevel;

    @Column(name = "is_read")
    private Boolean isRead;

    /**
     * 消息内容
     */
    private String content;

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
     * 获取消息分类:分享奖励,晋级奖励,高管薪酬,平台通知
     *
     * @return type - 消息分类:分享奖励,晋级奖励,高管薪酬,平台通知
     */
    public String getType() {
        return type;
    }

    /**
     * 设置消息分类:分享奖励,晋级奖励,高管薪酬,平台通知
     *
     * @param type 消息分类:分享奖励,晋级奖励,高管薪酬,平台通知
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取会员id
     *
     * @return member_id - 会员id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置会员id
     *
     * @param memberId 会员id
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取关联的平台消息详情id
     *
     * @return system_message_id - 关联的平台消息详情id
     */
    public Long getSystemMessageId() {
        return systemMessageId;
    }

    /**
     * 设置关联的平台消息详情id
     *
     * @param systemMessageId 关联的平台消息详情id
     */
    public void setSystemMessageId(Long systemMessageId) {
        this.systemMessageId = systemMessageId;
    }

    /**
     * 获取升级到的等级
     *
     * @return promotion_level - 升级到的等级
     */
    public String getPromotionLevel() {
        return promotionLevel;
    }

    /**
     * 设置升级到的等级
     *
     * @param promotionLevel 升级到的等级
     */
    public void setPromotionLevel(String promotionLevel) {
        this.promotionLevel = promotionLevel == null ? null : promotionLevel.trim();
    }

    /**
     * @return is_read
     */
    public Boolean getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     */
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}