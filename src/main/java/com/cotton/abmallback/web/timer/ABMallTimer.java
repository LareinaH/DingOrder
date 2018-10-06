package com.cotton.abmallback.web.timer;

import com.cotton.abmallback.manager.MessageManager;
import com.cotton.abmallback.manager.PromotionManager;
import com.cotton.abmallback.service.CashPickUpService;
import com.cotton.abmallback.service.OrdersService;
import com.cotton.abmallback.service.ShopActivitiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ABMallTimmer
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */

@Component
public class ABMallTimer {

    private final OrdersService ordersService;

    private final CashPickUpService cashPickUpService;

    private final ShopActivitiesService shopActivitiesService;

    private final PromotionManager promotionManager;

    private final MessageManager messageManager;

    private Logger logger = LoggerFactory.getLogger(ABMallTimer.class);



    public ABMallTimer(OrdersService ordersService, CashPickUpService cashPickUpService, ShopActivitiesService shopActivitiesService, PromotionManager promotionManager, MessageManager messageManager) {
        this.ordersService = ordersService;
        this.cashPickUpService = cashPickUpService;
        this.shopActivitiesService = shopActivitiesService;
        this.promotionManager = promotionManager;
        this.messageManager = messageManager;
    }


    /**
     * 定时取消已经超时的订单
     */
    @Scheduled(cron = "0 */5 * * * ?" )
    public void systemCancelOrder() {

        logger.info("定时取消已经超时的订单定时器benin");

        ordersService.systemCancelOrder();
    }

    /**
     * 定时确认收货
     */
    @Scheduled(cron = "0 */30 * * * ?" )
    public void systemConfirmedOrder() {

        logger.info("定时确认收货定时器benin");
        ordersService.systemConfirmedOrder();
    }

    /**
     * 查看红包发送状态
     */
    @Scheduled(cron = "0 */1 * * * ?" )
    public void checkRedpack() {

        logger.info("查看红包发送状态");
        cashPickUpService.checkRedpack();

    }

    /**
     * 发送平台消息
     */
    @Scheduled(cron = "0 */5 * * * ?" )
    public void sendPlatformMessage() {

        logger.info("发送平台消息定时器benin");
        messageManager.sendSystemNotice();
    }

    /**
     * 关闭已经结束的活动
     */
    @Scheduled(cron = "0 */1 * * * ?" )
    public void finishActivities() {

        logger.info("关闭已经结束的活动定时器benin");
        shopActivitiesService.finishActivities();
    }

    /**
     *
     */
    @Scheduled(cron = "0 */1 * * * ?" )
    public void promotMember() {

        logger.info("校验v2用户能否升级v3");
        promotionManager.memberPromotionAll();

    }
}
