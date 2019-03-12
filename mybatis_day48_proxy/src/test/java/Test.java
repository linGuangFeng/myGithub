
import dao.IUserDao;
import model.QueryUserCondition;
import model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Test {

    InputStream is;
    SqlSession sqlSession;
    //代理对象
    IUserDao iUserDao;


    @Before
    public void before() throws Exception {
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFaction = sqlSessionFactoryBuilder.build(is);
        //事务默认手动提交
        sqlSession = sqlSessionFaction.openSession();
        //设置成自动提交，但不推荐使用
//        sqlSession = sqlSessionFaction.openSession(true);
        iUserDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void after() throws Exception {
        is.close();
        sqlSession.close();
    }



    @org.junit.Test
    public void findAll(){
        List<User> users = iUserDao.findAll();
        users.forEach(System.out::println);
    }

    @org.junit.Test
    public void insert(){
        User user = new User();
        user.setUserName("测试");
        user.setPassword("1234");
        iUserDao.insert(user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @org.junit.Test
    //插入数据后获取主键值
    public void insertGetPrimaryKey(){
        User user = new User();
        user.setUserName("获取插入后得到的主键值");
        user.setPassword("1234");
        System.out.println("保存前" + user);
        iUserDao.insertGetPrimaryKey(user);
        System.out.println("保存后" + user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @org.junit.Test
    //插入数据后获取主键值
    public void insertGetPrimaryKey02(){
        User user = new User();
        user.setUserName("获取插入后得到的主键值");
        user.setPassword("1234");
        System.out.println("保存前" + user);
        iUserDao.insertGetPrimaryKey02(user);
        System.out.println("保存后" + user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();

    }

    @org.junit.Test
    //更新数据
    public void update(){
        User user = new User();
        user.setUserName("数据更新了");
        user.setPassword("密码变了");
        user.setId(10);
        iUserDao.update(user);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @org.junit.Test
    //删除一条数据
    public void delete(){

        iUserDao.delete(10);
        //对表进行更改，需要手动提交事务
        sqlSession.commit();
    }

    @org.junit.Test
    //根据姓名与id范围搜索用户
   public void findByCondition(){
        //创建搜索条件对象,并输入搜索条件
        QueryUserCondition queryUserCondition =
                new QueryUserCondition(new User("%a%"),1,10);
        //查询并遍历
        List<User> users = iUserDao.findByCondition(queryUserCondition);
        users.forEach(System.out::println);
    }
    @org.junit.Test
    //根据姓名与id范围搜索用户
    //可sql注入
    public void findByCondition02(){
        //查询并遍历
//        List<User> users = iUserDao.findByCondition02("a");
        //sql注入
        List<User> users = iUserDao.findByCondition02("a' or 1=1; -- ");
        users.forEach(System.out::println);
    }

}
