package com.jdkProxy.dao.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ProxyFactory {
    public static <T> T getProxy(T target) {

        try {

            return (T) Proxy.newProxyInstance(
                    target.getClass().getClassLoader(),
                    target.getClass().getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            double money = (double)args[0];
                            if (money >10000) return method.invoke(target,args);
                            return null;
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
