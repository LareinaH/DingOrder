package com.cotton.abmallback.model;

import java.math.BigDecimal;

/**
 * AccountMoneyFlowVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/4
 */
public class AccountMoneyFlowVO extends AccountMoneyFlow{

    /**
     * 订单总价
     */
    private BigDecimal orderTotalMoney;

    /**
     * 商品名称
     */
    private String orderGoodName;

    /**
     * 商品规格
     */
    private String orderGoodsSpecificationName;

    /**
     * 商品缩略图
     */
    private String orderGoodThum;


    /**
     * 会员名头像
     */
    private String buyerPhoto;


    /**
     * 会员名
     */
    private String buyerName;


    public BigDecimal getOrderTotalMoney() {
        return orderTotalMoney;
    }

    public void setOrderTotalMoney(BigDecimal orderTotalMoney) {
        this.orderTotalMoney = orderTotalMoney;
    }

    public String getOrderGoodName() {
        return orderGoodName;
    }

    public void setOrderGoodName(String orderGoodName) {
        this.orderGoodName = orderGoodName;
    }

    public String getOrderGoodsSpecificationName() {
        return orderGoodsSpecificationName;
    }

    public void setOrderGoodsSpecificationName(String orderGoodsSpecificationName) {
        this.orderGoodsSpecificationName = orderGoodsSpecificationName;
    }

    public String getOrderGoodThum() {
        return orderGoodThum;
    }

    public void setOrderGoodThum(String orderGoodThum) {
        this.orderGoodThum = orderGoodThum;
    }

    public String getBuyerPhoto() {
        return buyerPhoto;
    }

    public void setBuyerPhoto(String buyerPhoto) {
        this.buyerPhoto = buyerPhoto;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }
}
