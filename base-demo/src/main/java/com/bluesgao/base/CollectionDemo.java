package com.bluesgao.base;

import com.alibaba.fastjson.JSON;

import java.util.*;

public class CollectionDemo {
    public static void main(String[] args) {
        testMap();
    }

    public static void testSet() {
        Set<DemoDTO> set = new LinkedHashSet<>(8);
        set.add(new DemoDTO("bluesgao", 20));
        set.add(new DemoDTO("bluesgao1", 20));
        System.out.println(JSON.toJSON(set));
    }

    public static void testMap() {
        Map<String, Object> settings = new HashMap<>(8);
        settings.put("username", "bluesgao");
        String[] fields = {"name", "age"};
        settings.put("fields", fields);

        System.out.println(settings.get("fields") instanceof String[]);
        System.out.println(settings.get("fields") instanceof List);
        System.out.println(settings.get("username") instanceof String);

    }
}
