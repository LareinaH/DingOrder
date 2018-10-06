package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "goods_specification")
public class GoodsSpecification extends BaseModel {
    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 规格编号
     */
    @Column(name = "goods_specification_no")
    private String goodsSpecificationNo;

    /**
     * 规格名字
     */
    @Column(name = "goods_specification_name")
    private String goodsSpecificationName;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 原价
     */
    private BigDecimal price;

    /**
     * 优惠价
     */
    @Column(name = "preferential_price")
    private BigDecimal preferentialPrice;

    /**
     * 是否上架 0:不上架 1:上架
     */
    @Column(name = "is_on_sell")
    private Boolean isOnSell;

    /**
     * 销量
     */
    @Column(name = "sales_amount")
    private Integer salesAmount;

    /**
     * 是否在回收站
     */
    @Column(name = "is_recycled")
    private Boolean isRecycled;

    /**
     * 获取商品id
     *
     * @return goods_id - 商品id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
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

    /**
     * 获取规格名字
     *
     * @return goods_specification_name - 规格名字
     */
    public String getGoodsSpecificationName() {
        return goodsSpecificationName;
    }

    /**
     * 设置规格名字
     *
     * @param goodsSpecificationName 规格名字
     */
    public void setGoodsSpecificationName(String goodsSpecificationName) {
        this.goodsSpecificationName = goodsSpecificationName == null ? null : goodsSpecificationName.trim();
    }

    /**
     * 获取库存
     *
     * @return stock - 库存
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * 设置库存
     *
     * @param stock 库存
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * 获取原价
     *
     * @return price - 原价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置原价
     *
     * @param price 原价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取优惠价
     *
     * @return preferential_price - 优惠价
     */
    public BigDecimal getPreferentialPrice() {
        return preferentialPrice;
    }

    /**
     * 设置优惠价
     *
     * @param preferentialPrice 优惠价
     */
    public void setPreferentialPrice(BigDecimal preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    /**
     * 获取是否上架 0:不上架 1:上架
     *
     * @return is_on_sell - 是否上架 0:不上架 1:上架
     */
    public Boolean getIsOnSell() {
        return isOnSell;
    }

    /**
     * 设置是否上架 0:不上架 1:上架
     *
     * @param isOnSell 是否上架 0:不上架 1:上架
     */
    public void setIsOnSell(Boolean isOnSell) {
        this.isOnSell = isOnSell;
    }

    /**
     * 获取销量
     *
     * @return sales_amount - 销量
     */
    public Integer getSalesAmount() {
        return salesAmount;
    }

    /**
     * 设置销量
     *
     * @param salesAmount 销量
     */
    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    /**
     * 获取是否在回收站
     *
     * @return is_recycled - 是否在回收站
     */
    public Boolean getIsRecycled() {
        return isRecycled;
    }

    /**
     * 设置是否在回收站
     *
     * @param isRecycled 是否在回收站
     */
    public void setIsRecycled(Boolean isRecycled) {
        this.isRecycled = isRecycled;
    }
}