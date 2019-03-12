import com.jdkProxy.dao.IStar;
import com.jdkProxy.dao.impl.ZhouJieLun;
import com.jdkProxy.dao.proxy.ProxyFactory;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 需求：对目标对象，生成代理对象
 * 原理：
 * 1. 利用cglib生成, Enhancer.create(..)
 * 2. 运行时期动态生成字节码对象
 *    class $CGLIB123 extends LiuDeHuaStar
 *
 */
public class Test {



    @org.junit.Test
    public void jdkProxy(){

        //获取ZhouJieLun的代理对象
        IStar zhouJieLun = new ZhouJieLun();
        IStar zhouJieLun1Proxy =(IStar) Proxy.newProxyInstance(
                Test.class.getClassLoader(),
                zhouJieLun.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        double i = (double) args[0];
                        if (i > 10000.0) return method.invoke(zhouJieLun, args);
                        return  null;
                    }
                }
        );
        zhouJieLun1Proxy.dance(100);


        IStar proxy = ProxyFactory.getProxy(zhouJieLun);
        proxy.sing(1000000);
    }

    // 参数1：代理对象
    // 参数2：表示当前执行的方法的Method对象
    // 参数3：方法的参数
    // 参数4：表示代理对象中的方法的代理
    @org.junit.Test
    public void cglibProxy(){
        ZhouJieLun zhouJieLun = new ZhouJieLun();
        ZhouJieLun zhouJieLunProxy  =(ZhouJieLun) Enhancer.create(ZhouJieLun.class,
                new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                double money = (double) objects[0];
                if (money >= 10000) return method.invoke(zhouJieLun, objects);
                return null;
            }
        });


        zhouJieLunProxy.dance(1000);
        zhouJieLunProxy.sing(10000);

    }


}
