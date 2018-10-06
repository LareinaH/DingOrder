package com.cotton.abmallback.model.vo;

import com.cotton.abmallback.model.OrderGoods;
import com.cotton.abmallback.model.Orders;

import java.util.List;

/**
 * OrdersVO
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/3
 */
public class OrdersVO extends Orders {

    public List<OrderGoods> getOrderGoodsList() {
        return orderGoodsList;
    }

    public void setOrderGoodsList(List<OrderGoods> orderGoodsList) {
        this.orderGoodsList = orderGoodsList;
    }

    private List<OrderGoods> orderGoodsList;
}
