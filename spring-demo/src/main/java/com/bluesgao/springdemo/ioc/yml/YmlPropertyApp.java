package com.bluesgao.springdemo.ioc.yml;

import com.bluesgao.springdemo.ioc.yml.config.JdbcYmlConfiguration;
import com.bluesgao.springdemo.ioc.yml.entity.JdbcYmlProperty;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName：YmlPropertyApp
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 17:58
 **/
public class YmlPropertyApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(JdbcYmlConfiguration.class);
        System.out.println(ctx.getBean(JdbcYmlProperty.class).toString());
    }
}
