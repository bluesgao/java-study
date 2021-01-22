package com.bluesgao.dds;

public class DataSourceUtils {

    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static String getDbKey() {
        return local.get();
    }

    public static void setDbKey(String dbKey) {
        if (DynamicDataSource.checkDbKey(dbKey)) {
            local.set(dbKey);
        } else {
            throw new NullPointerException("不存在id为\"" + dbKey + "\"的数据源！");
        }
    }

    public static void clear() {
        local.remove();
    }
}
