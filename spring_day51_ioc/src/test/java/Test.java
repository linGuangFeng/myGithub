import com.dao.IUserDao;
import entity.CollectionBean;
import entity.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class Test {


    //加载配置文件，得到对应的实现类，基础练习
    @org.junit.Test
    public void useTest(){
        /**
         * |-- BeanFactory 容器的顶层接口; 创建容器时候默认不创建单例的对象，使用时候才创建。
         *   |-- ApplicationContext 子接口； 创建容器时候默认就创建单例的对象。
         *      |-- ClassPathXmlApplicationContext 加载类路径下的容器配置文件
         *      |-- FileSystemXmlApplicationContext 加载文件系统下的容器配置文件
         *      |-- AnnotationConfigApplicationContext 注解开发时候创建容器
         *     |-- WebApplicationContext web项目中创建容器的接口
         * 通过BeanFactory创建容器
         */
//        ApplicationContext configFileClass = new ClassPathXmlApplicationContext("spring.xml");
//        ApplicationContext configFileClass = new FileSystemXmlApplicationContext("E:\\IDEA\\program\\spring\\spring_day51_ioc\\src\\main\\resources\\spring.xml");
        Resource resource = new ClassPathResource("spring.xml");
        BeanFactory configFileClass = new XmlBeanFactory(resource);

        IUserDao iUserDao1 = (IUserDao) configFileClass.getBean("mysql");
        IUserDao iUserDao2 = (IUserDao) configFileClass.getBean("oracle");
        iUserDao1.database();
        iUserDao2.database();
    }




    //scope属性单列与多列测试
    @org.junit.Test
    public void scop(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        System.out.println("开始生成实现类");

        IUserDao singleton1 = applicationContext.getBean("singleton",IUserDao.class);
        IUserDao singleton2 = (IUserDao)applicationContext.getBean("singleton");

        IUserDao prototype1 = (IUserDao)applicationContext.getBean("prototype");
        IUserDao prototype2 = (IUserDao)applicationContext.getBean("prototype");


        System.out.println("开始获取单列");
        System.out.println("单列地址：" + singleton1);
        System.out.println("单列地址：" + singleton2);
        System.out.println("========================================================");
        System.out.println("开始获取多列");
        System.out.println("多列地址：" + prototype1);
        System.out.println("多列地址：" + prototype2);

         ((ClassPathXmlApplicationContext) applicationContext).close();

    }


    @org.junit.Test
    public void callStatic(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
//        User mysql = classPathXmlApplicationContext.getBean("mysql_static",User.class);
//        User oracle = classPathXmlApplicationContext.getBean("oracle_static",User.class);
    }

    @org.junit.Test
    public void callInstance(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
//        User mysql = classPathXmlApplicationContext.getBean("mysql_instance",User.class);
//        User oracle = classPathXmlApplicationContext.getBean("oracle_instance",User.class);
    }



//    普通数据封装测试
    @org.junit.Test
    public void beanData(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User ref = classPathXmlApplicationContext.getBean("ref", User.class);
        User name = classPathXmlApplicationContext.getBean("name", User.class);
        User index = classPathXmlApplicationContext.getBean("index", User.class);
        User type = classPathXmlApplicationContext.getBean("type", User.class);
        User p = classPathXmlApplicationContext.getBean("p", User.class);
        System.out.println(ref);
        System.out.println("=======");
        System.out.println(name);
        System.out.println("=======");
        System.out.println(index);
        System.out.println("=======");
        System.out.println(type);
        System.out.println("=======");
        System.out.println(p);
        System.out.println("=======");
    }


//    集合数据封装测试
    @org.junit.Test
    public void collectionBean(){
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring.xml");
        CollectionBean collectionBean = classPathXmlApplicationContext.getBean("collectionBean", CollectionBean.class);
        System.out.println(collectionBean);
    }
}
