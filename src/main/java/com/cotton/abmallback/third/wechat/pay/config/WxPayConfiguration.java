package com.cotton.abmallback.third.wechat.pay.config;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WxPayConfiguration
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/20
 */
@Configuration
@ConditionalOnClass(WxPayService.class)
@EnableConfigurationProperties(WxPayProperties.class)
public class WxPayConfiguration {
    @Autowired
    private WxPayProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public WxPayConfig payConfig() {
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(this.properties.getAppId());
        payConfig.setMchId(this.properties.getMchId());
        payConfig.setMchKey(this.properties.getMchKey());
        payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
        payConfig.setKeyPath(this.properties.getKeyPath());

        return payConfig;
    }


    @Bean(name = "wxPayService")
    //@ConditionalOnMissingBean
    public WxPayService wxPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    @Bean(name = "wxMpPayService")
    //@ConditionalOnMissingBean
    public WxPayService wxMpPayService(WxPayConfig payConfig) {
        WxPayService wxPayService = new WxPayServiceImpl();

        WxPayConfig mpPayConfig = new WxPayConfig();
        mpPayConfig.setAppId(this.properties.getMpAppId());
        mpPayConfig.setMchId(this.properties.getMchId());
        mpPayConfig.setMchKey(this.properties.getMchKey());
        mpPayConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
        mpPayConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
        mpPayConfig.setKeyPath(this.properties.getKeyPath());
        wxPayService.setConfig(mpPayConfig);
        return wxPayService;
    }

}
