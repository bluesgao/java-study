package com.bluesgao.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class DateTest {

    /**
     * 计算当前日期与{@code endDate}的间隔天数
     *
     * @param endDate
     * @return 间隔天数
     */
    static long until(LocalDate endDate) {
        return LocalDate.now().until(endDate, ChronoUnit.DAYS);
    }

    /**
     * 计算日期{@code startDate}与{@code endDate}的间隔天数
     *
     * @param startDate
     * @param endDate
     * @return 间隔天数
     */
    static long until(LocalDate startDate, LocalDate endDate) {
        return startDate.until(endDate, ChronoUnit.DAYS);
    }

    public static void main(String[] args) {
        Date minDate = new Date();
        System.out.println("minDate:" + minDate);
        String minDateStr = "2018-12-02 22:00:12";
        System.out.println("minDateStr:" + minDateStr);
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //String minDateStr = format.format(minDate);
        LocalDate minLocalDate = LocalDate.parse(minDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("minLocalDate:" + minLocalDate);

        LocalDate currentLocalDate = LocalDate.now();
        System.out.println("currentLocalDate:" + currentLocalDate);

        long interval = minLocalDate.until(currentLocalDate, ChronoUnit.DAYS);
        System.out.println("interval:" + interval);
        //判断 当前日期 = 最小购买日期+1
        System.out.println("当前日期:" + currentLocalDate+"与最小日期"+minLocalDate+"间隔"+interval+"天");

    }
}
