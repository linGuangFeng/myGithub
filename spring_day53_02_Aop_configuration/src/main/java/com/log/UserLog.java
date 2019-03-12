package com.log;

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
}
