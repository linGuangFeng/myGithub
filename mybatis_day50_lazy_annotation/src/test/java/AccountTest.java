import dao.IAccountDao;
import dao.IUserDao;
import entity.Account;
import entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class AccountTest {
    InputStream is;
    SqlSession sqlSession;
    IAccountDao iAccountDao;

    @Before
    public void before() throws Exception{
        is = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
        sqlSession = sqlSessionFactory.openSession();
        iAccountDao = sqlSession.getMapper(IAccountDao.class);
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
        List<Account> accounts = iAccountDao.findAll();
        for (Account account : accounts) {
            System.out.println("账户：" + account);
            System.out.println("用户：" + account.getUser());
            System.out.println("================");
        }
    }

    @org.junit.Test
    public void findById() throws Exception{
        System.out.println(iAccountDao.findById(1));
    }



    /**
     *注解练习
     */
    @Test
    public void findAllAnno() throws Exception{
        List<Account> accounts = iAccountDao.findAllAnno_01();
        for (Account account : accounts) {
            System.out.println("账户：" + account);
            System.out.println("用户：" + account.getUser());
            System.out.println("================");
        }

    }
}
