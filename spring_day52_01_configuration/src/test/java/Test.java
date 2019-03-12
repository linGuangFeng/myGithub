import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.service.IAccountService;

public class Test {


    IAccountService iAccountService;

    ClassPathXmlApplicationContext classPathXmlApplicationContext;
    @Before
    public void before(){
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml" );
    }

    @org.junit.Test
    public void findAll(){

        iAccountService = classPathXmlApplicationContext.getBean("iAccountService",IAccountService.class);
        System.out.println(iAccountService.findAll());

    }
}
