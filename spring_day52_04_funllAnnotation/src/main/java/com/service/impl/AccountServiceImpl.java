package com.service.impl;



import com.dao.IAccountDao;
import com.entity.Account;
import com.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Configuration
 * 1. 修改一个类,此类就是注解配置管理类
 * 2. 创建容器时候,就需要加载此注解修饰的类
 * 3. 此注解修饰的类是用来取代bean.xml
 *
 * @ComponentScan
 * 1. 开启注解扫描
 * 2. 相当于:<context:component-scan base-package="com.itheima"/>
 *
 * @Import
 * 1. 加载其他的配置管理类
 * 2. 相当于: <import resource="classpath:mail.xml"/>
 */
import java.util.List;

@Service("iAccountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    IAccountDao iAccountDao;

/*    public void setIAccountDao(IAccountDao iAccountDao){
        this.iAccountDao = iAccountDao;
    }*/


    public List<Account> findAll(){
        return iAccountDao.findAll();
    }
}
