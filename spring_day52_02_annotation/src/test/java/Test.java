import entity.dataInto.DataInto01;
import entity.dataInto.DataInto02;
import entity.dataInto.DataInto03;
import entity.dataInto.DataInto04;
import entity.对象范围与相关生命周期.Life01;
import entity.对象范围与相关生命周期.Life02;
import entity.对象范围与相关生命周期.Life03;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    ClassPathXmlApplicationContext classPathXmlApplicationContext;


    @org.junit.Test
    public void test(){
        dataIntoTest();
        life();
    }

    @org.junit.Test
    public void dataIntoTest(){
        System.out.println("===============数据注入==================");
        DataInto01 dataInto01 = classPathXmlApplicationContext.getBean("dataInto01", DataInto01.class);
        DataInto02 dataInto02 = classPathXmlApplicationContext.getBean("dataInto02", DataInto02.class);
        DataInto03 dataInto03 = classPathXmlApplicationContext.getBean("dataInto03", DataInto03.class);
        DataInto04 dataInto04 = classPathXmlApplicationContext.getBean("dataInto04", DataInto04.class);

        System.out.println("数据01_@AutoWired的使用：" + dataInto01);
        System.out.println("数据02_@Qualifier配合@AutoWired的使用：" + dataInto02);
        System.out.println("数据03_@Resourcr的使用：" + dataInto03);
        System.out.println("数据04_@Value的使用：" + dataInto04);

    }

    @org.junit.Test
    public void life(){
        System.out.println("===============对象范围及相关生命周期==================");
        Life01 life01 = classPathXmlApplicationContext.getBean("life01", Life01.class);
        Life02 life02 = classPathXmlApplicationContext.getBean("life02", Life02.class);
        Life03 life03 = classPathXmlApplicationContext.getBean("life03", Life03.class);
    }


    @After
    public void after(){
        classPathXmlApplicationContext.close();
    }

    @Before
    public void before(){
        System.out.println("===============创建对象==================");
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("bean.xml");
    }


}
