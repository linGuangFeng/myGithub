import dao.IUserDao;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.InputStream;
import java.util.List;

public class Test {

    @org.junit.Test
    public void findAll() throws Exception {
        //1.获取配置文件的文件流
        InputStream fis = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSession工厂构造器
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        //3.根据工厂构造器，创建SqlSession工厂
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(fis);
        //4.根据SqlSession工厂，创建SqlSession对象
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //5.对dao的接口生成代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //6.使用代理对象执行接口的方法
        List list = userDao.findAll();
        list.forEach(System.out::println);
        //7.释放资源
        sqlSession.close();
        fis.close();
    }
}
