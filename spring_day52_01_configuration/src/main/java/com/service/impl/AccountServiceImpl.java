package com.service.impl;

import com.dao.IAccountDao;
import entity.Account;
import com.service.IAccountService;

import java.util.List;

public class AccountServiceImpl implements IAccountService {

    IAccountDao iAccountDao;

    public void setIAccountDao(IAccountDao iAccountDao){
        this.iAccountDao = iAccountDao;
    }


    public List<Account> findAll(){
        return iAccountDao.findAll();
    }
}
