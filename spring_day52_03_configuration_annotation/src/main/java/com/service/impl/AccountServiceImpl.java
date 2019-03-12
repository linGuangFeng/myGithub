package com.service.impl;

import com.dao.IAccountDao;
import entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.service.IAccountService;

import java.util.List;

@Service("iAccountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    IAccountDao iAccountDao;

    public void setIAccountDao(IAccountDao iAccountDao){
        this.iAccountDao = iAccountDao;
    }


    public List<Account> findAll(){
        return iAccountDao.findAll();
    }
}
