package com.bluesgao.java.study.string;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateDemo {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);

        LocalDateTime localDateTime = LocalDateTime.now();
        String dateStr = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(dateStr);

        LocalDate localDate1 = LocalDate.parse("20110101", DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(localDate1);

        Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }
}
