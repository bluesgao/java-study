package com.bluesgao.tx.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @ClassName：SpringConfig
 * @Description：
 * @Author：bluesgao
 * @Date：2021/1/25 14:35
 **/
@Configuration
@ComponentScan("com.bluesgao")
@Import(DataSourceConfig.class)
public class SpringConfig {
}
