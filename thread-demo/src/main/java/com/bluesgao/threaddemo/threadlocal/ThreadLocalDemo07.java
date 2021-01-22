package com.bluesgao.threaddemo.threadlocal;

public class ThreadLocalDemo07 {
    public static void main(String[] args) {
        new Service1().service1();
    }

    static class Service1 {
        public void service1() {
            //设置holder
            ContextHolder.userHolder.set(new User("川建国"));
            ContextHolder.addrHolder.set(new Addr("北京市"));
            //调用服务2
            new Service2().service2();
        }
    }

    static class Service2 {
        public void service2() {
            //获取用户
            System.out.println("Service2拿到用户名：" + ContextHolder.userHolder.get().name);
            ContextHolder.userHolder.remove();

            //调用服务3
            new Service3().service3();
        }
    }

    static class Service3 {
        public void service3() {
            System.out.println("Service3拿到地址：" + ContextHolder.addrHolder.get().city);

            //清除value
            ContextHolder.addrHolder.remove();
        }
    }

    static class ContextHolder {
        public static ThreadLocal<User> userHolder = new ThreadLocal<>();
        public static ThreadLocal<Addr> addrHolder = new ThreadLocal<>();

    }

    static class User {
        String name;

        public User(String name) {
            this.name = name;
        }
    }

    static class Addr {
        String city;

        public Addr(String city) {
            this.city = city;
        }
    }
}




