package com.bluesgao.mockdemo.rpc.dto;

/**
 * @ClassName：UserDto
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/2 09:11
 **/
public class UserDto {
    private String uId;
    private String uName;

    public UserDto() {
    }

    public UserDto(String uId, String uName) {
        this.uId = uId;
        this.uName = uName;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "uId='" + uId + '\'' +
                ", uName='" + uName + '\'' +
                '}';
    }
}
