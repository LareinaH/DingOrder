package com.cotton.abmallback.manager.impl;

import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.manager.DistributionManager;
import com.cotton.abmallback.manager.OrdersManager;
import com.cotton.abmallback.manager.PromotionManager;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.service.MemberService;
import com.cotton.abmallback.service.OrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OrdersManagerImpl
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/15
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersManagerImpl implements OrdersManager {

    private final OrdersService ordersService;

    private final PromotionManager promotionManager;

    private final MemberService memberService;

    private final DistributionManager distributionManager;

    public OrdersManagerImpl(OrdersService ordersService, PromotionManager promotionManager, MemberService memberService, DistributionManager distributionManager) {
        this.ordersService = ordersService;
        this.promotionManager = promotionManager;
        this.memberService = memberService;
        this.distributionManager = distributionManager;
    }


    @Override
    public boolean paySuccess(String orderNo, String tradeNo, String payMode) {

        //根据orderNo 找到订单
        Orders model = new Orders();
        model.setOrderNo(orderNo);
        model.setIsDeleted(false);
        Orders orders = ordersService.selectOne(model);

        if(null == orders){
            return false;
        }

        if(orders.getOrderStatus().equals(OrderStatusEnum.WAIT_BUYER_PAY.name())) {

            //更新状态
            orders.setTradeNo(tradeNo);
            orders.setPayMode(payMode);
            orders.setIsPaid(true);
            orders.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.name());

            if (ordersService.update(orders)) {
                Member member = memberService.getById(orders.getMemberId());

                member.setMoneyTotalSpend(member.getMoneyTotalSpend().add(orders.getTotalMoney()));

                memberService.update(member);

                return true;

            }
            return false;
        }

        return true;
    }

    @Override
    public boolean afterPaySuccess(String orderNo) {

        //根据orderNo 找到订单
        Orders model = new Orders();
        model.setOrderNo(orderNo);
        model.setIsDeleted(false);
        Orders orders = ordersService.selectOne(model);

        if(null == orders){
            return false;
        }

        Member member = memberService.getById(orders.getMemberId());


        //先分润
        distributionManager.orderDistribute(orderNo);

        //提升等级
        promotionManager.memberPromotion(member,orders.getId());
        return false;
    }
}
