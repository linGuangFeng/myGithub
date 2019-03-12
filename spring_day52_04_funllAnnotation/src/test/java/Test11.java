import com.factory.SpringConfiguration;
import com.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

// SpringJUnit4ClassRunner 表示spring提供的创建容器的工具类
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置管理类
@ContextConfiguration(classes = SpringConfiguration.class)
public class Test11 {


   /* @Autowired
    IAccountService iAccountService;*/


    @Autowired
    private DataSource dataSource;
    @Test
    public void findAll(){
        System.out.println(dataSource);
        //System.out.println(iAccountService.findAll());

    }
}
