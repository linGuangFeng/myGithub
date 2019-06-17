package demo.anno_aop.ascept;

import demo.handle_exception.annotion.VisitAnnotion;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.nio.file.AccessDeniedException;

@Aspect
@Component
@AllArgsConstructor
public class TestAop {


    //注解名称引用入参名
    @Around("@annotation(visitAnnotion)")
    public Object around(ProceedingJoinPoint point, VisitAnnotion visitAnnotion) throws Throwable {
        throw new AccessDeniedException("Access is denied");
//        return point.proceed();
    }

}
