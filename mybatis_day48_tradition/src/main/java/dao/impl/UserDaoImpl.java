package dao.impl;

import dao.IUserDao;
import model.QueryUserCondition;
import model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements IUserDao {
    String iPath = "dao.IUserDao.";
    SqlSession sqlSession;

    public UserDaoImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List findAll() {
        // 参数1: 配置文件种的sql语句，通过namespace+id查找
        // 参数2：传入的值
        List<User> users = sqlSession.selectList(iPath +"findAll");
        users.forEach(System.out::println);
        return users;
    }

    @Override
    public void insert(User user) {
        sqlSession.insert(iPath + "insert",user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @Override
    public void insertGetPrimaryKey(User user) {
        System.out.println("保存前" + user);
        sqlSession.insert(iPath + "insertGetPrimaryKey",user);
        System.out.println("保存后" + user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @Override
    public void insertGetPrimaryKey02(User user) {
        System.out.println("保存前" + user);
        sqlSession.insert(iPath + "insertGetPrimaryKey02",user);
        System.out.println("保存后" + user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @Override
    public void update(User user) {
        user.setUserName("数据更新了");
        user.setPassword("密码变了");
        user.setId(10);
        sqlSession.update(iPath + "update",user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @Override
    public void delete(int id) {

        sqlSession.delete(iPath+ "delete",id);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @Override
    public List findByCondition(QueryUserCondition queryUserCondition) {
        //查询并遍历
        List<User> users = sqlSession.selectList(iPath + "finByCondition");
        users.forEach(System.out::println);
        return users;
    }

    @Override
    public List findByCondition02(String userName) {
        //查询并遍历
//        List<User> users = iUserDao.findByCondition02("a");
        //sql注入
        List<User> users = sqlSession.selectList(iPath +"findByCondition02",userName);
        users.forEach(System.out::println);
        return users;
    }
}
