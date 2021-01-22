package com.bluesgao.ssm.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.bluesgao.ssm.demo.dao.AccountDao;
import com.bluesgao.ssm.demo.domain.Account;
import com.bluesgao.ssm.demo.service.AccountService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Getter
@Setter
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    @Transactional
    public Boolean deposit(Integer accountId, Integer balance) {
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(balance);//存钱
        return updateBalanceByCAS(account, 2);
    }

    @Override
    @Transactional
    public Boolean draw(Integer accountId, Integer balance) {
        Account account = new Account();
        account.setId(accountId);
        account.setBalance(-balance);//取钱
        return updateBalanceByCAS(account, 3);
    }


    private Account getAccount(Integer accountId){
        Account account = null;
        try {
            account = accountDao.selectByPrimaryKey(accountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    //mysql tx_isolation=read-committed
    private Boolean updateBalanceByCAS(Account account, int n){

        boolean result = false;
        int loop = 0;
        do {//cas重试n次
            int ret = 0;
            Account oldAccount = getAccount(account.getId());
            log.info("第{}次 update balance oldAccount:{}", loop,JSON.toJSONString(oldAccount));

            if (oldAccount !=null) {
                account.setPreBalance(oldAccount.getBalance());
                account.setVersion(oldAccount.getVersion());

                try {
                    ret = accountDao.updateBalance(account);
                    log.info("第{}次 update balance input:{}, output:{}", loop,JSON.toJSONString(account), ret);
                } catch (Exception e) {
                    log.error("第{}次 update balance input:{}, error:{}", loop,JSON.toJSONString(account), e);
                }
            }

            if (ret==1){
                result = true;
                break;
            }else {
                loop ++;
            }
        }while (loop<=n);

        return result;
    }

}
