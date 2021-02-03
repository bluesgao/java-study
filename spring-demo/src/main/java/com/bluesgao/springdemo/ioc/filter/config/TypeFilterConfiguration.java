package com.bluesgao.springdemo.ioc.filter.config;

import com.bluesgao.springdemo.ioc.filter.CustomTypeFilter;
import com.bluesgao.springdemo.ioc.filter.anno.Animal;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * @ClassName：TypeFilterConfiguration
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 11:43
 **/
@Configuration
@ComponentScan(basePackages = "com.bluesgao.springdemo.ioc.filter",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Animal.class),
                @ComponentScan.Filter(type = FilterType.CUSTOM, value = CustomTypeFilter.class)
        }
)

public class TypeFilterConfiguration {
}
