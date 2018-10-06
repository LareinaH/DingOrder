package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "goods_group_goods")
public class GoodsGroupGoods extends BaseModel {
    /**
     * 商品分组id
     */
    @Column(name = "goods_group_id")
    private String goodsGroupId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private String goodsId;

    /**
     * 获取商品分组id
     *
     * @return goods_group_id - 商品分组id
     */
    public String getGoodsGroupId() {
        return goodsGroupId;
    }

    /**
     * 设置商品分组id
     *
     * @param goodsGroupId 商品分组id
     */
    public void setGoodsGroupId(String goodsGroupId) {
        this.goodsGroupId = goodsGroupId == null ? null : goodsGroupId.trim();
    }

    /**
     * 获取商品id
     *
     * @return goods_id - 商品id
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }
}