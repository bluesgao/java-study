package com.bluesgao.jvm.demo.cl;

import java.sql.Driver;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @ClassName：ClassLoaderDemo
 * @Description：ServiceLoader原理-
 * 关键：打破了双亲委派机制，使用TCCL-Thread.currentThread().getContextClassLoader()
 *      java.util.ServiceLoader是jdk核心包有BCL加载，应用main方法所在的类是由ACL加载，这2个类不属于同一个CL空间，
 *      如果直接使用java.util.ServiceLoader的类加载器加载实现Driver.class的spi实现类，那么在应用中将不能使用，
 *      在jvm中唯一确定一个类是由（classloader,class的全限定名）决定，
 *      因此在ServiceLoader#load()方法中进行了classloader的切换。
 * 步骤：
 * ①java.util.ServiceLoader#load(java.lang.Class)
 *   classloader的切换（由BootstrapCL->AppCL）
 *   Thread.currentThread().getContextClassLoader()
 * ②java.util.ServiceLoader#load(java.lang.Class, java.lang.ClassLoader)
 *   通过ServiceLoader.load去加载指定类接口，和AppClassLoader的spi实现
 * ③java.util.ServiceLoader#ServiceLoader(java.lang.Class, java.lang.ClassLoader)
 *   调用ServiceLoader的私有化构造器，在其中调用java.util.ServiceLoader#reload()方法。
 * ④java.util.ServiceLoader#reload()
 *   清空spi实现的缓存providers.clear()，实例化内部类LazyIterator
 * ⑤java.util.ServiceLoader.LazyIterator#hasNext()->java.util.ServiceLoader.LazyIterator#hasNextService()
 *   在通过META-INF/services/目录下，读取以类全限定名命名的文件，并按行解析其中的内容，返回spi类的迭代器
 * ⑥java.util.ServiceLoader.LazyIterator#next()->java.util.ServiceLoader.LazyIterator#nextService()
 *  遍历步骤③返回的spi类的迭代器，然后通过Class.forName加载spi类，并java.lang.Class#newInstance()实例化
 * ⑦providers.put(cn, p)
 *  将实例化的spi缓存在java.util.ServiceLoader#providers中（LinkedHashMap）。
 * @Author：bluesgao
 * @Date：2021/3/5 15:47
 **/
public class ServiceLoaderDemo {
    public static void main(String[] args) {
        System.out.println("serviceloader :" + ServiceLoader.class.getClassLoader());
        System.out.println("current thread classloader[" + Thread.currentThread().getContextClassLoader() + "]");
        //注意：需要在pom文件添加mysql-connector-java的坐标
        ServiceLoader cl = ServiceLoader.load(Driver.class);
        Iterator itr = cl.iterator();
        while (itr.hasNext()) {
            Driver d = (Driver) itr.next();
            System.out.println("classloader[" + d.getClass().getClassLoader() + "]"+"driverclass[" + d.getClass() + "] ");
        }
    }
}
