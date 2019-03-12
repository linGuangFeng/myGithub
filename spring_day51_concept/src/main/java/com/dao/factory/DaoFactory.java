package com.dao.factory;

import com.dao.IUserDao;

import java.util.ResourceBundle;

public class DaoFactory {


    public static Object getUserDaoImpl(String resourcePath,String key){

        //获取要加载的资源文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resourcePath);
        //获取实现类的类全名
        String className = resourceBundle.getString(key);

        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
