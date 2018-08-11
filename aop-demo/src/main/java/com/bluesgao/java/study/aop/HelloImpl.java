package com.bluesgao.java.study.aop;

import javax.sound.midi.Soundbank;

/**
 * ClassName: HelloImpl
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/5/25 15:54
 **/
public class HelloImpl implements Hello{
    public void hi(String name) {
        System.out.println("hao are you?"+name);
    }
}
