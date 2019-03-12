package com.xml.dao.impl;

import com.xml.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;


public class UserDaoImpl implements IUserDao {

    JdbcTemplate jdbcTemplate;
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public void insert() {
        jdbcTemplate.update("insert into user values (null,'ce','测')");
    }
}
