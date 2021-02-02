package com.bluesgao.base;

import javax.sound.midi.Soundbank;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @ClassName：BigDecimalDemo
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/2 09:47
 **/
public class BigDecimalDemo {
    public static void main(String[] args) {
        test1();
        System.out.println("**************");
        test2();
        System.out.println("**************");
        test3();
        System.out.println("**************");
        test4();
        System.out.println("**************");
        test5();
        System.out.println("**************");
        roundoff1();
        System.out.println("**************");
        roundoff2();
        System.out.println("**************");
        roundoff3();
        System.out.println("**************");
        overflow1();
    }

    public static void test1() {
        BigDecimal a = new BigDecimal(1.0f);
        BigDecimal b = new BigDecimal(1);
        System.out.println("compareTo:" + a.compareTo(b));
        System.out.println("equals:" + a.equals(b));
    }

    public static void test2() {
        BigDecimal a = new BigDecimal("1.0");
        BigDecimal b = BigDecimal.valueOf(1.0f);
        System.out.println("compareTo:" + a.compareTo(b));
        System.out.println("equals:" + a.equals(b));
    }

    public static void test3() {
        //scale=1
        BigDecimal a = new BigDecimal("0.1");
        //scale=27
        BigDecimal b = new BigDecimal(0.1f);
        System.out.println("compareTo:" + a.compareTo(b));
        System.out.println("equals:" + a.equals(b));
    }

    public static void test4() {
        //scale=1
        BigDecimal a = new BigDecimal("0.1");
        //scale=1
        BigDecimal b = BigDecimal.valueOf(0.1);
        System.out.println("compareTo:" + a.compareTo(b));
        System.out.println("equals:" + a.equals(b));
    }

    public static void test5() {
        //scale=1
        BigDecimal a = BigDecimal.valueOf(0.1);
        //scale=17
        BigDecimal b = BigDecimal.valueOf(0.1f);
        System.out.println("compareTo:" + a.compareTo(b));
        System.out.println("equals:" + a.equals(b));
    }

    public static void roundoff1() {
        double num1 = 3.35;
        float num2 = 3.35f;
        //四舍五入
        String str1 = String.format("%.1f", num1);
        String str2 = String.format("%.1f", num2);
        System.out.println(str1);
        System.out.println(str2);
    }

    public static void roundoff2() {
        double num1 = 3.35;
        float num2 = 3.35f;
        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num1));
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num2));
    }

    public static void roundoff3() {
        BigDecimal num1 = new BigDecimal("3.35");

        BigDecimal num2 = num1.setScale(1, BigDecimal.ROUND_DOWN);
        System.out.println(num2);

        BigDecimal num3 = num1.setScale(1, BigDecimal.ROUND_HALF_UP);
        System.out.println(num3);
    }

    public static void overflow1(){
        long l = Long.MAX_VALUE;
        System.out.println(l+1);
        System.out.println(l+1 == Long.MIN_VALUE);
    }

}
