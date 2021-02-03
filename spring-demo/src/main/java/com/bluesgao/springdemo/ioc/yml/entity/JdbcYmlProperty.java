package com.bluesgao.springdemo.ioc.yml.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @ClassName：JdbcProperties
 * @Description：
 * @Author：bluesgao
 * @Date：2021/2/3 17:47
 **/
@Component
public class JdbcYmlProperty {
    @Value("${yml.jdbc.url}")
    private String url;
    @Value("${yml.jdbc.driver}")
    private String driverClassName;
    @Value("${yml.jdbc.username}")
    private String userName;
    @Value("${yml.jdbc.password}")
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "JdbcProperties{" +
                "url='" + url + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
