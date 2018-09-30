package com.bluesgao.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密工具类
 */
public class EncryptUtils {

    public static String MD5(final String strText){
        return Encrypt(strText, "MD5");
    }

    /**
     * 传入文本内容，返回 SHA-256 串
     *
     * @param strText 要加密的字符串
     * @return
     */

    public static String SHA256(final String strText) {
        return Encrypt(strText, "SHA-256");
    }

    /**
     * 传入文本内容，返回 SHA-512 串
     *
     * @param strText 要加密的字符串
     * @return
     */
    public static  String SHA512(final String strText) {
        return Encrypt(strText, "SHA-512");
    }

    /**
     * 字符串 SHA 加密
     *
     * @param strText 要加密的字符串
     * @param strType 加密类型
     * @return
     */
    private static String Encrypt(final String strText, final String strType) {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0) {
            try {
                // SHA 加密开始
                // 创建加密对象 并传入加密类型
                MessageDigest messageDigest = MessageDigest
                        .getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 类型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 转换为 string
                StringBuffer strHexString = new StringBuffer();
                // 遍历 byte buffer
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        return strResult;
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtils.SHA256("hello"));
        System.out.println(EncryptUtils.SHA512("hello"));
        System.out.println(EncryptUtils.MD5("hello"));
    }
}
