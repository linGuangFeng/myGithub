package custom;

public interface SqlSession {


    //生成代理对象
     <T>T getMapper(Class<T> daoType) ;

      void close();
}
