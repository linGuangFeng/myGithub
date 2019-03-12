
import dao.IUserDao;
import dao.impl.UserDaoImpl;
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
    //实现类对象
    UserDaoImpl userDaoImpl;


    @Before
    public void before() throws Exception {
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFaction = sqlSessionFactoryBuilder.build(is);
        //事务默认手动提交
        sqlSession = sqlSessionFaction.openSession();
       //创建实现类对象
        userDaoImpl = new UserDaoImpl(sqlSession);
    }

    @After
    public void after() throws Exception {
        is.close();
        sqlSession.close();
    }



    @org.junit.Test
    public void findAll(){
        userDaoImpl.findAll();
    }

    @org.junit.Test
    public void insert(){
        User user = new User();
        user.setUserName("测试");
        user.setPassword("1234");
        userDaoImpl.insert(user);
    }

    @org.junit.Test
    //插入数据后获取主键值
    public void insertGetPrimaryKey(){
        User user = new User();
        user.setUserName("获取插入后得到的主键值");
        user.setPassword("1234");
        userDaoImpl.insert(user);
    }

    @org.junit.Test
    //插入数据后获取主键值
    public void insertGetPrimaryKey02(){
        User user = new User();
        user.setUserName("获取插入后得到的主键值");
        user.setPassword("1234");
       userDaoImpl.insert(user);
    }

    @org.junit.Test
    //更新数据
    public void update(){
        User user = new User();
        user.setUserName("数据更新了");
        user.setPassword("密码变了");
        user.setId(10);
        userDaoImpl.update(user);
    }

    @org.junit.Test
    //删除一条数据
    public void delete() {
        userDaoImpl.delete(3);
    }

    @org.junit.Test
    //根据姓名与id范围搜索用户
   public void findByCondition(){
        //创建搜索条件对象,并输入搜索条件
        QueryUserCondition queryUserCondition =
                new QueryUserCondition(new User("%a%"),1,10);
        userDaoImpl.findByCondition(queryUserCondition);
    }

    @org.junit.Test
    //根据姓名与id范围搜索用户
    //可sql注入
    public void findByCondition02(){
//        userDaoImpl.findByCondition02("a");
        userDaoImpl.findByCondition02("a' or 1=1; -- ");
    }

}
