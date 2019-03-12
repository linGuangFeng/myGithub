import com.xml.service.IUserService;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    IUserService iUserService;
    @Before
    public void before(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
        iUserService = classPathXmlApplicationContext.getBean("iUserService",IUserService.class);
    }

    @org.junit.Test
    public void Test(){
//        编程式事务
//        iUserService.insertRow();

//        声明式事务
        iUserService.insertMethod();
    }
}
