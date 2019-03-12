package entity.createObjectAnno;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/*
@Component        创建对象，加入容器.
@Controller		一般用于表现层的注解。 用法同@Component
@Service		一般用于业务层的注解。 用法同@Component
@Repository       一般用于持久层的注解。  用法同@Component
* */
@Controller
public class User04 {
    public User04(){
        System.out.println("Repository注解创建对象！！！");
    }
}
