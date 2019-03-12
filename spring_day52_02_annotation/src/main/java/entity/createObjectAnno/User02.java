package entity.createObjectAnno;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/*
@Component        创建对象，加入容器.
@Controller		一般用于表现层的注解。 用法同@Component
@Service		一般用于业务层的注解。 用法同@Component
@Repository       一般用于持久层的注解。  用法同@Component
* */
@Service
public class User02 {
    public User02(){
        System.out.println("Service注解创建对象！！！");
    }
}

