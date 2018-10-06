package com.cotton.abmallback.manager;

import com.cotton.abmallback.model.Member;

/**
 * PromotionManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/3
 */
public interface PromotionManager {

    /**
     * 会员晋级（判断会员是否达到晋级条件，如果达到，晋级。）
     * @param member 会员
     * @param orderId 触发的订单编号
     */
    void memberPromotion(Member member,long orderId);

    void memberPromotionAll();

}
