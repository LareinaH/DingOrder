package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "account_money_flow")
public class AccountMoneyFlow extends BaseModel {
    /**
     * 会员id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 流水分类:分享奖励,晋级奖励,高管薪酬
     */
    @Column(name = "account_money_type")
    private String accountMoneyType;

    /**
     * 晋级到的级别
     */
    @Column(name = "promotion_level")
    private String promotionLevel;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 分销金额
     */
    @Column(name = "distribut_money")
    private BigDecimal distributMoney;

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
     * 获取流水分类:分享奖励,晋级奖励,高管薪酬
     *
     * @return account_money_type - 流水分类:分享奖励,晋级奖励,高管薪酬
     */
    public String getAccountMoneyType() {
        return accountMoneyType;
    }

    /**
     * 设置流水分类:分享奖励,晋级奖励,高管薪酬
     *
     * @param accountMoneyType 流水分类:分享奖励,晋级奖励,高管薪酬
     */
    public void setAccountMoneyType(String accountMoneyType) {
        this.accountMoneyType = accountMoneyType == null ? null : accountMoneyType.trim();
    }

    /**
     * 获取晋级到的级别
     *
     * @return promotion_level - 晋级到的级别
     */
    public String getPromotionLevel() {
        return promotionLevel;
    }

    /**
     * 设置晋级到的级别
     *
     * @param promotionLevel 晋级到的级别
     */
    public void setPromotionLevel(String promotionLevel) {
        this.promotionLevel = promotionLevel == null ? null : promotionLevel.trim();
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取分销金额
     *
     * @return distribut_money - 分销金额
     */
    public BigDecimal getDistributMoney() {
        return distributMoney;
    }

    /**
     * 设置分销金额
     *
     * @param distributMoney 分销金额
     */
    public void setDistributMoney(BigDecimal distributMoney) {
        this.distributMoney = distributMoney;
    }
}