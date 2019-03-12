import dao.IUserDao;
import entity.Account;
import entity.FindUserByCondition;
import entity.Role;
import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Test {

    InputStream is;
    SqlSession sqlSession ;
    IUserDao iUserDao;

    @Before
    public void before() throws Exception{
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        sqlSession = sqlSessionFactoryBuilder.build(is).openSession();
        iUserDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void after() throws Exception{
        sqlSession.close();
        is.close();
    }


    @org.junit.Test
    public void whereExe() throws Exception{

        //查询条件
        User user = new User();
        user.setUsername("%王%");
        FindUserByCondition findUserByCondition = new FindUserByCondition();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date start = simpleDateFormat.parse("2018-2-1");
        Date end = simpleDateFormat.parse("2018-3-1");

        //查询条件写入
        findUserByCondition.setUser(user);
        findUserByCondition.setEnd(end);
        findUserByCondition.setStart(start);

        //开始查询并遍历结果
        List<User> users = iUserDao.whereExe(findUserByCondition);
        users.forEach(System.out::println);
    }

    @org.junit.Test
    public void findAll()  throws Exception{
        List<User> users = iUserDao.findAll();
        users.forEach(System.out::println);
    }

    @org.junit.Test
    public void setExe()  throws Exception{
        User user = new User();
        user.setId(42);
        user.setSex("男");
//        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse("2018-3-30"));
        iUserDao.setExe(user);
        sqlSession.commit();
    }

    @org.junit.Test
    public void foreachExe() throws Exception{

        List<Integer> ids = new ArrayList<>();
        Collections.addAll(ids,41,42,43);
        FindUserByCondition findUserByCondition = new FindUserByCondition();
        findUserByCondition.setIds(ids);
        List<User> users = iUserDao.foreachExe(findUserByCondition);
        users.forEach(System.out::println);
    }

    //一对一练习法1
    @org.junit.Test
    public void oneOne() throws Exception{

        List<Account> accounts = iUserDao.oneOne();
        accounts.forEach(System.out::println);
    }

    //一对一练习法2
    @org.junit.Test
    public void oneOne02() throws Exception{

        List<Account> accounts = iUserDao.oneOne02();
        accounts.forEach(System.out::println);
    }

    //一对多练习
    @org.junit.Test
    public void oneMore() throws Exception{

        List<User> users = iUserDao.oneMore();
        users.forEach(System.out::println);
    }

    //对对多练习
    @org.junit.Test
    public void moreMore() throws Exception{
        List<User> users = iUserDao.moreMore();
        users.forEach(System.out::println);
    }
    @org.junit.Test
    public void moreMore_() throws Exception{
        List<Role> roles = iUserDao.moreMore_();
        roles.forEach(System.out::println);
    }
}
