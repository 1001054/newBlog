package com.xj.controller;

import com.xj.model.params.PageParam;
import com.xj.pojo.Admin;
import com.xj.pojo.Permission;
import com.xj.service.AdminService;
import com.xj.service.PermissionService;
import com.xj.service.SecurityUserService;
import com.xj.vo.PageResult;
import com.xj.vo.Result;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private AdminService adminService;

    @PostMapping("info")
    public String info(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken){
            return "默认用户";
        }
        return authentication.getName();
    }

    @PostMapping("adminList")
    public Result listAdmin(@RequestBody PageParam pageParam){
        return adminService.listAdmin(pageParam);
    }

    @PostMapping("update")
    public Result adminUpdate(@RequestBody Admin admin){
        return adminService.adminUpdate(admin);
    }

    @PostMapping("add")
    public Result adminAdd(@RequestBody Admin admin){
        return adminService.adminAdd(admin);
    }

    @GetMapping("delete/{id}")
    public Result adminDelete(@PathVariable("id") Long id){
        return adminService.adminDelete(id);
    }

    @PostMapping("permission/permissionList")
    public Result listPermission(@RequestBody PageParam pageParam){
        return permissionService.listPermission(pageParam);
    }
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
