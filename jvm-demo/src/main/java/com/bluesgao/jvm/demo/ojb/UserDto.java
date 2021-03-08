package com.bluesgao.jvm.demo.ojb;

import lombok.*;

/**
 * @ClassName：UserDto
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/8 09:28
 **/
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String userName;
    private String userId;
    private boolean flag;
    private Integer userAge;
}
