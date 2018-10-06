package com.cotton.abmallback.service.impl;

import com.cotton.abmallback.enumeration.ShopActivityStatusEnum;
import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.ShopActivities;
import com.cotton.abmallback.service.ShopActivitiesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * ShopActivities
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/24
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopActivitiesServiceImpl extends BaseServiceImpl<ShopActivities> implements ShopActivitiesService {
    @Override
    public void beginActivities() {

        //找到开始时间小于当前，未开始的活动
        Example example = new Example(ShopActivities.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        criteria.andLessThanOrEqualTo("gmtStart",new Date());
        criteria.andGreaterThanOrEqualTo("gmtEnd",new Date());
        criteria.andEqualTo("activityStatus",ShopActivityStatusEnum.NOT_BEGIN.name());

        List<ShopActivities> shopActivitiesList = queryList(example);

        for(ShopActivities shopActivities : shopActivitiesList){
            shopActivities.setActivityStatus(ShopActivityStatusEnum.PROCESSING.name());
            update(shopActivities);
        }
    }

    @Override
    public void finishActivities() {

        //找到结束时间小于当前，已经结束的活动
        Example example = new Example(ShopActivities.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isDeleted", false);

        criteria.andLessThanOrEqualTo("gmtEnd",new Date());
        criteria.andEqualTo("activityStatus",ShopActivityStatusEnum.PROCESSING.name());

        List<ShopActivities> shopActivitiesList = queryList(example);

        for(ShopActivities shopActivities : shopActivitiesList){
            shopActivities.setActivityStatus(ShopActivityStatusEnum.FINISH.name());
            update(shopActivities);
        }
    }
}