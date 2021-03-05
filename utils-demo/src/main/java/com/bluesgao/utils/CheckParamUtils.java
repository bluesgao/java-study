package com.bluesgao.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName：CheckParamUtils
 * @Description：
 * @Author：bluesgao
 * @Date：2021/3/4 11:48
 **/
public class CheckParamUtils {
    public static void isNotNull(String field, String fieldDes) throws IllegalStateException {
        Preconditions.checkState(!StringUtils.isBlank(field), fieldDes + ParamErrEnum.NOT_NULL.getMsg());
    }

    public static void isBiggerZero(Long field, String fieldDes) throws IllegalStateException {
        Preconditions.checkState(field != null && field > 0, fieldDes + ParamErrEnum.NOT_SMALLER_ZEOR.getMsg());
    }

    public static void isBiggerZero(Integer field, String fieldDes) throws IllegalStateException {
        Preconditions.checkState(field != null && field > 0, fieldDes + ParamErrEnum.NOT_SMALLER_ZEOR.getMsg());
    }
}
