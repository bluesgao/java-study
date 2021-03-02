package com.bluesgao.mockdemo.rpc.api;

import com.bluesgao.mockdemo.rpc.dto.AddressDto;

/**
 * @ClassName：AddressService
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/2 09:25
 **/
public interface AddressService {
    AddressDto getAddressInfo(String uId);
}
