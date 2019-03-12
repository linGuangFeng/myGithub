package com.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class UserDao {


    public void save() {

        System.out.println("保存用户");
    }


    public void select() {
        System.out.println("查看用户");
    }
}
