package com.cotton.abmallback.web.controller.admin;

import com.cotton.abmallback.model.DistributionConfig;
import com.cotton.abmallback.model.vo.ConfigObject;
import com.cotton.abmallback.service.DistributionConfigService;
import com.cotton.abmallback.web.controller.ABMallAdminBaseController;
import com.cotton.base.common.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DistributionConfigController
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/27
 */
@Controller
@RequestMapping("/admin/distributionConfig")
public class DistributionConfigController extends ABMallAdminBaseController {

    private final DistributionConfigService distributionConfigService;

    public DistributionConfigController(DistributionConfigService distributionConfigService) {
        this.distributionConfigService = distributionConfigService;
    }

    @ResponseBody
    @RequestMapping(value = "/config")
    public RestResponse<Map<String, Object>> getConfig() {

        Map<String, Object> map = new HashMap<>(2);

        DistributionConfig model = new DistributionConfig();
        model.setIsDeleted(false);

        List<DistributionConfig> distributionConfigList =  distributionConfigService.queryList(model);

        for(DistributionConfig distributionConfig : distributionConfigList){

            ConfigObject.ConfigItem obj = ConfigObject.createConfigItem();
            obj.setId(distributionConfig.getId());
            obj.setItem(distributionConfig.getItem());
            obj.setValue(distributionConfig.getValue());
            obj.setDefaultVaule(distributionConfig.getDefaultValue());

            map.put(obj.getItem(),obj);
        }
        return RestResponse.getSuccesseResponse(map);
    }

    @ResponseBody
    @RequestMapping(value = "/updateConfig", method = {RequestMethod.POST})
    public RestResponse<Void> updateConfig(@RequestBody ConfigObject configObject) {

        for(ConfigObject.ConfigItem configItem : configObject.getDataList()) {

            DistributionConfig msgMessageTemplate = new DistributionConfig();
            msgMessageTemplate.setValue(configItem.getValue());
            msgMessageTemplate.setId(configItem.getId());
            distributionConfigService.update(msgMessageTemplate);
        }

        return RestResponse.getSuccesseResponse();
    }
}
