import com.dao.IUserDao;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.log.UserLog;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    ApplicationContext classPathXmlApplicationContext;
    IUserDao iUserDao;
    UserDao userDao;

    @Before
    public void before(){
        //使用配置文件加载注解
//        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
        //纯注解
        classPathXmlApplicationContext = new AnnotationConfigApplicationContext(UserLog.class);

        //实现类代理测试
        iUserDao = classPathXmlApplicationContext.getBean("iUserDao", IUserDao.class);
        //类代理测试
        userDao = classPathXmlApplicationContext.getBean("userDao",UserDao.class);

    }
    @org.junit.Test
    public void Test(){

        System.out.println("=======实现类代理测试========");
        iUserDao.save();
        System.out.println("=======类代理测试========");
        userDao.save();
    }
}
