package entity.对象范围与相关生命周期;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
@Scope("singleton")
public class Life02 {


    @PostConstruct
    public void init(){
        System.out.println("Life02_单列_未延时初始化");
    }


    @PreDestroy
    public void distroy(){
        System.out.println("Life02_单列_已销毁");
    }
}
