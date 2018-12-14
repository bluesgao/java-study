package com.bluesgao.redis;

import org.xerial.snappy.Snappy;

public class SnappyTest {
    public static void main(String[] args) throws Exception {
        String input ="Hello snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.";

        //压缩
        byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
        System.out.println(compressed.length);
        System.out.println(new String(compressed, "UTF-8"));

        //解压
        byte[] uncompressed = Snappy.uncompress(compressed);
        System.out.println(uncompressed.length);

        String result = new String(uncompressed, "UTF-8");
        System.out.println(result);
    }
}
