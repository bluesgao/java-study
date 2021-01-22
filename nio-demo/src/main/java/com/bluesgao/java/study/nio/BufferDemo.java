package com.bluesgao.java.study.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/bluesgao/IdeaProjects/java-study/nio-demo/src/main/java/com/bluesgao/java/study/nio/BufferDemo.java";
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(filePath,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FileChannel fileChannel = file.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(48);

        while (fileChannel.read(byteBuffer)!=-1){//写数据到buffer中
            byteBuffer.flip();//将Buffer从写模式切换到读模式
            while (byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get());//从buffer中读取数据
            }
            byteBuffer.clear();//让Buffer准备好再次被写入
        }

        file.close();
    }
}
