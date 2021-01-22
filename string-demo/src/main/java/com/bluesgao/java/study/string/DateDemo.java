package com.bluesgao.java.study.string;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateDemo {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(new Date().getTime());

        String dateStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dateStr);


        LocalDate localDate1 = LocalDate.parse("20110101", DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(localDate1);

        Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));


        LocalDateTime localDateTime3 = LocalDateTime.now();
        System.out.println("localDateTime3:"+localDateTime3);
        LocalDateTime localDateTime4 = localDateTime.minus(1, ChronoUnit.HOURS);
        System.out.println("localDateTime4:"+localDateTime4);
        Duration duration = Duration.between(localDateTime4, localDateTime3);
        System.out.println(duration.toDays());
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());




        Date startDate = new Date();
        try {
            Thread.sleep(2340);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date endDate = new Date();
        Long diff = endDate.getTime() -startDate.getTime();

        System.out.println("diff:"+diff);

        Integer expire = (int)Math.ceil(1.0*diff/1000);
        System.out.println("expire:"+expire);


    }
}
