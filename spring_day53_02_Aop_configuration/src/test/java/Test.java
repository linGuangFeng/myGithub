import com.dao.IUserDao;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import org.junit.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    ClassPathXmlApplicationContext classPathXmlApplicationContext;
    IUserDao iUserDao;
    UserDao userDao;

    @Before
    public void before(){
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
        //实现类代理测试
        iUserDao = classPathXmlApplicationContext.getBean("IUserDao", IUserDao.class);
        //类代理测试
        userDao = classPathXmlApplicationContext.getBean("UserDao",UserDao.class);

    }
    @org.junit.Test
    public void Test(){

        System.out.println("=======实现类代理测试========");
        iUserDao.save();
        System.out.println("=======类代理测试========");
        userDao.save();

    }
}
