package com.cotton.abmallback.manager;

import com.cotton.abmallback.model.MsgPlatformMessage;

import java.math.BigDecimal;

/**
 * MessageManager
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
public interface MessageManager {

    /**
     * 发送平台消息
     * @param
     */
    void sendSystemNotice();

    /**
     * 发送平台消息
     * @param msgPlatformMessage
     */
    void sendSystemNotice(MsgPlatformMessage msgPlatformMessage);

    /**
     * 发送分享奖励消息
     * @param memberId
     */
    void sendShareAward(long memberId, BigDecimal money);


    /**
     * 发送复购奖励消息
     * @param memberId
     */
    void sendRepurchaseAward(long memberId, BigDecimal money);

    /**
     * 发送高管奖励消息
     * @param memberId
     */
    void sendExecutiveAward(long memberId,BigDecimal money);

    /**
     * 发送升级消息
     * @param memberId
     */
    void sendPromotionAward(long memberId,String promotionLevel, BigDecimal money);

}
