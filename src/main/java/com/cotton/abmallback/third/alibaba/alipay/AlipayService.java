package com.cotton.abmallback.third.alibaba.alipay;

import java.math.BigDecimal;
import java.util.Map;

/**
 * AlipayService
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
public interface AlipayService {

     Boolean notifyVerify(Map<String, String> params);

     Map<String, Object> payWithAlipay(String tradeNo, BigDecimal amount);

}
