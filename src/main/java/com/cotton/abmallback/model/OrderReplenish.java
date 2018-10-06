package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "order_replenish")
public class OrderReplenish extends BaseModel {
    /**
     * 订单编号
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 商品编号
     */
    @Column(name = "logistic_code")
    private String logisticCode;

    /**
     * 获取订单编号
     *
     * @return order_id - 订单编号
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单编号
     *
     * @param orderId 订单编号
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品编号
     *
     * @return logistic_code - 商品编号
     */
    public String getLogisticCode() {
        return logisticCode;
    }

    /**
     * 设置商品编号
     *
     * @param logisticCode 商品编号
     */
    public void setLogisticCode(String logisticCode) {
        this.logisticCode = logisticCode == null ? null : logisticCode.trim();
    }
}