package com.dao;



import com.entity.Account;

import java.util.List;

public interface IAccountDao {



    List<Account> findAll();
}
