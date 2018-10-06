package com.cotton.abmallback.third.wechat;

import java.math.BigDecimal;

/**
 *  聚分云 微信发红包
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/12
 */

public interface JufenyunService {

    /**
     * 发送红包
     * @param openId
     * @param money
     * @return
     */
    JufenyunResultObject sendRedpack(String openId, BigDecimal money);

    JufenyunResultObject getRedpackInfo(String redpack_sn);
}
