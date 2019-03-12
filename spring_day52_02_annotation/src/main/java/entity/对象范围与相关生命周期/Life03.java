package entity.对象范围与相关生命周期;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
@Scope("prototype")
public class Life03 {


    @PostConstruct
    public void init(){
        System.out.println("Life03_多列_初始化");
    }


    @PreDestroy
    public void distroy(){
        System.out.println("Life03_多列_已销毁");
    }
}
