package com.bluesgao.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JsonDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("读取datasource配置 开始");
                arrayParse();
                System.out.println("读取datasource配置 结束 ");
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    private static void objectParse() {

        String myJsonObj = "{\n" +
                "    \"name\":\"runoob\",\n" +
                "    \"alexa\":10000,\n" +
                "    \"sites\": {\n" +
                "        \"site1\":\"www.runoob.com\",\n" +
                "        \"site2\":\"m.runoob.com\",\n" +
                "        \"site3\":\"c.runoob.com\"\n" +
                "    }\n" +
                "}";
        JSONObject jsonobj = JSON.parseObject(myJsonObj); //将json字符串转换成jsonObject对象
        /***获取JSONObject中每个key对应的value值时，可以根据实际场景中想得到什么类型就分别运用不到的方法***/
        System.out.println(jsonobj.get("name")); //取出name对应的value值，得到的是一个object
        System.out.println(jsonobj.getString("name")); //取出name对应的value值，得到的是一个String
        System.out.println(jsonobj.getIntValue("alexa")); //取出name对应的value值，得到的是一个int
        System.out.println(jsonobj.get("sites")); //取出sites对应的value值，得到的是一个object
        System.out.println(jsonobj.getString("sites"));
        System.out.println(jsonobj.getJSONObject("sites")); //取出sites对应的value值，得到一个JSONObject子对象
        System.out.println(jsonobj.getJSONObject("sites").getString("site2")); //取出嵌套的JSONObject子对象中site2对应的value值，必须用getJSONObject()先获取JSONObject


        DemoDTO demoDTO = JSON.parseObject(myJsonObj, DemoDTO.class);
        System.out.println(demoDTO);
    }

    private static void arrayParse() {
        String arrayJson = "[" +
                "{\n" +
                "    \"id\":\"r1\",\n" +
                "    \"type\":\"mysql\",\n" +
                "    \"ds\": {\n" +
                "        \"url\":\"www.runoob.com\",\n" +
                "        \"username\":\"m.runoob.com\",\n" +
                "        \"password\":\"c.runoob.com\"\n" +
                "    }\n" +
                "}," +
                "{\n" +
                "    \"id\":\"r2\",\n" +
                "    \"type\":\"redis\",\n" +
                "    \"ds\": {\n" +
                "        \"host\":\"www.runoob.com\",\n" +
                "        \"port\":\"6371\",\n" +
                "        \"username\":\"test\"\n" +
                "    }\n" +
                "}" +
                "]";

        List<DataSourceDto> sourceDtoList = JSON.parseArray(arrayJson, DataSourceDto.class);
        System.out.println("sourceDtoList:" + sourceDtoList.toString());
    }


}
