package com.dao.impl;

import com.dao.IUserDao;
import entity.User;

public class UserDaoImpl_oracle implements IUserDao {
    @Override
    public void database() {
        System.out.println("当前使用的数据库是：oracle");
    }

    @Override
    public void scope() {
        System.out.println("多列");
    }

    public void initMethod() {
        System.out.println("oracle初始化");
    }

    public void destroyMethod(){
        System.out.println("oracle已销毁");
    }

    public static User staticMethod() {
        System.out.println("oracle的静态方法使用中！");
        return new User();
    }

    public static User instanceMethod(){
        System.out.println("oracle的实列方法使用中！");
        return new User();
    }
}
