package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.enumeration.OrderStatusEnum;
import com.cotton.abmallback.model.Member;
import com.cotton.abmallback.service.MemberService;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.Orders;
import com.cotton.abmallback.service.OrdersService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.time.*;
import java.util.Date;
import java.util.List;

/**
 * Orders
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/9
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrdersServiceImpl extends BaseServiceImpl<Orders> implements OrdersService {

    private final MemberService memberService;

    public OrdersServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public void systemCancelOrder() {

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderStatus",OrderStatusEnum.WAIT_BUYER_PAY);
        criteria.andEqualTo("isDeleted",false);

        //下单超过30分钟未付款的订单，被取消
        LocalDateTime localTime = LocalDateTime.now();
        LocalDateTime localTime1 = localTime.minusMinutes(30L);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localTime1.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());

        criteria.andLessThanOrEqualTo("gmtCreate",date);

        List<Orders> ordersList = queryList(example);

        if(ordersList.size() > 0){
            for(Orders orders : ordersList){
                orders.setOrderStatus(OrderStatusEnum.CANCEL.name());
                update(orders);
            }
        }

    }

    @Override
    public void systemConfirmedOrder() {

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderStatus",OrderStatusEnum.WAIT_CONFIRM);
        criteria.andEqualTo("isDeleted",false);

        //发货超过15天的订单，被自动确认收货
        LocalDateTime localTime = LocalDateTime.now();
        LocalDateTime localTime1 = localTime.minusDays(15L);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localTime1.atZone(zoneId);

        Date date = Date.from(zdt.toInstant());

        criteria.andLessThanOrEqualTo("deliveryTime",date);

        List<Orders> ordersList = queryList(example);

        if(ordersList.size() > 0){
            for(Orders orders : ordersList){
                orders.setOrderStatus(OrderStatusEnum.CONFIRMED.name());
                update(orders);
            }
        }

    }
}