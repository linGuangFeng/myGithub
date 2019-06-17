package demo.valid.controller;


import demo.valid.entity.PersonScope;
import demo.valid.valid.PersonalValidtor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

/**
 * 全局异常获取参考地址：
 * https://www.jianshu.com/p/10172512bd48
 */
@RestController
public class TestController {


    /**
     * 直接获取校验结果
     * @param personScope
     * @param bindingResult
     * @return
     */
    @GetMapping(value = "method_01")
    public Object method_01(@Valid PersonScope personScope, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            sb.append(((FieldError)objectError).getField() +" : ").append(objectError.getDefaultMessage());
            return "    法1" + sb.toString();
        }else{
            return "成功!";
        }
    }

    /**
     * 全局捕获异常方法，不能有BindingResult对象
     * @param personScope
     * @return
     */
    @GetMapping(value = "method_02")
    public Object method_02(@Valid PersonScope personScope){
        return "成功!";
    }

    //todo 查百度
    @InitBinder
    public void initBinder(DataBinder binder) {
        binder.setValidator(new PersonalValidtor());
    }
    @GetMapping(value = "method_03")
    public Object method_03(DataBinder binder, BindingResult bindingResult) {
        return "成功！";
    }

    @GetMapping(value = "method_04")
    public Object method_04(@Validated PersonScope personScope, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuffer sb = new StringBuffer();
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            sb.append(((FieldError)objectError).getField() +" : ").append(objectError.getDefaultMessage());
            return "    法4" + sb.toString();
        }else{
            return "成功!";
        }
    }

}
