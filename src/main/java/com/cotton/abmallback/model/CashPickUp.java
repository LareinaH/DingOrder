package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "cash_pick_up")
public class CashPickUp extends BaseModel {
    /**
     * 会员id
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 提现金额
     */
    private BigDecimal money;

    /**
     * 提现账号
     */
    @Column(name = "account_no")
    private String accountNo;

    /**
     * 体现状态
     */
    @Column(name = "cash_status")
    private String cashStatus;

    /**
     * 备注
     */
    private String remark;

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
     * 获取提现金额
     *
     * @return money - 提现金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置提现金额
     *
     * @param money 提现金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取提现账号
     *
     * @return account_no - 提现账号
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * 设置提现账号
     *
     * @param accountNo 提现账号
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    /**
     * 获取体现状态
     *
     * @return cash_status - 体现状态
     */
    public String getCashStatus() {
        return cashStatus;
    }

    /**
     * 设置体现状态
     *
     * @param cashStatus 体现状态
     */
    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus == null ? null : cashStatus.trim();
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}