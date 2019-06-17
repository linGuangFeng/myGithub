package demo.valid.valid;


import demo.valid.entity.PersonScope;
import org.springframework.util.StringUtils;
import org.springframework.validation.*;

/**
 * Created by wb-zhangkenan on 2016/9/2.
 */
public class PersonalValidtor implements Validator{

    /**
     * 判断支持的JavaBean类型
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return PersonScope.class.equals(aClass);
    }

    /**
     * 实现Validator中的validate接口
     * @param obj
     * @param errors
     */
    @Override
    public void validate(Object obj, Errors errors) {
        //把校验信息注册到Error的实现类里
        errors.getAllErrors();
        ValidationUtils.rejectIfEmpty(errors,"username",null,"姓名不能为空!");
        PersonScope personScope = (PersonScope) obj;
        if(StringUtils.isEmpty(personScope.getPassword())){
            errors.rejectValue("password",null,"密码不为空!!!!");
        }
      /*  ObjectError objectError = errors.getAllErrors().get(0);
        StringBuilder sb = new StringBuilder();
        sb.append(((FieldError)objectError).getField() +" : ").append(objectError.getDefaultMessage());
        errors.rejectValue("    法2：" + ((FieldError)objectError).getField() +" : ",objectError.getDefaultMessage());*/
    }
}
