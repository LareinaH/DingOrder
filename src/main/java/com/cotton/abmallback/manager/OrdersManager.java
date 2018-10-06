package com.cotton.abmallback.manager;

/**
 * OrdersManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/15
 */
public interface OrdersManager {

    /**
     * 付款成功
     */
    boolean paySuccess(String orderNo,String tradeNo,String payMode);

    /**
     * 付款成功
     */
    boolean afterPaySuccess(String orderNo);
}
