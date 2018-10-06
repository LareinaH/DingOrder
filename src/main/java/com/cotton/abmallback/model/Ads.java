package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "ads")
public class Ads extends BaseModel {
    /**
     * 广告类型（位置）
     */
    @Column(name = "ad_type")
    private String adType;

    /**
     * 邀请码码对应的会员等级,当type=INVITING_CODE_BACKGROUND时有效
     */
    private String level;

    /**
     * 图片地址
     */
    @Column(name = "ad_url")
    private String adUrl;

    /**
     * 链接类型：goods | activity | nothing | url
     */
    @Column(name = "link_type")
    private String linkType;

    /**
     * 链接参数，如果类型是goods  参数是goodsid  如果类型活动，参数是 活动id，如果类型是url 参数是跳转url
     */
    @Column(name = "link_params")
    private String linkParams;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 获取广告类型（位置）
     *
     * @return ad_type - 广告类型（位置）
     */
    public String getAdType() {
        return adType;
    }

    /**
     * 设置广告类型（位置）
     *
     * @param adType 广告类型（位置）
     */
    public void setAdType(String adType) {
        this.adType = adType == null ? null : adType.trim();
    }

    /**
     * 获取邀请码码对应的会员等级,当type=INVITING_CODE_BACKGROUND时有效
     *
     * @return level - 邀请码码对应的会员等级,当type=INVITING_CODE_BACKGROUND时有效
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置邀请码码对应的会员等级,当type=INVITING_CODE_BACKGROUND时有效
     *
     * @param level 邀请码码对应的会员等级,当type=INVITING_CODE_BACKGROUND时有效
     */
    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    /**
     * 获取图片地址
     *
     * @return ad_url - 图片地址
     */
    public String getAdUrl() {
        return adUrl;
    }

    /**
     * 设置图片地址
     *
     * @param adUrl 图片地址
     */
    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl == null ? null : adUrl.trim();
    }

    /**
     * 获取链接类型：goods | activity | nothing | url
     *
     * @return link_type - 链接类型：goods | activity | nothing | url
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * 设置链接类型：goods | activity | nothing | url
     *
     * @param linkType 链接类型：goods | activity | nothing | url
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType == null ? null : linkType.trim();
    }

    /**
     * 获取链接参数，如果类型是goods  参数是goodsid  如果类型活动，参数是 活动id，如果类型是url 参数是跳转url
     *
     * @return link_params - 链接参数，如果类型是goods  参数是goodsid  如果类型活动，参数是 活动id，如果类型是url 参数是跳转url
     */
    public String getLinkParams() {
        return linkParams;
    }

    /**
     * 设置链接参数，如果类型是goods  参数是goodsid  如果类型活动，参数是 活动id，如果类型是url 参数是跳转url
     *
     * @param linkParams 链接参数，如果类型是goods  参数是goodsid  如果类型活动，参数是 活动id，如果类型是url 参数是跳转url
     */
    public void setLinkParams(String linkParams) {
        this.linkParams = linkParams == null ? null : linkParams.trim();
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}