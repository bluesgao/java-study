package com.bluesgao.springdemo.ioc.property;

import com.bluesgao.springdemo.ioc.property.config.JdbcPropertiesConfiguration;
import com.bluesgao.springdemo.ioc.property.entity.JdbcProperty;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ClassName：PropertySourceApp
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 17:50
 **/
public class PropertySourceApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(
                JdbcPropertiesConfiguration.class);
        System.out.println(ctx.getBean(JdbcProperty.class).toString());
    }
}
