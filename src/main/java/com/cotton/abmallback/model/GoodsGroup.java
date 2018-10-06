package com.cotton.abmallback.model;

import com.cotton.base.model.BaseModel;
import javax.persistence.*;

@Table(name = "goods_group")
public class GoodsGroup extends BaseModel {
    /**
     * 商品分组编号
     */
    @Column(name = "goods_group_no")
    private String goodsGroupNo;

    /**
     * 商品分组名字
     */
    @Column(name = "goods_group_name")
    private String goodsGroupName;

    /**
     * 获取商品分组编号
     *
     * @return goods_group_no - 商品分组编号
     */
    public String getGoodsGroupNo() {
        return goodsGroupNo;
    }

    /**
     * 设置商品分组编号
     *
     * @param goodsGroupNo 商品分组编号
     */
    public void setGoodsGroupNo(String goodsGroupNo) {
        this.goodsGroupNo = goodsGroupNo == null ? null : goodsGroupNo.trim();
    }

    /**
     * 获取商品分组名字
     *
     * @return goods_group_name - 商品分组名字
     */
    public String getGoodsGroupName() {
        return goodsGroupName;
    }

    /**
     * 设置商品分组名字
     *
     * @param goodsGroupName 商品分组名字
     */
    public void setGoodsGroupName(String goodsGroupName) {
        this.goodsGroupName = goodsGroupName == null ? null : goodsGroupName.trim();
    }
}