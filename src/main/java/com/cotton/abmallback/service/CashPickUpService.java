package com.cotton.abmallback.service;

import com.cotton.base.service.BaseService;
import com.cotton.abmallback.model.CashPickUp;

/**
 * CashPickUp
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */
public interface CashPickUpService extends BaseService<CashPickUp> {

    /**
     * 发红包
     */
    void checkRedpack();


}
