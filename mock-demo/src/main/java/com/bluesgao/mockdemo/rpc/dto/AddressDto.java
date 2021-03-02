package com.bluesgao.mockdemo.rpc.dto;

import java.util.List;

/**
 * @ClassName：AddressDto
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/2 09:26
 **/
public class AddressDto {
    private String uId;
    private List<String> addrs;
    public AddressDto() {
    }

    public AddressDto(String uId, List<String> addrs) {
        this.uId = uId;
        this.addrs = addrs;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public List<String> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<String> addrs) {
        this.addrs = addrs;
    }

    @Override
    public String toString() {
        return "AddressDto{" +
                "uId='" + uId + '\'' +
                ", addrs=" + addrs +
                '}';
    }
}
