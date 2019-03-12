package entity.createObjectAnno;

import org.springframework.stereotype.Component;

/*
@Component        创建对象，加入容器.
@Controller		一般用于表现层的注解。 用法同@Component
@Service		一般用于业务层的注解。 用法同@Component
@Repository       一般用于持久层的注解。  用法同@Component
* */
@Component
public class User01 {
    public User01(){
        System.out.println("Component注解创建对象！！！");
    }
}

