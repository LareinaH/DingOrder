package com.cotton.abmallback.model.vo.admin;

import com.cotton.abmallback.model.GoodsGroup;


/**
 * GoodsGroupVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/8
 */
public class GoodsGroupVO extends GoodsGroup {

    private long goodsCount;

    public long getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(long goodsCount) {
        this.goodsCount = goodsCount;
    }
}
