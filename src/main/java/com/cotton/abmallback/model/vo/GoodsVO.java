package com.cotton.abmallback.model.vo;

import com.cotton.abmallback.model.Goods;
import com.cotton.abmallback.model.GoodsSpecification;

import java.util.List;

/**
 * GoodsVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/22
 */
public class GoodsVO extends Goods {

    private List<GoodsSpecification> goodsSpecificationList;

    public List<GoodsSpecification> getGoodsSpecificationList() {
        return goodsSpecificationList;
    }

    public void setGoodsSpecificationList(List<GoodsSpecification> goodsSpecificationList) {
        this.goodsSpecificationList = goodsSpecificationList;
    }
}
