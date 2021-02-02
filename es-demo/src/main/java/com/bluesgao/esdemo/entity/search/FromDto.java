package com.bluesgao.esdemo.entity.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ClassName：FromDto
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 09:11
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FromDto {
    private long from;
    private boolean includeUpper;
}
