package com.bluesgao.java.study.string;

import java.math.BigInteger;

public class CastDemo {
    public static void main(String[] args) {
        System.out.println(Long.MAX_VALUE);
        //"9223_3720_3685_4775_807"
        //Object v = Long.valueOf("1688_7888_5495_9506_2445");
        BigInteger bg = new BigInteger("16887888549595062445");
        System.out.println(bg);
    }
}
