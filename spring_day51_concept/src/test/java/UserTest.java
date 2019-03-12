import com.dao.IUserDao;
import com.dao.factory.DaoFactory;
import com.dao.factory.DaoFactory02;
import com.dao.impl.UserDaoImpl_mysql;
import org.junit.Test;

public class UserTest {
    static String resourcePath = "com.dao.IUserDao";
    static String key = "IUserDao";
    Class clazz = IUserDao.class;



    @Test
    public void insert(){
        IUserDao iUserDao = (IUserDao) DaoFactory.getUserDaoImpl(resourcePath, key);
        iUserDao.insert();
    }
    @Test
    public void insert02(){
        IUserDao iUserDao = DaoFactory02.getUserDaoImpl(resourcePath, key,IUserDao.class);
        iUserDao.insert();
    }

    @Test
    public void test(){
        System.out.println(UserDaoImpl_mysql.class.getName());
    }
}
