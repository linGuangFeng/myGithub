package com.dao.impl;


import com.dao.IAccountDao;
import com.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("iAccountDao")
public class AccountDaoImpl implements IAccountDao {


    @Autowired
    JdbcTemplate jdbcTemplate;

/*    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }*/

    @Override
    public List<Account> findAll() {
        return jdbcTemplate.query("select * from account",new BeanPropertyRowMapper<>(Account.class));
    }
}
