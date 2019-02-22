package com.bluesgao.java.study.string;

import java.util.HashMap;
import java.util.Map;

public class Boolean2String {
    public static void main(String[] args) {
        String str = "true";
        System.out.println(Boolean.valueOf(str));
        System.out.println(Boolean.getBoolean(str));
    }

    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> tempMap = new HashMap<Integer, Integer>();
        for (int j = 0; j < nums.length; j++) {
            int v = target - nums[j];
            if (tempMap.containsKey(v)) {
                result[0] = j;
                result[1] = tempMap.get(v);
            }else {
                //将数组元素当作map的key，数组下标当作map的value
                tempMap.put(nums[j], j);
            }
        }
        return result;
    }
}
