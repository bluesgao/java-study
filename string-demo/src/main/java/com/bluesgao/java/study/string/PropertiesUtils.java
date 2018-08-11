package com.bluesgao.java.study.string;

import java.io.IOException;
import java.util.Properties;

/**
 * ClassName: PropertyUtils
 * Desc: TODO
 * Author: gaoxin11
 * Date: 2018/7/10 14:44
 **/
public class PropertiesUtils {

    public static Properties loadProperties(String file) {
        Properties p = new Properties();
        try {
            p.load(PropertiesUtils.class.getResourceAsStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    public static String getValueByKey(Properties p, String key) {
        return p.getProperty(key, "");
    }

    public static String getValueByKey(String file, String key){
        Properties p = PropertiesUtils.loadProperties(file);
        return PropertiesUtils.getValueByKey(p, key);
    }
}
