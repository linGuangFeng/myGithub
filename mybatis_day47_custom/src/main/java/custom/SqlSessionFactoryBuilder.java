package custom;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {
    public SqlSessionFactoryBuilder() {
    }


    //工厂接口创建SqlSessionFactory接口
    public SqlSessionFactory build(InputStream fis){
        return new SqlSessionFactory();
    }
}
