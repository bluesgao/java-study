package com.bluesgao.springdemo.ioc.yml.config;

import com.bluesgao.springdemo.ioc.yml.factory.YmlPropertySourceFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @ClassName：JdbcYmlConfiguration
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 17:59
 **/
@Configuration
@ComponentScan("com.bluesgao.springdemo.ioc.yml.entity")
@PropertySource(value = "classpath:properties/jdbc.yml", factory = YmlPropertySourceFactory.class)
public class JdbcYmlConfiguration {
}
