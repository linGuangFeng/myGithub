package com.dao.impl;

import com.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl implements IUserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insert() {
        String sql = "insert into user values(null,'测','试')";
        jdbcTemplate.update(sql);
        int i = 5/0;
        jdbcTemplate.update(sql);
    }
}
