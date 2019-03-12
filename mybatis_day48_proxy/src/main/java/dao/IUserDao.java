package dao;

import model.QueryUserCondition;
import model.User;

import java.util.List;

public interface IUserDao<T> {
    /**
     * 查询全部用户
     */
    List<T> findAll();

    void insert (User user);

    //获取插入后的主键
    void insertGetPrimaryKey(User user);
    //获取插入后的主键
    void insertGetPrimaryKey02(User user);

    void update(User user);

    void delete(int id);

//    按照姓名、id范围查找用户,不可sql注入
    List<T> findByCondition(QueryUserCondition queryUserCondition);

    //    按照姓名模糊查找用户,可sql注入
    List<T> findByCondition02(String userName);
}
