package com.dao;

import entity.Account;

import java.util.List;

public interface IAccountDao {



    List<Account> findAll();
}
