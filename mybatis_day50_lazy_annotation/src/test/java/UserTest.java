import dao.IUserDao;
import entity.FindUserByCondition;
import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jws.soap.SOAPBinding;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserTest {
    InputStream is;
    SqlSession sqlSession;
    IUserDao iUserDao;

    @Before
    public void before() throws Exception{
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        sqlSession = sqlSessionFactory.openSession();
        iUserDao = sqlSession.getMapper(IUserDao.class);
    }

    @After
    public void after() throws Exception{
        is.close();
    }

    /***
     * 延时加载练习
     * @return
     */
    @org.junit.Test
    public void findAll() throws Exception{
        List<User> users = iUserDao.findAll();
        for (User user:users) {
            System.out.println("用户：" + user);
            System.out.println("账户：" + user.getAccounts());
            System.out.println("================");
        }
    }

    @org.junit.Test
    public void findById() throws Exception{
        System.out.println(iUserDao.findById(43));
    }



    /**
     *注解练习
     */

    //查找所有用户
    @Test
    public void findAllAnno(){
        List<User> users = iUserDao.findAllAnno();
        System.out.println("共" + users.size() + "条记录");
        users.forEach(System.out::println);

    }

    //插入数据
    @Test
    public void insertAnno_01(){
        User user = new User();
        user.setUsername("插入法1");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setAddress("广东");
        if (iUserDao.insertAnno_01(user)) System.out.println("插入成功");
        else System.out.println("插入失败！");
        sqlSession.commit();
    }

    //插入数据
    @Test
    public void insertAnno_02(){
        User user = new User();
        user.setUsername("插入法1");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setAddress("广东");
        if (iUserDao.insertAnno_02("插入法2",new Date(),"男","深圳")) System.out.println("插入成功");
        else System.out.println("插入失败！");
        sqlSession.commit();
    }

    //查找所有用户
    @Test
    public void findUserLimitAnno(){
        List<User> users = iUserDao.findUserLimitAnno(2,2);
        System.out.println("共" + users.size() + "条记录");
        users.forEach(System.out::println);

    }

    @Test
    public void findUserByConditionAnno() throws Exception{
        User user = new User();
        user.setUsername("%王%");
        FindUserByCondition findUserByCondition = new FindUserByCondition();
        findUserByCondition.setUser(user);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      /*  findUserByCondition.setStart(simpleDateFormat.parse("2019-2-1"));
        findUserByCondition.setEnd(simpleDateFormat.parse("2019-3-1"));*/


        List<User> users = iUserDao.findUserByConditionAnno(findUserByCondition);
        System.out.println("共" + users.size() + "条记录");
        users.forEach(System.out::println);
    }

    @Test
    public void findAllAnno_01(){
        List<User> users = iUserDao.findAllAnno_01(46);
        System.out.println(users);
        for (User user : users){
            System.out.println("用户：" + user);
            System.out.println("账户：" + user.getAccounts());
            System.out.println("------------------------");
        }
    }
}
