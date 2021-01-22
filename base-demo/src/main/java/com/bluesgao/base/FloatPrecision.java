package com.bluesgao.base;

public class FloatPrecision {
    public static void main(String[] args) {
        float a = 200_000_00.0f;
        float b = 1.0f;

        float c = a + b;
        System.out.println("c="+c);//c=2.0E7

        float d = c -a;
        System.out.println("d="+d);//d=0.0

        float sum = 0.0f;
        for (int i=0;i<200_000_00;i++){
            float x = 1.0f;
            sum = sum + x;
        }
        System.out.println("sum="+sum);


    }
}
