package com.bluesgao.jvm.demo.ojb;

import org.openjdk.jol.info.ClassLayout;

/**
 * @ClassName：JolDemo https://blog.csdn.net/zhaocuit/article/details/100208879
 * @Description：对象内存布局 = 对象头 + 对象属性 + 对齐填充
 * @Author：bluesgao
 * @Date：2021/3/8 09:38
 **/
public class JolDemo {
    public static void main(String[] args) {
        UserDto user = new UserDto();
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
    }
}
