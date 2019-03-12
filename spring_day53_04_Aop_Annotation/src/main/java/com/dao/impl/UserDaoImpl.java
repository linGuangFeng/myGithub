package com.dao.impl;


import com.dao.IUserDao;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


@Service("iUserDao")
public class UserDaoImpl implements IUserDao {


    public void save() {
//        int i = 1/0;
        System.out.println("保存用户");
    }


    public void select() {
        System.out.println("查看用户");
    }
}
