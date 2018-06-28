package com.bluesgao.java.study.genclass;

/**
 * ClassName: MyClassLoader
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/6/28 21:18
 **/
public class MyClassLoader extends ClassLoader {
    private String name;
    private byte[] bytes;

    public MyClassLoader(String name, byte[] bytes) {
        this.name = name;
        this.bytes = bytes;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (bytes == null || bytes.length == 0) {
            return super.findClass(name);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
