package dao;

import entity.FindUserByCondition;
import entity.User;

import java.util.List;

public interface IUserDao<T> {

    //where标签使用练习
    List<T> whereExe(FindUserByCondition findUserByCondition);

    List<T> findAll();

    //set标签使用练习
    void setExe(User user);

    //foreach标签使用练习
    List<T> foreachExe(FindUserByCondition findUserByCondition);

    //一对一练习
    List<T> oneOne();

    //一对一练习法2
    List<T> oneOne02();

    //一对多练习
    List<T> oneMore();

    //多对多练习
    List<T> moreMore();
    List<T> moreMore_();




}
