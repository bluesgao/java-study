package com.bluesgao.demo.agent;

public class Test {
    public static void main(String[] args) {
        System.out.println("premain test");
        //changeMethodName();
        changeMethodContent();
    }

    private static void changeMethodName(){
        System.out.println("changeMethodName");
    }

    private static void changeMethodContent(){
        System.out.println("changeMethodContent");
    }
}
