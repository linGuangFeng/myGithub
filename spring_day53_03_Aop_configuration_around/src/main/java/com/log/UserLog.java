package com.log;

import org.aspectj.lang.ProceedingJoinPoint;

public class UserLog {
    public void before (){
        System.out.println("[前置]！");
    }
    public void after(){
        System.out.println("[后置]！");
    }
    public void exeption(){
        System.out.println("[异常]！");
    }
    public void end(){
        System.out.println("[执行完毕]！");
    }


    /**
     * 环绕通知
     * 1. 环绕目标方法执行
     * 2. 可以获取方法信息：方法参数，方法所在类
     * 3. 修改方法参数、修改方法返回值
     */
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        //获取目标
        Object target = pjp.getTarget();
        Object[] args = pjp.getArgs();
        //获取调用方法名，参数及类全名
        String methodName = pjp.getSignature().getName();
        String className = target.getClass().getName();
        System.out.println("当前执行的方法名：" + methodName);
        System.out.println("当前执行的类全名：" + className);

        try{
            System.out.println("【执行目标前】");
            // 执行目标对象方法（放行）, 参数可以传入也可以不传入
            Object ret = pjp.proceed(args);
            System.out.println("【执行目标后】");

        }catch (Exception e){
            System.out.println("【目标执行异常】");
        }finally {
            System.out.println("【执行完毕】");
        }

        //如果方法有返回值，下一行是可以修改方法的返回值的，
        // 方法的返回值是 ret = pjp.proceed(args);
        return null;
    }

}
