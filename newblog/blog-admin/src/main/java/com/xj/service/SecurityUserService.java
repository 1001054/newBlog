package com.xj.service;

import com.xj.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //登录时，会把username 传到这里
        //通过username 查询admin表， 如果username 存在，将密码告诉security
        //如果不存在，返回null，表示失败
        Admin admin = adminService.findAdminByUsername(username);
        if(admin == null){
            return null;
        }
        UserDetails userDetails = new User(username, admin.getPassword(), new ArrayList<>());
        return userDetails;
    }
}
