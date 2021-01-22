package com.bluesgao.ssm.demo.service;

import com.bluesgao.ssm.demo.domain.Account;

public interface AccountService {
    Boolean deposit(Integer accountId, Integer balance);
    Boolean draw(Integer accountId, Integer balance);

}
