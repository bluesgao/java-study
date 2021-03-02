package com.bluesgao.mockdemo.caller;

import java.util.List;

/**
 * @ClassName：UserInfoDetailDto
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/2 09:37
 **/
public class UserInfoDetailDto {
    private String uId;
    private String uName;
    private List<String> addrs;

    public UserInfoDetailDto(String uId, String uName, List<String> addrs) {
        this.uId = uId;
        this.uName = uName;
        this.addrs = addrs;
    }

    public UserInfoDetailDto() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public List<String> getAddrs() {
        return addrs;
    }

    public void setAddrs(List<String> addrs) {
        this.addrs = addrs;
    }

    @Override
    public String toString() {
        return "UserInfoDetailDto{" +
                "uId='" + uId + '\'' +
                ", uName='" + uName + '\'' +
                ", addrs=" + addrs +
                '}';
    }
}
