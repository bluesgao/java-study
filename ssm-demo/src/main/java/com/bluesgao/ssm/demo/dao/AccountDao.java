package com.bluesgao.ssm.demo.dao;

import com.bluesgao.ssm.demo.domain.Account;

public interface AccountDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    Account selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Account record);

    int updateBalance(Account record);

    int updateByPrimaryKey(Account record);
}