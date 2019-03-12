package com.xml.service.impl;

import com.xml.dao.IUserDao;
import com.xml.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class UserServiceImpl implements IUserService {

    IUserDao iUserDao;
    TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    public void setIUserDao(IUserDao iUserDao) {
        this.iUserDao = iUserDao;
    }




    @Override
    public void insertRow() {
        // 创建回调函数，把需要进行事务控制的代码放入回调函数中。spring会自动进行事务控制
        TransactionCallback<TransactionTemplate> transactionCallback = new TransactionCallback<TransactionTemplate>() {
            @Override
            public TransactionTemplate doInTransaction(TransactionStatus transactionStatus) {
                iUserDao.insert();
//                int i = 1 / 0;
                return null;
            }
        };

        // 执行事务控制，传入回调函数
        transactionTemplate.execute(transactionCallback);
    }

    @Override
    public void insertMethod() {
        iUserDao.insert();
//                int i = 1 / 0;
    }
}
