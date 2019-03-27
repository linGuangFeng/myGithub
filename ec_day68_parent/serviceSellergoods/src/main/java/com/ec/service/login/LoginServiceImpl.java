package com.ec.service.login;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class LoginServiceImpl implements ILoginService {


    @Override
    public String showName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
