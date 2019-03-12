import com.config.ConfigClass;
import com.dao.IUserDao;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConfigClass.class)
public class Test {


    //法1
    @Autowired
    private IUserDao userDao_anno;



    @Before
    public void before(){


        //方法2
//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ConfigClass.class);
//        userDao_anno = annotationConfigApplicationContext.getBean("userDao",IUserDao.class);

    }

    @org.junit.Test

    public void Test(){
        userDao_anno.insert();
    }
}
