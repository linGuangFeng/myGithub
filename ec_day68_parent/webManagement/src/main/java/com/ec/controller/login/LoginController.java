package com.ec.controller.login;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ec.pojo.resutl.Result;
import com.ec.pojo.user.User;
import com.ec.service.login.ILoginService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("login")
public class LoginController {

    @Reference
    ILoginService loginService;
    /**
     * 页面展示用户名
     * @return
     */
    @RequestMapping("showName")
    public User showName() {
        User user = new User();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        user.setUsername(username);
        return user;
    }
}
