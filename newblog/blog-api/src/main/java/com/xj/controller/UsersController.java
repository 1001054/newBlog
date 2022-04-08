package com.xj.controller;

import com.xj.pojo.SysUser;
import com.xj.service.SysUserService;
import com.xj.vo.ErrorCode;
import com.xj.vo.LoginUserVo;
import com.xj.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){
        SysUser user = sysUserService.findUserByToken(token);
        if(user == null){
            return Result.fail(ErrorCode.TOKEN_ERROR);
        }
        LoginUserVo loginUserVo = new LoginUserVo(user);
        return Result.success(loginUserVo);
    }
}
