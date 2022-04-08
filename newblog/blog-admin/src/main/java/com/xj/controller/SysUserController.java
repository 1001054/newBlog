package com.xj.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xj.mapper.SysUserMapper;
import com.xj.model.params.PageParam;
import com.xj.pojo.SysUser;
import com.xj.service.SysUserService;
import com.xj.vo.PageResult;
import com.xj.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @PostMapping("list")
    public Result userList(@RequestBody PageParam pageParam){
        return sysUserService.userList(pageParam);
    }

    @PostMapping("update")
    public Result userUpdate(@RequestBody SysUser sysUser){
        return sysUserService.userUpdate(sysUser);
    }

    @PostMapping("add")
    public Result userAdd(@RequestBody SysUser sysUser){
        return sysUserService.userAdd(sysUser);
    }

    @GetMapping("delete/{id}")
    public Result userDelete(@PathVariable("id") Long id){
        return sysUserService.userDelete(id);
    }
}
