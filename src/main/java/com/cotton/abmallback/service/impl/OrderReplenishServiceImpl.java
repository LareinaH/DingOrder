package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.OrderReplenish;
import com.cotton.abmallback.service.OrderReplenishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OrderReplenish
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/8
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OrderReplenishServiceImpl extends BaseServiceImpl<OrderReplenish> implements OrderReplenishService {
}