package com.xml.service;

import com.xml.dao.IUserDao;

public interface IUserService {
//    编程式事务
    void insertRow();


//    声明式事务
    void insertMethod();
}
