package com.cotton.abmallback.web.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cotton.abmallback.web.interceptor.admin.CheckAdminLoginInterceptor;
import com.cotton.abmallback.web.interceptor.front.CheckLoginInterceptor;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * WebConfigurer
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/6/19
 */

@Configuration
public class WebConfigurer extends BaseConfigurer{

    /**
     * 覆盖方法configureMessageConverters，使用fastJson
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {

        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //配置fastJson 参数
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteDateUseDateFormat);

        fastConverter.setFastJsonConfig(fastJsonConfig);

        HttpMessageConverter<?> converter = fastConverter;

        return new HttpMessageConverters(converter);
    }

    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //是否发送Cookie信息
        config.setAllowCredentials(true);
        //放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        //放行哪些原始域(头部信息)
        config.addAllowedHeader("*");
        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        //config.addExposedHeader("*");

        //2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }


    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        return loggingFilter;
    }


    @Bean
    CheckLoginInterceptor checkLoginInterceptor(){
        return new CheckLoginInterceptor();
    }

    @Bean
    CheckAdminLoginInterceptor checkAdminLoginInterceptor(){
        return new CheckAdminLoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(checkLoginInterceptor()).addPathPatterns("/**").
                excludePathPatterns("/un/member/*","/wechat/**","/alipay/**","/admin/**","/interface/**","/actuator/*","/404","/error");

        registry.addInterceptor(checkAdminLoginInterceptor()).addPathPatterns("/admin/**").
                excludePathPatterns("/admin/un/**");
        super.addInterceptors(registry);
    }

}
