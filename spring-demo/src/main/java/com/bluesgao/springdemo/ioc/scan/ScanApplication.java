package com.bluesgao.springdemo.ioc.scan;

import com.bluesgao.springdemo.ioc.scan.config.BasePackageClassesConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

/**
 * @ClassName：ScanApplication
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 11:01
 **/
public class ScanApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BasePackageClassesConfiguration.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        Stream.of(beanDefinitionNames).forEach(System.out::println);
    }
}
