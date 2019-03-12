package com.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@ComponentScan("com")//开启注解扫描
@EnableAspectJAutoProxy//开启自动代理
@Aspect//指定当前类为切面
@Component//创建对象
public class UserLog {
    @Pointcut("execution(* com..*UserDao.*(..))")
    public void pt(){ }

    @Before("pt()")
    public void before (){
        System.out.println("[前置]！");
    }
    @AfterReturning("pt()")
    public void after(){
        System.out.println("[后置]！");
    }
    @AfterThrowing("pt()")
    public void exeption(){
        System.out.println("[异常]！");
    }
    @After("pt()")
    public void end(){
        System.out.println("[执行完毕]！");
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint pjp){
        try{
            System.out.println("[前置]");
            Object proceed = pjp.proceed(pjp.getArgs());
            System.out.println("后置");
            return proceed;
        } catch (Throwable throwable) {
            System.out.println("[异常]");
            return null;
        } finally {
            System.out.println("[完毕]");
        }

    }
}
