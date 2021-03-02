package com.bluesgao.mockdemo.rpc.api;

import com.bluesgao.mockdemo.rpc.dto.UserDto;

/**
 * @ClassName：UserService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/2 09:10
 **/
public interface UserService {
    UserDto getUserInfo(String uId);
}
