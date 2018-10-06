package com.cotton.abmallback.manager;

/**
 * DistributionManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
public interface DistributionManager {

    /**
     * 订单分销
     * @param orderNo
     */
    void orderDistribute(String orderNo);
}
