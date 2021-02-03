package com.bluesgao.springdemo.ioc.property.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName：JdbcPropertiesConfiguration
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 17:51
 **/
@Configuration
@ComponentScan("com.bluesgao.springdemo.ioc.property.entity")
@PropertySource("classpath:properties/jdbc.properties")
public class JdbcPropertiesConfiguration {

}
