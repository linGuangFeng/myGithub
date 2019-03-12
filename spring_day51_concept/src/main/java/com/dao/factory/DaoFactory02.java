package com.dao.factory;

import java.util.ResourceBundle;

public class DaoFactory02 {


    public static <T> T getUserDaoImpl(String resourcePath,String key,Class<T> t){

        //获取要加载的资源文件
        ResourceBundle resourceBundle = ResourceBundle.getBundle(resourcePath);
        //获取实现类的类全名
        String className = resourceBundle.getString(key);

        try {
            return (T)Class.forName(className).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
