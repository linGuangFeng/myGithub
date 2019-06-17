package demo.valid.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PersonScope {
    String id;
    @NotNull(message = "用户名不为空")
    String username;
    @NotNull(message = "密码不为空")
    String password;
    String age;
    String number;



}
