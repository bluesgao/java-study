package com.bluesgao.springdemo.ioc.scan.config;

import com.bluesgao.springdemo.ioc.scan.entity.UserDto;
import com.bluesgao.springdemo.ioc.scan.service.CommonService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName：BasePackageClassConfiguration
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 11:02
 **/
@Configuration
@ComponentScan(basePackageClasses = {UserDto.class, CommonService.class})
public class BasePackageClassesConfiguration {
}
