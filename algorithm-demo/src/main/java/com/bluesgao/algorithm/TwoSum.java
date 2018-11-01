package com.bluesgao.algorithm;

/**
 * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
 * 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
 * <p>
 * 给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 */
public class TwoSum {
    public static void twoSum(int[] nums, int target) {
        System.out.println("输入数组:" + print(nums));
        System.out.println("输入目标值：" + target);

        if (nums == null || nums.length == 0) {
            System.out.println("输入数组不能为空");
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    System.out.println("数组中和为目标值的两个数的组合有：[" + nums[i] + "," + nums[j] + "]");
                }
            }
        }
    }

    private static String print(int[] nums) {
        StringBuffer str = new StringBuffer("[");
        for (int i = 0; i < nums.length; i++) {
            str.append(nums[i]);
            if (i < nums.length - 1) {
                str.append(",");
            }
        }
        str.append("]");
        return str.toString();
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15, 1, 0, 9, 7, 2, 1, 0, 9, -1, 10, -2, -3};
        int target = 9;
        twoSum(nums, target);
    }
}
