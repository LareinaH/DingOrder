package com.cotton;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * spring-boot 启动类
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */
@SpringBootApplication
@EnableScheduling
public class Application {

    /**
     * 程序入口
     * @param args 入参
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
    }

}