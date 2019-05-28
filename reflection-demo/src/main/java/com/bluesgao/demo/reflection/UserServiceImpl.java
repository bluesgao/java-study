package com.bluesgao.demo.reflection;

public class UserServiceImpl implements UserService {
    public boolean createUserInfo(User user) {
        System.out.println("createUserInfo "+ user.toString());
        return false;
    }
}
