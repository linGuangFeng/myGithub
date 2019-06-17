package demo.valid.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalException {
    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public String validationBodyException(BindingResult bindingResult) {
        StringBuffer sb = new StringBuffer();
        ObjectError objectError = bindingResult.getAllErrors().get(0);
        sb.append(((FieldError) objectError).getField() + " : ").append(objectError.getDefaultMessage());
        return "    法2：" + sb.toString();
    }


}
