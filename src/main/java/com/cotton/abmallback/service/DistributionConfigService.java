package com.cotton.abmallback.service;

import com.cotton.base.service.BaseService;
import com.cotton.abmallback.model.DistributionConfig;

import java.util.Map;

/**
 * DistributionConfig
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
public interface DistributionConfigService extends BaseService<DistributionConfig> {

    Map<String,DistributionConfig> getAllDistributionConfig();


}
