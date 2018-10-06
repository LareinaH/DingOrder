package com.cotton.abmallback.service;

import com.cotton.base.service.BaseService;
import com.cotton.abmallback.model.ShopActivities;

/**
 * ShopActivities
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
public interface ShopActivitiesService extends BaseService<ShopActivities> {

    /**
     * 启动活动
     */
    void beginActivities();

    /**
     * 结束活动
     */
    void finishActivities();
}
