package com.bluesgao.jvm.demo;

public class LvtTest {
    public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];//64MB
        }
        //int a = 0;
        System.gc();
    }
}

/*
[GC (System.gc())  68864K->66304K(125952K), 0.0013823 secs]
[Full GC (System.gc())  66304K->66201K(125952K), 0.0044615 secs]

[GC (System.gc())  68864K->66288K(125952K), 0.0008935 secs]
[Full GC (System.gc())  66288K->66201K(125952K), 0.0047394 secs]

[GC (System.gc())  68864K->66304K(125952K), 0.0009071 secs]
[Full GC (System.gc())  66304K->665K(125952K), 0.0047815 secs]

* */
