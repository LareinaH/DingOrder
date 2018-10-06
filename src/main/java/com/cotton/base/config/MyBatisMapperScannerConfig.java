package com.cotton.base.config;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * MyBatis扫描mapper接口配置
 *
 * @author lareina_h
 * @version 1.0
 * @date 2018/5/10
 */

@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.cotton.abmallback.mapper");
		Properties properties = new Properties();
		properties.setProperty("mappers", "tk.mybatis.mapper.common.Mapper,tk.mybatis.mapper.common.special.InsertListMapper");
		properties.setProperty("style", "normal");
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}
}
