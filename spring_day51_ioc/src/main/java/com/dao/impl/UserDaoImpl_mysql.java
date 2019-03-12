package com.dao.impl;

import com.dao.IUserDao;
import entity.User;

public class UserDaoImpl_mysql implements IUserDao {
    @Override
    public void database() {
        System.out.println("当前使用的数据库是：mysql");
    }

    @Override
    public void scope() {
        System.out.println("单列");
    }


    public void initMethod() {
        System.out.println("mysql初始化");
    }

    public void destroyMethod(){
        System.out.println("mysql已销毁");
    }


    public static User staticMethod() {
        System.out.println("mysql的静态方法使用中！");
        return new User();
    }

    public static User instanceMethod(){


        System.out.println("mysql的实列方法使用中！");
        return new User();
    }

}
