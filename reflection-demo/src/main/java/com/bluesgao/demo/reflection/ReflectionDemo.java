package com.bluesgao.demo.reflection;

import java.lang.reflect.Method;

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        //Class userClass = Class.forName("com.bluesgao.demo.reflection.User");
        //User user = (User) userClass.newInstance();
        User user = new User();
        user.setAge(100);
        user.setName("gx");

        Class userServiceClass = Class.forName("com.bluesgao.demo.reflection.UserServiceImpl");
        Method createUserMethod = userServiceClass.getMethod("createUserInfo", new Class[]{User.class});
        Object ret = createUserMethod.invoke(userServiceClass.newInstance(), user);
        System.out.println("ret:"+ret);
    }
}
