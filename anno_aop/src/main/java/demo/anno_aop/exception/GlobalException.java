package demo.anno_aop.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public String validationBodyException() {
        return "注解拦截测试成功！";
    }


}
