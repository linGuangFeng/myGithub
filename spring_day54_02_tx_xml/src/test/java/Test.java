import com.dao.IUserDao;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    IUserDao userDao;

    @Before
    public void before(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
        userDao = classPathXmlApplicationContext.getBean("userDao",IUserDao.class);

    }

    @org.junit.Test
    public void Test(){
        userDao.insert();
    }
}
