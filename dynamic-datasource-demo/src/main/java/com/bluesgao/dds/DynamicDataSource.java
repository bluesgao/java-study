package com.bluesgao.dds;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * 动态数据源
 */
@Component
public class DynamicDataSource extends AbstractRoutingDataSource {
    private static Map<String, DataSource> dataSourceMap = new HashMap<String, DataSource>();

    private final static Map<DataSourceEntity, String> dseMap = new HashMap<>();

    //spring注入需要set方法，不是必要的，可以注入也可以不注入
    public static void setDataSourceMap(Map<String, DataSource> dataSourceMap) {
        DynamicDataSource.dataSourceMap = dataSourceMap;
    }

    //获取数据源信息集合
    public static Map<DataSourceEntity, String> getdseMap() {
        return dseMap;
    }

    //检查是否包含指定id的数据源
    public static boolean checkDbKey(String dbKey) {
        if (dataSourceMap.get(dbKey) != null) return true;
        return false;
    }

    //抽象方法，必须重写，用来判断使用哪个数据源
    @Override
    protected String determineCurrentLookupKey() {
        return DataSourceUtils.getDbKey();
    }

    /**
     * 对数据源的初始化方法，由于这里已经将数据源集合放在本类中，如果不重写将会由于父类参数为null而抛出异常。
     */
    @Override
    public void afterPropertiesSet() {
    }

    /**
     * 确定使用哪一个数据源
     * 这里不做null判断，因为是经过null判断后再进入的。
     */
    @Override
    protected DataSource determineTargetDataSource() {
        System.out.println("tttt");
        String dsKey = determineCurrentLookupKey();
        DataSource dds = dataSourceMap.get(dsKey);
        return dds;
    }

    /**
     * 添加数据源
     * 为了防止多线程添加同一个数据源，这里采用同步,同时会判断是否已存在
     *
     * @param ip
     * @param port
     * @param username
     * @param password
     * @return String 新建数据源对应的key，如果已经存在，则返回之前的key。
     */
    public synchronized String addDataSource(String ip, int port, String username, String password, String db) {
        DataSourceEntity dse = new DataSourceEntity(ip, port, username, password, db);
        String value = dseMap.get(dse);
        if (dseMap.get(dse) != null) {
            return value;//已存在则返回该数据源的id
        }

        DataSource ds = createDataSource(ip, port, username, password, db);

        String dbkey = genKey(ip, String.valueOf(port), db);
        dataSourceMap.put(dbkey, ds);//存储数据源集合
        dseMap.put(dse, dbkey);//保存已经存储了哪些数据源

        return dbkey;
    }

    private static String genKey(String... args) {
        StringJoiner stringJoiner = new StringJoiner(":", "", "");
        for (String s : args) {
            stringJoiner.add(s);
        }
        return stringJoiner.toString();
    }

    /**
     * 创建一个数据源
     */
    private DataSource createDataSource(String ip, int port, String username, String password, String db) {
        DruidDataSource dds = new DruidDataSource();
        dds.setDriverClassName("com.mysql.jdbc.Driver");
        // jdbc:mysql://gyl.mysql.dev.wyyt:6612/cscc_dev?allowMultiQueries=true&serverTimezone=Asia/Shanghai
        dds.setUrl(String.format("jdbc:mysql://%s:%s/%s?allowMultiQueries=true&serverTimezone=Asia/Shanghai", ip, port, db));
        dds.setUsername(username);
        dds.setPassword(password);
        return dds;
    }
}
