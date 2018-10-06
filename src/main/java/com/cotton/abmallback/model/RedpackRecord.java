package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "redpack_record")
public class RedpackRecord extends BaseModel {
    /**
     * 提现记录id
     */
    @Column(name = "cash_pick_up_id")
    private Long cashPickUpId;

    /**
     * 红包服务商
     */
    @Column(name = "redpeck_source")
    private String redpeckSource;

    /**
     * 发放金额
     */
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    /**
     * 红包编号
     */
    @Column(name = "redpack_sn")
    private String redpackSn;

    /**
     * 红包地址
     */
    @Column(name = "redpack_url")
    private String redpackUrl;

    /**
     * 红包状态  0:等待发放；1：已发放；2：等待退回；3：已退回；4：发放成功
     */
    private String status;

    /**
     * 获取提现记录id
     *
     * @return cash_pick_up_id - 提现记录id
     */
    public Long getCashPickUpId() {
        return cashPickUpId;
    }

    /**
     * 设置提现记录id
     *
     * @param cashPickUpId 提现记录id
     */
    public void setCashPickUpId(Long cashPickUpId) {
        this.cashPickUpId = cashPickUpId;
    }

    /**
     * 获取红包服务商
     *
     * @return redpeck_source - 红包服务商
     */
    public String getRedpeckSource() {
        return redpeckSource;
    }

    /**
     * 设置红包服务商
     *
     * @param redpeckSource 红包服务商
     */
    public void setRedpeckSource(String redpeckSource) {
        this.redpeckSource = redpeckSource == null ? null : redpeckSource.trim();
    }

    /**
     * 获取发放金额
     *
     * @return total_money - 发放金额
     */
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    /**
     * 设置发放金额
     *
     * @param totalMoney 发放金额
     */
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    /**
     * 获取红包编号
     *
     * @return redpack_sn - 红包编号
     */
    public String getRedpackSn() {
        return redpackSn;
    }

    /**
     * 设置红包编号
     *
     * @param redpackSn 红包编号
     */
    public void setRedpackSn(String redpackSn) {
        this.redpackSn = redpackSn == null ? null : redpackSn.trim();
    }

    /**
     * 获取红包地址
     *
     * @return redpack_url - 红包地址
     */
    public String getRedpackUrl() {
        return redpackUrl;
    }

    /**
     * 设置红包地址
     *
     * @param redpackUrl 红包地址
     */
    public void setRedpackUrl(String redpackUrl) {
        this.redpackUrl = redpackUrl == null ? null : redpackUrl.trim();
    }

    /**
     * 获取红包状态  0:等待发放；1：已发放；2：等待退回；3：已退回；4：发放成功
     *
     * @return status - 红包状态  0:等待发放；1：已发放；2：等待退回；3：已退回；4：发放成功
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置红包状态  0:等待发放；1：已发放；2：等待退回；3：已退回；4：发放成功
     *
     * @param status 红包状态  0:等待发放；1：已发放；2：等待退回；3：已退回；4：发放成功
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}