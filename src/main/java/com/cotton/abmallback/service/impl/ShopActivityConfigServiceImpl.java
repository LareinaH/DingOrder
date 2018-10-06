package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.ShopActivityConfig;
import com.cotton.abmallback.service.ShopActivityConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ShopActivityConfig
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/7/5
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShopActivityConfigServiceImpl extends BaseServiceImpl<ShopActivityConfig> implements ShopActivityConfigService {
}