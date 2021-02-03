package com.bluesgao.springdemo.ioc.filter;

import com.bluesgao.springdemo.ioc.filter.config.TypeFilterConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @ClassName：TypeFilterApplication
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 11:35
 **/
public class TypeFilterApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(TypeFilterConfiguration.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
    }
}
