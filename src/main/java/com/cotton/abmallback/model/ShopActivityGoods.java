package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "shop_activity_goods")
public class ShopActivityGoods extends BaseModel {
    /**
     * 活动id
     */
    @Column(name = "activity_id")
    private Long activityId;

    /**
     * 商品id
     */
    @Column(name = "goods_id")
    private Long goodsId;

    /**
     * 获取活动id
     *
     * @return activity_id - 活动id
     */
    public Long getActivityId() {
        return activityId;
    }

    /**
     * 设置活动id
     *
     * @param activityId 活动id
     */
    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

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
}