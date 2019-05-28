package com.bluesgao.po;

/*
在java中，String有两种赋值方式
第一种是通过“字面量”赋值，如：String str="hello"
第二种是通过new关键字创建新对象，如：String str=new String("hello")


*/
public class StringDemo {

    public static void main(String[] args) {
        String s1 = "abcaaaa";
        String s2 = new String("abcaaaa");
        String s3 = s2.intern();
        String s4 = s1.intern();
        System.out.printf("s1==s2:%s \n",s1 == s2);//false s1常量池的地址引用，s2堆地址引用
        System.out.printf("s1==s3:%s \n",s1 == s3);//true 调用intern方法时，会去常量池中查找是否有等于该字符串的对象，有的话就直接返回其应用
        System.out.printf("s1==s4:%s \n",s1 == s4);//true

        System.out.printf("s2==s3:%s \n",s2 == s3);//false
        System.out.printf("s2==s4:%s \n",s2 == s4);//false

        System.out.printf("s3==s4:%s \n",s3 == s4);//true
    }
}
