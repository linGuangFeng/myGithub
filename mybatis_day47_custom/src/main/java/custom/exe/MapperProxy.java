package custom.exe;

import model.User;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 事件处理程序：执行代理方法时候，会触发这里的invoke()方法
 */
public class MapperProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 当前执行的方法名称: findAll
        String methodName = method.getName();
        //当前类全名
        String fulllClassName = method.getDeclaringClass().getName();
        if ("findAll".equals(methodName)){
            // 执行查询: 数据先写死
            User user = new User();
            user.setId(100);
            user.setUserName("球球");
            user.setPassword("123456");
            List<User> list = new ArrayList<>();
            list.add(user);
            return list;
        }
        return null;
    }
}
