package com.dao.impl;

import com.dao.IUserDao;

public class UserDaoImpl_oracle implements IUserDao {
    @Override
    public void insert() {
        System.out.println("使用oracle数据库");
    }
}
