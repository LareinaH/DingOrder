package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "order_goods")
public class OrderGoods extends BaseModel {
    /**
     * 订单编号
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 商品编号
     */
    @Column(name = "good_id")
    private Long goodId;

    @Column(name = "good_specification_id")
    private Long goodSpecificationId;

    /**
     * 商品名称
     */
    @Column(name = "good_name")
    private String goodName;

    @Column(name = "goods_specification_name")
    private String goodsSpecificationName;

    /**
     * 商品缩略图
     */
    @Column(name = "good_thum")
    private String goodThum;

    /**
     * 商品价格
     */
    @Column(name = "good_price")
    private BigDecimal goodPrice;

    /**
     * 商品数量
     */
    @Column(name = "good_num")
    private Integer goodNum;

    /**
     * 规格编号
     */
    @Column(name = "goods_specification_no")
    private String goodsSpecificationNo;

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
     * @return good_id - 商品编号
     */
    public Long getGoodId() {
        return goodId;
    }

    /**
     * 设置商品编号
     *
     * @param goodId 商品编号
     */
    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    /**
     * @return good_specification_id
     */
    public Long getGoodSpecificationId() {
        return goodSpecificationId;
    }

    /**
     * @param goodSpecificationId
     */
    public void setGoodSpecificationId(Long goodSpecificationId) {
        this.goodSpecificationId = goodSpecificationId;
    }

    /**
     * 获取商品名称
     *
     * @return good_name - 商品名称
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * 设置商品名称
     *
     * @param goodName 商品名称
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }

    /**
     * @return goods_specification_name
     */
    public String getGoodsSpecificationName() {
        return goodsSpecificationName;
    }

    /**
     * @param goodsSpecificationName
     */
    public void setGoodsSpecificationName(String goodsSpecificationName) {
        this.goodsSpecificationName = goodsSpecificationName == null ? null : goodsSpecificationName.trim();
    }

    /**
     * 获取商品缩略图
     *
     * @return good_thum - 商品缩略图
     */
    public String getGoodThum() {
        return goodThum;
    }

    /**
     * 设置商品缩略图
     *
     * @param goodThum 商品缩略图
     */
    public void setGoodThum(String goodThum) {
        this.goodThum = goodThum == null ? null : goodThum.trim();
    }

    /**
     * 获取商品价格
     *
     * @return good_price - 商品价格
     */
    public BigDecimal getGoodPrice() {
        return goodPrice;
    }

    /**
     * 设置商品价格
     *
     * @param goodPrice 商品价格
     */
    public void setGoodPrice(BigDecimal goodPrice) {
        this.goodPrice = goodPrice;
    }

    /**
     * 获取商品数量
     *
     * @return good_num - 商品数量
     */
    public Integer getGoodNum() {
        return goodNum;
    }

    /**
     * 设置商品数量
     *
     * @param goodNum 商品数量
     */
    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    /**
     * 获取规格编号
     *
     * @return goods_specification_no - 规格编号
     */
    public String getGoodsSpecificationNo() {
        return goodsSpecificationNo;
    }

    /**
     * 设置规格编号
     *
     * @param goodsSpecificationNo 规格编号
     */
    public void setGoodsSpecificationNo(String goodsSpecificationNo) {
        this.goodsSpecificationNo = goodsSpecificationNo == null ? null : goodsSpecificationNo.trim();
    }
}