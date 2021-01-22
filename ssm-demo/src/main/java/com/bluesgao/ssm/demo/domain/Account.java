package com.bluesgao.ssm.demo.domain;

public class Account {
    private Integer id;

    private Integer balance;

    private Integer version;

    private Integer preBalance;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getPreBalance() {
        return preBalance;
    }

    public void setPreBalance(Integer preBalance) {
        this.preBalance = preBalance;
    }
}