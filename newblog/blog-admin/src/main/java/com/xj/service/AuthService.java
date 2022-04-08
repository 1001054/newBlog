package com.xj.service;

import com.xj.pojo.Admin;
import com.xj.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private AdminService adminService;

    public boolean auth(HttpServletRequest request, Authentication authentication ){
        //获取请求路径
        String requestURI = request.getRequestURI();
        //获取用户信息
        Object principal = authentication.getPrincipal();
        if(principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if(admin == null){
            //用户名不存在
            return false;
        }
        Long id = admin.getId();
        //如果是root用户(id为1)就不查看权限了
        if(1 == id){
            //超级管理员
            return true;
        }
        List<Permission> permissions = adminService.findPermissionsByAdminId(id);
        //逐一辨别请求路径是否在该用户的权限中
        requestURI = StringUtils.split(requestURI, '?')[0];
        for (Permission permission : permissions) {
            if(requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }
}
