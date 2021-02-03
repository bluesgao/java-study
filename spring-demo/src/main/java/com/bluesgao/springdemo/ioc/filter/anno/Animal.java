package com.bluesgao.springdemo.ioc.filter.anno;

import java.lang.annotation.*;

/**
 * @ClassName：Animal
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 11:42
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Animal {
}
