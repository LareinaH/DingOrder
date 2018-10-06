package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "goods")
public class Goods extends BaseModel {
    /**
     * 商品编号
     */
    @Column(name = "goods_no")
    private String goodsNo;

    /**
     * 商品名字
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 缩略图
     */
    private String thums;

    /**
     * 商品详情图
     */
    private String images;

    /**
     * 商品简介
     */
    private String breif;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 优惠价
     */
    @Column(name = "preferential_price")
    private BigDecimal preferentialPrice;

    /**
     * 原价
     */
    private String price;

    /**
     * 分组id
     */
    @Column(name = "group_id")
    private Long groupId;

    /**
     * 是否上架 0:不上架 1:上架
     */
    @Column(name = "is_on_sell")
    private Boolean isOnSell;

    /**
     * 虚拟销量
     */
    @Column(name = "virtual_sales_amount")
    private Integer virtualSalesAmount;

    /**
     * 销量
     */
    @Column(name = "sales_amount")
    private Integer salesAmount;

    /**
     * 是否正在活动中
     */
    @Column(name = "is_in_activities")
    private Boolean isInActivities;

    /**
     * 是否在回收站
     */
    @Column(name = "is_recycled")
    private Boolean isRecycled;

    /**
     * 关键字
     */
    private String keywords;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 获取商品编号
     *
     * @return goods_no - 商品编号
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置商品编号
     *
     * @param goodsNo 商品编号
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo == null ? null : goodsNo.trim();
    }

    /**
     * 获取商品名字
     *
     * @return goods_name - 商品名字
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名字
     *
     * @param goodsName 商品名字
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
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
     * 获取缩略图
     *
     * @return thums - 缩略图
     */
    public String getThums() {
        return thums;
    }

    /**
     * 设置缩略图
     *
     * @param thums 缩略图
     */
    public void setThums(String thums) {
        this.thums = thums == null ? null : thums.trim();
    }

    /**
     * 获取商品详情图
     *
     * @return images - 商品详情图
     */
    public String getImages() {
        return images;
    }

    /**
     * 设置商品详情图
     *
     * @param images 商品详情图
     */
    public void setImages(String images) {
        this.images = images == null ? null : images.trim();
    }

    /**
     * 获取商品简介
     *
     * @return breif - 商品简介
     */
    public String getBreif() {
        return breif;
    }

    /**
     * 设置商品简介
     *
     * @param breif 商品简介
     */
    public void setBreif(String breif) {
        this.breif = breif == null ? null : breif.trim();
    }

    /**
     * 获取商品单位
     *
     * @return unit - 商品单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置商品单位
     *
     * @param unit 商品单位
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
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
     * 获取原价
     *
     * @return price - 原价
     */
    public String getPrice() {
        return price;
    }

    /**
     * 设置原价
     *
     * @param price 原价
     */
    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    /**
     * 获取分组id
     *
     * @return group_id - 分组id
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * 设置分组id
     *
     * @param groupId 分组id
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
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
     * 获取虚拟销量
     *
     * @return virtual_sales_amount - 虚拟销量
     */
    public Integer getVirtualSalesAmount() {
        return virtualSalesAmount;
    }

    /**
     * 设置虚拟销量
     *
     * @param virtualSalesAmount 虚拟销量
     */
    public void setVirtualSalesAmount(Integer virtualSalesAmount) {
        this.virtualSalesAmount = virtualSalesAmount;
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
     * 获取是否正在活动中
     *
     * @return is_in_activities - 是否正在活动中
     */
    public Boolean getIsInActivities() {
        return isInActivities;
    }

    /**
     * 设置是否正在活动中
     *
     * @param isInActivities 是否正在活动中
     */
    public void setIsInActivities(Boolean isInActivities) {
        this.isInActivities = isInActivities;
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

    /**
     * 获取关键字
     *
     * @return keywords - 关键字
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置关键字
     *
     * @param keywords 关键字
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * 获取商品描述
     *
     * @return description - 商品描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置商品描述
     *
     * @param description 商品描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
}