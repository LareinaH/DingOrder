package com.cotton.abmallback.model.vo;

import com.cotton.abmallback.model.Orders;

public class OrdersWithGoodsInfo extends Orders {
    /**
     * 规格编号
     */
    private String goodsSpecificationNo;

    /**
     * 商品名称
     */
    private String goodName;

    private String goodsSpecificationName;

    /**
     * 商品数量
     */
    private Integer goodNum;

    public String getGoodsSpecificationNo() {
        return goodsSpecificationNo;
    }

    public void setGoodsSpecificationNo(String goodsSpecificationNo) {
        this.goodsSpecificationNo = goodsSpecificationNo;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodsSpecificationName() {
        return goodsSpecificationName;
    }

    public void setGoodsSpecificationName(String goodsSpecificationName) {
        this.goodsSpecificationName = goodsSpecificationName;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }
}
