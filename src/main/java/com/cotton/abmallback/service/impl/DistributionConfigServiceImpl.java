package com.cotton.abmallback.service.impl;

import com.cotton.base.service.impl.BaseServiceImpl;
import com.cotton.abmallback.model.DistributionConfig;
import com.cotton.abmallback.service.DistributionConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DistributionConfig
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/26
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DistributionConfigServiceImpl extends BaseServiceImpl<DistributionConfig> implements DistributionConfigService {
    @Override
    public Map<String, DistributionConfig> getAllDistributionConfig() {

        Map<String,DistributionConfig> map = new HashMap<>();

        List<DistributionConfig> distributionConfigList =  queryList();

        for(DistributionConfig distributionConfig : distributionConfigList){

            if(!distributionConfig.getIsDeleted()){
                map.put(distributionConfig.getItem(),distributionConfig);
            }

        }
        return map;
    }
}