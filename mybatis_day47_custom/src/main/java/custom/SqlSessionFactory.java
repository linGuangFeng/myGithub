package custom;

import custom.exe.DefaultSqlSession;

public class SqlSessionFactory {
    public SqlSession openSession(){
        return new DefaultSqlSession();
    }
}
