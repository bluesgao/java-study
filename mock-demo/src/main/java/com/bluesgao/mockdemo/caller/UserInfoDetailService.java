package com.bluesgao.mockdemo.caller;

import com.bluesgao.mockdemo.rpc.api.AddressService;
import com.bluesgao.mockdemo.rpc.dto.AddressDto;
import com.bluesgao.mockdemo.rpc.dto.UserDto;
import com.bluesgao.mockdemo.rpc.api.UserService;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName：CallerService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/2 09:15
 **/
public class UserInfoDetailService {
    public UserInfoDetailDto getUserInfoDetail(String uId) {
        UserService us = Mockito.mock(UserService.class);
        Mockito.when(us.getUserInfo(uId)).thenReturn(new UserDto(uId, uId + "name"));

        AddressService as = Mockito.mock(AddressService.class);
        List<String> uaddrs = new ArrayList<>();
        uaddrs.add(uId + "地址1");
        uaddrs.add(uId + "地址2");
        Mockito.when(as.getAddressInfo(uId)).thenReturn(new AddressDto(uId, uaddrs));


        UserDto userDto = us.getUserInfo(uId);

        AddressDto addressDto = as.getAddressInfo(uId);

        //System.out.println("userDto:" + userDto.toString());
        //System.out.println("addressDto:" + addressDto.toString());

        UserInfoDetailDto userInfoDetailDto = new UserInfoDetailDto();
        userInfoDetailDto.setuId(uId);
        userInfoDetailDto.setuName(userDto.getuName());
        userInfoDetailDto.setAddrs(addressDto.getAddrs());
        return userInfoDetailDto;
    }

    public static void main(String[] args) {
        UserInfoDetailService cs = new UserInfoDetailService();
        System.out.println(cs.getUserInfoDetail("111"));
    }
}
