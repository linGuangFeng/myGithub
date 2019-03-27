package com.ec.service;

import com.ec.pojo.seller.Seller;
import com.ec.service.seller.ISellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class UserDetailsServiceImpl implements UserDetailsService {

//    配置文件注入
    private ISellerService sellerService;

    public ISellerService getSellerService() {
        return sellerService;
    }

    public void setSellerService(ISellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//       根据用户名查找用户
        Seller seller = sellerService.findOne(username);
        if (seller !=null && seller.getStatus().equals("1")) {//判断用户审核是否通过
//            返回用户名，用户密码，认证的角色信息
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
            return new User(seller.getSellerId(),seller.getPassword(),authorities);
        }
        return null;
    }
}
