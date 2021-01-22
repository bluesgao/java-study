package com.bluesgao.base;

import java.math.BigDecimal;

//IEEE 754 浮点数表示法
//https://blog.csdn.net/yaowanliang/article/details/89102815
//https://blog.csdn.net/qq_41054313/article/details/90374427
//https://www.taowong.com/blog/2018/07/10/principle-of-computer-float-num.html
//https://blog.csdn.net/weixin_42562514/article/details/85264421?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase
//https://blog.csdn.net/wanf425/article/details/80990447?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
//https://blog.csdn.net/aduovip/article/details/47728921?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.nonecase
//https://jingyan.baidu.com/article/597a0643614568312b5243c0.html
//https://blog.csdn.net/qq_41054313/article/details/90374427
/*
IEEE754表示法：（-1）^s * 1.f * 2^(e-127)

十进制表示法：-0.25
二进制表示法：-0.01
IEEE754表示法：(-1)^1 * 1.01 * 2^-2
s = 1（符号位-负数为0，正数为1）
1.f =1.01（尾数）
e-127 =-2,e=125=0111 1101 (阶码)
内存布局：
1 0111 1101 0100 0000 0000 0000 0000 000

十进制表示法：0.125
二进制表示法：0.001
IEEE754表示法：(-1)^0 * 1.0 * 2^-2
s = 0（符号位-负数为0，正数为1）
1.f =1.00（尾数）
e-127 =-2,e=125=0111 1101 (阶码)
内存布局：
1 0111 1101 0000 0000 0000 0000 0000 000

十进制表示法：0.5
二进制表示法：0.1
IEEE754表示法：(-1)^0 * 1.0 * 2^-1
s = 0（符号位-负数为0，正数为1）
1.f =1.0（尾数）
e-127 =-1,e=126=0111 1111 (阶码)
内存布局：
1 0111 1111 0000 0000 0000 0000 0000 000


十进制表示法：0.1
0.1转化成bai二进制的算法：
0.1*2=0.2======取出整du数部分zhi0
0.2*2=0.4======取出整数部分0
0.4*2=0.8======取出整数部分0
0.8*2=1.6======取出整数部分1
0.6*2=1.2======取出整数部分1
0.2*2=0.4======取出整数部分0
0.4*2=0.8======取出整数部分0
0.8*2=1.6======取出整数部分1
0.6*2=1.2======取出整数部分1
接下来会无限循环
0.2*2=0.4======取出整数部分0
0.4*2=0.8======取出整数部分0
0.8*2=1.6======取出整数部分1
0.6*2=1.2======取出整数部分1
所以0.1转化成二进制是：0.0 0011 0011 ......

十进制小数转换成二进制小数的计算方法：
采用“乘2取整，顺序排列”法。
具体做法是：用2乘十进制小数，可以得到积，将积的整数部分取dao出，再用2乘余下的小数部分，又得到一个积，再将积的整数部分取出，如此进行，直到积中的小数部分为零，此时0或1为二进制的最后一位。或者达到所要求的精度为止。
然后把取出的整数部分按顺序排列起来，先取的整数作为二进制小数的高位有效位，后取的整数作为低位有效位。

IEEE754表示法：(-1)^0 * 1.10011 * 2^-4
s = 0（符号位-负数为0，正数为1）
1.f =1.10011（尾数）
e-127 =-4,e=123=0111 1100 (阶码)
内存布局：


a,浮点数如何表示
b,浮点数相加

 */
public class BinaryDemo {
    public static void main(String[] args) {
        System.out.println(1.0f-0.9f);//0.100000024
        BigDecimal money = new BigDecimal(0.1f);
        System.out.println(money);//0.100000001490116119384765625
    }
}
