package com.bluesgao.demo.hbase;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
public class HBaseTest {
    private static final String nodes = "myhbase";

    public static void main(String[] args) throws Exception {
        log.info("main");
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum","myhbase");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conf.set("log4j.logger.org.apache.hadoop.hbase","WARN");
        final HBaseService hBaseService = new HBaseService(conf);
        //System.out.println(hBaseService.getAllTableNames());

//            List<String> cf = new ArrayList<>();
//            cf.add("cf1");
//            cf.add("cf2");
//            cf.add("cf3");
//            hBaseService.creatTable("t_test",cf);


//        System.out.println("start");
//
//        Configuration conf = HBaseConfiguration.create();
//        conf.set("hbase.zookeeper.quorum", "myhbase");
//        conf.set("hbase.zookeeper.property.clientPort", "2181");
//        Connection conn = ConnectionFactory.createConnection(conf);
//
//        System.out.println(conn);
//        Admin admin = conn.getAdmin();
//        System.out.println("获取admin成功");
//
//        System.out.println(admin.tableExists(TableName.valueOf("test:user")));

//        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,4,500L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
//        CompletionService<String> completionService = new ExecutorCompletionService<>(poolExecutor);

        String tableName = "t_test";
        String rowKey = "k001";
        String familyName = "cf1";
        String condition = "cf1";
        String expect = "1";
        String[] columns = {"cf1","cf2","cf3"}; String[] values={"1","2","3"};
        //hBaseService.putData(tableName,rowKey,familyName,columns,values);

        System.out.println(hBaseService.getRowData(tableName,rowKey));

//        for (int i=0;i<4;i++){
//            completionService.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    hBaseService.checkAndPut(tableName,rowKey,familyName,condition,expect,columns,values);
//                    System.out.println("执行完成"+Thread.currentThread().getName());
//                    return null;
//                }
//            });
//        }



    }
}
