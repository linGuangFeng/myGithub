package custom.exe;

import custom.SqlSession;

import java.lang.reflect.Proxy;

public class DefaultSqlSession implements SqlSession {
    /**
     * 生成代理对象的实现
     * |--Proxy 提供了生成代理对象的方法
     *   static Object newProxyInstance(
     *      ClassLoader loader, 指定类加载器
     *      Class<?>[] interfaces, 指定代理类实现的接口类型数组
     *      InvocationHandler h    事件处理程序（回调函数）
     *   )
     */
    @Override
    public <T> T getMapper(Class<T> daoType) {
        return (T) Proxy.newProxyInstance(
                daoType.getClassLoader(),
                new Class[]{daoType},
                new MapperProxy()
        );
    }

    @Override
    public void close() {

    }
}
