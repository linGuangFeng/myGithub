package com.dao.impl;

import com.dao.IUserDao;

public class UserDaoImpl_mysql implements IUserDao {
    @Override
    public void insert() {
        System.out.println("使用mysql数据库");
    }
}
